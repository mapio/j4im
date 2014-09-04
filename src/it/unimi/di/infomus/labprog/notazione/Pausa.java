package it.unimi.di.infomus.labprog.notazione;

public class Pausa extends Simbolo {

	public Pausa( final Durata durata ) {
		super( durata );
	}
	
	public Pausa() {
		super();
	}
	
	public static Pausa fromString( final String pausa ) {
		if ( pausa.charAt( 0 ) != '_' ) throw new IllegalArgumentException( "La nota " + pausa + " non è una pausa" );
		if ( pausa.charAt( 1 ) == ':' )
			return new Pausa( Durata.fromString( pausa.substring( 2 ) ) );
		return new Pausa();
	}
	
	public String toString() {
		return "_" + ( durata == Durata.SEMIMINIMA ? "" : ":" + durata ); 
	}

}
