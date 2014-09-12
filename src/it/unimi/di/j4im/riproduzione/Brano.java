package it.unimi.di.j4im.riproduzione;

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

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

/** Questa classe rappresenta un brano musicale; ogni elemento di un brano è una {@link Parte}. */
public class Brano {

	/** La risoluzione usata nella sequenza. */
	static final double RESOLUTION = 960.0;

	/** La sequenza usata per rappresentare il brano. */
	final Sequence sequence;
	
	/** Costruisce un nuovo brano. */
	public Brano() {
		try {
			sequence = new Sequence( Sequence.PPQ, (int)RESOLUTION );
		} catch ( InvalidMidiDataException e ) {
			throw new RuntimeException( e ); // questo non dovrebbe mai accadere
		}
	}
	
	private Brano( final Sequence sequence ) {
		this.sequence = sequence;
	}
	
	/** Riproduce (un dato numero di volte) il brano (usando il {@link Sintetizzatore}.
	 * 
	 * @param ripetizioni il numero di volte per cui la riproduzione va ripetuta 
	 *                    (se il valore non è positivo, il brano viene ripetuto all'infinito).
	 * 
	 */
	public void riproduci( final int ripetizioni ) {
		Sintetizzatore.riproduci( sequence, ripetizioni  );
	}
	
	/** Riproduce il brano (usando il {@link Sintetizzatore}. */
	public void riproduci() {
		Sintetizzatore.riproduci( sequence );
	}

	/** Scrive il brano in un file midi.
	 * 
	 * @param path il percorso del file.
	 * @throws IOException se ci sono errori di I/O.
	 */
	public void scrivi( final String path ) throws IOException {
		MidiSystem.write( sequence, 1, new File( path ) );
	}

	/** Restituisce un brano ottenuto leggendo la sequenza da un file midi.
	 * 
	 * @param path il percorso del file.
	 * @throws IOException se ci sono errori di I/O.
	 * @throws InvalidMidiDataException se il file non è nel formato corretto.
	 */
	public static Brano leggi( final String path ) throws IOException, InvalidMidiDataException {
		return new Brano( MidiSystem.getSequence( new File( path ) ) );
	}

}
