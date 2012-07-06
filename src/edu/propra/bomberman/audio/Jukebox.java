package edu.propra.bomberman.audio;

import java.net.URL;
import java.applet.*;
import javax.sound.midi.*;

public class Jukebox {

	/**
	 * 
	 * Jukebox ist eine Klasse um die Sounds abzuspielen die zu einem bestimmten
	 * Event von der Gameengine aufgerufen werden
	 * 
	 * Es werden final Variablennamen verwendet, um sich schneller im Index der
	 * Sounddateien zurechtzufinden. Beispielsweise Gun für Kanonenschuss, Roll
	 * für eine Trommel, Explosion wie der name schon sagt, soundup für einen
	 * besonderen event. usw
	 * 
	 * Die Sounds sind als Clips gespeichert und können von der Methode
	 * playSound abgespielt werden. Es ist auch die Verwendung von midi-Dateien
	 * möglich.
	 * 
	 * Damit der Player nich leer abschließt, wird ein Thread benutzt um das
	 * Programm zu "beschaeftigen", dieser wartet, bis die datei geladen wird,
	 * dann wird sie von dem player abgepsielt
	 * 
	 * 
	 * Für diese wird separat die Datei erst als Sequenz angelegt, die internen
	 * midi -Tracks werden als sequenz zu dem midi-sequencer gesendet, die dann
	 * in hoerbare Gerausche umgewandelt werden.
	 * 
	 * 
	 * Weitere Parameter sind noch ausbaufähig.- Tempowechsel oder
	 * Lautstärkeregelung.
	 * 
	 * @author Alex Hepting
	 * 
	 * @version 1.0
	 */
	public static final int Gun = 0;
	public static final int Roll = 1;
	public static final int Explosion = 2;
	public static final int Soundup = 3;
	public static final int Sounddown = 4;
	public static final int Death = 5;
	private AudioClip[] sounds; // AudioClips

	Sequencer sequencer;

	public Jukebox() {
		initSounds();
	}

	private void initSounds() {

		/*
		 * Initialisiert die Sounds
		 */
		try {
			// needed for correct loading of resources in JAR-Files
			ClassLoader loader = Jukebox.class.getClassLoader();
			// load AudioClips
			sounds = new AudioClip[7];
			sounds[Jukebox.Gun] = Applet.newAudioClip(loader
					.getResource("resources/gun.wav"));
			sounds[Jukebox.Roll] = Applet.newAudioClip(loader
					.getResource("resources/roll.wav"));
			sounds[Jukebox.Explosion] = Applet.newAudioClip(loader
					.getResource("resources/explosion.wav"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {

		/*
		 * Mainmethode zum testen
		 */
		Jukebox jukebox = new Jukebox(); // initialize jukebox

		jukebox.playSound(0); // play first sound
		try {
			Thread.sleep(100); // wait a bit
		} catch (InterruptedException ex) {
		}
		jukebox.playSound(1); // play second sound

		Sequence music = jukebox
				.loadSequence("resources/Deadmau5_-_Arguru.mid"); // load
																	// Sequence
																	// http://www.nonstop2k.com/community/midifiles/3239-deadmau5-arguru-progressive-house-midi.html
		jukebox.playSequence(music); // start playing Sequence
		try {

			Thread.sleep(1000); // wait a bit

		} catch (InterruptedException ex) {
		}
		jukebox.stopSequence();

		Sequence music2 = jukebox.loadSequence("resources/atb__don't_stop.mid"); // load
																					// Sequence
																					// http://www.nonstop2k.com/community/midifiles/980-atb-don-t-stop-trance-midi.html
		jukebox.playSequence(music2); // start playing Sequence

		jukebox.playSound(4);
		jukebox.refresh(600);
		jukebox.playSound(3);

		try {

			Thread.sleep(1000); // wait a bit

		} catch (InterruptedException ex) {
		}

	}

	public void refresh(int i) {

		/*
		 * refresh (oder eher refreshrate) Legt die Zwischenzeit fest bis ein
		 * anderer Sound gespielt wird
		 */
		// TODO Auto-generated method stub
		try {

			Thread.sleep(i); // wait a bit

		} catch (InterruptedException ex) {
		}
	}

	public Sequence loadSequence(String filename) {
		Sequence result = null;
		try {
			ClassLoader loader = Jukebox.class.getClassLoader();
			URL url = loader.getResource(filename);
			result = MidiSystem.getSequence(url);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void playSequence(Sequence seq) {
		// TODO Auto-generated method stub
		if (seq == null)
			return;
		try {

			// Create a sequencer for the sequence

			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(seq);

			// Start playing
			sequencer.start();
		} catch (MidiUnavailableException e) {
		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
		}
	}

	public void playSound(int index) {
		if (index > 0 && index < sounds.length)
			sounds[index].play();
	}

	private void stopSequence() {
		if (sequencer.isRunning())
			sequencer.stop();

	}
}