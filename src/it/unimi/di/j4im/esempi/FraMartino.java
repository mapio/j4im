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

	
	public static void suona( final String brano ) {
		final Strumento flauto = new Strumento( "Flute" );
		Simbolo sim;
		for ( String s: brano.split( "," ) ) {
			sim = s.charAt( 0 ) == '_' ? new Pausa( s ) : new Nota( s ); 
			System.out.println( sim );
			sim.suona( flauto );
		}		
	}

	public static void suonaBrano( final String brano ) throws InvalidMidiDataException, InterruptedException {
		Brano b = new Brano();
		Parte parte = b.parte( new Strumento( "Piano" ) );
		parte.fromString( brano );
		b.riproduci();		
	}
	
	public static void canone( final String brano ) throws InvalidMidiDataException, InterruptedException {
		Brano b = new Brano();
		Parte parte = b.parte( new Strumento( "Piano" ) );
		parte.fromString( brano );
		parte = b.parte( new Strumento( "Guitar" ) );
		parte.fromString( brano );
		parte.trasla( Durata.SEMIBREVE );
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
		suonaBrano( fraMartino );
		
		Sintetizzatore.spegni();
	}
	
	
}
