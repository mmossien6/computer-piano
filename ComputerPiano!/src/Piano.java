/* PIANO CLASS
 * - The purpose of the Piano class is to play the audio. It stores a list of keys, each of
 * which has a string pitch, and there is a method that will play the proper audio pitch
 * given the string pitch.
 * - The Piano class uses the JFugue object 'RealtimePlayer' to output sounds.
 */

import javax.sound.midi.MidiUnavailableException;
import org.jfugue.realtime.RealtimePlayer;

public class Piano {
	private Key[] piano = new Key[13];
	RealtimePlayer realTimePlayer;

	public Piano() {
		try {
			realTimePlayer = new RealtimePlayer();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		piano[0] = new Key("C4s");
		piano[1] = new Key("Db4s");
		piano[2] = new Key("D4s");
		piano[3] = new Key("Eb4s");
		piano[4] = new Key("E4s");
		piano[5] = new Key("F4s");
		piano[6] = new Key("Gb4s");
		piano[7] = new Key("G4s");
		piano[8] = new Key("Ab4s");
		piano[9] = new Key("A4s");
		piano[10] = new Key("Bb4s");
		piano[11] = new Key("B4s");
		piano[12] = new Key("C5s");
	}

	public void playNote(char key) { // plays a pitch depending upon the key
										// pressed
		if (key == 'a') {
			realTimePlayer.play(piano[0].getPitch());
		} else if (key == 'w') {
			realTimePlayer.play(piano[1].getPitch());
		} else if (key == 's') {
			realTimePlayer.play(piano[2].getPitch());
		} else if (key == 'e') {
			realTimePlayer.play(piano[3].getPitch());
		} else if (key == 'd') {
			realTimePlayer.play(piano[4].getPitch());
		} else if (key == 'f') {
			realTimePlayer.play(piano[5].getPitch());
		} else if (key == 't') {
			realTimePlayer.play(piano[6].getPitch());
		} else if (key == 'g') {
			realTimePlayer.play(piano[7].getPitch());
		} else if (key == 'y') {
			realTimePlayer.play(piano[8].getPitch());
		} else if (key == 'h') {
			realTimePlayer.play(piano[9].getPitch());
		} else if (key == 'u') {
			realTimePlayer.play(piano[10].getPitch());
		} else if (key == 'j') {
			realTimePlayer.play(piano[11].getPitch());
		} else if (key == 'k') {
			realTimePlayer.play(piano[12].getPitch());
		}
	}

	public String getValueFromKeyboard(char key) { // gets a string pitch
													// depending upon key
													// pressed
		if (key == 'a') {
			return piano[0].getPitch();
		} else if (key == 'w') {
			return piano[1].getPitch();
		} else if (key == 's') {
			return piano[2].getPitch();
		} else if (key == 'e') {
			return piano[3].getPitch();
		} else if (key == 'd') {
			return piano[4].getPitch();
		} else if (key == 'f') {
			return piano[5].getPitch();
		} else if (key == 't') {
			return piano[6].getPitch();
		} else if (key == 'g') {
			return piano[7].getPitch();
		} else if (key == 'y') {
			return piano[8].getPitch();
		} else if (key == 'h') {
			return piano[9].getPitch();
		} else if (key == 'u') {
			return piano[10].getPitch();
		} else if (key == 'j') {
			return piano[11].getPitch();
		} else if (key == 'k') {
			return piano[12].getPitch();
		} else
			return null;
	}

	public void playThis(String pitch) { // plays a particular pitch
		realTimePlayer.play(pitch);
	}

}