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

/** Nome di una nota. */
public enum Nome {

	DO( 0 ), RE( 2 ), MI( 4 ), FA( 5 ), SOL( 7 ), LA( 9 ), SI( 11 );

	/** La distanza in semitoni dall'inizio dell'ottava. */
	final int semitoni;

	Nome( final int semitoni ) {
		this.semitoni = semitoni;
	}

}
