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

import it.unimi.di.j4im.notazione.Simbolo;
import it.unimi.di.j4im.riproduzione.Brano;
import it.unimi.di.j4im.riproduzione.Parte;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

import java.io.IOException;

/** Suona (e salva in un file) "Fra martino" costruendo un {@link Brano brano}. */
public class FraMartinoBrano {
		
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
		Parte parte = new Parte( brano, new Strumento( "Piano" ) );
		parte.accoda( fraMartino );
		
		brano.scrivi( "framartino.mid" );
		brano.riproduci();
		
		Sintetizzatore.spegni();
	}
	
}
