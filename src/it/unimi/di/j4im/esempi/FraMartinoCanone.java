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

import it.unimi.di.j4im.notazione.Durata;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;
import it.unimi.di.j4im.notazione.Simbolo;
import it.unimi.di.j4im.riproduzione.Brano;
import it.unimi.di.j4im.riproduzione.Parte;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

import java.io.IOException;

/** Suona un canone ottenuto a partire da "Fra martino" per trasposizione e traslazione. */
public class FraMartinoCanone {
	
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
			
		Brano brano = new Brano();
		
		// parte originale
		
		Parte originale = new Parte( brano, new Strumento( "Piano" ) );
		originale.accoda( fraMartino );
		
		// costruzione di un array di simboli traspossti di una ottava 
		
		Simbolo[] fraMartinoTraslatoTrasposto = new Simbolo[ fraMartino.length ];
		for ( int i = 0; i < fraMartino.length; i++ )
			if ( fraMartino[ i ] instanceof Nota ) {
				final Nota n = (Nota)fraMartino[ i ];
				fraMartinoTraslatoTrasposto[ i ] = 
					Nota.costruttore().nota( n ).pitch( n.pitch() + 12 ).costruisci();
			}

		// parte trasposta, con traslazione (in tempo) di una misura
		
		Parte traslataTrasposta = new Parte( brano, new Strumento( "Guitar" ) );
		traslataTrasposta.accoda( new Pausa( Durata.SEMIBREVE ) );
		traslataTrasposta.accoda( fraMartinoTraslatoTrasposto );
		
		// riroduzione del brano
		
		brano.riproduci();		

		Sintetizzatore.spegni();
	}

}
