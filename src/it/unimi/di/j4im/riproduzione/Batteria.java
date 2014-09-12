package it.unimi.di.j4im.riproduzione;

/*
 * Copyright 2014 Massimo Santini
 * 
 * This file is part of j4im.
 *
 * j4im is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * j4im is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with j4im.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
