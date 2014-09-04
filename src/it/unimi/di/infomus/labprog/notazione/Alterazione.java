package it.unimi.di.infomus.labprog.notazione;

public enum Alterazione {
	
	BEMOLLE( "♭", -1 ), 
	NULLA( "", 0 ), 
	DIESIS( "♯", +1 );

	final String rapp;
	final int semitoni;
	
	Alterazione( final String rapp, final int semitoni ) {
		this.rapp = rapp;
		this.semitoni = semitoni;
	}
	
	public static Alterazione fromString( final String str ) {
		Alterazione alterazione;
		if ( str.startsWith( BEMOLLE.rapp ) )
			alterazione = BEMOLLE;
		else if ( str.startsWith( DIESIS.rapp ) )
			alterazione = DIESIS;
		else alterazione = NULLA;
		return alterazione;
	}
	
	public String toString() {
		return rapp;
	}
}