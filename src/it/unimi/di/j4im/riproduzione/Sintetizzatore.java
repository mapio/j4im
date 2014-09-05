package it.unimi.di.j4im.riproduzione;

import java.io.IOException;
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

	public static final int DEFAULT_INTENSITA = 64;
	public static final int DEFAULT_BPM = 120;
	public static final int END_OF_TRACK_MESSAGE = 0x2F;
	
	protected static Sintetizzatore INSTANCE = null;
		
	private final Synthesizer synth;
	private final Sequencer sequencer;
	private final MidiChannel[] canali;
	private int canaliAllocati;

	static class StrumentoImpl {
		final MidiChannel mc;
		final Instrument inst;
		final int n;
		StrumentoImpl( final MidiChannel mc, final Instrument inst, final int n ) {
			this.mc = mc;
			this.inst = inst;
			this.n = n;
			this.mc.programChange( inst.getPatch().getProgram() );
		}		
	} 
	
	private Sintetizzatore() throws MidiUnavailableException {
		synth = MidiSystem.getSynthesizer();
		if ( ! synth.isOpen() ) synth.open();

		sequencer =  MidiSystem.getSequencer();
		if ( ! sequencer.isOpen() ) sequencer.open();
	
		sequencer.getTransmitter().setReceiver( synth.getReceiver() );
	
		canali = synth.getChannels();
		canaliAllocati = 0;
	}

	private StrumentoImpl strumentoImpl( final String nome ) {
		if ( canaliAllocati >= canali.length ) throw new IllegalStateException( "Il sintetizzatore non supporta più di " + canali.length + " srumenti" );
		for ( Instrument inst : synth.getLoadedInstruments() )
			if ( inst.getName().contains( nome ) ) {
				canaliAllocati++;
				return new StrumentoImpl( canali[ canaliAllocati - 1 ], inst, canaliAllocati - 1 );
			}
		throw new IllegalArgumentException( "Il sintetizzatore non è in grado di riprodurre lo strumento " + nome );
	}
	
	private void close() {
		synth.close();
		sequencer.close();
	}

	public void riproduci( final int bpm, final Brano brano ) throws InvalidMidiDataException, InterruptedException  {
		sequencer.setTempoInBPM( bpm );
		final CountDownLatch cdl = new CountDownLatch( 1 );
		MetaEventListener mel = new MetaEventListener() {
			public void meta( MetaMessage meta ) {
				if ( meta.getType() == END_OF_TRACK_MESSAGE )
					sequencer.stop();
					cdl.countDown();
			}
		};
		sequencer.addMetaEventListener( mel );
		sequencer.setSequence( brano.seq );
		sequencer.start();
		cdl.await();
		sequencer.stop();
		sequencer.removeMetaEventListener( mel );
	}

	
	/* metodi "esterni" */
	
	public static void accendi() throws MidiUnavailableException {
		if ( INSTANCE != null ) throw new IllegalStateException( "Il sitetizzatore è già stato acceso" );
		INSTANCE = new Sintetizzatore();
	}
	
	protected static StrumentoImpl strumento( final String nome ) {
		if ( INSTANCE == null ) throw new IllegalStateException( "Non è stato acceso il sitetizzatore" );
		return INSTANCE.strumentoImpl( nome );
	}
	
	public static void spegni() throws IOException {
		if ( INSTANCE != null ) INSTANCE.close();
		INSTANCE = null;
	}
	
}
