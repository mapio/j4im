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

/** Durata di una simbolo musicale (espressa come frazione della misura). */
public class Durata {

	public static final Durata SEMIBREVE = new Durata( 1 );
	public static final Durata MINIMA = new Durata( 2 );
	public static final Durata SEMIMINIMA = new Durata( 4 );
	public static final Durata CROMA = new Durata( 8 );
	public static final Durata SEMICROMA = new Durata( 16 );
	public static final Durata BISCROMA = new Durata( 32 );
	public static final Durata SEMIBISCROMA = new Durata( 64 );

	private final int numeratore;
	private final int denominatore;

	/**
	 * Costruisce una durata dati numeratore e denominatore (devono essere
	 * entrambe positivi).
	 * 
	 * @param numeratore il numeratore.
	 * @param denominatore il denominatore.
	 * @throws IllegalArgumentException se numerotaore, o denominatore sono
	 *             negativi, o nulli.
	 */
	public Durata( final int numeratore, final int denominatore ) {
		if ( numeratore <= 0 || denominatore <= 0 )
			throw new IllegalArgumentException( "Non sono possibili durate non positive, o il denominatore nullo." );
		this.numeratore = numeratore;
		this.denominatore = denominatore;
	}

	/**
	 * Costruisce una durata espressa come frazione dell'unità.
	 * 
	 * @param denominatore il denominatore.
	 */
	public Durata( final int denominatore ) {
		this( 1, denominatore );
	}

	/**
	 * Restituisce la durata contenuta in una stringa data.
	 * 
	 * <p>
	 * La stringa deve avere formato dato da due numeri separati da
	 * <samp>/</samp>.
	 * </p>
	 * 
	 * @param str la stringa in cui si trova la durata.
	 * @return la durata.
	 * @throws IllegalArgumentException se la stringa non contiene una durata
	 *             valida.
	 */
	public static Durata fromString( final String str ) {
		final int barra = str.indexOf( '/' );
		if ( barra == -1 )
			throw new IllegalArgumentException( "Impossibile determinare la durata di " + str );
		try {
			final int numeratore = Integer.parseInt( str.substring( 0, barra ) );
			final int denominatore = Integer.parseInt( str.substring( barra + 1 ) );
			return new Durata( numeratore, denominatore );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException( "Impossibile determinare la durata di " + str );
		}
	}

	/**
	 * Restituisce la durata in millisecondi, dato il numero di quarti al
	 * minuto.
	 * 
	 * @param bpm il numero di quarti al minuto.
	 * @return La durata in millisecondi.
	 */
	public int ms( final int bpm ) {
		return (int)( 1000 * ( 60.0 / bpm ) * ( 4.0 / denominatore ) );
	}

	/**
	 * Resituisce la durata in ticks, data la risoluzione.
	 * 
	 * @param resolution la risoluzione.
	 * @return La durata in ticks.
	 */
	public int ticks( final double resolution ) {
		return (int)( 4 * resolution / denominatore );
	}

	public boolean piuBreve( final Durata altra ) {
		if ( numeratore * altra.denominatore < altra.numeratore * denominatore )
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return numeratore + "/" + denominatore;
	}
}
