package it.unimi.di.j4im.notazione;

import it.unimi.di.j4im.riproduzione.Strumento;

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
	
	public Nota( final String nota ) {
		super( nota );
		altezza = Altezza.fromString( nota );
		String resto = nota.substring( altezza.toString().length() );
		alterazione = Alterazione.fromString( resto );
		resto = resto.substring( alterazione.toString().length() );
		if ( resto.length() == 0 ) {
			ottava = OTTAVA_DEFAULT;
		} else {
			final int posDuepunti = resto.indexOf( ":" );
			if ( posDuepunti == -1 )
				ottava = Integer.parseInt( resto ); // resto != ""
			else if ( posDuepunti > 0 )
				ottava = Integer.parseInt( resto.substring( 0, posDuepunti ) );
			else 
				ottava = OTTAVA_DEFAULT;
		}
	}

	public Nota( final int pitch, final Durata durata ) {
		super( durata );
		switch ( pitch % 12 ) {
			case 0:
				altezza = Altezza.DO;
				alterazione = Alterazione.NULLA;
				break;
			case 1:
				altezza = Altezza.DO; 
				alterazione = Alterazione.DIESIS;
				break;
			case 2:
				altezza = Altezza.RE;
				alterazione = Alterazione.NULLA;
				break;
			case 3:
				altezza = Altezza.RE; 
				alterazione = Alterazione.DIESIS;
				break;
			case 4:
				altezza = Altezza.MI;
				alterazione = Alterazione.NULLA;
				break;
			case 5:
				altezza = Altezza.FA;
				alterazione = Alterazione.NULLA;
				break;
			case 6:
				altezza = Altezza.FA;
				alterazione = Alterazione.DIESIS;
				break;
			case 7:
				altezza = Altezza.SOL;
				alterazione = Alterazione.NULLA;
				break;
			case 8:
				altezza = Altezza.SOL; 
				alterazione = Alterazione.DIESIS;
				break;
			case 9:
				altezza = Altezza.LA;
				alterazione = Alterazione.NULLA;
				break;
			case 10:
				altezza = Altezza.LA; 
				alterazione = Alterazione.DIESIS;
				break;
			case 11:
				altezza = Altezza.SI; 
				alterazione = Alterazione.NULLA;
				break;
			default:
				altezza = null; 
				alterazione = null;
		}
		ottava = pitch / 12 - 1;
	}

	public Nota( final int pitch ) {
		this( pitch, Simbolo.DURATA_DEFAULT );
	}
	
	public int pitch() {
		return 12 * ( ottava + 1 ) + altezza.semitoni + alterazione.semitoni;
	} 
		
	public String toString() {
		return altezza.toString() + alterazione.toString() + ( ottava == 4 ? "" : "" + ottava ) + ( durata == Durata.SEMIMINIMA ? "" : ":" + durata );
	}

	@Override
	public void suona( final Strumento strumento ) {
		strumento.suona( this );
	}

	@Override
	public void suona( final Strumento strumento, final int intesita ) {
		strumento.suona( this, intesita );
	}
	
}
