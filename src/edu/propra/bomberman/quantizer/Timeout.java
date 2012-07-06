package edu.propra.bomberman.quantizer;

public class Timeout {

	/**
	 * Author: Alex - Time by utilizing the threads with 2 different
	 * modes
	 * 
	 * 
	 * Die Methode timeout implementiert direkt die Anzahl der Ticks
	 * und das Zeitformat in einem Schritt als beide parameter
	 * 
	 * Die Mainmethode hat 2 Eintraege, die nacheinander abgearbeitet werden
	 * also nur serielle Events von der Klasse behandelt werden können. 
	 * Es ist dazu gedacht in logischen Programmschritten verwendet zu verwenden
	 * .
	 * 
	 * Desswegen ist die Methode eher für seriellle Events in den Actions angesetzt.
	 * 
	 */
	// This class file is for standard testing
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Add your events here by timeout(time, fraction)
		// some examples:
		timeout(2000, 0.001);
		timeout(2, 1);

	}

	private static void timeout(int i, double d) {
		// TODO Auto-generated method stub
		//Initialize the values
		Quantizer.setDelay(i);
		Quantizer.setFraction(d);
		String[] args = null;
		Quantizer.main(args);
		Quantizer.reset();
	}

}
