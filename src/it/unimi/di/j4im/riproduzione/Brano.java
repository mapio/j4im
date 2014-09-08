package it.unimi.di.j4im.riproduzione;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

/** Questa classe rappresenta un brano musicale; ogni elemento di un brano è una {@link Parte}. */
public class Brano {

	/** La risoluzione usata nella sequenza. */
	static final double RESOLUTION = 960.0;

	/** La sequenza usata per rappresentare il brano. */
	final Sequence sequence;
	
	/** Costruisce un nuovo brano. */
	public Brano() {
		try {
			sequence = new Sequence( Sequence.PPQ, (int)RESOLUTION );
		} catch ( InvalidMidiDataException e ) {
			throw new RuntimeException( e ); // questo non dovrebbe mai accadere
		}
	}
	
	/** Riproduce il brano (usando il {@link Sintetizzatore}. */
	public void riproduci() {
		Sintetizzatore.riproduci( sequence );
	}
	
}
