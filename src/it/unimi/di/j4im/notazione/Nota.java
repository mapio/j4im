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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static class Costruttore {

		private final static Pattern PATTERN = Pattern.compile( "^(?<altezza>DO|RE|MI|FA|SOL|LA|SI)(?<alterazione>(#|♭|♯))?(?<ottava>-?\\d+)?(?::(?<durata>\\d+/\\d+))?(?::(?<intensita>\\d+))?$" ); 

		Altezza altezza = Altezza.DO;
		Alterazione alterazione = Alterazione.NULLA;
		Durata durata = Simbolo.DURATA_DEFAULT;
		int ottava = Nota.OTTAVA_DEFAULT;
		int intensita = Nota.INTENSITA_DEFAULT;
		
		private Costruttore() {}

		public Costruttore nota( final Nota nota ) {
			altezza = nota.altezza;
			alterazione = nota.alterazione;
			ottava = nota.ottava;
			durata = nota.durata;
			intensita = nota.intensita;
			return this;
		}
		
		public Costruttore altezza( final Altezza altezza ) {
			this.altezza = altezza;
			controllaPitch();
			return this;
		}

		public Costruttore alterazione( final Alterazione alterazione ) {
			this.alterazione = alterazione;
			controllaPitch();
			return this;
		}

		public Costruttore ottava( final int ottava ) {
			this.ottava = ottava;
			controllaPitch();
			return this;
		}

		public Costruttore pitch( final int pitch ) {
			alterazione = Alterazione.NULLA;
			switch ( pitch % 12 ) {
				case 0:
					altezza = Altezza.DO;
					break;
				case 1:
					altezza = Altezza.DO; 
					alterazione = Alterazione.DIESIS;
					break;
				case 2:
					altezza = Altezza.RE;
					break;
				case 3:
					altezza = Altezza.RE; 
					alterazione = Alterazione.DIESIS;
					break;
				case 4:
					altezza = Altezza.MI;
					break;
				case 5:
					altezza = Altezza.FA;
					break;
				case 6:
					altezza = Altezza.FA;
					alterazione = Alterazione.DIESIS;
					break;
				case 7:
					altezza = Altezza.SOL;
					break;
				case 8:
					altezza = Altezza.SOL; 
					alterazione = Alterazione.DIESIS;
					break;
				case 9:
					altezza = Altezza.LA;
					break;
				case 10:
					altezza = Altezza.LA; 
					alterazione = Alterazione.DIESIS;
					break;
				case 11:
					altezza = Altezza.SI; 
					break;
			}
			ottava = pitch / 12 - 1;
			controllaPitch();
			return this;
		}
		
		public Costruttore durata( final Durata durata ) {
			this.durata = durata;
			return this;
		}
		
		public Costruttore intensita( final int intensita ) {
			if ( intensita < 0 || intensita > 127 ) throw new IllegalArgumentException( "L'intensità dev'essere compresa tra 0 e 127 (estremi inclusi)." );
			this.intensita = intensita;
			return this;
		}
			
		public Costruttore string( final String nota ) {
			Matcher m = PATTERN.matcher( nota );
			if ( ! m.find() ) throw new IllegalArgumentException( "Impossibile comprendere la nota " + nota );
			if ( m.group( "altezza" ) != null )
				altezza = Altezza.fromString( m.group( "altezza" ) );
			if ( m.group( "alterazione" ) != null )
				alterazione = Alterazione.fromString( m.group( "alterazione" ) );
			if ( m.group( "ottava" ) != null ) try {
				ottava = Integer.parseInt( m.group( "ottava" ) );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( "Impossibile determinare l'ottava di " + nota );
			}
			controllaPitch();
			if ( m.group( "durata" ) != null )
				durata = Durata.fromString( m.group( "durata" ) );
			if ( m.group( "intensita" ) != null ) try {
				intensita( Integer.parseInt( m.group( "intensita" ) ) );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( "Impossibile determinare l'intensità di " + nota );
			} 
			return this;
		}

		public Nota costruisci() {
			return new Nota( altezza, alterazione, ottava, durata, intensita );
		}
		
		@Override
		public String toString() {
			return "CostruttoreNota<"+
					altezza.toString() + 
					alterazione.toString() + 
					( ottava == Nota.OTTAVA_DEFAULT ? "" : "" + ottava ) + 
					( durata == Simbolo.DURATA_DEFAULT ? "" : ":" + durata ) +
					( intensita == Nota.INTENSITA_DEFAULT ? "" : ":" + intensita ) +">";
			
		}
		
		private void controllaPitch() {
			final int pitch = 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni; 
			if ( pitch < 0 || pitch > 127  ) throw new IllegalArgumentException( "La nota eccede l'intervallo D-1, SOL9." );
		}
		
	}

	public final static int INTENSITA_DEFAULT = 64;
	public final static int OTTAVA_DEFAULT = 4;

	private final Altezza altezza;
	private final Alterazione alterazione;
	private final int ottava;
	private final int intensita;

	public static Costruttore costruttore() {
		return new Costruttore();
	}
	
	private Nota( final Costruttore costruttore ) {
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
		this( costruttore()
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
		this( costruttore().altezza( altezza ) );
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
		this( costruttore().string( nota ) );
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
		this( costruttore().pitch( pitch ).durata( durata ).intensita( intensita ) );
	}

	/** Costruisce una nota dato il pitch (definendo gli altri parametri coi valori di default).
	 * 
	 * @param pitch il pitch della nota.
	 */
	public Nota( final int pitch ) {
		this( costruttore().pitch( pitch ) );
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
