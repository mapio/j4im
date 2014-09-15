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

/** Durata di una nota, o pausa (espressa come frazione della misura). */
public class Durata {
	
	public static final Durata SEMIBREVE = new Durata( 1 ); 
	public static final Durata MINIMA = new Durata( 2 );
	public static final Durata SEMIMINIMA = new Durata( 4 ); 
	public static final Durata CROMA = new Durata( 8 );
	public static final Durata SEMICROMA = new Durata( 16 ); 
	public static final Durata BISCROMA = new Durata( 32 );
	public static final Durata SEMIBISCROMA = new Durata( 64 );
	
	/** Il numeratore frazione. */
	final int numeratore;
	
	/** Il denominatore della frazione. */
	final int denominatore;
	
	/**
	 * Costruisce una durata dati numeratore e denominatore (devono essere entrambe positivi).
	 * 
	 * @param numeratore il numeratore.
	 * @param denominatore il denominatore.
	 */
	public Durata( final int numeratore, final int denominatore ) {
		if ( numeratore < 0 || denominatore < 0 ) throw new IllegalArgumentException( "Non sono possibili durate negative." );
		this.numeratore = numeratore;
		this.denominatore = denominatore;
	}
	
	/**
	 * Costruisce una durata espressa come frazione dell'unità.
	 * 
	 * @param denominatore il denominatore.
	 */
	Durata( final int denominatore ) {
		this( 1, denominatore );
	}
	
	/** Restituisce la durata con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova la durata.
	 * @return  Se la durata corrispondente se la stringa inizia per una delle possibili durate:
	 * 		    <samp>1/1</samp>, <samp>1/2</samp>, <samp>1/4</samp>, <samp>1/8</samp>, <samp>1/16</samp>, <samp>1/32</samp>, <samp>1/64</samp>.
	 * @throws IllegalArgumentException se la stringa non inizia con una durata valida.
	 */		
	public static Durata fromString( final String str ) {
		final int barra = str.indexOf( '/' );
		try {
			final int numeratore = Integer.parseInt( str.substring( 0, barra ) );
			final int denominatore = Integer.parseInt( str.substring( barra + 1 ) );
			return new Durata( numeratore, denominatore );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException( "Impossibile determinare la durata di " + str );
		}
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
		return numeratore + "/" + denominatore;
	}
}