import java.awt.Color;

public class Arena {

	public static int b = 0;

	static byte w = 25;
	static byte h = 25;

	static Color color;
	static byte levelswitch = 0;
	static byte soundswitch = 0;

	static double x = 0;
	static double y = 0;
	static double a = 0.7; // Beschleunigung
	static int n = 0;
	final static byte opt = (byte) Math.sqrt((w * h) * 1.5);
	static byte gamespeed = opt; // 14 ist defaultwert

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
	public static int obst = 0;
	public static byte cxy = 0; // Information wurde obere oder untere Wand
								// getroffen?

	// Konstruktoren

	static double[] inventar = new double[5];

	public static Player plr = new Player(randy, randx);
	public static Bombe bo = new Bombe(0, 0);
	public static Apple apple = new Apple(0, 0);

	private static String signal;

	public static boolean run=true;

	// ////////////////////////////////////////////////////////////////////////////////
	// / main
	// test test
	public static void main(String[] args) {
		draw(); // Initialisiere den Bildschirminhalt mit draw() einmal
		// welcome();
		system(); // Setze die Grafik und die Steuerung in eine Schleife

	}

	private static void system() {
		
		
		while (run) {
			
		
			// Beginn der Messung in Millisekunden

			long start = System.currentTimeMillis();

			
		steuerung(); // Tastatureingaben
		levelswitch(); // Leveldaten

			plr.draw(lastkey); // Player zeichnen
			bo.draw(); //Bombe Zeichnen

			signal = ("Duration in ms: " + (System.currentTimeMillis() - start));

			// Ende der Messung
			out(signal);
			
			
		}

	}

