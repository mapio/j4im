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

/** Questa classe rappresenta una alterazione musicale. */
public enum Alterazione {
	
	BEMOLLE( "♭", -1 ), 
	NULLA( "", 0 ), 
	DIESIS( "♯", +1 );

	/** La rappresentazione testuale dell'alterazione. */
	final String rapp;
	
	/** Il numero (relativo) di semitoni rappresentato da questa alterazione. */
	final int semitoni;
	
	Alterazione( final String rapp, final int semitoni ) {
		this.rapp = rapp;
		this.semitoni = semitoni;
	}
	
	/** Restituisce l'alterazione con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova l'alterazione.
	 * @return  Se la stringa inizia per ♯ (o <samp>#</samp>), o ♭, viene restituita
	 *          l'alterazione corrispondente, altrimenti viene restituita
	 *          l'alterazione nulla. 
	 */
	public static Alterazione fromString( final String str ) {
		Alterazione alterazione;
		if ( str.startsWith( BEMOLLE.rapp ) )
			alterazione = BEMOLLE;
		else if ( str.startsWith( DIESIS.rapp ) || str.startsWith( "#") )
			alterazione = DIESIS;
		else alterazione = NULLA;
		return alterazione;
	}
	
	@Override
	public String toString() {
		return rapp;
	}
}