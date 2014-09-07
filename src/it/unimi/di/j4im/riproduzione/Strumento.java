package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;

public class Strumento {
	
	final int canale;
	private final String nome;
	
	public Strumento( final String nome ) {
		canale = Sintetizzatore.assegnaCanale( nome );
		this.nome = nome;
	}
	
	public void suona( final Nota nota, final int intensita ) {
		final int pitch = nota.pitch();
		Sintetizzatore.accendiNota( canale, pitch, intensita );
		Sintetizzatore.attendi( nota.durata() );
		Sintetizzatore.spegniNota( canale, pitch );
	}

	public void suona( final Nota nota ) {
		suona( nota, Sintetizzatore.INTENSITA_DEFAULT );
	}

	public void suona( final Pausa pausa ) {
		Sintetizzatore.attendi( pausa.durata() );
	}
	
	public String toString() {
		return nome;
	}
}