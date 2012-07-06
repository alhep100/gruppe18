package edu.propra.bomberman.quantizer;

public class Quantizer {

	static boolean reset = false;
	// default how long should the stopwatch work
	private static int delay = 5;
	// default how short should be the interval
	private static double fraction = 1;
	// String to print out the right format in realtime
	static String format = "";
	// String to print out the current interval trough concatenation
	static String emp = "!";

	/**
	 * 
	 * In der quant methode wird eine while schleife verwendet, die infinit
	 * l�uft. es wird eine finite Variable verwendet, die inkrementiert wird,
	 * die mit der vorgegebenen Zeit (delay) verglichen wird. wird und dann bei
	 * einem identischen Zaehlerstand wieder zur�ckgesetzt wird
	 * 
	 * Der Multipliaktor fraction stellt sicher, welches Zeitformat verwendet
	 * wird. Zeitformat kann als string ausgegeben werden. Das wird anhand von
	 * if Abfragen �berpr�ft. Die Variablen sind soweit statisch gehalten (nicht
	 * zu verwechseln mit 'static'), Es gibt zwei Formate: im Sekundenbereich
	 * und Millisekundenbereich
	 * 
	 * 
	 * Im unteren Bereich der Klasse sind die Standardmethoden f�r getter und
	 * setter generiert
	 * 
	 */

	public static int getDelay() {
		return delay;
	}

	public static double getFraction() {
		return fraction;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		quant(getDelay());// TODO Auto-generated method stub
		// start by default values
	}

	private static int quant(int delay) {
		// TODO Auto-generated method stub

		int a = 0;
		while (true) {

			try {
				Thread.sleep((long) (1000 * getFraction())); // wait a second
				// multiplied by
				// fraction
			} catch (InterruptedException ex) {
			}
			a++;
			if (a % 100 == 0) {
				emp = emp + "!";
				// Concatenate with a ! every 100th Step detected if modulo
				// equals zero
			}
			if (getFraction() == 1) {
				System.out.println(a + "s");

				format = "_sec";
				// Print out current trace
			}
			if (getFraction() == 0.001) {
				System.out.println(a + "ms");
				System.out.println("Time is running out!" + emp);
				format = "_millisec";

			}
			if (a >= delay || reset == true) {
				System.out.println("Quantizer stopped at " + a + format);
				break;
				// Print out position and break the loop
			}
		}
		return a;
		// returns the counter
	}

	public static void reset() {
		// TODO Auto-generated method stub
		reset = false;
		// reset the reset to false per default if changed during the game

	}

	public static void setDelay(int delay) {
		Quantizer.delay = delay;
	}

	public static void setFraction(double fraction) {
		Quantizer.fraction = fraction;
	}

}
