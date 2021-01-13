/* NOTE CLASS
 * - The purpose of the Note class is to provide the proper information needed to display
 * each note. It contains a Key, which has this information.
 */
public class Note {
	private Key valueOfNote;

	public Note(int yLocation, String pitch) {
		valueOfNote = new Key(pitch);
		valueOfNote.setStaffLoc(yLocation);
	}

	public Key getKey() {
		return valueOfNote;
	}

}