package it.unimi.di.j4im.notazione;

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

/** Altezza di una nota. 
 * 
 * <p> 
 * Per una discussione più completa delle altezze si veda la documentazione di {@link Nota}.
 * </p>
 * 
 * @see Nota
 */
public enum Altezza {
	
	DO( 0 ), 
	RE( 2 ), 
	MI( 4 ), 
	FA( 5 ), 
	SOL( 7 ), 
	LA( 9 ), 
	SI( 11 );
	
	/** La distanza in semitoni dall'inizio dell'ottava. */
	final int semitoni;
	
	Altezza( final int semitoni ) {
		this.semitoni = semitoni;
	}

	/** Restituisce l'altezza con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova l'altezza.
	 * @return  Se la stringa inizia per uno dei nomi di nota (<samp>DO</samp>, <samp>RE</samp>…), 
	 *          restituisce l'altezza corrispondente.
	 * @throws IllegalArgumentException se la stringa non inizia con un nome di nota.
	 */		
	public static Altezza fromString( final String str ) {
		for ( Altezza altezza : Altezza.values() ) 
			if ( str.equals( altezza.toString() ) ) return altezza;
		throw new IllegalArgumentException( "Non è possibile dedurre l'altezza di " + str );
	}
}