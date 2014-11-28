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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unimi.di.j4im.riproduzione.Strumento;

/**
 * Una nota.
 * 
 * <p>
 * Una nota è caratterizzata da: <em>altezza</em>, {@link Durata durata} ed
 * <em>intensità</em>.
 * </p>
 * 
 * <p>
 * L'<em>altezza</em> di una nota è qui denotata secondo la <a
 * href="http://en.wikipedia.org/wiki/Scientific_pitch_notation">notazione
 * scientifica</a>, (fatto salvo che sono usati nomi italiani di nota:
 * <samp>DO</samp>, <samp>RE</samp>… invece di quelli anglosassoni
 * <samp>C</samp>, <samp>B</samp>…). In particolare, il <samp>DO</samp> centrale
 * è <samp>DO4</samp>, altrimenti detto, l'ottava centrale è la numero 4.
 * L'altezza di una nota è rappresentata in due modi equivalenti: dal
 * {@link Nome nome}, {@link Alterazione alterazione} ed ottava, oppure dal
 * <em>pitch</em> che è un numero compreso tra 0 (corrispondente a
 * <samp>DO-1</samp>) e 127 (corrispondente a <samp>SOL9</samp>).
 * </p>
 * 
 * <p>
 * L'<em>intensità</em> di una nota è un numero intero compreso tra 0
 * (corrispondente al silenzio) e 127.
 * </p>
 * 
 * @see Nome
 * @see Alterazione
 * @see Durata
 */
public class Nota extends Simbolo {

	/**
	 * Fabbricatore di note.
	 * 
	 * <p>
	 * Il fabbricatore di una nota può essere utilizzato per fabbricare una nota
	 * "per passi", definendo di volta in volta le varie caratteristiche, a
	 * partire da un insieme di valori di defalut: il <samp>DO</samp> (centrale
	 * senza alterazione), con {@link Simbolo#DURATA_DEFAULT} e
	 * {@link Nota#INTENSITA_DEFAULT}. Le caratteristiche possono essere
	 * modificate utilizzando i metodi col relativo nome (ad esempio:
	 * {@link #intensita(int)}, o {@link #nome(Nome)}) che possono essere
	 * invocati "in catena". Una volta definite le caratteristiche di interesse,
	 * si può ottenere la nota relativa invocando il metodo {@link #fabbrica()}
	 * (o passando il fabbricatore al costruttore
	 * {@link Nota#Nota(Fabbricatore)})·
	 * </p>
	 * 
	 * <p>
	 * Ad esempio, per costruire una nota traslata di un ottavo rispetto ad una
	 * nota data si può utilizzare il sequente codice:
	 * </p>
	 * 
	 * <pre>
	 * 		Nota originale = ... // la nota come è definita in origine
	 * 		Nota trasposta = Nota.fabbricatore().nota( originale ).pitch( originale.pitch() + 12 ).fabbrica();
	 * </pre>
	 * 
	 * <p>
	 * Si osservi che ogni medoto cambia solo i valori che può derivare dagli
	 * argomenti con cui è chiamato. Ad esempio, se al metodo
	 * {@link #string(String)} viene passata la stringa <samp>DO</samp> esso
	 * altererà solo il nome della nota, ma non la sua alterazione, col che se
	 * essa era in precedenza un diesis, tale resterà (anche se la stringa
	 * passata come argomento non presenta alterazione).
	 * </p>
	 */
	public static class Fabbricatore {

		private final static Pattern PATTERN = Pattern.compile( "^(?<altezza>DO|RE|MI|FA|SOL|LA|SI)(?<alterazione>(#|♭|♯|b))?(?<ottava>-?\\d+)?(?::(?<durata>\\d+/\\d+))?(?::(?<intensita>\\d+))?$" );

		Nome nome = Nome.DO;
		Alterazione alterazione = Alterazione.NULLA;
		int ottava = Nota.OTTAVA_DEFAULT;
		Durata durata = Simbolo.DURATA_DEFAULT;
		int intensita = Nota.INTENSITA_DEFAULT;

		// impedisce l'istanziazione se non attraverso il metodo
		// Nota.fabbricatore()
		private Fabbricatore() {}

		/**
		 * Imposta i valori del fabbricatore a quelli della nota data.
		 * 
		 * @param nota la nota.
		 * @return Il fabbricatore.
		 */
		public Fabbricatore nota( final Nota nota ) {
			nome = nota.nome;
			alterazione = nota.alterazione;
			ottava = nota.ottava;
			durata = nota.durata;
			intensita = nota.intensita;
			return this;
		}

