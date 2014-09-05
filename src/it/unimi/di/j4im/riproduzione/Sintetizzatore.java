package it.unimi.di.j4im.riproduzione;

import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class Sintetizzatore {

	public static final int DEFAULT_INTENSITA = 64;
	public static final int DEFAULT_BPM = 120;
	
	protected static Sintetizzatore INSTANCE = null;
		
	private final Synthesizer synth;
	private final Sequencer sequencer;
	private final MidiChannel[] canali;
	private int canaliAllocati;
	private int bpm;
	
	class StrumentoImpl {
		final MidiChannel mc;
		final Instrument inst;
		final int n;
		StrumentoImpl( final String nome ) {			
			if ( canaliAllocati >= canali.length ) throw new IllegalStateException( "Il sintetizzatore non supporta più di " + canali.length + " srumenti" );
			for ( Instrument inst : synth.getLoadedInstruments() )
				if ( inst.getName().contains( nome ) ) {
					this.mc = canali[ canaliAllocati ];
					this.inst = inst; 
					this.n = canaliAllocati++;
					this.mc.programChange( inst.getPatch().getProgram() );
					return;
				}
			throw new IllegalArgumentException( "Il sintetizzatore non è in grado di riprodurre lo strumento " + nome );
		}		
	} 
	
	private Sintetizzatore( final int bpm ) throws MidiUnavailableException {
		
		if ( bpm < 0 || bpm > 960 ) throw new IllegalArgumentException( "I BPM devono essere compresi tra 1 e 960" );
		this.bpm = bpm;

		synth = MidiSystem.getSynthesizer();
		if ( ! synth.isOpen() ) synth.open();

		sequencer =  MidiSystem.getSequencer();
		if ( ! sequencer.isOpen() ) sequencer.open();
	
		sequencer.getTransmitter().setReceiver( synth.getReceiver() );
	
		canali = synth.getChannels();
		canaliAllocati = 0;
	}
	
	private void close() {
		synth.close();
		sequencer.close();
	}

	/* metodi di pacchetto */

	static Sequencer sequencer() {
		if ( INSTANCE == null ) throw new IllegalStateException( "Non è stato acceso il sitetizzatore" );
		return INSTANCE.sequencer;
	}
	
	static StrumentoImpl strumento( final String nome ) {
		if ( INSTANCE == null ) throw new IllegalStateException( "Non è stato acceso il sitetizzatore" );
		return INSTANCE.new StrumentoImpl( nome );
	}
	
	/* metodi pubblici  */
	
	public static void accendi( final int bpm ) throws MidiUnavailableException {
		if ( INSTANCE != null ) throw new IllegalStateException( "Il sitetizzatore è già stato acceso" );
		INSTANCE = new Sintetizzatore( bpm );
	}

	public static void accendi() throws MidiUnavailableException {
		accendi( Sintetizzatore.DEFAULT_BPM );
	}

	public static int bpm() {
		if ( INSTANCE == null ) throw new IllegalStateException( "Non è stato acceso il sitetizzatore" );
		return INSTANCE.bpm;
	}
	
	public static void bpm( final int bpm ) {
		if ( INSTANCE == null ) throw new IllegalStateException( "Non è stato acceso il sitetizzatore" );
		INSTANCE.bpm = bpm;
	}

	public static void spegni() throws IOException {
		if ( INSTANCE != null ) INSTANCE.close();
		INSTANCE = null;
	}
	
}
