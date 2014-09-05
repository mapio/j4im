package it.unimi.di.j4im.notazione;

public enum Durata {
	
	SEMIBREVE( 1 ), 
	MINIMA( 2 ), 
	SEMIMINIMA( 4 ), 
	CROMA( 8 ), 
	SEMICROMA( 16 ), 
	BISCROMA( 32 ), 
	SEMIBISCROMA( 64 );
	
	final int denominatore;
	
	Durata( final int denominatore ) {
		this.denominatore = denominatore;
	}
	
	public static Durata fromString( final String str ) {
		for ( Durata d : Durata.values() )
			if ( str.equals( d.toString() ) ) return d;
		throw new IllegalArgumentException( "Impossibile determinare la durata di " + str );
	}

	public int denominatore() {
		return denominatore;
	}

	public int ms( final int bpm ) {
		return (int)( 1000 * ( 60.0 / bpm ) * ( 4.0 / denominatore ) );
	}
	
	public int ticks( final double RESOLUTION ) {
		return (int)( 4 * RESOLUTION / denominatore );
	}

	public String toString() {
		return "1/" + denominatore();
	}
}