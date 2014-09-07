package it.unimi.di.j4im.notazione;

/** Questa classe rappresenta l'altezza di una nota. */
public enum Altezza {
	
	DO( 0 ), 
	RE( 2 ), 
	MI( 4 ), 
	FA( 5 ), 
	SOL( 7 ), 
	LA( 9 ), 
	SI( 11 );
	
	/** La distanza in semitoni dall'inizio dell'ottava. */
	final int semitoni;
	
	Altezza( final int semitoni ) {
		this.semitoni = semitoni;
	}

	/** Restituisce l'altezza con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova l'altezza.
	 * @return  Se la stringa inizia per uno dei nomi di nota, 
	 *          restituisce l'altezza corrispondente.
	 * @throws IllegalArgumentException se la stringa non inizia 
	 *         con un nome di nota.
	 */		
	public static Altezza fromString( final String str ) {
		for ( Altezza altezza : Altezza.values() ) 
			if ( str.startsWith( altezza.toString() ) ) return altezza;
		throw new IllegalArgumentException( "Non ?? possibile dedurre l'altezza di " + str );
	}
}