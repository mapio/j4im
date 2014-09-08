package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Durata;

import java.util.concurrent.CountDownLatch;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class Sintetizzatore {

	private static final int END_OF_TRACK_MESSAGE = 0x2F;

	public static final int INTENSITA_DEFAULT = 64;
	public static final int DEFAULT_BPM = 120;
		
	private static final Synthesizer synth;
	protected static final Sequencer sequencer;
	private static final MidiChannel[] canali;
	private static int canaliAssegnati;
	private static int bpm = DEFAULT_BPM;
		
	static {
		try {
			synth = MidiSystem.getSynthesizer();
			if ( ! synth.isOpen() ) synth.open();

			sequencer =  MidiSystem.getSequencer();
			if ( ! sequencer.isOpen() ) sequencer.open();
		
			sequencer.getTransmitter().setReceiver( synth.getReceiver() );
		
			canali = synth.getChannels();
			canaliAssegnati = 0;
			
			Runtime.getRuntime().addShutdownHook( new Thread() {
				public void run() {
					synth.close();
					sequencer.close();
					System.err.println( "fine" );
				}
			} );
			
		} catch ( MidiUnavailableException e ) {
			throw new RuntimeException( e );
		}		
	}

	private Sintetizzatore() {} // per impedire la costruzione di una istanza
	
	public static void accendi() throws MidiUnavailableException {}

	public static int bpm() {
		return bpm;
	}
	
	public static void bpm( final int bpm ) {
		if ( bpm < 0 || bpm > 960 ) throw new IllegalArgumentException( "I BPM devono essere compresi tra 1 e 960" );
		Sintetizzatore.bpm = bpm;
		sequencer.setTempoInBPM( bpm );
	}

	public static int assegnaCanale( final String nomeStrumento ) {
		if ( canaliAssegnati >= canali.length ) throw new IllegalStateException( "Il sintetizzatore non supporta più di " + canali.length + " srumenti" );
		for ( Instrument inst : synth.getLoadedInstruments() )
			if ( inst.getName().contains( nomeStrumento ) ) {
				canali[ canaliAssegnati ].programChange( inst.getPatch().getProgram() );
				return canaliAssegnati++;
			}
		throw new IllegalArgumentException( "Il sintetizzatore non è in grado di riprodurre lo strumento " + nomeStrumento );
	}
	
	public static void accendiNota( final int canale, final int pitch, final int intensita ) {
		canali[ canale ].noteOn( pitch, intensita );
	}

	public static void spegniNota( final int canale, final int pitch ) {
		canali[ canale ].noteOff( pitch );		
	}
	
	public static void attendi( final Durata durata ) {
		try {
			Thread.sleep( durata.ms( Sintetizzatore.bpm() ) );
		} catch ( InterruptedException swallow ) {}
	}
	
	public static void riproduci( Brano brano ) throws InvalidMidiDataException, InterruptedException {
		final CountDownLatch cdl = new CountDownLatch( 1 );
		MetaEventListener mel = new MetaEventListener() {
			public void meta( MetaMessage meta ) {
				if ( meta.getType() == END_OF_TRACK_MESSAGE )
					sequencer.stop();
					cdl.countDown();
			}
		};
		sequencer.addMetaEventListener( mel );
		sequencer.setSequence( brano.sequence );
		sequencer.start();
		cdl.await();
		sequencer.stop();
		sequencer.removeMetaEventListener( mel );
	}
	
	public static void spegni() {
		sequencer.close();
		synth.close();
	}
	
}
