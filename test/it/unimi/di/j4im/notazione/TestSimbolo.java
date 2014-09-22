package it.unimi.di.j4im.notazione;

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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestSimbolo {

	@Test
	public void testNotaFromString() {

		// tolgo 0
		assertEquals( "DO♭5:1/32:90", new Nota( "DO♭5:1/32:90" ).toString() );
		
		// tolgo 1
		assertEquals( "DO5:1/32:90", new Nota( "DO5:1/32:90" ).toString() );
		assertEquals( "DO♭:1/32:90", new Nota( "DO♭:1/32:90" ).toString() );
		assertEquals( "DO♭5:90", new Nota( "DO♭5:90" ).toString() );
		assertEquals( "DO♭5:1/32", new Nota( "DO♭5:1/32" ).toString() );
		
		// tolgo 2	
		assertEquals( "DO:1/32:90", new Nota( "DO:1/32:90" ).toString() );
		assertEquals( "DO5:90", new Nota( "DO5:90" ).toString() );
		assertEquals( "DO5:1/32", new Nota( "DO5:1/32" ).toString() );
		assertEquals( "DO♭:90", new Nota( "DO♭:90" ).toString() );
		assertEquals( "DO♭:1/32", new Nota( "DO♭:1/32" ).toString() );
		assertEquals( "DO♭5", new Nota( "DO♭5" ).toString() );

		// tolgo 3
		assertEquals( "DO:90", new Nota( "DO:90" ).toString() );
		assertEquals( "DO5", new Nota( "DO5" ).toString() );
		assertEquals( "DO:1/32", new Nota( "DO:1/32" ).toString() );
		assertEquals( "DO♭", new Nota( "DO♭" ).toString() );

		// tolgo 4
		assertEquals( "DO♭5:1/32:90", new Nota( "DO♭5:1/32:90" ).toString() );

	}

	@Test
	public void testPausaFromString() {
		assertEquals( "_:1/32", new Pausa( "_:1/32" ).toString() );
		assertEquals( "_", new Pausa( "_" ).toString() );
	}
	
	@Test
	public void testPitch() {
		assertEquals( 60, new Nota( "DO" ).pitch() );
		assertEquals( "DO", new Nota( 60 ).toString() );
		assertEquals( 0, new Nota( "DO-1" ).pitch() );
		assertEquals( 127, new Nota( "SOL9" ).pitch() );
	}
	
	@Test
	public void testFrequenza() {
		for ( Nome nome: Nome.values() ) {
			final Nota expexted =  new Nota( nome );
			final Nota actual = Nota.fabbricatore().frequenza( expexted.frequenza() ).fabbrica();
			assertEquals( expexted.pitch(), actual.pitch() );
		}
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testRanges() {
		new Nota( "SOL#9" );
	}
}
