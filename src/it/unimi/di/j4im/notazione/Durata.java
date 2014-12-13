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
		this.numeratore/=Durata.mcd(numeratore, denominatore);
		this.denominatore/=Durata.mcd(numeratore, denominatore);
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
	 * Utilizzato dal costruttore: restituisce il massimo comun divisore tra numeratore e denominatore, oppure -1 in caso di errori.
	 * @param a Il numeratore.
	 * @param b Il denominatore.
 	 * @return Il massimo comun divisore tra numeratore e denominatore.
	 */
	public static int mcd( int a, int b ) {
		while ( b != 0 ) {
			int r = a % b;
			a = b;
			b = r;
	        }
		return a;
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
	 * Restituisce una durata dalla somma tra questa durata e la durata argomento dell'invocazione.
	 * @param altra La durata da sommare.
	 * @return La somma.
	 */
	public Durata piu (Durata altra){
		return new Durata(numeratore*altra.denominatore+denominatore*altra.numeratore, denominatore*altra.denominatore);
	}
	
	/**
	 * Restituisce una durata dal modulo della differenza tra questa durata e la durata argomento dell'invocazione.
	 * @param altra La durata da sottrarre.
	 * @return Il modulo della differenza.
	 */	
	public Durata meno (Durata altra){
		return new Durata(numeratore*altra.denominatore-denominatore*altra.numeratore, denominatore*altra.denominatore);
	}
	
	/**
	 * Restituisce una durata dal prodotto tra questa durata e l'intero argomento dell'invocazione.
	 * @param x L'intero per cui moltiplicare.
	 * @return Il prodotto.
	 */
	public Durata per (int x){
		return new Durata(numeratore*x, denominatore);
	}
	
	/**
	 * Restituisce una durata dal quoziente tra questa durata e l'intero argomento dell'invocazione.
	 * @param x L'intero per cui dividere.
	 * @return Il quoziente.
	 */
	public Durata diviso (int x){
		return new Durata(numeratore, denominatore*x);
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
	
	/**
	 * Confronta questa durata con la durata argomento dell'invocazione e restituisce -1, zero, o 1 a seconda che la durata che esegue il metodo sia più breve, uguale o più lunga
	 * della durata specificata come argomento.
	 * @param altra La durata da confrontare.
	 * @return -1, zero, o 1 a seconda che la durata che esegue il metodo sia più breve, uguale o più lunga di quella specificata come argomento.
	 */
	public int compareTo (Durata altra){
		if (numeratore * altra.denominatore > altra.numeratore * denominatore)
			return 1;
		if (numeratore * altra.denominatore < altra.numeratore * denominatore)
			return -1;
		return 0;
	} 

	@Override
	public boolean equals( Object other ) {
		if ( this == other )
			return true;
		if ( !( other instanceof Durata ) )
			return false;
		final Durata that = (Durata)other;
		return this.numeratore == that.numeratore && this.denominatore == that.denominatore;
	}

	@Override
	public String toString() {
		return numeratore + "/" + denominatore;
	}
}
