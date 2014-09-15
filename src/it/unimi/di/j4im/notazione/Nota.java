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

/**
 * Una nota.
 * 
 * <p>Si osservi che l'altezza "complessiva" (ossia altezza ed ottava) viene qui
 * denotata secondo la <a href="http://en.wikipedia.org/wiki/Scientific_pitch_notation">notazione
 * scientifica</a>, (fatto salvo che sono usati nomi italiani di nota:
 * <samp>DO</samp>, <samp>RE</samp>… invece di quelli anglosassoni
 * <samp>C</samp>, <samp>B</samp>…). In particolare, il <samp>DO</samp> centrale
 * è <samp>DO4</samp>, altrimenti detto, l'ottava centrale è la numero 4.</p>
 * 
 * <p>Le note sono <em>immutabili</em> ed gli attributi sono variabili <code>final</code>
 * pubbliche (ed immutabili).</p>
 * 
 * @see Altezza
 * @see Alterazione
 * @see Durata
 * 
 */
public class Nota extends Simbolo {

	public final static int INTENSITA_DEFAULT = 64;
	public final static int OTTAVA_DEFAULT = 4;

	final Altezza altezza;
	final Alterazione alterazione;
	final int ottava;
	final int intensita;

	private Nota( final CostruttoreNota costruttore ) {
		super( costruttore.durata );
		altezza = costruttore.altezza;
		alterazione = costruttore.alterazione;
		ottava = costruttore.ottava;
		intensita = costruttore.intensita;
	}

	/** Costruisce una nota a partire dai parametri che la definiscono.
	 * 
	 * <p>Si osservi che i parametri devono essere tali che la nota sia compresa nell'intervallo 
	 * da <samp>D-1</samp> a <samp>SOL9</samp>, estremi inclusi.</p>
	 * 
	 * @param altezza l'altezza della nota.
	 * @param alterazione l'alterazione della nota.
	 * @param ottava l'ottava della nota.
	 * @param durata la durata della nota.
	 * @param intensita l'intensità della nota.
	 * 
	 * @throws IllegalArgumentException se la nota eccede l'intervallo da <samp>D-1</samp> a <samp>SOL9</samp>.
	 *
	 */
	public Nota( final Altezza altezza, final Alterazione alterazione, final int ottava, final Durata durata, final int intensita ) {
		this( CostruttoreNota.nuova()
				.altezza( altezza )
				.alterazione( alterazione )
				.ottava( ottava )
				.durata( durata )
				.intensita( intensita )
		);
	}

	/** Costruisce una nota data l'altezza (definendo gli altri parametri coi valori di default).
	 * 
	 * @param altezza l'altezza della nota.
	 */
	public Nota( final Altezza altezza ) {
		this( CostruttoreNota.nuova().altezza( altezza ) );
	}

	/** Costruisce una nota a partire dalla sua rappresentazione testuale.
	 * 
	 * <p>La rappresentazione testuale di una nota è data dalla rappresentazione testuale della sua altezza, seguita
	 * eventualemnte dalla rappresentazione testuale dell'alterazione,
	 * seguita eventualmetne dal numero di ottava (se assente, verrà intesa l'ottava {@link Nota#OTTAVA_DEFAULT }, seguita
	 * eventualmente dal segno <samp>:</samp> e dalla rappresentazione testuale della durata (se assente,
	 * verrà intesa la durata {@link Simbolo#DURATA_DEFAULT}.</p>
	 * 
	 * @param nota la rappresentazione testuale della nota.
	 * @throws IllegalArgumentException se la nota eccede l'intervallo da <samp>D-1</samp> a <samp>SOL9</samp>, o se 
	 *         la rappresentazione testuale della durata è di formato scorretto.
	 */
	public Nota( final String nota ) {
		this( CostruttoreNota.nuova().string( nota ) );
	}
	
	/** Costruisce una nota a partire dal pitch, durata ed intensitò.
	 * 
	 * Si osservi che il pitch dev'essere compreso tra 0 (corrispondente a <samp>DO-1</samp>) e
	 * 127 (corrispondente a <samp>SOL9</samp>), estremi inclusi (il DO centrale <samp>DO4</samp> 
	 * corrisponde ad un pitch pari a 60). 
	 * 
	 * @param pitch il pitch della nota.
	 * @param durata la durata della nota.
	 * @param intensita l'intensità della nota.
	 * 
	 */
	public Nota( final int pitch, final Durata durata, final int intensita ) {
		this( CostruttoreNota.nuova().pitch( pitch ).durata( durata ).intensita( intensita ) );
	}

	/** Costruisce una nota dato il pitch (definendo gli altri parametri coi valori di default).
	 * 
	 * @param pitch il pitch della nota.
	 */
	public Nota( final int pitch ) {
		this( CostruttoreNota.nuova().pitch( pitch ) );
	}

	/** Restituisce il pitch della nota.
	 * 
	 * @return Il pitch della nota.
	 */
	public int pitch() {
		return 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni;
	} 
	
	/** Resituisce l'intensità della nota
	 * 
	 * @return l'intensità.
	 */
	public int intensita() {
		return intensita;
	}
	
	@Override
	public String toString() {
		return 
				altezza.toString() + 
				alterazione.toString() + 
				( ottava == OTTAVA_DEFAULT ? "" : "" + ottava ) + 
				( durata == Simbolo.DURATA_DEFAULT ? "" : ":" + durata ) +
				( intensita == INTENSITA_DEFAULT ? "" : ":" + intensita );
	}

	@Override
	public void suonaCon( final Strumento strumento ) {
		strumento.suona( this );
	}
	
}
