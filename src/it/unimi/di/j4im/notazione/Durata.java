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

/** Durata di una nota, o pausa. */
public enum Durata {
	
	SEMIBREVE( 1 ), 
	MINIMA( 2 ), 
	SEMIMINIMA( 4 ), 
	CROMA( 8 ), 
	SEMICROMA( 16 ), 
	BISCROMA( 32 ), 
	SEMIBISCROMA( 64 );
	
	/** La durata della nota come frazione della misura. */
	final int denominatore;
	
	Durata( final int denominatore ) {
		this.denominatore = denominatore;
	}
	
	/** Restituisce la durata con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova la durata.
	 * @return  Se la durata corrispondente se la stringa inizia per una delle possibili durate:
	 * 		    <samp>1/1</samp>, <samp>1/2</samp>, <samp>1/4</samp>, <samp>1/8</samp>, <samp>1/16</samp>, <samp>1/32</samp>, <samp>1/64</samp>.
	 * @throws IllegalArgumentException se la stringa non inizia con una durata valida.
	 */		
	public static Durata fromString( final String str ) {
		for ( Durata d : Durata.values() )
			if ( str.equals( d.toString() ) ) return d;
		throw new IllegalArgumentException( "Impossibile determinare la durata di " + str );
	}

	/** Restituisce la durata (come frazione della misura).
	 * 
	 * @return Il denominatore.
	 */
	public int denominatore() {
		return denominatore;
	}

	/** Restituisce la durata in millisecondi, dato il numero di quarti al minuto. 
	 * 
	 * @param bpm il numero di quarti al minuto.
	 * @return La durata in millisecondi.
	 */
	public int ms( final int bpm ) {
		return (int)( 1000 * ( 60.0 / bpm ) * ( 4.0 / denominatore ) );
	}
	
	/** Resituisce la durata in ticks, data la risoluzione.
	 * 
	 * @param resolution la risoluzione.
	 * @return La durata in ticks.
	 */
	public int ticks( final double resolution ) {
		return (int)( 4 * resolution / denominatore );
	}

	@Override
	public String toString() {
		return "1/" + denominatore();
	}
}