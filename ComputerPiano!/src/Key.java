/* KEY CLASS 
 * - The purpose of this class is to store the pitch and staff location of a note.
 * - This class simplifies the Note class, which has an instance variable of type Key.
 * - It has a constructor and standard getters/setters.
 */
public class Key {
	private String pitch;
	private int staffLoc;

	public Key(String pitch) {
		this.pitch = pitch;
	}

	public void setPitch(String pitch) {
		this.pitch = pitch;
	}

	public String getPitch() {
		return pitch;
	}

	public void setStaffLoc(int staffLoc) {
		this.staffLoc = staffLoc;
	}

	public int getStaffLoc() {
		return staffLoc;
	}
}