	private static void out(String signal2) {

		StdDraw.textLeft(-w, -h, signal);

	
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

		collision();
		tasten(levelswitch);

		// Levelswitch f�r das schnelle wechseln von Levels

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Kollision
	// ///////////////////////////////////////////////////////////////////////////////

	private static void collision() {

		Arena.x = plr.x;
		Arena.y = plr.y;

		if (plr.x >= w) {

			plr.x = w;
			if (levelswitch == 0)
				colorwall();
		}
		if (plr.x <= -w) {
			plr.x = -w;

		}
		if (plr.y >= h) {
			plr.y = h;

		}
		if (plr.y <= -h) {
			plr.y = -h;
			if (levelswitch <= 1)
				if (plr.health<=50)
				{
				apfel();
				}

		}

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// / Levels
	// ///////////////////////////////////////////////////////////////////////////////

	private static void welcome() {

		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		String message = "Viel Spass!";
		StdDraw.textLeft(0, -5, message);
		StdDraw.show(400);
		StdDraw.picture(0, 0, "jpg/introscreen.jpg");
		message = "Deine beiden Gl�ckszahlen sind die: " + randx + " und "
				+ randy;
		StdDraw.textLeft(-w, 0, message);
		StdDraw.show(400);
		String kette = "Die Position des Spielers ist ";
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

	private static void levelswitch() {

		// Objektspuren des players

		gate1();

		gate3();
		gate4();

		

		if (levelswitch == 0) {
			mainlevel();
		}
		if (levelswitch == 1) {
			setuplevel();
		}

		if (obst >= 3) {
			levelswitch = 2;
			obstlevel();
		}
		
		if (levelswitch==99)
		{
			StdDraw.clear();
			
			gameover();
		}
		
	}

	private static void setuplevel() {
		fr(gamespeed); // Bildschirmrefresh
		gate2();
		StdDraw.picture(0, 0, "jpg/boden.jpg", w * 3, h * 3);

		StdDraw.setPenColor(color);
		String message = "Setup Level: Ausr�stung / PowerUps";
		StdDraw.text(0, -h, message);

	}

	private static void levelbrand() {
		StdDraw.picture(-w, h, "gif/unicorn.gif", 6, 6);
		StdDraw.picture(w - 1, h - 1, "gif/schlumpf.gif", 4, 4, 30);

	}

	private static void mainlevel() {
		fr(gamespeed); // Bildschirmrefresh
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.filledSquare(0, 0, w);
		
		StdDraw.picture(Arena.x, Arena.y, "gif/teleport.gif");
		StdDraw.setPenColor(StdDraw.MAGENTA);
		StdDraw.filledSquare(0, 0, w - 1);
		StdDraw.setPenColor(color);
		StdDraw.filledSquare(0, 0, w - 1);

		StdDraw.picture(w - 7, -h + 7, "gif/Jabba_Granate.gif", 15, 15);
		levelbrand();

		String message = "Kampflevel: Erprobe deine Skills";
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0, -h - 1, message);
		action1();

	}

	
	private static void gameover(){
		
	//	Arena.run=false;
		
		
		StdDraw.text(0, 5, "GAME OVER");
		StdDraw.text(0, -5, "Continue?? y/n");
		StdDraw.picture(0, 0, "gif/deko1.gif");
		StdDraw.show(500);
		StdDraw.picture(0, 0, "gif/deko.gif");
		StdDraw.show(500);
		Arena.plr.x=0;
		Arena.plr.y=0;
	
		
	}
	
	private static void obstlevel() {
		fr(gamespeed); // Bildschirmrefresh
		StdDraw.picture(0, 0, "jpg/kacheln.jpg", w * 2, h * 2);
		levelbrand();
		// Exitbedingung des Levels
		if (apple.anzahl == 5) {
			StdAudio.play("audio/yeah.wav");
			apple.anzahl = 0;
			levelswitch = 0;
			Arena.obst = 0;
			plr.y = 0;
			a = 1;
			Arena.gamespeed = opt;
		}
		// Ausgangsbedingung des Levels
		if (n == 0) {
			// LR
			if (cxy == 1) {
				apple.y = Math.random() * h;
				System.out.println("AppleY" + apple.y);

			
			}
			// OU
			if (cxy == 2) {
				apple.x = Math.random() * w;
				System.out.println("AppleY" + apple.x);
			
			}

			Arena.gamespeed += apple.anzahl;
			n++;
			cxy=0;
		}

		StdDraw.text(plr.x, h + 1, "�pfel: " + apple.anzahl);

		// Apfelkollision (mit dem Spieler)

		if ((Math.abs(apple.x - plr.x) <= w * 0.15)
				&& (Math.abs(apple.y - plr.y) <= h * 0.15)) {
			StdDraw.text(apple.x, apple.y + 5, "Apfel gefunden!");
plr.health+=7;

if (plr.health>=100){plr.health=100;}
	
			apple.anzahl++;
			a *= 1.5;
			n = 0;

		}
		apple.draw();

		// Beschleunigung des Apfels
		if (apple.anzahl % 2 == 0) {
			apple.x += Arena.a;

		} else {
			apple.y += Arena.a;

		}

		// Apfelkollision (mit der Wand)

		// mit LR
		if ((apple.x > w + 4) || (apple.x < -w - 4)) {
			a = a * -1; // Wenn apple die Wand trifft �ndere die Richtung der
						// Beschleunigung a
			apple.y += Math.random() * 10;
			if (apple.y > h) {
				apple.y = -h;
			}

			if (cxy == 1) {
				Arena.x = apple.x;
				apple.x = apple.y;
				apple.y = Arena.x;
			}
			cxy = 1;
		}
		// mit OU
		if ((apple.y > h + 4) || (apple.y < -h - 4)) {

			a = a * -1; // Wenn apple die Wand trifft �ndere die Richtung der
						// Beschleunigung a

			apple.x += Math.random() * 10;
			if (apple.x > w) {
				apple.y = -w;
			}
			if (cxy == 2) {
				Arena.y = apple.y;
				apple.y = apple.x;
				apple.x = Arena.y;
			}
			cxy = 2;
		}

		String message = "Obstlevel: Sammel �pfel ein!";
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0, -h - 1, message);
		action1();

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Events
	// ///////////////////////////////////////////////////////////////////////////////

	private static void colorwall() {
		// Rechter Rand Event
		plr.health-=10;
		counter++;
		plr.x = 0;
		plr.y--;
		if (counter == 1) {
			plr.x = w / 2;
		}

		StdAudio.play("audio/cool.wav");
		StdAudio.play("audio/right.wav");

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

		counter = 0;
	}

	private static void apfel() {
		// Unterer Rand Event
		counter++;
		obst++;
		n = 0;
		if (counter >= 1) {
			plr.y = -h + 3;
		}

		StdAudio.play("audio/center.wav");

		

		StdDraw.picture(plr.x, 6, "gif/apple.gif", 3, 3, 45);

		
		StdDraw.picture(plr.x, -8, "gif/apple.gif", 4, 4, 120);

		StdDraw.picture(plr.x, plr.y + 5, "gif/apple.gif", 6, 6, 200);
		StdDraw.picture(plr.x - 1, plr.y + 3, "gif/star.gif", 2, 2, 200);
		StdDraw.picture(plr.x + 1, plr.y + 2, "gif/star.gif", 1, 1, 180);
		StdDraw.text(0, 0, "Obst: " + obst);
		StdDraw.show(80);
		counter = 0;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// /Effekte
	//
	// einige levelabh�ngige Aktionen
	//
	// ///////////////////////////////////////////////////////////////////////////////

	private static void action1() {
		if (space) {

			StdDraw.picture(w - n, -h + n, "gif/star.gif", 3, 3, n * 12);
			StdDraw.picture(w - n, h - n, "gif/star.gif", 3, 3, n * -12);
			StdDraw.picture(-w + n, -h + n, "gif/star.gif", 3, 3, n * 12);
			StdDraw.picture(-w + n, h - n, "gif/star.gif", 3, 3, n * -12);

			n++;

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
			levelswitch = 1;

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
	}

	private static void reset() {
		plr.x = 0;
		plr.y = 0;

	}

	// Kamplevelteleporter

	private static void gate2() {

		if ((plr.x == 0) && (plr.y == h)) {

			plr.y = -h;
			plr.x = 0;
			StdAudio.play("audio/robot.wav");
			soundswitch = 2;
			levelswitch = 0;
			reset();
		}
	}

	// Unicornteleporter

	private static void gate3() {
		if ((plr.x == -w) && (plr.y == h)) {

			plr.y = -h;
			plr.x = w;
			if (soundswitch == 2) {
				StdAudio.play("audio/pony.wav");
			}
			StdAudio.play("audio/robot2.wav");

			levelswitch = 1;
		}
	}

	private static void gate4() {
		if ((plr.x == w - 2) && (plr.y == -h)) {

			plr.y = -h;
			plr.x = w;
			if (soundswitch == 3) {
				StdAudio.play("audio/death_comp.wav");

			}

		}
	}

}