		/**
		 * Imposta il nome della nota.
		 * 
		 * @param nome il nome.
		 * @return Il fabbricatore.
		 */
		public Fabbricatore nome( final Nome nome ) {
			this.nome = nome;
			controllaPitch();
			return this;
		}

		/**
		 * Imposta l'alterazione della nota.
		 * 
		 * @param alterazione l'alterazione.
		 * @return Il fabbricatore.
		 */
		public Fabbricatore alterazione( final Alterazione alterazione ) {
			this.alterazione = alterazione;
			controllaPitch();
			return this;
		}

		/**
		 * Imposta l'ottava della nota.
		 * 
		 * @param ottava l'ottava
		 * @return Il fabbricatore.
		 */
		public Fabbricatore ottava( final int ottava ) {
			this.ottava = ottava;
			controllaPitch();
			return this;
		}

		/**
		 * Imposta l'altezza della nota, dato il pitch.
		 * 
		 * <p>
		 * Questo metodo imposta l'altezza, l'alterazione e l'ottava della nota.
		 * </p>
		 * 
		 * @param pitch il pitch.
		 * @return Il fabbricatore.
		 * @throws IllegalArgumentException se il pitch non è compreso
		 *             nell'intervallo da 0 a 127.
		 */
		public Fabbricatore pitch( final int pitch ) {
			alterazione = Alterazione.NULLA;
			switch ( pitch % 12 ) {
				case 0:
					nome = Nome.DO;
					break;
				case 1:
					nome = Nome.DO;
					alterazione = Alterazione.DIESIS;
					break;
				case 2:
					nome = Nome.RE;
					break;
				case 3:
					nome = Nome.RE;
					alterazione = Alterazione.DIESIS;
					break;
				case 4:
					nome = Nome.MI;
					break;
				case 5:
					nome = Nome.FA;
					break;
				case 6:
					nome = Nome.FA;
					alterazione = Alterazione.DIESIS;
					break;
				case 7:
					nome = Nome.SOL;
					break;
				case 8:
					nome = Nome.SOL;
					alterazione = Alterazione.DIESIS;
					break;
				case 9:
					nome = Nome.LA;
					break;
				case 10:
					nome = Nome.LA;
					alterazione = Alterazione.DIESIS;
					break;
				case 11:
					nome = Nome.SI;
					break;
			}
			ottava = pitch / 12 - 1;
			controllaPitch();
			return this;
		}

		/**
		 * Imposta l'altezza della nota, data una frequenza (in Hz) nella scala
		 * ben temperata.
		 * 
		 * <p>
		 * Questo metodo imposta l'altezza, l'alterazione e l'ottava della nota
		 * la cui frequenza nella scala ben temperata è più vicina alla
		 * frequenza data.
		 * </p>
		 * 
		 * @param frequenza la frequenza.
		 * @return Il fabbricatore.
		 * @throws IllegalArgumentException se la frequenza porta ad una nota di
		 *             altezaz non valida.
		 */
		public Fabbricatore frequenza( final float frequenza ) {
			return pitch( (int)Math.round( PITCH_LA + ( Math.log( frequenza ) - Math.log( FREQUENZA_LA ) ) / Math.log( RAPPORTO_DI_FREQUENZA ) ) );
		}

		/**
		 * Imposta la durata della nota.
		 * 
		 * @param durata la durata.
		 * @return Il fabbricatore.
		 */
		public Fabbricatore durata( final Durata durata ) {
			this.durata = durata;
			return this;
		}

		/**
		 * Imposta l'intensità della nota.
		 * 
		 * @param intensita l'intensità.
		 * @return Il fabbricatore.
		 * @throws IllegalArgumentException se l'ìntensità non è compresa
		 *             nell'intervallo da 0 a 127.
		 */
		public Fabbricatore intensita( final int intensita ) {
			if ( intensita < 0 || intensita > 127 )
				throw new IllegalArgumentException( "L'intensità dev'essere compresa tra 0 e 127 (estremi inclusi)." );
			this.intensita = intensita;
			return this;
		}

