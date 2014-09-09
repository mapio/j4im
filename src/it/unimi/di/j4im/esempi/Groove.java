package it.unimi.di.j4im.esempi;

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.riproduzione.Batteria;
import it.unimi.di.j4im.riproduzione.Batteria.Pezzo;
import it.unimi.di.j4im.riproduzione.Brano;
import it.unimi.di.j4im.riproduzione.Parte;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;

public class Groove {

	public static void main( String[] args ) {
		
		Sintetizzatore.accendi();
		Sintetizzatore.bpm( 240 );
		
		Nota[] kh = new Nota[] { new Nota( Pezzo.KICK.pitch ), new Nota( Pezzo.CLOSED_HIHAT.pitch ) };
		Nota[] h = new Nota[] { new Nota( Pezzo.CLOSED_HIHAT.pitch ) };
		Nota[] ks = new Nota[] { new Nota( Pezzo.KICK.pitch ), new Nota( Pezzo.SNARE.pitch ) };
		
		Brano brano = new Brano();
		Parte p = new Parte( brano, new Batteria() );
		
		p.accodaAccordo( kh, 127 );
		p.accodaAccordo( h, 80 );
		p.accodaAccordo( ks, 80 );
		p.accodaAccordo( h, 80 );		

		brano.riproduci( -1 );

		Sintetizzatore.spegni();
	}
	
}
