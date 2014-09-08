package it.unimi.di.j4im.notazione;

import it.unimi.di.j4im.riproduzione.Strumento;

/** Questa classe rappresenta una pausa. */
public class Pausa extends Simbolo {

	/**
	 * Costruisce una pausa data la sua durata.
	 * 
	 * Una pausa è rappresentata come <samp>_</samp> eventualmente seguito da
	 * <samp>:</samp> e dalla durata.
	 * 
	 * @param durata la durata della pausa.
	 * 
	 */
	public Pausa( final Durata durata ) {
		super( durata );
	}
	
	public Pausa() {
		super();
	}
	
	/** Costruisce una pausa data la sua rappresentazione testuale.
	 * 
	 * <p>La rappresentazione testuale di una pausa è data da <samp>_</samp>, seguita
	 * eventualmente dal segno <samp>:</samp> e dalla rappresentazione testuale della 
	 * durata (se assente, verrà intesa la durata {@link Simbolo#DURATA_DEFAULT}.</p>
	 *  
	 * @param pausa la rappresentazione testuale.
	 * @throws IllegalArgumentException se la stringa non inizia per <samp>_</samp>, o se 
	 *         la rappresentazione testuale della durata è di formato scorretto.
	 *         
	 */
	public Pausa( final String pausa ) {
		super( pausa );
		if ( pausa.charAt( 0 ) != '_' ) throw new IllegalArgumentException( "La nota " + pausa + " non è una pausa" );
	}
	
	@Override
	public String toString() {
		return "_" + ( durata == Durata.SEMIMINIMA ? "" : ":" + durata ); 
	}

	@Override
	public void suonaCon( final Strumento strumento ) {
		strumento.suona( this );
	}

	@Override
	public void suonaCon( final Strumento strumento, final int intesita ) {
		strumento.suona( this );
	}

}
