package it.unimi.di.j4im.notazione;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSimbolo {

	@Test
	public void testNotaFromString() {
		assertEquals( "DO♭5:1/32", new Nota( "DO♭5:1/32" ).toString() );
		
		assertEquals( "DO5:1/32",   new Nota( "DO5:1/32" ).toString() );
		assertEquals( "DO♭:1/32",  new Nota( "DO♭:1/32" ).toString() );
		assertEquals( "DO♭5",      new Nota( "DO♭5" ).toString() );
		
		assertEquals( "DO:1/32",    new Nota( "DO:1/32" ).toString() );
		assertEquals( "DO5",        new Nota( "DO5" ).toString() );
		
		assertEquals( "DO:1/32",    new Nota( "DO:1/32" ).toString() );
		assertEquals( "DO♭",       new Nota( "DO♭").toString() );
		
		assertEquals( "DO5",        new Nota( "DO5" ).toString() );
		assertEquals( "DO♭",       new Nota( "DO♭").toString() );
		
		assertEquals( "DO",         new Nota( "DO" ).toString() );
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
	
	@Test( expected = IllegalArgumentException.class )
	public void testRanges() {
		new Nota( "SOL#9" );
	}
}
