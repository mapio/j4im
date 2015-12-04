package it.unimi.di.j4im.riproduzione;

import static org.junit.Assert.assertEquals;

import javax.sound.midi.MidiEvent;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Simbolo;

public class TestParte {

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
	public void testAccordoNote() {
		final Nota doLungo = new Nota( "DO:1/2" );
		Nota[] accordo = new Nota[] { new Nota( "MI" ), doLungo, new Nota( "SOL" ) };
		Brano b = new Brano();
		Parte p = new Parte( b, new Strumento( "Piano" ) );
		p.accodaAccordo( accordo );
		final MidiEvent[] eventi = p.eventi();
		assertEquals( doLungo.durata().ticks( Brano.RESOLUTION ), eventi[ eventi.length - 1 ].getTick() );
	}

	@Test
	public void testAccordoSimboli() {
		final Simbolo[] accordo = Simbolo.simboli( "MI,DO:1/2,SOL" );
		final Nota doLungo = new Nota( "DO:1/2" );
		Brano b = new Brano();
		Parte p = new Parte( b, new Strumento( "Piano" ) );
		p.accodaAccordo( accordo );
		final MidiEvent[] eventi = p.eventi();
		assertEquals( doLungo.durata().ticks( Brano.RESOLUTION ), eventi[ eventi.length - 1 ].getTick() );
	}

}
