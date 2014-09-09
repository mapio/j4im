package it.unimi.di.j4im.riproduzione;

public class Batteria extends Strumento {

	public enum Pezzo {
	     
		KICK( 36 ),
		SNARE( 38 ),
	    CLAP( 39 ),
	    CLOSED_HIHAT( 42 ),
	    PEDAL_HIHAT( 44 ),
	    LO_TOM( 45 ),
	    HI_TOM( 50 ),
	    CRASH( 49 ),
	    RIDE( 53 );
		
		public final int pitch;
		
		Pezzo( final int pitch ) {
			this.pitch = pitch;
		}

	}
	
	public Batteria() {
		super( 9, "Drumkit" );
	}

}
