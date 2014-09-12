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
