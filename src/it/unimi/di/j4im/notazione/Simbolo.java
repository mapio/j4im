package it.unimi.di.j4im.notazione;

import it.unimi.di.j4im.riproduzione.Strumento;

/** Questa è la superclasse dei simboli della notazione musicale. */
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
	
	/** Costruisce un simbolo di durata pari a {@link #DURATA_DEFAULT}. */
	public Simbolo() {
		this( Durata.SEMIMINIMA );
	}
	
	/** Costruisce un simbolo a partire dalla sua rappresentazione testuale.
	 * 
	 * <p>Il simbolo comprende solo la durata, pertanto il costruttore si basa
	 * sulla presenza di <samp>:</samp> seguito dalla rappresentazione testuale 
	 * della durata, o assume la {@link #DURATA_DEFAULT}.
	 * 
	 * @param simbolo la rappresentazione testuale.
	 * 
	 */
	protected Simbolo( final String simbolo ) {
		final int posDuepunti = simbolo.indexOf( ":" );
		if ( posDuepunti > 0 )
			durata = Durata.fromString( simbolo.substring( posDuepunti + 1 ) );
		else
			durata = Simbolo.DURATA_DEFAULT;
	}
	
	/** Restituisce la durata del simbolo.
	 * 
	 * @return la durata.
	 * 
	 */
	public Durata durata() {
		return durata;
	}
	
	/** Suona la nota sullo strumento dato.	
	 * 
	 * @param strumento lo strumento.
	 * @param intesita l'intensità con cui suonare la nota (un valore compreso tra 0 e 127 estremi inclusi).
	 * 
	 */
	public abstract void suona( final Strumento strumento, final int intesita );
	
	public abstract void suona( final Strumento strumento );
	
		
}
