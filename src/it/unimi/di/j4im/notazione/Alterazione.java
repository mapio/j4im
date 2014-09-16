package it.unimi.di.j4im.notazione;

/*
 * Copyright 2014 Massimo Santini
 * 
 * This file is part of j4im.
 * 
 * j4im is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * j4im is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * j4im. If not, see <http://www.gnu.org/licenses/>.
 */

/** Una alterazione musicale. */
public enum Alterazione {

	BEMOLLE( "♭", -1 ), NULLA( "", 0 ), DIESIS( "♯", +1 );

	/** La rappresentazione testuale dell'alterazione. */
	final String rapp;

	/** Il numero (relativo) di semitoni rappresentato da questa alterazione. */
	final int semitoni;

	Alterazione( final String rapp, final int semitoni ) {
		this.rapp = rapp;
		this.semitoni = semitoni;
	}

	/**
	 * Restituisce l'alterazione contenuta in una stringa data.
	 * 
	 * @param str la stringa in cui si trova l'alterazione.
	 * @return Se la stringa è ♯ (o <samp>#</samp>), o ♭, viene restituita
	 *         l'alterazione corrispondente, altrimenti se è vuota viene
	 *         restituita l'alterazione nulla.
	 * @throws IllegalArgumentException se la stringa è di formato errato.
	 */
	public static Alterazione fromString( final String str ) {
		Alterazione alterazione;
		if ( str.equals( BEMOLLE.rapp ) )
			alterazione = BEMOLLE;
		else if ( str.equals( DIESIS.rapp ) || str.equals( "#" ) )
			alterazione = DIESIS;
		else if ( str.length() == 0 )
			alterazione = NULLA;
		else throw new IllegalArgumentException( "Non è possibile comprendere l'alterazione " + str );
		return alterazione;
	}

	@Override
	public String toString() {
		return rapp;
	}
}
