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

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.ShortMessage;

/** Uno strumento musicale.
 * 
 * <p>Gli strumenti musicali dipendono dal {@link Sintetizzatore}, per una discussione
 * dei dettagli implementativi si veda il metodo {@link Sintetizzatore#assegnaCanale(String)}.</p>
 * 
 * 
 * <h3>Dettagli implementativi</h3>
 * 
 * <p>L'implemetazione di uno strumento si basa sull'accoppiamento di un <em>programma</em> e di un 
 * <em>canale</em> nel senso attribuito a tali temrini dallo standard <a href="http://www.midi.org/">MIDI</a>).
 * </p>
 * 
 * <p>
 * Più in dettaglio, il costruttore {@link #Strumento(String)} invoca il metodo 
 * {@link Sintetizzatore#assegnaCanale(String)} (che effettua un {@link MidiChannel#programChange(int)}
 * sul canale assegnato prima di restituirlo); in seguito le note suonate su tale canale attraverso 
 * {@link Sintetizzatore#accendiNota(int, int, int)} o accodate al brano e quindi trasformate in messaggi
 * {@link ShortMessage#NOTE_ON} e {@link ShortMessage#NOTE_ON} per quel canale, saranno riprodotte 
 * con lo strumento assegnato.
 * </p>
 * 
 *
 * @see Sintetizzatore
 * 
 */ 
public class Strumento {

	/** Il numero di {@link MidiChannel} corrispondente a questo strumento. */
	final int canale;
	
	/** Il nome dello strumento. */
	private final String nome;
	
	/** Costruisce uno strumento a partire da (parte del suo) nome.
	 * 
	 * <p>Si può ottenere un 
	 * @param nome il nome.
	 */
	public Strumento( final String nome ) {
		canale = Sintetizzatore.assegnaCanale( nome );
		this.nome = nome;
	}
	
	Strumento( final int canale, final String nome ) {
		this.canale = canale;
		this.nome = nome;
	}
	
	/** Inizia a suonare la nota assegnata.
	 * 
	 * @param nota la nota.
	 * 
	 */
	public void suona( final Nota nota ) {
		final int pitch = nota.pitch();
		Sintetizzatore.accendiNota( canale, pitch, nota.intensita() );
		Sintetizzatore.attendi( nota.durata() );
		Sintetizzatore.spegniNota( canale, pitch );
	}

	/** Attende un tempo pari alla durata della pausa assegnata.
	 * 
	 * @param pausa la pausa.
	 * 
	 */
	public void suona( final Pausa pausa ) {
		Sintetizzatore.attendi( pausa.durata() );
	}
	
	@Override
	public String toString() {
		return nome;
	}
}