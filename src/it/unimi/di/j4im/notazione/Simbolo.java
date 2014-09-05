package it.unimi.di.j4im.notazione;

import it.unimi.di.j4im.riproduzione.Strumento;

public abstract class Simbolo {
	
	final Durata durata;
	final static Durata DURATA_DEFAULT = Durata.SEMIMINIMA;

	public Simbolo( final Durata durata ) {
		this.durata = durata;
	}
	
	public Simbolo() {
		this( Durata.SEMIMINIMA );
	}
	
	protected Simbolo( final String simbolo ) {
		final int posDuepunti = simbolo.indexOf( ":" );
		if ( posDuepunti > 0 )
			durata = Durata.fromString( simbolo.substring( posDuepunti + 1 ) );
		else
			durata = Simbolo.DURATA_DEFAULT;
	}
	
	public Durata durata() {
		return durata;
	}
		
	public abstract void suona( final Strumento strumento );
	public abstract void suona( final Strumento strumento, final int intesita );
		
}
