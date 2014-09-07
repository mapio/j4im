package it.unimi.di.j4im.esempi;

import it.unimi.di.j4im.notazione.Durata;
import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;
import it.unimi.di.j4im.notazione.Simbolo;
import it.unimi.di.j4im.riproduzione.Brano;
import it.unimi.di.j4im.riproduzione.Brano.Parte;
import it.unimi.di.j4im.riproduzione.Sintetizzatore;
import it.unimi.di.j4im.riproduzione.Strumento;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class FraMartino {
	
	public static void suona( final String parte ) {
		final Strumento flauto = new Strumento( "Flute" );
		Simbolo sim;
		for ( String s: parte.split( "," ) ) {
			sim = s.charAt( 0 ) == '_' ? new Pausa( s ) : new Nota( s ); 
			System.out.println( sim );
			sim.suona( flauto );
		}		
	}

	public static void suonaParte( final String parte ) throws InvalidMidiDataException, InterruptedException {
		Brano b = new Brano();
		b.parte( new Strumento( "Piano" ), parte );
		b.riproduci();		
	}
	
	public static void canone( final String parte ) throws InvalidMidiDataException, InterruptedException {
		Brano b = new Brano();
		b.parte( new Strumento( "Piano" ), parte );
		Parte p = b.parte( new Strumento( "Guitar" ), parte );
		p.trasla( Durata.SEMIBREVE );
		b.riproduci();		
	}
	
	public static void main( String[] args ) throws MidiUnavailableException, InvalidMidiDataException, IOException, InterruptedException {
	
		Sintetizzatore.accendi();
		
		String fraMartino = 
			"DO,RE,MI,DO," +
			"DO,RE,MI,DO," +
			"MI,FA,SOL:1/2," +
			"MI,FA,SOL:1/2," +
			"SOL:1/8,LA:1/8,SOL:1/8,FA:1/8,MI,DO," +
			"SOL:1/8,LA:1/8,SOL:1/8,FA:1/8,MI,DO," +
			"RE,SOL3,DO:1/2," +
			"RE,SOL3,DO:1/2";
		
		suona( fraMartino );
		canone( fraMartino );
		suonaParte( fraMartino );
		
		Sintetizzatore.spegni();
	}
	
	
}
