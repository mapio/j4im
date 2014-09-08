package it.unimi.di.j4im.riproduzione;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public class Brano {

	public static final double RESOLUTION = 960.0;

	final Sequence sequence;
	
	public Brano() {
		try {
			sequence = new Sequence( Sequence.PPQ, (int)RESOLUTION );
		} catch ( InvalidMidiDataException e ) {
			throw new RuntimeException( e ); // questo non dovrebbe mai accadere
		}
	}

	public Parte parte( final Strumento strumento ) {
		return new Parte( this, strumento );
	}

	public Parte parte( final Strumento strumento, final String parte ) {
		return new Parte( this, strumento, parte );
	}
	
	public void riproduci() {
		Sintetizzatore.riproduci( sequence );
	}
	
}
