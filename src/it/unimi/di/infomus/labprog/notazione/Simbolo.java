package it.unimi.di.infomus.labprog.notazione;

public class Simbolo {
	
	final Durata durata;
	final static Durata DURATA_DEFAULT = Durata.SEMIMINIMA;

	public Simbolo( final Durata durata ) {
		this.durata = durata;
	}
	
	public Simbolo() {
		this( Durata.SEMIMINIMA );
	}
	
	public Durata durata() {
		return durata;
	}
	
	public int durata( final int bpm ) {
		return (int)( 1000 * ( 60.0 / bpm ) * ( 4.0 / durata.denominatore() ) );
	}
}
