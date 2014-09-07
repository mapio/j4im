package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Durata;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Brano {

	public class Parte {

		private final int canale;
		private final Track track;
		private long ticks;
		
		Parte( final Strumento strumento ) {
			this.canale = strumento.canale;
			track = sequence.createTrack();
			ticks = 0;
		}
		
		Parte( final Strumento strumento, final String parte ) throws InvalidMidiDataException {
			this( strumento );
			for ( String s : parte.split( "," ) )
				if ( s.charAt( 0 ) == '_' ) 
					accoda( new Pausa( s ) );
				else 
					accoda( new Nota( s ) );
		}
		
		public void accoda( final Nota nota, final int intensita ) throws InvalidMidiDataException {
			final int pitch = nota.pitch();
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_ON, canale, pitch, intensita ), ticks ) );
			ticks += nota.durata().ticks( RESOLUTION );
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_OFF, canale, pitch, 0 ), ticks ) );
		}
		
		public void accoda( final Nota nota ) throws InvalidMidiDataException {
			accoda( nota, Sintetizzatore.INTENSITA_DEFAULT );
		}

		public void accoda( final Pausa pausa ) {
			ticks += pausa.durata().ticks( RESOLUTION );
		}

		public void trasla( final Durata durata ) {
			final int numEventi = track.size();
			final int ticks = durata.ticks( RESOLUTION ); 
			for ( int i = 0; i < numEventi; i++ ) {
				final MidiEvent e = track.get( i );
				e.setTick( e.getTick() + ticks );
			}
		}
		
	}
	
	public static final double RESOLUTION = 960.0;

	final Sequence sequence;
	
	public Brano() throws InvalidMidiDataException {
		sequence = new Sequence( Sequence.PPQ, (int)RESOLUTION );
	}

	public Parte parte( final Strumento strumento ) {
		return new Parte( strumento );
	}

	public Parte parte( final Strumento strumento, final String parte ) throws InvalidMidiDataException {
		return new Parte( strumento, parte );
	}
	
	public void riproduci() throws InvalidMidiDataException, InterruptedException  {
		Sintetizzatore.riproduci( this );
	}
	
}
