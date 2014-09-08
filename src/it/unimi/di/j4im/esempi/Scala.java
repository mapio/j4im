package it.unimi.di.j4im.esempi;

import it.unimi.di.j4im.notazione.Altezza;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

public class Scala {
	
	public static void main( String[] args ) {
	
		Sintetizzatore.accendi();
		
		Strumento piano = new Strumento( "Piano" );
		for ( Altezza altezza : Altezza.values() ) {
			Nota nota = new Nota( altezza );
			System.out.println( nota );
			nota.suonaCon( piano );
		}

		Strumento chitarra = new Strumento( "Guitar" );
		for ( int pitch = 60; pitch < 72; pitch++ ) {
			Nota nota = new Nota( pitch );
			System.out.println( nota );
			nota.suonaCon( chitarra );
		}

		Sintetizzatore.spegni();

	}
		
}
