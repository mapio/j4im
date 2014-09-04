package it.unimi.di.infomus.labprog.riproduzione;

import it.unimi.di.infomus.labprog.notazione.Durata;
import it.unimi.di.infomus.labprog.notazione.Nota;
import it.unimi.di.infomus.labprog.notazione.Pausa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Brano {

	public class Parte {

		final int channel;
		final Track track =  seq.createTrack();
		long tick = 0;
		
		public Parte( final int channel ) {
			this.channel = channel;
		}
		
		public void accoda( final Nota nota, final int intensita ) throws InvalidMidiDataException {
			final int pitch = nota.pitch();
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_ON, channel, pitch, intensita ), tick ) );
			tick += ( 4 * RESOLUTION ) / nota.durata().denominatore();
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_OFF, channel, pitch, 0 ), tick ) );
		}
		
		public void accoda( final Nota nota ) throws InvalidMidiDataException {
			accoda( nota, Sintetizzatore.DEFAULT_INTENSITA );
		}

		public void accoda( final Pausa pausa ) {
			tick += ( 4 * RESOLUTION ) / pausa.durata().denominatore();
		}

		public void trasla( final Durata durata ) {
			final int numEventi = track.size();
			final int tick = (int)( 4 * RESOLUTION / durata.denominatore()); 
			for ( int i = 0; i < numEventi; i++ ) {
				final MidiEvent e = track.get( i );
				e.setTick( e.getTick() + tick );
			}
		}
		
		public void fromString( final String str ) throws InvalidMidiDataException {			
			for ( String s : str.split( "," ) )
				if ( s.charAt( 0 ) == '_' ) 
					this.accoda( Pausa.fromString( s ) );
				else 
					this.accoda( Nota.fromString( s ) );
		}

	}
	
	final static double RESOLUTION = 960.0;
	final Sequence seq;
	
	
	public Brano() throws InvalidMidiDataException {
		seq = new Sequence( Sequence.PPQ, (int)RESOLUTION );
	}

	public Parte parte( final int channel ) {
		return new Parte( channel );
	}
}
