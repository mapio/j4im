package it.unimi.di.j4im.riproduzione;

import java.util.Arrays;
import java.util.Comparator;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/*
 * Copyright 2014 Massimo Santini
 * 
 * This file is part of j4im.
 *
 * j4im is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * j4im is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with j4im.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;
import it.unimi.di.j4im.notazione.Simbolo;

/** Una parte di un {@link Brano brano} musicale.
 * 
 * <p>
 * Una parte è una sequenza di {@link Simbolo simboli} assegnati ad uno
 * specifico {@link Strumento strumento} che deve essere usato per riprodurla; 
 * essa dev'essere pate di un {@link Brano brano}, per questa ragione è 
 * necessario specificare entrambi all'atto della sua creazione  
 * col costruttore {@link #Parte(Brano, Strumento)}.
 * </p> 
 *
 * <p>
 * Si possono aggiungere simboli ad una parte coi metodi {@link #accoda(Nota)}, o
 * {@link #accoda(Pausa)}, che hanno per effetto di aggiungere tali simboli "in fondo"
 * alla parte, in modo che (quando il brano sarà riprodotto) siano suonati di seguito 
 * a tutti i simboli accodati in precedenza (esite un metodo di comodo 
 * {@link #accoda(Simbolo[])} che consente di accodare in sequenza un vettore di simboli). 
 * </p>
 * 
 * <p>Per far si che un gruppo di note (quando il brano sarà riprodotto) suoni a partire 
 * dallo stesso istante (come in un accordo), esse vanno accodate usando metodo 
 * {@link #accodaAccordo(Nota[])}; il simbolo accodato alla parte dopo l'inivocazione 
 * di questo medoto (e quindi i successivi) suonerà (una volta che il brano sarà riprodotto)
 * dopo la nota di maggior durata presente nell'accordo.
 * </p>
 *
 * 
 * <h3>Dettagli implementativi</h3>
 * 
 * <p>
 * Questa classe si basa su una {@link Track} a cui viene inizialmente
 * accodato uno {@link ShortMessage#PROGRAM_CHANGE} che ne imposta il 
 * canale al valore dato da {@link Sintetizzatore#assegnaCanale(String)}
 * invocato alla creazione dello {@link Strumento} usato alla sua 
 * costruzione.
 * </p>
 * 
 * <p>
 * In seguito, le {@link Nota} accodate vengono tradotte in una coppia
 * di {@link MidiEvent} costituiti rispettivamente da uno {@link ShortMessage#NOTE_ON} 
 * e uno {@link ShortMessage#NOTE_ON} il cui pitch e distanza in tick vengono
 * calcolati in base ai valori della nota accodata; l'accodamento di una {@link Pausa}
 * ha il solo effetto di modificare l'ultimo tick della traccia, mentre l'accodamento
 * di un accordo ha l'effetto di accodare una sequenza di {@link MidiEvent} relativi a
 * {@link ShortMessage#NOTE_ON} e {@link ShortMessage#NOTE_ON} i cui tick sono
 * calcolati in modo da costruire l'effetto polifonico dell'accordo.
 * </p>
 * 
 */
public class Parte {

	/** Il canale a cui è assegnato lo strumento della parte. */
	private final int canale;
	
	/** La {@link Track} usata per rappresentare le note della parte */
	private final Track track;
	
	/** Il numero di ticks del prossimo {@link MidiMessage}. */
	private long ticks;
	
	/** Costruisce una nuova parte all'interno del {@link Brano} specificato che verrò riprodotta con lo {@link Strumento} dato.
	 * 
	 * @param brano il brano.
	 * @param strumento lo strumento.
	 * 
	 */
	public Parte( final Brano brano, final Strumento strumento ) {
		canale = strumento.canale;
		track = brano.sequence.createTrack();
		ticks = 0;
	}

	/** Accoda la nota specificata alla parte.
	 * 
	 * @param nota la nota.
	 * 
	 */
	public void accoda( final Nota nota ) {
		final int pitch = nota.pitch();
		try {
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_ON, canale, pitch, nota.intensita() ), ticks ) );
			ticks += nota.durata().ticks( Brano.RESOLUTION );
			track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_OFF, canale, pitch, 0 ), ticks ) );
		} catch ( InvalidMidiDataException e ) {
			throw new IllegalArgumentException( "Nota non valida", e ); // non dovrebbe mai capitare
		}
	}
		
	/** Accoda la pausa specificata alla parte.
	 * 
	 * @param pausa la nota.
	 * 
	 */
	public void accoda( final Pausa pausa ) {
		ticks += pausa.durata().ticks( Brano.RESOLUTION );
	}

	/** Accoda il simbolo specificato alla parte.
	 * 
	 * @param simbolo il simbolo.
	 * 
	 */
	public void accoda( final Simbolo simbolo ) {
		if ( simbolo instanceof Pausa ) 
			accoda( (Pausa)simbolo );
		else 
			accoda( (Nota)simbolo );
	}

	/** Accoda (in sequenza) un array di {@link Simbolo} alla parte. 
	 *
	 * @param simboli l'array di simboli.
	 * 
	 */
	public void accoda( final Simbolo[] simboli ) {
		for ( Simbolo s : simboli ) accoda( s );
	}

	/** Accoda un accordo (vettore di {@link Nota}) alla parte.
	 * 
	 * <p> 
	 * Le note presenti nel vettore <samp>accordo</samp> inizieranno 
	 * a suonare nello stesso istante, viceversa i simboli aggiunti alla
	 * sequenza dopo l'accordo suoneranno al termine della nota di maggior
	 * durata dell'accordo stesso.
	 * </p> 
	 * 
	 * @param accordo il vettore di note che formano l'accordo.
	 * 
	 */
	public void accodaAccordo( final Nota[] accordo ) {
		final Nota[] note = Arrays.copyOf( accordo, accordo.length );
		Arrays.sort( note, new Comparator<Nota>() {
			public int compare( Nota p, Nota q ) {
				return p.durata().compareTo( q.durata() );
			}
		} );
		try {
			for ( Nota n : note )
				track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_ON, canale, n.pitch(), n.intensita() ), ticks ) );
			for ( Nota n : note )
				track.add( new MidiEvent( new ShortMessage( ShortMessage.NOTE_OFF, canale, n.pitch(), 0 ), ticks + n.durata().ticks( Brano.RESOLUTION ) ) );
			ticks += note[ note.length - 1 ].durata().ticks( Brano.RESOLUTION );
		} catch ( InvalidMidiDataException e ) {
			throw new IllegalArgumentException( "Nota non valida", e ); // non dovrebbe mai capitare
		}
	}

	/** Restituisce gli eventi contenuti nella sequenza
	 * 
	 * @return L'elenco di eventi contenuti nella sequenza.
	 * 
	 */
	MidiEvent[] eventi() {
		int n = track.size();
		MidiEvent[] eventi = new MidiEvent[ n ];
		for ( int i = 0; i < n; i++ )
			eventi[ i ] = track.get( i );
		return eventi;
	}
	
}
