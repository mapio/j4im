package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSintetizzatore {

	private static boolean HAS_RESOURCES;

	@BeforeClass
	public static void runOnceBefore() {
		try {
			Sintetizzatore.accendi();
			HAS_RESOURCES = true;
		} catch ( Exception e ) {
			HAS_RESOURCES = false;
		}
	}

	@AfterClass
	public static void runOnceAfter() {
		if ( HAS_RESOURCES )
			Sintetizzatore.spegni();
	}

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue( HAS_RESOURCES );
	}

	@Test
	public void testBatteria() {
		new Nota( Batteria.CRASH ).suonaCon( new Batteria() );
	}

}
