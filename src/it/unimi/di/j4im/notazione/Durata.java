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
public class Durata implements Comparable <Durata> {

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
	 * entrambe positivi) e semplifica la frazione corrispondente.
	 *
	 * @param numeratore il numeratore.
	 * @param denominatore il denominatore.
	 * @throws IllegalArgumentException se numeratore, o denominatore sono
	 *             negativi, o nulli.
	 */
	public Durata( final int numeratore, final int denominatore ) {
		if ( numeratore <= 0 || denominatore <= 0 )
			throw new IllegalArgumentException( "Non sono possibili durate non positive, o il denominatore nullo." );
		final int mcd = Durata.mcd( numeratore, denominatore );
		this.numeratore = numeratore / mcd;
		this.denominatore = denominatore / mcd;
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

	/**
	 * Restituisce la durata corrispondente alla somma tra questa e la durata data.
	 *
	 * @param altra la durata da sommare.
	 * @return la somma.
	 */
	public Durata piu( final Durata altra ) {
		return new Durata( numeratore * altra.denominatore + denominatore * altra.numeratore, denominatore * altra.denominatore );
	}

	/**
	 * Restituisce la durata corrispondente alla sottrazione tra questa e la durata data.
	 *
	 * @param altra la durata da sottrarre.
	 * @return la differenza.
	 * @throws IllegalArgumentException se la sottrazione risulta in una durata negativa.
	 */
	public Durata meno( final Durata altra ) {
		return new Durata( numeratore * altra.denominatore - denominatore * altra.numeratore, denominatore * altra.denominatore );
	}

	/**
	 * Restituisce la durata data dal prodotto tra questa ed un intero dato.
	 *
	 * @param x l'intero per cui moltiplicare.
	 * @return il prodotto.
	 * @throws IllegalArgumentException se l'argomento è negativo, o nullo.
	 */
	public Durata per( final int x ) {
		if ( x <= 0 ) throw new IllegalArgumentException( "Non si può moltiplicare una durata per una grandezza negativa, o nulla." );
		return new Durata( numeratore * x, denominatore );
	}

	/**
	 * Restituisce la durata data dal rapporto tra questa ed un intero dato.
	 *
	 * @param x l'intero per cui dividere.
	 * @return il rapporto.
	 * @throws IllegalArgumentException se l'argomento è negativo, o nullo.
	 */
	public Durata diviso( final int x ) {
		if ( x <= 0 ) throw new IllegalArgumentException( "Non si può dividere una durata per una grandezza negativa, o nulla." );
		return new Durata( numeratore, denominatore * x );
	}

	/**
	 * Restituisce il numeratore della durata.
	 *
	 * @return il numeratore.
	 */
	public int numeratore() {
		return numeratore;
	}

	/**
	 * Restituisce il denominatore della durata.
	 *
	 * @return il denominatore.
	 */
	public int denominatore() {
		return denominatore;
	}

	@Override
	public int compareTo( final Durata altra ) {
		if ( this.equals( altra ) ) return 0;
		return numeratore * altra.denominatore < altra.numeratore * denominatore ? -1 : 1 ;
	}

	@Override
	public boolean equals( final Object other ) {
		if ( this == other )
			return true;
		if ( !( other instanceof Durata ) )
			return false;
		final Durata that = (Durata)other;
		return this.numeratore == that.numeratore && this.denominatore == that.denominatore;
	}

	@Override
	public int hashCode() {
		return ( numeratore << 16 ) | denominatore;
	}

	@Override
	public String toString() {
		return numeratore + "/" + denominatore;
	}

	/**
	 * Restituisce il massimo comun divisore tra due numeri.
	 * Assume che entrambe i numeri siano positivi (non effettua controlli in merito).
	 *
	 * @param a primo numero.
	 * @param b secondo numero.
 	 * @return il massimo comun divisore.
	 */
	private static int mcd( int a, int b ) {
		while ( b != 0 ) {
			int r = a % b;
			a = b;
			b = r;
	        }
		return a;
	}

}
