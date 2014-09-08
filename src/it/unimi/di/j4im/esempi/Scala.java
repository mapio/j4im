package it.unimi.di.j4im.esempi;

import it.unimi.di.j4im.notazione.Altezza;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

public class Scala {
	
	public static void main( String[] args ) throws MidiUnavailableException, IOException {
	
		Sintetizzatore.accendi();
		
		Strumento piano = new Strumento( "Piano" );
		for ( Altezza altezza : Altezza.values() ) {
			Nota nota = new Nota( altezza );
			System.out.println( nota );
			nota.suona( piano );
		}

		Strumento chitarra = new Strumento( "Guitar" );
		for ( int pitch = 60; pitch < 72; pitch++ ) {
			Nota nota = new Nota( pitch );
			System.out.println( nota );
			nota.suona( chitarra );
		}

		Sintetizzatore.spegni();

	}
		
}
