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

public class TestDurata {

	@Test
	public void testPiu() {
		assertEquals( new Durata( 1, 2 ), new Durata( 1, 4 ).piu( new Durata( 1, 4 ) ) );
	}

	@Test
	public void testMeno() {
		assertEquals( new Durata( 1, 4 ), new Durata( 1, 2 ).meno( new Durata( 1, 4 ) ) );
	}

	@Test
	public void testPer() {
		assertEquals( new Durata( 1, 2 ), new Durata( 1, 4 ).per( 2 ) );
	}

	@Test
	public void testDiviso() {
		assertEquals( new Durata( 1, 4 ), new Durata( 1, 2 ).diviso( 2 ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNegativo() {
		new Durata( 1, 4 ).meno( new Durata( 1, 2 ) );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testDivisoZero() {
		new Durata( 1 ).diviso( 0 );
	}
	
	
}
