package it.unimi.di.j4im.notazione;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestNota {

	@Test
	public void testNotaFromString() {
		assertEquals( "DO♭5:1/32", Nota.fromString( "DO♭5:1/32" ).toString() );
		
		assertEquals( "DO5:1/32",   Nota.fromString( "DO5:1/32" ).toString() );
		assertEquals( "DO♭:1/32",  Nota.fromString( "DO♭:1/32" ).toString() );
		assertEquals( "DO♭5",      Nota.fromString( "DO♭5" ).toString() );
		
		assertEquals( "DO:1/32",    Nota.fromString( "DO:1/32" ).toString() );
		assertEquals( "DO5",        Nota.fromString( "DO5" ).toString() );
		
		assertEquals( "DO:1/32",    Nota.fromString( "DO:1/32" ).toString() );
		assertEquals( "DO♭",       Nota.fromString( "DO♭").toString() );
		
		assertEquals( "DO5",        Nota.fromString( "DO5" ).toString() );
		assertEquals( "DO♭",       Nota.fromString( "DO♭").toString() );
		
		assertEquals( "DO",         Nota.fromString( "DO" ).toString() );
	}

}
