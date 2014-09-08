package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Durata;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Parte {

	/**
	 * 
	 */
	private final Brano brano;
	private final int canale;
	private final Track track;
	private long ticks;
	
	Parte( Brano brano, final Strumento strumento ) {
		this.brano = brano;
		this.canale = strumento.canale;
		track = this.brano.sequence.createTrack();
		ticks = 0;
	}
	
	Parte( Brano brano, final Strumento strumento, final String parte ) {
		this( brano, strumento );
		for ( String s : parte.split( "," ) )
			if ( s.charAt( 0 ) == '_' ) 
				accoda( new Pausa( s ) );
			else 
				accoda( new Nota( s ) );
	}
	
	public void accoda( final Nota nota, final int intensita ) {
		final int pitch = nota.pitch();
		try {
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_ON, canale, pitch, intensita ), ticks ) );
			ticks += nota.durata().ticks( Brano.RESOLUTION );
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_OFF, canale, pitch, 0 ), ticks ) );
		} catch ( InvalidMidiDataException e ) {
			throw new IllegalArgumentException( "Nota non valida", e );
		}
	}
	
	public void accoda( final Nota nota ) {
		accoda( nota, Sintetizzatore.INTENSITA_DEFAULT );
	}

	public void accoda( final Pausa pausa ) {
		ticks += pausa.durata().ticks( Brano.RESOLUTION );
	}

	public void trasla( final Durata durata ) {
		final int numEventi = track.size();
		final int ticks = durata.ticks( Brano.RESOLUTION ); 
		for ( int i = 0; i < numEventi; i++ ) {
			final MidiEvent e = track.get( i );
			e.setTick( e.getTick() + ticks );
		}
	}
	
}