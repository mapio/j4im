package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSintetizzatore {

	@BeforeClass
	public static void runOnceBefore() {
		if ( System.getenv( "NOMIDI" ) == null )
			Sintetizzatore.accendi();
	}

	@AfterClass
	public static void runOnceAfter() {
		if ( System.getenv( "NOMIDI" ) == null )
			Sintetizzatore.spegni();
	}

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue( "NO MIDI", System.getenv( "NOMIDI" ) == null );
	}

	@Test
	public void testBatteria() {
		new Nota( Batteria.CRASH ).suonaCon( new Batteria() );
	}

}
