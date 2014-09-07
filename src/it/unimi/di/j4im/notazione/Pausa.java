package it.unimi.di.j4im.notazione;

import it.unimi.di.j4im.riproduzione.Strumento;

/** Questa classe rappresenta una pausa. */
public class Pausa extends Simbolo {

	public Pausa( final Durata durata ) {
		super( durata );
	}
	
	public Pausa() {
		super();
	}
	
	public Pausa( final String pausa ) {
		super( pausa );
		if ( pausa.charAt( 0 ) != '_' ) throw new IllegalArgumentException( "La nota " + pausa + " non è una pausa" );
	}
	
	public String toString() {
		return "_" + ( durata == Durata.SEMIMINIMA ? "" : ":" + durata ); 
	}

	@Override
	public void suona( final Strumento strumento ) {
		strumento.suona( this );
	}

	@Override
	public void suona( final Strumento strumento, final int intesita ) {
		strumento.suona( this );
	}

}
