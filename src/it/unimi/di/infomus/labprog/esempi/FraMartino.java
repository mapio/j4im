package it.unimi.di.infomus.labprog.esempi;

import java.io.IOException;

import it.unimi.di.infomus.labprog.notazione.Durata;
import it.unimi.di.infomus.labprog.notazione.Nota;
import it.unimi.di.infomus.labprog.notazione.Pausa;
import it.unimi.di.infomus.labprog.riproduzione.Brano;
import it.unimi.di.infomus.labprog.riproduzione.Brano.Parte;
import it.unimi.di.infomus.labprog.riproduzione.Sintetizzatore;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class FraMartino {

	public static void suona( final Sintetizzatore synth, final String brano ) {
		for ( String s: brano.split( "," ) )
			if ( s.charAt( 0 ) == '_' ) {
				Pausa pausa = Pausa.fromString( s );
				System.out.println( pausa );
				synth.suona( pausa );
			} else {
				Nota nota = Nota.fromString( s );
				System.out.println( nota );
				synth.suona( nota );
			}		
	}

	public static void suonaBrano( final Sintetizzatore synth, final String brano ) throws InvalidMidiDataException, InterruptedException {
		Brano b = new Brano();
		Parte parte = b.parte( 0 );
		parte.fromString( brano );
		synth.riproduci( b );		
	}

	public static void canone( final Sintetizzatore synth, final String brano ) throws InvalidMidiDataException, InterruptedException {
		Brano b = new Brano();
		Parte parte = b.parte( 0 );
		parte.fromString( brano );
		parte = b.parte( 1 );
		parte.fromString( brano );
		parte.trasla( Durata.SEMIBREVE );
		synth.riproduci( b );		
	}
	
	public static void main( String[] args ) throws MidiUnavailableException, InvalidMidiDataException, IOException, InterruptedException {
	
		Sintetizzatore synth = new Sintetizzatore( 180, "Piano", "Piano" );
		
		String fraMartino = 
			"DO,RE,MI,DO," +
			"DO,RE,MI,DO," +
			"MI,FA,SOL:1/2," +
			"MI,FA,SOL:1/2," +
			"SOL:1/8,LA:1/8,SOL:1/8,FA:1/8,MI,DO," +
			"SOL:1/8,LA:1/8,SOL:1/8,FA:1/8,MI,DO," +
			"RE,SOL3,DO:1/2," +
			"RE,SOL3,DO:1/2";
		
		// suona( synth, fraMartino );
		canone( synth, fraMartino );
		suonaBrano( synth, fraMartino );
		synth.close();
	}
	
	
}
