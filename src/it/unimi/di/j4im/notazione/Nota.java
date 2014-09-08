package it.unimi.di.j4im.notazione;

import it.unimi.di.j4im.riproduzione.Strumento;

/**
 * Questa classe rappresenta una nota.
 * 
 * Si osservi che l'altezza "complessiva" (ossia altezza ed ottava) viene qui
 * denotata secondo la <a href="http://en.wikipedia.org/wiki/Scientific_pitch_notation">notazione
 * scientifica</a>, (fatto salvo che sono usati nomi italiani di nota:
 * <samp>DO</samp>, <samp>RE</samp>… invece di quelli anglosassoni
 * <samp>C</samp>, <samp>B</samp>…). In particolare, il <samp>DO</samp> centrale
 * è <samp>DO4</samp>, altrimenti detto, l'ottava centrale è la numero 4.
 * 
 * @see Altezza
 * @see Alterazione
 * @see Durata
 * 
 */
public class Nota extends Simbolo {
	
	final static int OTTAVA_DEFAULT = 4;
	
	final Altezza altezza;
	final Alterazione alterazione;
	final int ottava;

	/** Costruisce una nota a partire dai parametri che la definiscono.
	 * 
	 * <p>Si osservi che i parametri devono essere tali che la nota sia compresa nell'intervallo 
	 * da <samp>D-1</samp> a <samp>SOL9</samp>, estremi inclusi.</p>
	 * 
	 * @param altezza l'altezza della nota.
	 * @param alterazione l'alterazione della nota.
	 * @param ottava l'ottava della nota.
	 * @param durata la durata della nota.
	 * 
	 * @throws IllegalArgumentException se la nota eccede l'intervallo da <samp>D-1</samp> a <samp>SOL9</samp>.
	 *
	 */
	public Nota( final Altezza altezza, final Alterazione alterazione, final int ottava, final Durata durata ) {
		super( durata );
		final int pitch = 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni; 
		if ( pitch < 0 || pitch > 127  ) throw new IllegalArgumentException( "La nota eccede l'intervallo D-1, SOL9" );
		this.altezza = altezza;
		this.alterazione = alterazione;
		this.ottava = ottava;
	}
	
	public Nota( final Altezza altezza, final Alterazione alterazione ) {
		this( altezza, alterazione, OTTAVA_DEFAULT, Simbolo.DURATA_DEFAULT );
	}
	
	public Nota( final Altezza altezza, final int ottava ) {
		this( altezza, Alterazione.NULLA, OTTAVA_DEFAULT, Simbolo.DURATA_DEFAULT );
	}

	public Nota( final Altezza altezza ) {
		this( altezza, OTTAVA_DEFAULT );
	}
	
	/** Costruisce una nota a partire dalla sua rappresentazione testuale.
	 * 
	 * <p>La rappresentazione testuale di una nota è data dalla rappresentazione testuale della sua altezza,
	 * seguita eventualmetne dal numero di ottava (se assente, verrà intesa l'ottava {@link Nota#OTTAVA_DEFAULT }, seguita
	 * eventualmente dal segno <samp>:</samp> e dalla rappresentazione testuale della durata (se assente,
	 * verrà intesa la durata {@link Simbolo#DURATA_DEFAULT}.</p>
	 * 
	 * @param nota la rappresentazione testuale della nota.
	 * @throws IllegalArgumentException se la nota eccede l'intervallo da <samp>D-1</samp> a <samp>SOL9</samp>, o se 
	 *         la rappresentazione testuale della durata è di formato scorretto.
	 */
	public Nota( final String nota ) {
		super( nota );
		altezza = Altezza.fromString( nota );
		String resto = nota.substring( altezza.toString().length() );
		alterazione = Alterazione.fromString( resto );
		resto = resto.substring( alterazione.toString().length() );
		if ( resto.length() == 0 ) {
			ottava = OTTAVA_DEFAULT;
		} else {
			final int posDuepunti = resto.indexOf( ":" );
			if ( posDuepunti == -1 )
				ottava = Integer.parseInt( resto ); // resto != ""
			else if ( posDuepunti > 0 )
				ottava = Integer.parseInt( resto.substring( 0, posDuepunti ) );
			else 
				ottava = OTTAVA_DEFAULT;
		}
		final int pitch = 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni; 
		if ( pitch < 0 || pitch > 127  ) throw new IllegalArgumentException( "La nota eccede l'intervallo D-1, SOL9" );
	}

	/** Costruisce una nota a partire dal pitch e dalla durata.
	 * 
	 * Si osservi che il pitch dev'essere compreso tra 0 (corrispondente a <samp>DO-1</samp>) e
	 * 127 (corrispondente a <samp>SOL9</samp>), estremi inclusi (il DO centrale <samp>DO4</samp> 
	 * corrisponde ad un pitch pari a 60). 
	 * 
	 * @param pitch il pitch della nota.
	 * @param durata la durata della nota.
	 * 
	 */
	public Nota( final int pitch, final Durata durata ) {
		super( durata );
		if ( pitch < 0 || pitch > 127  ) throw new IllegalArgumentException( "La nota eccede l'intervallo D0-1, SOL9" );
		switch ( pitch % 12 ) {
			case 0:
				altezza = Altezza.DO;
				alterazione = Alterazione.NULLA;
				break;
			case 1:
				altezza = Altezza.DO; 
				alterazione = Alterazione.DIESIS;
				break;
			case 2:
				altezza = Altezza.RE;
				alterazione = Alterazione.NULLA;
				break;
			case 3:
				altezza = Altezza.RE; 
				alterazione = Alterazione.DIESIS;
				break;
			case 4:
				altezza = Altezza.MI;
				alterazione = Alterazione.NULLA;
				break;
			case 5:
				altezza = Altezza.FA;
				alterazione = Alterazione.NULLA;
				break;
			case 6:
				altezza = Altezza.FA;
				alterazione = Alterazione.DIESIS;
				break;
			case 7:
				altezza = Altezza.SOL;
				alterazione = Alterazione.NULLA;
				break;
			case 8:
				altezza = Altezza.SOL; 
				alterazione = Alterazione.DIESIS;
				break;
			case 9:
				altezza = Altezza.LA;
				alterazione = Alterazione.NULLA;
				break;
			case 10:
				altezza = Altezza.LA; 
				alterazione = Alterazione.DIESIS;
				break;
			case 11:
				altezza = Altezza.SI; 
				alterazione = Alterazione.NULLA;
				break;
			default:
				altezza = null; 
				alterazione = null;
		}
		ottava = pitch / 12 - 1;
	}

	public Nota( final int pitch ) {
		this( pitch, Simbolo.DURATA_DEFAULT );
	}

	/** Restituisce il pitch della nota. */
	public int pitch() {
		return 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni;
	} 
		
	@Override
	public String toString() {
		return altezza.toString() + alterazione.toString() + ( ottava == 4 ? "" : "" + ottava ) + ( durata == Durata.SEMIMINIMA ? "" : ":" + durata );
	}

	@Override
	public void suona( final Strumento strumento ) {
		strumento.suona( this );
	}

	@Override
	public void suona( final Strumento strumento, final int intesita ) {
		strumento.suona( this, intesita );
	}
	
}
