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

/** La batteria. */
public class Batteria extends Strumento {

	// I pezzi della batteria 
	
	    public final static int KICK = 35;
	    public final static int KICK_2 = 36;
	    public final static int SIDE_STICK = 37;
	    public final static int SNARE = 38;
	    public final static int SNARE_2 = 40;
	    public final static int CLAP = 39;
	    public final static int CLOSED_HIHAT = 42;
	    public final static int PEDAL_HIHAT = 44;
	    public final static int OPEN_HIHAT = 46;
	    public final static int LO_TOM = 43;
	    public final static int MID_TOM = 47;
	    public final static int HI_TOM = 50;
	    public final static int CRASH = 49;
	    public final static int CRASH_2 = 57;
	    public final static int RIDE = 53;
	    public final static int SPLASH_CYM = 55;
	    public final static int CHINESE_CYM = 52;
	    public final static int COWBELL = 56;
	    public final static int CLAVES = 75;
	
	/** Costruisce lo strumento "batteria". */
	public Batteria() {
		super( 9, "Drumkit" );
	}

}
