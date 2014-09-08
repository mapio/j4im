package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;

import javax.sound.midi.MidiChannel;

/** Questa classe rappresenta uno strumento musicale.
 * 
 * <p>Gli strumenti musicali dipendono dal {@link Sintetizzatore}, per una discussione
 * dei dettagli implementativi si veda il metodo {@link Sintetizzatore#assegnaCanale(String)}.</p>
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
	
	/** Inizia a suonare la nota assegnata.
	 * 
	 * @param nota la nota.
	 * @param intensita l'intensità (dev'essere un valore compreso tra 0 e 127).
	 * 
	 * @throws IllegalArgumentException se l'intensità eccede l'intervallo 0, 127.
	 * 
	 */
	public void suona( final Nota nota, final int intensita ) {
		final int pitch = nota.pitch();
		if ( intensita < 0 || intensita > 127 ) throw new IllegalArgumentException( "L'intensità dev'essere compresa tra 0 e 127, estremi inclusi." );
		Sintetizzatore.accendiNota( canale, pitch, intensita );
		Sintetizzatore.attendi( nota.durata() );
		Sintetizzatore.spegniNota( canale, pitch );
	}

	/** Cessa di suonare la nota assegnata.
	 * 
	 * @param nota la nota.
	 * 
	 */
	public void suona( final Nota nota ) {
		suona( nota, Sintetizzatore.INTENSITA_DEFAULT );
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