		/**
		 * Imposta i valori dati dalla rappresentazione testuale della nota.
		 * 
		 * <p>
		 * Per una descrizione della rappresentazione testuale della nota si
		 * veda il costruttore {@link Nota#Nota(String)}.
		 * </p>
		 * 
		 * @param nota la rappresentazione testuale.
		 * @return Il fabbricatore.
		 */
		public Fabbricatore string( final String nota ) {
			Matcher m = PATTERN.matcher( nota );
			if ( !m.find() )
				throw new IllegalArgumentException( "Impossibile comprendere la nota " + nota );
			if ( m.group( "altezza" ) != null )
				nome = Nome.valueOf( m.group( "altezza" ) );
			if ( m.group( "alterazione" ) != null )
				alterazione = Alterazione.fromString( m.group( "alterazione" ) );
			if ( m.group( "ottava" ) != null )
				try {
					ottava = Integer.parseInt( m.group( "ottava" ) );
				} catch ( NumberFormatException e ) {
					throw new IllegalArgumentException( "Impossibile determinare l'ottava di " + nota );
				}
			controllaPitch();
			if ( m.group( "durata" ) != null )
				durata = Durata.fromString( m.group( "durata" ) );
			if ( m.group( "intensita" ) != null )
				try {
					intensita( Integer.parseInt( m.group( "intensita" ) ) );
				} catch ( NumberFormatException e ) {
					throw new IllegalArgumentException( "Impossibile determinare l'intensità di " + nota );
				}
			return this;
		}

		/**
		 * Fabbrica una nota a partire dai parametri definiti nel fabbricatore.
		 * 
		 * @return la nota.
		 */
		public Nota fabbrica() {
			return new Nota( this );
		}

		@Override
		public String toString() {
			return "Nota.Fabbricatore<" + nome.toString() + alterazione.toString() + ( ottava == Nota.OTTAVA_DEFAULT ? "" : "" + ottava ) + ( durata == Simbolo.DURATA_DEFAULT ? "" : ":" + durata ) + ( intensita == Nota.INTENSITA_DEFAULT ? "" : ":" + intensita ) + ">";

		}

		/**
		 * Verifica che il pitch sia nell'intervallo da 0 a 127.
		 * 
		 * @throws IllegalArgumentException se il pitch non è valido.
		 */
		private void controllaPitch() {
			final int pitch = 12 * ( ottava + 1 ) + nome.semitoni + alterazione.semitoni;
			if ( pitch < 0 || pitch > 127 )
				throw new IllegalArgumentException( "La nota eccede l'intervallo D-1, SOL9." );
		}

	}

	/**
	 * Il rapporto tra le frequenze di due semitoni successivi nella scala ben
	 * temperata.
	 */
	public final static double RAPPORTO_DI_FREQUENZA = Math.pow( 2.0, 1.0 / 12.0 );

	/** La frequenza (in Hz) della nota <samp>LA</samp>. */
	public final static int FREQUENZA_LA = 440;

	/** Il pitch della nota <samp>LA</samp>. */
	public final static int PITCH_LA = new Nota( "LA" ).pitch();

	public final static int INTENSITA_DEFAULT = 64;
	public final static int OTTAVA_DEFAULT = 4;

	private final Nome nome;
	private final Alterazione alterazione;
	private final int ottava;
	private final int intensita;

	/**
	 * Resituisce un fabbricatore.
	 * 
	 * @return Un fabbricatore.
	 * 
	 * @see Fabbricatore
	 */
	public static Fabbricatore fabbricatore() {
		return new Fabbricatore();
	}

	/**
	 * Costruisce una nota dato un fabbricatore.
	 * 
	 * @param fabbricatore il fabbricatore.
	 */
	private Nota( final Fabbricatore fabbricatore ) {
		super( fabbricatore.durata );
		nome = fabbricatore.nome;
		alterazione = fabbricatore.alterazione;
		ottava = fabbricatore.ottava;
		intensita = fabbricatore.intensita;
	}

	/**
	 * Costruisce una nota a partire dai parametri che la definiscono.
	 * <p>
	 * Si osservi che i parametri devono essere tali che la nota sia compresa
	 * nell'intervallo da <samp>D-1</samp> a <samp>SOL9</samp>, estremi inclusi.
	 * </p>
	 * 
	 * @param nome il nome della nota.
	 * @param alterazione l'alterazione della nota.
	 * @param ottava l'ottava della nota.
	 * @param durata la durata della nota.
	 * @param intensita l'intensità della nota.
	 * @throws IllegalArgumentException se l'altezza della nota eccede
	 *             l'intervallo da <samp>D-1</samp> a <samp>SOL9</samp>.
	 */
	public Nota( final Nome nome, final Alterazione alterazione, final int ottava, final Durata durata, final int intensita ) {
		this( fabbricatore().nome( nome ).alterazione( alterazione ).ottava( ottava ).durata( durata ).intensita( intensita ) );
	}

	/**
	 * Costruisce una nota dato il nome (definendo gli altri parametri coi
	 * valori di default).
	 * 
	 * @param nome il nome della nota.
	 */
	public Nota( final Nome nome ) {
		this( fabbricatore().nome( nome ) );
	}

