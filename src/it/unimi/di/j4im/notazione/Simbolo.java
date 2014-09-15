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

import it.unimi.di.j4im.riproduzione.Strumento;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Superclasse (astratta) dei simboli della notazione musicale. 
 * 
 * @see Durata
 *
 */
public abstract class Simbolo {

	/** La durata di "default" del simbolo, pari ad una semiminima (1/4 di misura). */
	final static Durata DURATA_DEFAULT = Durata.SEMIMINIMA;

	/** La durata del simbolo. */
	final Durata durata;

	/** Costruisce un simbolo della durata assegnata.
	 * 
	 * @param durata la durata.
	 * 
	 */
	public Simbolo( final Durata durata ) {
		this.durata = durata;
	}
	
	
	/** Restituisce la durata di un simbolo ottenuta effettuando il parsing della 
	 *  sua rappresentazione testuale col pattern dato. Se il parsing ha successo, 
	 *  ma il gruppo 'durata' non ha match, viene restiutita la {@link #DURATA_DEFAULT}.
	 * 
	 * @param pattern il pattern da usare per il parsing (deve contenere un <em>named group</em> di nome <code>durata</code>).
	 * @param simbolo la rappresentazione testuale.
	 * @return la durata.
	 */
	static Durata fromString( final Pattern pattern, final String simbolo ) {
		final Matcher m = pattern.matcher( simbolo );
		if ( ! m.find() ) throw new IllegalArgumentException( "Impossibile determinare la durata di " + simbolo );
		final String durata = m.group( "durata" );
		return durata != null ? Durata.fromString( durata ) : DURATA_DEFAULT;
	}
	
	/** Restituisce la durata del simbolo.
	 * 
	 * @return La durata.
	 * 
	 */
	public Durata durata() {
		return durata;
	}
	
	/** Suona il simbolo con lo strumento dato.	
	 * 
	 * @param strumento lo strumento.
	 *
	 */
	public abstract void suonaCon( final Strumento strumento );
	
	/** Restituice un array di simboli ottenuto a partire dalla loro rappresentazione testuale. 
	 *
	 * @param str un elenco di simboli separati da <samp>,</samp>.
	 * @return I vettore di simboli.
	 * @throws IllegalArgumentException se la rappresentazione testuale dei simboli rispetta il formato consentito.
	 * 
	 */
	public static Simbolo[] simboli( final String str ) {
		final String[] strs = str.split( "," );
		final Simbolo[] simboli = new Simbolo[ strs.length ];
		for ( int i = 0; i < simboli.length; i++ )
			if ( strs[ i ].charAt( 0 ) == '_' ) 
				simboli[ i ] = new Pausa( strs[ i ] );
			else 
				simboli[ i ] = new Nota( strs[ i ] );
		return simboli;
	}
	
}
