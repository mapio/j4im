package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestSintetizzatore {
	
	@BeforeClass 
	public static void runOnceBefore() {
		Sintetizzatore.accendi();
	}
	
	@AfterClass 
	public static void runOnceAfter() {
		Sintetizzatore.spegni();
	}
	
	@Test
	public void testBatteria() {
		new Nota( Batteria.CRASH ).suonaCon( new Batteria() );
	}

}
