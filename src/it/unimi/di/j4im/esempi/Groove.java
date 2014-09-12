package it.unimi.di.j4im.esempi;

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

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.riproduzione.Batteria;
import it.unimi.di.j4im.riproduzione.Batteria.Pezzo;
import it.unimi.di.j4im.riproduzione.Brano;
import it.unimi.di.j4im.riproduzione.Parte;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;

public class Groove {

	public static void main( String[] args ) {
		
		Sintetizzatore.accendi();
		Sintetizzatore.bpm( 240 );
		
		Nota[] kh = new Nota[] { new Nota( Pezzo.KICK.pitch ), new Nota( Pezzo.CLOSED_HIHAT.pitch ) };
		Nota[] h = new Nota[] { new Nota( Pezzo.CLOSED_HIHAT.pitch ) };
		Nota[] ks = new Nota[] { new Nota( Pezzo.KICK.pitch ), new Nota( Pezzo.SNARE.pitch ) };
		
		Brano brano = new Brano();
		Parte p = new Parte( brano, new Batteria() );
		
		p.accodaAccordo( kh, 127 );
		p.accodaAccordo( h, 80 );
		p.accodaAccordo( ks, 80 );
		p.accodaAccordo( h, 80 );		

		brano.riproduci( -1 );

		Sintetizzatore.spegni();
	}
	
}
