package it.unimi.di.j4im.riproduzione;

import java.util.concurrent.CountDownLatch;

import it.unimi.di.j4im.notazione.Durata;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Brano {

	public class Parte {

		final Strumento strumento;
		final Track track =  sequence.createTrack();
		long tick = 0;
		
		public Parte( final Strumento strumento ) {
			this.strumento = strumento;
		}
		
		public void accoda( final Nota nota, final int intensita ) throws InvalidMidiDataException {
			final int pitch = nota.pitch();
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_ON, strumento.canale(), pitch, intensita ), tick ) );
			tick += nota.durata().ticks( RESOLUTION );
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_OFF, strumento.canale(), pitch, 0 ), tick ) );
		}
		
		public void accoda( final Nota nota ) throws InvalidMidiDataException {
			accoda( nota, Sintetizzatore.DEFAULT_INTENSITA );
		}

		public void accoda( final Pausa pausa ) {
			tick += pausa.durata().ticks( RESOLUTION );
		}

		public void trasla( final Durata durata ) {
			final int numEventi = track.size();
			final int tick = durata.ticks( RESOLUTION ); 
			for ( int i = 0; i < numEventi; i++ ) {
				final MidiEvent e = track.get( i );
				e.setTick( e.getTick() + tick );
			}
		}
		
		public void fromString( final String str ) throws InvalidMidiDataException {			
			for ( String s : str.split( "," ) )
				if ( s.charAt( 0 ) == '_' ) 
					this.accoda( new Pausa( s ) );
				else 
					this.accoda( new Nota( s ) );
		}

	}
	
	private static final double RESOLUTION = 960.0;
	private static final int END_OF_TRACK_MESSAGE = 0x2F;

	private final Sequence sequence;
	
	public Brano() throws InvalidMidiDataException {
		sequence = new Sequence( Sequence.PPQ, (int)RESOLUTION );
	}

	public Parte parte( final Strumento strumento ) {
		return new Parte( strumento );
	}
	
	public void riproduci() throws InvalidMidiDataException, InterruptedException  {
		final Sequencer sequencer = Sintetizzatore.sequencer();
		sequencer.setTempoInBPM( Sintetizzatore.bpm() );
		final CountDownLatch cdl = new CountDownLatch( 1 );
		MetaEventListener mel = new MetaEventListener() {
			public void meta( MetaMessage meta ) {
				if ( meta.getType() == END_OF_TRACK_MESSAGE )
					sequencer.stop();
					cdl.countDown();
			}
		};
		sequencer.addMetaEventListener( mel );
		sequencer.setSequence( sequence );
		sequencer.start();
		cdl.await();
		sequencer.stop();
		sequencer.removeMetaEventListener( mel );
	}
	
}
