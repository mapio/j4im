package it.unimi.di.j4im.notazione;

/** Questa classe rappresenta la durata di una nota, o pausa. */
public enum Durata {
	
	SEMIBREVE( 1 ), 
	MINIMA( 2 ), 
	SEMIMINIMA( 4 ), 
	CROMA( 8 ), 
	SEMICROMA( 16 ), 
	BISCROMA( 32 ), 
	SEMIBISCROMA( 64 );
	
	/** La durata della nota come frazione della misura. */
	final int denominatore;
	
	Durata( final int denominatore ) {
		this.denominatore = denominatore;
	}
	
	/** Restituisce la durata con cui inizia una stringa data.
	 * 
	 * @param str la stringa al cui inizio si trova la durata.
	 * @return  la durata corrispondente se la stringa inizia per una delle possibili durate:
	 * 		    <samp>1/1</samp>, <samp>1/2</samp>, <samp>1/4</samp>, <samp>1/8</samp>, <samp>1/16</samp>, <samp>1/32</samp>, <samp>1/64</samp>.
	 * @throws IllegalArgumentException se la stringa non inizia con una durata valida.
	 */		
	public static Durata fromString( final String str ) {
		for ( Durata d : Durata.values() )
			if ( str.equals( d.toString() ) ) return d;
		throw new IllegalArgumentException( "Impossibile determinare la durata di " + str );
	}

	/** Restituisce la durata (come frazione della misura).
	 * 
	 * @return il denominatore.
	 */
	public int denominatore() {
		return denominatore;
	}

	/** Restituisce la durata in millisecondi, dato il numero di quarti al minuto. 
	 * 
	 * @param bpm il numero di quarti al minuto.
	 * @return la durata in millisecondi.
	 */
	public int ms( final int bpm ) {
		return (int)( 1000 * ( 60.0 / bpm ) * ( 4.0 / denominatore ) );
	}
	
	/** Resituisce la durata in ticks, data la risoluzione.
	 * 
	 * @param resolution la risoluzione.
	 * @return la durata in ticks.
	 */
	public int ticks( final double resolution ) {
		return (int)( 4 * resolution / denominatore );
	}

	public String toString() {
		return "1/" + denominatore();
	}
}