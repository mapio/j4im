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

import it.unimi.di.j4im.notazione.Nome;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

/** Suona una scala (dal <samp>DO</samp> al <samp>SI</samp>, senza alterazioni), costruendo le note in base al {@link Nome nome}. */
public class ScalaNome {
	
	public static void main( String[] args ) {
	
		Sintetizzatore.accendi();
		
		Strumento piano = new Strumento( "Piano" );
		for ( Nome nome : Nome.values() ) {
			Nota nota = new Nota( nome );
			System.out.format( "%3s: %.3fHz\n", nota, nota.frequenza() );
			nota.suonaCon( piano );
		}

		Sintetizzatore.spegni();

	}
		
}
