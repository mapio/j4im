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

import it.unimi.di.j4im.notazione.Durata;

import java.util.concurrent.CountDownLatch;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 * Risorsa musicale del sistema, in
 * grado di riprodurre note musicali con diversi timbri ed in modo polifonico.
 * 
 * <p>
 * L'uso diretto di questa classe richiede una certa comprensione del sietma <a
 * href="http://www.midi.org/">MIDI</a>, per un uso più elementare si consiglia
 * l'uso della classe {@link Strumento} e {@link Brano} (nonchè delle classi del
 * pacchetto {@link it.unimi.di.j4im.notazione notazione}). Gli unici metodi di 
 * questa classe che è sensato invocare direttamente sono:</p>
 * <ul>
 * <li> {@link #accendi()} all'inizio dell'uso delle risorse musicali, </li>
 * <li> {@link #strumenti()} per conoscere l'elenco di strumenti disponibili, </li>
 * <li> {@link #bpm(int)} e {@link #bpm()} per impostare e conosere il numero di quarti al minuto (che regolano la durata effettiva delle note e pause riprodotte), </li> 
 * <li> {@link #spegni()} da invocare al termine dell'uso delle risorse musicali; <strong>attenzione</strong>: la mancata invocazione di quetso metodo <em>impedisce la terminazione del programma</em>!.</li>
 * </ul>
 * 
 * <p> 
 * Volendo fare un uso diretto degli altri metodi di questa classe, si tenga presente
 * che per suonare una nota con un dato strumento è necessario dapprima assegnare lo 
 * strumento ad uno dei "canali" del sintetizzatore tramite il metodo 
 * {@link #assegnaCanale(String)} e quindi usare i metodi {@link #accendiNota(int, int, int)} 
 * e {@link #spegniNota(int, int)}.
 * </p>
 *  
 *   
 * <h3>Dettagli implementativi</h3>
 * 
 * <p>
 * Questa classe rappresenta il {@link Synthesizer} restituito da
 * {@link MidiSystem#getSynthesizer()} e il {@link Sequencer} restituito da
 * {@link MidiSystem#getSequencer()}.
 * </p>
 * 
 * <p>
 * La nozione di "strumento" adoperata nella classe {@link Strumento} si basa
 * sul metodo {@link #assegnaCanale(String)} che resituisce l'indice di uno dei
 * {@link MidiChannel} elencati dal metodo {@link Synthesizer#getChannels()} su
 * cui è stato effettuato un {@link MidiChannel#programChange(int)} a partire
 * dal numero di programma di uno {@link Instrument} reperito in base alla sua
 * rappresentazione testuale tra gli strumenti elencati dal metodo
 * {@link Synthesizer#getAvailableInstruments()}.
 * </p>
 * 
 * <p>
 * Una volta eseguita tale assegnazione (per via dell'invocazione diretta del
 * metodo {@link #assegnaCanale(String)}, o dell'invocazione del costruttore
 * {@link Strumento#Strumento(String)}), l'invocazione del metodo
 * {@link #accendiNota(int, int, int)} avrà l'effetto di eseguire un
 * {@link MidiChannel#noteOn(int, int)} sul canale relativo. Similmente, 
 * la trasformazione in {@link ShortMessage#NOTE_ON} e {@link ShortMessage#NOTE_ON}
 * a cui vengono sottoposte le note all'accodamento in una {@link Parte} sarà
 * fatta tenendo conto del canale associato allo strumento. 
 * </p>
 * 
 */
public class Sintetizzatore {

	private static final int END_OF_TRACK_MESSAGE = 0x2F;

	public static final int INTENSITA_DEFAULT = 64;
	public static final int BPM_DEFAULT = 120;
		
	private static final Synthesizer synth;
	private static final Sequencer sequencer;
	private static final MidiChannel[] canali;
	private static int canaliAssegnati;
	private static int bpm = BPM_DEFAULT;
		
	static {
		try {
			synth = MidiSystem.getSynthesizer();
			if ( ! synth.isOpen() ) synth.open();

			sequencer =  MidiSystem.getSequencer();
			if ( ! sequencer.isOpen() ) sequencer.open();

			canali = synth.getChannels();
			canaliAssegnati = 1; // il piano è sempre assegnato
			
			// vanno chiusi tutti i transmitter per evitare "echi"
			for ( Transmitter t : sequencer.getTransmitters() ) t.close();
			
			// va associato il sequencer al synth in modo che i program change sui channels siano rispettati
			sequencer.getTransmitter().setReceiver( synth.getReceiver() );

		} catch ( MidiUnavailableException e ) {
			throw new RuntimeException( e ); // non dovrebbe mai accadere
		}		
	}

	/* metodi di pacchetto, o privati */
	
	private Sintetizzatore() {} // per impedire la costruzione di una istanza
	
	/** Riroduce la sequenza il numero assegnato di volte.
	 * 
	 * @param sequenza la sequenza.
	 * @param volte il numero di volte (se <= 0 sarà ripetuta all'infinito).
	 */
	static void riproduci( final Sequence sequenza, final int volte ) {
		final CountDownLatch cdl = new CountDownLatch( 1 );
		MetaEventListener mel = new MetaEventListener() {
			public void meta( MetaMessage meta ) {
				if ( meta.getType() == END_OF_TRACK_MESSAGE )
					sequencer.stop();
					cdl.countDown();
			}
		};
		if ( volte <= 0 )
			sequencer.setLoopCount( Sequencer.LOOP_CONTINUOUSLY );
		else 
			sequencer.setLoopCount( volte - 1 );
		sequencer.addMetaEventListener( mel );
		try {
			sequencer.setSequence( sequenza );
		} catch ( InvalidMidiDataException e ) {
			throw new RuntimeException( e ); // questo non dovrebbe mai accadere
		}
		sequencer.setTickPosition( 0 );
		sequencer.setTempoInBPM( Sintetizzatore.bpm );
		sequencer.start();
		try {
			cdl.await();
		} catch ( InterruptedException ignora ) {} // in caso di interruzione, si procede a liberare le risorse
		sequencer.removeMetaEventListener( mel );
	}

	/** Riproduce la sequenza (una sola volta).
	 * 
	 * @param sequenza la sequenza.
	 */
	static void riproduci( final Sequence sequenza ) {
		riproduci( sequenza, 1 );
	}
	
	/* metodi pubblici */
	
	/** Accende il sintetizzatore predisponendo le risorse musicali del sistema. */
	public static void accendi() {}

	/** Restituisce il valore corrente di numero di quarti per minuto.
	 * 
	 * @return il numero di quarti per minuto.
	 */
	public static int bpm() {
		return bpm;
	}
	
	/** Imposta il valore corrente di numero di quarti per minuto.
	 * 
	 * @param bpm il numero di quarti per minuto.
	 * @throws IllegalArgumentException se i bpm non sono nell'intervallo da 1 a 960 (estremi inclusi).
	 *  
	 */
	public static void bpm( final int bpm ) {
		if ( bpm < 0 || bpm > 960 ) throw new IllegalArgumentException( "I BPM devono essere compresi tra 1 e 960." );
		Sintetizzatore.bpm = bpm;
		sequencer.setTempoInBPM( bpm );
	}

	/** Restituisce l'elenco degli strumenti disponibili
	 * 
	 * @return L'elenco di strumenti.
	 */
	public static String[] strumenti() {
		final Instrument[] insts = synth.getAvailableInstruments();
		final String[] strumenti = new String[ insts.length ];
		for ( int i = 0; i < insts.length; i++ )
			strumenti[ i ] = insts[ i ].toString();
		return strumenti;
	}
	
	/** Assegna lo strumento dato ad uno dei "canali" del sintetizzatore, restituendone l'indice.
	 * 
	 * @param nomeStrumento il nome dello strumento.
	 * @return l'indice del canale cui è stato assegnato tale strumento.
	 */
	public static int assegnaCanale( final String nomeStrumento ) {
		if ( canaliAssegnati >= canali.length ) throw new IllegalStateException( "Il sintetizzatore non supporta più di " + canali.length + " srumenti." );
		for ( Instrument inst : synth.getLoadedInstruments() )
			if ( inst.toString().contains( nomeStrumento ) ) {
				if ( canaliAssegnati == 9 ) canaliAssegnati++; // salta il canale preassegnato alla batteria
				canali[ canaliAssegnati ].programChange( inst.getPatch().getProgram() );
				return canaliAssegnati++;
			}
		throw new IllegalArgumentException( "Il sintetizzatore non è in grado di riprodurre lo strumento " + nomeStrumento );
	}
	
	/** Inizia a suonare una nota.
	 * 
	 * @param canale il canale (deve corrispondere ad uno dei valori restituiti da {@link #assegnaCanale(String)}).
	 * @param pitch il pitch (dev'essere compreso tra 0 e 127 estremi inclusi).
	 * @param intensita l'intensità (dev'essere compresa tra 0 e 127 estremi inclusi).
	 * @throws IllegalArgumentException se uno degli argomenti ha un valore non consentito.
	 */
	public static void accendiNota( final int canale, final int pitch, final int intensita ) {
		if ( canale != 9 && ( canale < 0 || canale >= canaliAssegnati ) ) throw new IllegalArgumentException( "Il numero di canali dev'essere compreso tra 0 e " + ( canaliAssegnati - 1 ) + " estremi inclusi." );
		if ( pitch < 0 || pitch > 127 ) throw new IllegalArgumentException( "Il pitch dev'essere compresa tra 0 e 127, estremi inclusi." );
		if ( intensita < 0 || intensita > 127 ) throw new IllegalArgumentException( "L'intensità dev'essere compresa tra 0 e 127, estremi inclusi." );
		canali[ canale ].noteOn( pitch, intensita );
	}

	/** Cessa di suonare una nota.
	 * 
	 * @param canale il canale (deve corrispondere ad uno dei valori restituiti da {@link #assegnaCanale(String)}).
	 * @param pitch il pitch (dev'essere compreso tra 0 e 127 estremi inclusi).
	 * @throws IllegalArgumentException se uno degli argomenti ha un valore non consentito.
	 */
	public static void spegniNota( final int canale, final int pitch ) {
		if ( pitch < 0 || pitch > 127 ) throw new IllegalArgumentException( "Il pitch dev'essere compresa tra 0 e 127, estremi inclusi." );
		canali[ canale ].noteOff( pitch );		
	}
	
	/** Sospende l'esecuzione per la durata assegnata.
	 * 
	 * @param durata la durata.
	 */
	public static void attendi( final Durata durata ) {
		try {
			Thread.sleep( durata.ms( Sintetizzatore.bpm() ) );
		} catch ( InterruptedException swallow ) {}
	}
		
	/** Spegne il sintetizzatore.
	 * 
	 * <p>Si osserva che è <em>necessario</em> invocare questo metodo per consentire la termianzione del programma.</p>
	 */
	public static void spegni() {
		sequencer.close();
		synth.close();
	}

	/** Se eseguita, questa classe, emette sul flusso d'uscita l'elenco di strumenti disponibili.
	 * 
	 * @param args gli argomenti sulla linea di comando (sarnano ignorati).
	 */
	public static void main( String[] args ) {
		Sintetizzatore.accendi();
		for ( String s : strumenti() )
			System.out.println( s );
		Sintetizzatore.spegni();
	}
	
}
