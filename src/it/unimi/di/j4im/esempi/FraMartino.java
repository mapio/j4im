package it.unimi.di.j4im.esempi;

/*
 * Copyright 2014 Massimo Santini
 * 
 * This file is part of j4im.
 *
 * j4im is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * j4im is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with j4im.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.io.IOException;

import it.unimi.di.j4im.notazione.Durata;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;
import it.unimi.di.j4im.notazione.Simbolo;
import it.unimi.di.j4im.riproduzione.Brano;
import it.unimi.di.j4im.riproduzione.Parte;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

public class FraMartino {
	
	public static void suona( final Simbolo[] simboli ) {
		final Strumento flauto = new Strumento( "Flute" );
		for ( Simbolo s : simboli ) {
			System.out.println( s );
			s.suonaCon( flauto );
		}		
	}
	
	public static void canone( final Simbolo[] simboli ) {
		Brano b = new Brano();
		
		Parte p0 = new Parte( b, new Strumento( "Piano" ) );
		p0.accoda( simboli );
		
		Parte p1 = new Parte( b, new Strumento( "Guitar" ) );
		
		// trasposizione di una ottava 
		for ( int i = 0; i < simboli.length; i++ )
			if ( simboli[ i ] instanceof Nota ) {
				final Nota n = (Nota)simboli[ i ];
				simboli[ i ] = new Nota( n.pitch() + 12, n.durata() );
			}

		// traslazione di una misura
		p1.accoda( new Pausa( Durata.SEMIBREVE ) );
		p1.accoda( simboli );
		
		b.riproduci();		
	}
	
	public static void main( String[] args ) throws IOException {
	
		Sintetizzatore.accendi();
		
		Simbolo[] fraMartino = Simbolo.simboli( 			
			"DO,RE,MI,DO," +
			"DO,RE,MI,DO," +
			"MI,FA,SOL:1/2," +
			"MI,FA,SOL:1/2," +
			"SOL:1/8,LA:1/8,SOL:1/8,FA:1/8,MI,DO," +
			"SOL:1/8,LA:1/8,SOL:1/8,FA:1/8,MI,DO," +
			"RE,SOL3,DO:1/2," +
			"RE,SOL3,DO:1/2" 
		);
		
		Brano b = new Brano();
		Parte p = new Parte( b, new Strumento( "Piano" ) );
		p.accoda( fraMartino );
		b.scrivi( "framartino.mid" );
		
		
		//suona( fraMartino );
		//canone( fraMartino );
		
		Sintetizzatore.spegni();
	}
	
	
}
