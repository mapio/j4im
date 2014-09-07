package it.unimi.di.j4im.riproduzione;

import it.unimi.di.j4im.notazione.Nota;
import it.unimi.di.j4im.notazione.Pausa;
import it.unimi.di.j4im.riproduzione.Sintetizzatore.StrumentoImpl;

public class Strumento {
	
	private final StrumentoImpl si;
	
	public Strumento( final String nome ) {
		this.si = Sintetizzatore.strumentoImpl( nome );
	}
	
	public void suona( final Nota nota, final int intensita ) {
		final int pitch = nota.pitch();
		si.mc.noteOn( pitch, intensita );
		try {
			Thread.sleep( nota.durata().ms( Sintetizzatore.bpm() ) );
		} catch ( InterruptedException swallow ) {}
		si.mc.noteOff( pitch );
	}

	public void suona( final Nota nota ) {
		suona( nota, Sintetizzatore.INTENSITA_DEFAULT );
	}

	public void suona( final Pausa pausa ) {
		try {
			Thread.sleep( pausa.durata().ms( Sintetizzatore.bpm() ) );
		} catch ( InterruptedException swallow ) {}
	}
	
	public String toString() {
		return si.inst.toString();
	}

	protected int canale() {
		return si.n;
	}
}