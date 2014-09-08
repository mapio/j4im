package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;
import it.unimi.di.j4im.notazione.Simbolo;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/** Questa classe rappresenta una parte di un {@link Brano} musicale, assegnata ad uno specifico {@link Strumento}. */
public class Parte {

	/** Il canale a cui è assegnato lo strumento della parte. */
	private final int canale;
	
	/** La {@link Track} usata per rappresentare le note della parte */
	private final Track track;
	
	/** Il numero di ticks del prossimo {@link MidiMessage}. */
	private long ticks;
	
	/** Costruisce una nuova parte all'interno del {@link Brano} specificato che verrò riprodotta con lo {@link Strumento} dato.
	 * 
	 * @param brano il brano.
	 * @param strumento lo strumento.
	 * 
	 */
	public Parte( final Brano brano, final Strumento strumento ) {
		canale = strumento.canale;
		track = brano.sequence.createTrack();
		ticks = 0;
	}

	/** Accoda la nota specificata alla parte.
	 * 
	 * @param nota la nota.
	 * @param intensita l'intensità a cui sarà riprodotta la nota (dev'essere un valore compreso tra 0 e 127, estremi inclusi),
	 * @throws IllegalArgumentException se l'intensità è fuori dall'intervallo consentito.
	 * 
	 */
	public void accoda( final Nota nota, final int intensita ) {
		if ( intensita < 0 || intensita > 127 ) throw new IllegalArgumentException( "L'intensità dev'essere compresa tra 0 e 127, estremi inclusi." );
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

	/** Accoda la pausa specificata alla parte.
	 * 
	 * @param pausa la nota.
	 * 
	 */
	public void accoda( final Pausa pausa ) {
		ticks += pausa.durata().ticks( Brano.RESOLUTION );
	}

	/** Accoda un elenco di {@link Simbolo} alla parte. 
	 *
	 * @param simboli la rappresentazione testuale della parte.
	 * 
	 */
	public void accoda( final Simbolo[] simboli ) {
		for ( Simbolo s : simboli )
			if ( s instanceof Pausa ) 
				accoda( (Pausa)s );
			else 
				accoda( (Nota)s );
	}
	
}