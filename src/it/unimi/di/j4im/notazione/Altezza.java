package it.unimi.di.j4im.notazione;

public enum Altezza {
	
	DO( 0 ), 
	RE( 2 ), 
	MI( 4 ), 
	FA( 5 ), 
	SOL( 7 ), 
	LA( 9 ), 
	SI( 11 );
	
	final int semitoni;
	
	Altezza( final int semitoni ) {
		this.semitoni = semitoni;
	}
		
	public static Altezza fromString( final String str ) {
		for ( Altezza altezza : Altezza.values() ) 
			if ( str.startsWith( altezza.toString() ) ) return altezza;
		throw new IllegalArgumentException( "Non ?? possibile dedurre l'altezza di " + str );
	}
}