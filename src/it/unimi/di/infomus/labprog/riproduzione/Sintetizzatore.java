package it.unimi.di.infomus.labprog.riproduzione;

import it.unimi.di.infomus.labprog.notazione.Nota;
import it.unimi.di.infomus.labprog.notazione.Pausa;

import java.io.Closeable;
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

public class Sintetizzatore implements Closeable {

	public static final int DEFAULT_INTENSITA = 64;
	public static final int END_OF_TRACK_MESSAGE = 0x2F;
	
	private final Synthesizer synth;
	private final Sequencer sequencer;
	private final Strumento[] strumenti;
	private int bpm;
	
	public class Strumento {
		
		private final MidiChannel mc;
		private final Instrument inst;
		
		private Strumento( final MidiChannel mc, Instrument inst ) {
			this.mc = mc;
			this.inst = inst;
			this.mc.programChange( inst.getPatch().getProgram() );
		}
		
		public void suona( final Nota nota, final int intensita ) {
			final int pitch = nota.pitch();
			mc.noteOn( pitch, intensita );
			try {
				Thread.sleep( nota.durata( bpm ) );
			} catch ( InterruptedException swallow ) {}
			mc.noteOff( pitch );
		}

		public void suona( final Nota nota ) {
			suona( nota, DEFAULT_INTENSITA );
		}

		public void suona( final Pausa pausa ) {
			try {
				Thread.sleep( pausa.durata( bpm ) );
			} catch ( InterruptedException swallow ) {}
		}
		
		public String toString() {
			return inst.toString();
		}
	}

	public Sintetizzatore( final int bpm, final String... nomiStrumenti ) throws MidiUnavailableException {

		/* bpm */
		
		if ( bpm < 0 || bpm > 960 ) throw new IllegalArgumentException( "I BPM devono essere compresi tra 1 e 960" );
		this.bpm = bpm;
		
		/* risorse di sistema */
		
		synth = MidiSystem.getSynthesizer();
		if ( ! synth.isOpen() ) synth.open();

		sequencer =  MidiSystem.getSequencer();
		if ( ! sequencer.isOpen() ) sequencer.open();
		
		sequencer.getTransmitter().setReceiver( synth.getReceiver() );
		sequencer.setTempoInBPM( bpm );
		
		/* strumenti */
		
		if ( nomiStrumenti.length == 0 ) throw new IllegalArgumentException( "?? necessario specificare almeno uno strumento" );
		
		final MidiChannel[] chs = synth.getChannels();
		if ( nomiStrumenti.length > chs.length ) throw new IllegalArgumentException( "Non si possono specificare pi?? di " + chs.length + " strumenti" );
		
		strumenti = new Strumento[ nomiStrumenti.length ];
		final Instrument[] insts = synth.getLoadedInstruments();
		for ( int s = 0; s < nomiStrumenti.length; s++ ) {
			if ( chs[ s ] == null ) throw new RuntimeException( "Il sintetizzatore di sistema ha a disposizione meno di " + s + " canali" );
			int i;
			for ( i = 0; i < insts.length; i++ )
				if ( insts[ i ].getName().contains( nomiStrumenti[ s ] ) ) break;
			if ( i < insts.length )
				strumenti[ s ] = new Strumento( chs[ s ], insts[ i ] );
			else throw new IllegalArgumentException( "Il sintetizzatore non ?? in grado di riprodurre lo strumento " + nomiStrumenti[ s ] );
		}
		
	}

	public Sintetizzatore( final String... nomiStrumenti ) throws MidiUnavailableException {
		this( 120, nomiStrumenti );
	}
	
	public Strumento strumento( final int idx ) {
		return strumenti[ idx ];
	}
	
	public Strumento strumento( final String nome ) {
		for ( Strumento s : strumenti )
			if ( s.inst.getName().contains( nome ) ) return s;
		throw new IllegalArgumentException( "Il sintetizzatore non contiene lo strumento " + nome );		
	}
	
	public void suona( final Nota nota, final int intensita ) {
		strumenti[ 0 ].suona( nota, intensita );
	}

	public void suona( final Nota nota ) {
		strumenti[ 0 ].suona( nota );
	}
	
	public void suona( final Pausa pausa ) {
		strumenti[ 0 ].suona( pausa );
	}
	
	public void riproduci( final Brano brano ) throws InvalidMidiDataException, InterruptedException  {
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
	
	public void close() throws IOException {
		synth.close();
		sequencer.close();
	}
}
