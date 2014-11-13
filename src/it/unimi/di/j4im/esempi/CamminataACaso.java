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
import it.unimi.di.j4im.riproduzione.Sintetizzatore;

import java.util.Random;

/** Suona un numero casuale di note a caso sequendo una "passeggiata casuale". */
public class CamminataACaso {

	public static void main( String[] args ) {

		final Random random = new Random();

		Sintetizzatore.accendi();

		int pitch = 64, durata = 4, quante = random.nextInt( 100 );
		for ( int i = 0; i < quante; i++ ) {
			pitch += random.nextBoolean() ? 1 : -1;
			if ( pitch < 0 ) pitch = 0;
			else if ( pitch > 127 ) pitch = 127;
			durata =  random.nextBoolean() ? durata / 2 : durata * 2;
			if ( durata < 4 ) durata = 4;
			else if ( durata > 32 ) durata = 32;
			Sintetizzatore.accendiNota( 0, pitch, 127 );
			Sintetizzatore.attendi( new Durata( durata ) );
			Sintetizzatore.spegniNota( 0, pitch );
		}

		Sintetizzatore.spegni();

	}

}
