package it.unimi.di.j4im.notazione;

/** Questa classe rappresenta una alterazione musicale. */
public enum Alterazione {
	
	BEMOLLE( "♭", -1 ), 
	NULLA( "", 0 ), 
	DIESIS( "♯", +1 );

	/** La rappresentazione testuale dell'alterazione. */
	final String rapp;
	
	/** Il numero (relativo) di semitoni rappresentato da questa alterazione. */
	final int semitoni;
	
	Alterazione( final String rapp, final int semitoni ) {
		this.rapp = rapp;
		this.semitoni = semitoni;
	}
	
	/** Restituisce l'alterazione con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova l'alterazione.
	 * @return  Se la stringa inizia per ♯ (o <samp>#</samp>), o ♭, viene restituita
	 *          l'alterazione corrispondente, altrimenti viene restituita
	 *          l'alterazione nulla. 
	 */
	public static Alterazione fromString( final String str ) {
		Alterazione alterazione;
		if ( str.startsWith( BEMOLLE.rapp ) )
			alterazione = BEMOLLE;
		else if ( str.startsWith( DIESIS.rapp ) || str.startsWith( "#") )
			alterazione = DIESIS;
		else alterazione = NULLA;
		return alterazione;
	}
	
	@Override
	public String toString() {
		return rapp;
	}
}