	/**
	 * Costruisce una nota a partire dalla sua rappresentazione testuale.
	 * 
	 * <p>
	 * La rappresentazione testuale di una nota è data dalla rappresentazione
	 * testuale del suo {@link Nome#valueOf(String) nome}, seguita eventualmnte
	 * dalla rappresentazione testuale dell'
	 * {@link Alterazione#fromString(String) alterazione}, seguita eventualmetne
	 * dal numero di ottava (se assente, verrà intesa l'ottava
	 * {@link Nota#OTTAVA_DEFAULT}), seguita eventualmente dal segno
	 * <samp>:</samp> e dalla rappresentazione testuale della
	 * {@link Durata#fromString(String) durata} (se assente, verrà intesa la
	 * durata {@link Simbolo#DURATA_DEFAULT}), seguita in fine eventualmente dal
	 * segno <samp>:</samp> e dal numero corrispondente all'intensità (se
	 * assente, verrà intesa l'intensità {@link #INTENSITA_DEFAULT}).
	 * </p>
	 * 
	 * <p>
	 * Ad esempio, il <samp>DO</samp> diesis un'ottava sopra la centrale con
	 * durata di una semimininma ed intensità media ha rappresentazione
	 * <samp>DO#5:1/4:64</samp>.
	 * </p>
	 * 
	 * @param nota la rappresentazione testuale della nota.
	 * @throws IllegalArgumentException se la nota eccede l'intervallo da
	 *             <samp>D-1</samp> a <samp>SOL9</samp>, o se la
	 *             rappresentazione testuale della durata, o intensità, è di
	 *             formato scorretto.
	 */
	public Nota( final String nota ) {
		this( fabbricatore().string( nota ) );
	}

	/**
	 * Costruisce una nota a partire dal pitch, durata ed intensità.
	 * 
	 * <p>
	 * Si osservi che il pitch dev'essere compreso tra 0 (corrispondente a
	 * <samp>DO-1</samp>) e 127 (corrispondente a <samp>SOL9</samp>), estremi
	 * inclusi (il DO centrale <samp>DO4</samp> corrisponde ad un pitch pari a
	 * 60).
	 * </p>
	 * 
	 * @param pitch il pitch della nota.
	 * @param durata la durata della nota.
	 * @param intensita l'intensità della nota.
	 */
	public Nota( final int pitch, final Durata durata, final int intensita ) {
		this( fabbricatore().pitch( pitch ).durata( durata ).intensita( intensita ) );
	}

	/**
	 * Costruisce una nota dato il pitch (definendo gli altri parametri coi
	 * valori di default).
	 * 
	 * @param pitch il pitch della nota.
	 */
	public Nota( final int pitch ) {
		this( fabbricatore().pitch( pitch ) );
	}

	/**
	 * Costruisce una nota a partire da una frequenza (in Hz), durata ed
	 * intensità.
	 * 
	 * <p>
	 * Costruisce la nota la cui frequenza nella scala ben temperata è più
	 * vicina alla frequenza data.
	 * </p>
	 * 
	 * @param frequenza la frequenza della nota.
	 * @param durata la durata della nota.
	 * @param intensita l'intensità della nota.
	 */
	public Nota( final float frequenza, final Durata durata, final int intensita ) {
		this( fabbricatore().frequenza( frequenza ).durata( durata ).intensita( intensita ) );
	}

	/**
	 * Costruisce una nota a partire da una frequenza (in HZ e definendo gli
	 * altri parametri coi valori di default).
	 * 
	 * @param frequenza la frequenza della nota.
	 */
	public Nota( final float frequenza ) {
		this( fabbricatore().frequenza( frequenza ) );
	}

	/**
	 * Restituisce il pitch della nota.
	 * 
	 * @return Il pitch della nota.
	 */
	public int pitch() {
		return 12 * ( ottava + 1 ) + nome.semitoni + alterazione.semitoni;
	}

	/**
	 * Restituisce la frequenza (in Hz) della nota.
	 * 
	 * @return la frequenza.
	 */
	public float frequenza() {
		return (float)( FREQUENZA_LA * Math.pow( RAPPORTO_DI_FREQUENZA, pitch() - PITCH_LA ) );
	}

	/**
	 * Resituisce l'intensità della nota
	 * 
	 * @return l'intensità.
	 */
	public int intensita() {
		return intensita;
	}

	@Override
	public String toString() {
		return nome.toString() + alterazione.toString() + ( ottava == OTTAVA_DEFAULT ? "" : "" + ottava ) + ( durata == Simbolo.DURATA_DEFAULT ? "" : ":" + durata ) + ( intensita == INTENSITA_DEFAULT ? "" : ":" + intensita );
	}

	@Override
	public void suonaCon( final Strumento strumento ) {
		strumento.suona( this );
	}

}
