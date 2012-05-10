import java.awt.Color;

public class Arena {

	static boolean event = false;

	public static int b = 0;

	static byte w = 25;
	static byte h = 25;
	static Color color;
	static byte levelswitch = 0;
	static byte soundswitch = 0;
	static byte gate = 1; // Randerkennung an oder aus (1 oder 0)
	static double x = 0;
	static double y = 0;
	static byte gamespeed = 20; // 14 ist defaultwert

	// Letzte gedr�ckte Taste besser ist es bei int zu lassen
	static int lastkey = 0;
	static double[][] feld = new double[w][h];
	// Keycodes
	public static boolean right = false;
	public static boolean left = false;
	public static boolean up = false;
	public static boolean down = false;
	public static boolean space = false;
	public static boolean click = false;
	// Zufallszahlen
	static double randx = (int) (Math.random() * w);
	static double randy = (int) (Math.random() * h);
	// counter
	public static int counter = 0;

	// Konstruktoren

	static double[] inventar = new double[5];

	public static Player plr = new Player(randy, randx);

	private static String signal;

	// ////////////////////////////////////////////////////////////////////////////////
	// / main
	// ///////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		draw(); // Initialisiere den Bildschirminhalt mit draw() einmal
		welcome();
		system(); // Setze die Grafik und die Steuerung in eine Schleife

	}

	private static void system() {
		while (true) {
			// Beginn der Messung in Millisekunden

			long start = System.currentTimeMillis();

			fr(gamespeed); // Bildschirmrefresh
			steuerung(); // Tastatureingaben
			levelset(); // Leveldaten

			plr.draw(lastkey); // Player zeichnen

			signal = ("Duration in ms: " + (System.currentTimeMillis() - start));

			// Ende der Messung
			out(signal);
		}

	}

	private static void out(String signal2) {

		StdDraw.textLeft(-w, -h, signal);
		StdDraw.textLeft(-w + 3, -h + 3, "G: " + String.valueOf(gate));
		Clicks(counter);
	}

	private static void Clicks(int counter2) {

	}

	private static void setcolor(byte i) {

		if (i == 0) {
			StdDraw.setPenColor(StdDraw.BLACK);
		}
		if (i == 1) {
			StdDraw.setPenColor(StdDraw.WHITE);
		}

	}

	// Initialisiere Screen

	private static void draw() {
		StdDraw.setCanvasSize(w * 25, h * 25);
		StdDraw.setXscale(-w, w);
		StdDraw.setYscale(-h, h);
		setcolor((byte) 0);

	}

	private static void fr(byte gamespeed2) {

		// Die zwei Linien nicht �ndern oder schleifen sonst kommt es zu Fehlern
		// : show and clear Vorsicht!!!!
		StdDraw.show(gamespeed);
		StdDraw.clear();

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Steuerung
	// ///////////////////////////////////////////////////////////////////////////////

	private static void tasten(byte levelswitch2) {

		if (space) {
			System.out.println("OK");

			lastkey = 6;

		}

		if (left) {
			x = plr.x;
			plr.x--;

			lastkey = 4; // Speicher die Aktion separat

		}

		if (down && up && left && right) {

			plr.x = 0;
			plr.y = 0;
			lastkey = 9;

		}

		if (right) {
			x = plr.x;
			plr.x++;

			lastkey = 1;

		}

		if (up) {

			y = plr.y;
			plr.y++;

			lastkey = 3;

		}

		if (down) {
			plr.y--;
			y = plr.y;
			lastkey = 0;

		}

		if (StdDraw.mousePressed()) {

			counter++;

		}

	}

	private static void steuerung() {

		collision(gate);
		tasten(levelswitch);

		// Levelswitch f�r das schnelle wechseln von Levels

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Kollision
	// ///////////////////////////////////////////////////////////////////////////////

	private static void collision(byte gate2) {

		Arena.gate = gate2;
		Arena.x = plr.x;
		Arena.y = plr.y;
		if (Arena.gate == 0) {

			if (plr.x >= w) {

				event2();

			}
			if (plr.x <= -w) {
				plr.x = -w;
				event1();

			}
			if (plr.y >= h) {
				plr.y = h;
				event4();

			}
			if (plr.y <= -h) {
				plr.y = -h;
				event3();

			}

		}

		if (Arena.gate == 1) {

			gate1();
			gate2();
			gate3();
			gate4();

			if (plr.x >= w) {
				plr.x = -w;

				if ((plr.x >= w) && (plr.y <= 2)) {
					event2();
				}

			}

			else {
				if (plr.x < -w)
					plr.x = w;
			}

			if (plr.y >= h) {
				plr.y = -h;

			}

			else {
				if (plr.y < -h)
					plr.y = h;
			}

		}

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Levels
	// ///////////////////////////////////////////////////////////////////////////////

	private static void welcome() {

		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		String message = "Das Spiel macht Spass!";
		StdDraw.textLeft(0, -5, message);
		StdDraw.show(400);
		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		message = "Deine beiden Gl�ckszahlen sind die: " + randx + " und "
				+ randy;
		StdDraw.textLeft(-w, 0, message);
		StdDraw.show(400);
		String kette = "Die Position deines Spielers ist ";
		StdDraw.textLeft(-w, 0, message);
		StdDraw.show(1000);
		StdDraw.clear();
		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		message = "Steuer das Spiel mit Pfeil oben, unten, rechts und Space.";

		if (plr.y <= 0) {
			kette += " unterhalb";
		} else {
			kette += " oberhalb";
		}

		if (plr.x <= w / 8) {
			kette += " und mittig";
		} else {
			kette += " und aussen";
		}

		if (plr.x <= 0) {
			kette += " links im Feld";

		} else {
			kette += " rechts im Feld.";
		}

		StdDraw.textLeft(-w, 0, message);
		StdDraw.textLeft(-w, -1, kette);
		StdDraw.show(500);

	}

	private static void levelset() {

		// Objektspuren des players

		if ((Arena.gate == 0)) {

			StdDraw.picture(Arena.x, Arena.y, "gif/teleport.gif");

		}

		if (levelswitch == 0) {
			mainlevel();
		} else {
			setuplevel();
		}

	}

	private static void setuplevel() {

		StdDraw.picture(0, 0, "jpg/boden.jpg", w * 2, h * 1.75);
		levelbrand();

		StdDraw.setPenColor(color);
		String message = "Setup Level: Ausr�stung / PowerUps";
		StdDraw.text(0, -h, message);

	}

	private static void levelbrand() {
		StdDraw.picture(-w, h, "gif/unicorn.gif", 6, 6);
		StdDraw.picture(w - 1, h - 1, "gif/schlumpf.gif", 4, 4, 30);
		StdDraw.picture(-w + 2, -h + 4, "gif/uni_duesseldorf_logo.gif", 10, 7,
				-75);
	}

	private static void mainlevel() {

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledSquare(0, 0, w);
		StdDraw.setPenColor(color);
		StdDraw.filledSquare(0, 0, w - 1);
		StdDraw.picture(w - 7, -h + 7, "gif/Jabba_Granate.gif", 15, 15);
		levelbrand();

		String message = "Kampflevel: Erprobe deine Skills";
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0, -h - 1, message);
		action1();

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Events
	// ///////////////////////////////////////////////////////////////////////////////
	private static void event1() {
		counter++;

		if (counter >= 1) {
			plr.x = -w / 1.5;
		}
		StdAudio.play("audio/pack.wav");
		double r = (Math.random() * 9);

		int R = (int) (Math.random() * 256);
		int G = (int) (Math.random() * 256);
		int B = (int) (Math.random() * 256);
		Color randomColor = new Color(R, G, B);
		StdDraw.setPenColor(randomColor);

		// render();
		StdDraw.picture(-w, plr.y * r, "gif/star.gif");
		StdDraw.show(30);
		soundswitch = 3;
		StdDraw.textLeft(0, h + 1, "Hier geht es nicht weiter.");
		counter = 0;

	}

	private static void event2() {
		// Rechter Rand Event
		counter++;
		plr.x = 0;
		plr.y--;
		if (counter == 1) {
			plr.x = w / 2;
		}

		StdAudio.play("audio/cool.wav");
		StdAudio.play("audio/right.wav");
		if (gate == 1) {
			double r = (Math.random() * 9);

			int R = (int) (Math.random() * 256);
			int G = (int) (Math.random() * 256);
			int B = (int) (Math.random() * 256);
			Color randomColor = new Color(R, G, B);
			StdDraw.setPenColor(randomColor);

			StdDraw.filledCircle(plr.x, plr.y * r, 5 * r);
			StdDraw.circle(plr.x, plr.y * r, 2 * r);
			color = randomColor;
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.textLeft(-4, -h - 1,
					"Dieser Level scheint gerade verschlossen zu sein.");
			StdDraw.show(30);
		}

		counter = 0;
	}

	private static void event3() {

		counter++;

		if (counter >= 1) {
			plr.y = -h + 3;
		}

		StdAudio.play("audio/center.wav");

		int d = 6;

		StdDraw.picture(plr.x, d, "gif/apple.gif", 3, 3, 45);

		d = d - 8;
		StdDraw.picture(plr.x, d, "gif/apple.gif", 4, 4, 120);

		StdDraw.picture(plr.x, plr.y + 5, "gif/apple.gif", 6, 6, 200);
		StdDraw.picture(plr.x - 1, plr.y + 3, "gif/star.gif", 2, 2, 200);
		StdDraw.picture(plr.x + 1, plr.y + 2, "gif/star.gif", 1, 1, 180);

		StdDraw.show(50);
		counter = 0;
	}

	private static void event4() {
		// TODO Auto-generated method stub
		counter++;

		if (counter <= 3) {
			StdAudio.play("audio/coollow.wav");
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Effekte
	//
	// einige levelabh�ngige Aktionen
	//
	// ///////////////////////////////////////////////////////////////////////////////

	private static void action1() {
		if (space) {

			StdDraw.picture(plr.x, plr.y + 3, "gif/star.gif", 1, 1, -40);
			StdDraw.picture(plr.x, plr.y - 3, "gif/star.gif", 1, 1, 40);
			StdDraw.picture(plr.x + 3, plr.y, "gif/star.gif", 1, 1, 40);
			StdDraw.picture(plr.x - 3, plr.y, "gif/star.gif", 1, 1, -40);

			StdAudio.play("audio/yeah.wav");
			Arena.space = false;

		}

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Teleporter
	//
	// Spezialaktion Gelangt der Spieler in eine Ecke kann er sich in
	// die gegen�berliegende Ecke teleportieren.
	// ///////////////////////////////////////////////////////////////////////////////

	// Schlumpfteleporter

	private static void gate1() {
		if ((plr.x == w) && (plr.y == h)) {

			plr.y = -h;
			plr.x = -w;
			seqence1();
		}
	}

	private static void seqence1() {
		if (soundswitch == 1) {
			StdAudio.play("audio/robot3.wav");
			soundswitch = 2;
		}
		if (soundswitch == 2) {
			StdAudio.play("audio/reverse.wav");
			StdAudio.play("audio/cc-warp.wav");
		}

		if (soundswitch == 0) {
			StdAudio.play("audio/horse.wav");
			soundswitch = 3;
		}

	}

	// Kamplevelteleporter

	private static void gate2() {
		if ((plr.x == 0) && (plr.y == h)) {

			plr.y = -h;
			plr.x = 0;
			seqence2();
		}
	}

	private static void seqence2() {
		StdAudio.play("audio/robot.wav");
		soundswitch = 2;
		levelswitch = 1;
	}

	// Unicornteleporter

	private static void gate3() {
		if ((plr.x == -w) && (plr.y == h)) {

			plr.y = -h;
			plr.x = w;
			seqence3();
		}
	}

	private static void seqence3() {
		if (soundswitch == 2) {
			StdAudio.play("audio/pony.wav");
		}
		StdAudio.play("audio/robot2.wav");
		soundswitch = 0;

	}

	private static void gate4() {
		if ((plr.x == w - 2) && (plr.y == -h)) {

			plr.y = -h;
			plr.x = w;
			seqence4();
		}
	}

	private static void seqence4() {
		if (soundswitch == 3) {
			StdAudio.play("audio/death_comp.wav");

		}

	}

}