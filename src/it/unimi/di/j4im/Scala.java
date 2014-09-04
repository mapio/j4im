package it.unimi.di.j4im;

import java.io.IOException;

import it.unimi.di.j4im.notazione.Altezza;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;

import javax.sound.midi.MidiUnavailableException;

public class Scala {
	
	public static void main( String[] args ) throws MidiUnavailableException, IOException {
	
		Sintetizzatore s = new Sintetizzatore( "Piano" );

		for ( Altezza altezza : Altezza.values() ) {
			Nota nota = new Nota( altezza );
			System.out.println( nota );
			s.suona( nota );
		}

		for ( int i = 60; i < 72; i++ ) {
			Nota nota = Nota.fromPitch( i );
			System.out.println( nota );
			s.suona( nota );
		}

		s.close();
	}

}
