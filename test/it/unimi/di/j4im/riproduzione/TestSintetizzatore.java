package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSintetizzatore {

	private static boolean HAS_RESOURCES;

	@BeforeClass
	public static void runOnceBefore() {
		try {
			MidiSystem.getSynthesizer();
			HAS_RESOURCES = true;
		} catch ( MidiUnavailableException e ) {
			HAS_RESOURCES = false;
		}
		if ( HAS_RESOURCES )
			Sintetizzatore.accendi();
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
