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

import it.unimi.di.j4im.riproduzione.Strumento;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Una pausa.
 * 
 * <p>
 * Una pausa è caratterizzata esclusivamente dalla sua {@link Durata durata}.
 * </p>
 * 
 * @see Durata
 */
public class Pausa extends Simbolo {

	private final static Pattern PATTERN = Pattern.compile( "^_(?::(?<durata>\\d+/\\d+))?$" );

	/**
	 * Costruisce una pausa data la sua durata.
	 * 
	 * @param durata la durata della pausa.
	 * 
	 */
	public Pausa( final Durata durata ) {
		super( durata );
	}

	/**
	 * Costruisce una pausa data la sua rappresentazione testuale.
	 * 
	 * <p>
	 * La rappresentazione testuale di una pausa è data da <samp>_</samp>,
	 * seguita eventualmente dal segno <samp>:</samp> e dalla rappresentazione
	 * testuale della durata (se assente, verrà intesa la durata
	 * {@link Simbolo#DURATA_DEFAULT}.
	 * </p>
	 * 
	 * @param pausa la rappresentazione testuale.
	 * @throws IllegalArgumentException se la stringa non inizia per
	 *             <samp>_</samp>, o se la rappresentazione testuale della
	 *             durata è di formato scorretto.
	 * 
	 */
	public Pausa( final String pausa ) {
		super( fromString( pausa ) );
	}

	/**
	 * Restituisce la durata di una pausa ottenuta effettuando il parsing della
	 * sua rappresentazione testuale. Se il parsing ha successo, ma il gruppo
	 * 'durata' non ha match, viene restiutita la {@link #DURATA_DEFAULT}.
	 * 
	 * @param pausa la rappresentazione testuale.
	 * @return la durata.
	 */
	private static Durata fromString( final String pausa ) {
		final Matcher m = PATTERN.matcher( pausa );
		if ( !m.find() )
			throw new IllegalArgumentException( "Impossibile determinare la durata di " + pausa );
		final String durata = m.group( "durata" );
		return durata != null ? Durata.fromString( durata ) : DURATA_DEFAULT;
	}

	@Override
	public void suonaCon( final Strumento strumento ) {
		strumento.suona( this );
	}

	@Override
	public String toString() {
		return "_" + ( durata == Simbolo.DURATA_DEFAULT ? "" : ":" + durata );
	}

}
