package it.unimi.di.j4im.notazione;

public class Nota extends Simbolo {
	
	final static int OTTAVA_DEFAULT = 4;
	final Altezza altezza;
	final Alterazione alterazione;
	final int ottava;

	public Nota( final Altezza altezza, final Alterazione alterazione, final int ottava, final Durata durata ) {
		super( durata );
		this.altezza = altezza;
		this.alterazione = alterazione;
		this.ottava = ottava;
	}
	
	public Nota( final Altezza altezza, final Alterazione alterazione ) {
		this( altezza, alterazione, OTTAVA_DEFAULT, Simbolo.DURATA_DEFAULT );
	}
	
	public Nota( final Altezza altezza, final int ottava ) {
		this( altezza, Alterazione.NULLA, OTTAVA_DEFAULT, Simbolo.DURATA_DEFAULT );
	}

	public Nota( final Altezza altezza ) {
		this( altezza, OTTAVA_DEFAULT );
	}
	
	public static Nota fromPitch( final int pitch, final Durata durata ) {
		Altezza altezza = null;
		Alterazione alterazione = Alterazione.NULLA;
		switch ( pitch % 12 ) {
			case 0:
				altezza = Altezza.DO; 
				break;
			case 1:
				altezza = Altezza.DO; 
				alterazione = Alterazione.DIESIS;
				break;
			case 2:
				altezza = Altezza.RE; 
				break;
			case 3:
				altezza = Altezza.RE; 
				alterazione = Alterazione.DIESIS;
				break;
			case 4:
				altezza = Altezza.MI; 
				break;
			case 5:
				altezza = Altezza.FA; 
				break;
			case 6:
				altezza = Altezza.FA; 
				alterazione = Alterazione.DIESIS;
				break;
			case 7:
				altezza = Altezza.SOL; 
				break;
			case 8:
				altezza = Altezza.SOL; 
				alterazione = Alterazione.DIESIS;
				break;
			case 9:
				altezza = Altezza.LA; 
				break;
			case 10:
				altezza = Altezza.LA; 
				alterazione = Alterazione.DIESIS;
				break;
			case 11:
				altezza = Altezza.SI; 
				break;				
		}
		return new Nota( altezza, alterazione, pitch / 12 - 1, durata );
	}

	public static Nota fromPitch( final int pitch ) {
		return fromPitch( pitch, Simbolo.DURATA_DEFAULT );
	}
	
	public static Nota fromString( final String nota ) {
		Altezza altezza = Altezza.fromString( nota );
		String resto = nota.substring( altezza.toString().length() );
		Alterazione alterazione = Alterazione.fromString( resto );
		resto = resto.substring( alterazione.toString().length() );
		if ( resto.length() == 0 ) return new Nota( altezza, alterazione );
		int posDuepunti = resto.indexOf( ":" );
		int ottava = OTTAVA_DEFAULT;
		Durata durata = Simbolo.DURATA_DEFAULT;
		if ( posDuepunti == -1 )
			ottava = Integer.parseInt( resto ); // resto != ""
		else {
			if ( posDuepunti > 0 )
				ottava = Integer.parseInt( resto.substring( 0, posDuepunti ) );
			durata = Durata.fromString( resto.substring( posDuepunti + 1 ) );
		}
		return new Nota( altezza, alterazione, ottava, durata );
	}
	
	public int pitch() {
		return 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni;
	} 
		
	public String toString() {
		return altezza.toString() + alterazione.toString() + ( ottava == 4 ? "" : "" + ottava ) + ( durata == Durata.SEMIMINIMA ? "" : ":" + durata );
	}
	
	public static void main( String[] args ) {
		}
	
}
