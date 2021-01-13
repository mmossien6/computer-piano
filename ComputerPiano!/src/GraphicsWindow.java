import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsWindow extends JPanel {
	private static ArrayList<Note> notes;
	private static ArrayList<BufferedImage> noteImages;
	private static int noteCount;
	private static BufferedImage mainBackground;
	private static Piano piano;
	private static boolean mainScreenOpen;
	private static boolean learnSongScreenOpen;
	private static JFrame GUI;
	private static Container contentPane;
	private static ArrayList<ArrayList<Note>> learnSongs;
	private static ArrayList<ArrayList<BufferedImage>> learnSongImages;
	private static boolean learningMode, isStopped;
	private static int counter;
	private static int learnSongToPlay; // from 0-3
	private static boolean resetStaff;
	private int x;

	public GraphicsWindow(String imagePath, boolean isMainScreen) throws IOException {
		initGUI(imagePath, isMainScreen);
		initLearnSongs();
		addKeyListener(new PanelListener());
		learningMode = false;
		isStopped = false;
		x = 0;
	}

	public void initGUI(String imagePath, boolean isMainScreen) {
		if (isMainScreen) {
			notes = new ArrayList<Note>();
			counter = 0;
			noteImages = new ArrayList<BufferedImage>();
			piano = new Piano();
			noteCount = 0;
		}

		mainScreenOpen = isMainScreen;
		learnSongScreenOpen = !mainScreenOpen;
		this.setLayout(null);
		GUI = new JFrame();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setResizable(false);
		GUI.setTitle("Computer Piano");
		int w = 1235, h = 835;
		GUI.setSize(w, h);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		GUI.setLocation((screenWidth - w) / 2, (screenHeight - h) / 2);
		this.setFocusable(true);
		contentPane = GUI.getContentPane();
		contentPane.add(this);
		try {
			imagePath = "C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + imagePath;
			System.out.println("imagePath is " + imagePath);
			mainBackground = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		GUI.setVisible(true);
	}

	public void saveUserSong() {
		// learnSongs.add(notes);
		// learnSongImages.add(noteImages);
	}

	public JFrame getGUI() {
		return GUI;
	}

	public ArrayList<Note> getSong() {
		if (!learningMode)
			return notes;
		else
			return learnSongs.get(learnSongToPlay);
	}

	public Piano getPiano() {
		return piano;
	}

	public void setSong(int songToPlay) {
		learningMode = true;
		learnSongToPlay = songToPlay;
		try {
			learnSongImages.get(learnSongToPlay).set(0, ImageIO
					.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "Lit" + getImagePath(learnSongs.get(learnSongToPlay).get(0).getKey().getPitch()))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {// will create the screen the user sees 
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (mainScreenOpen) {
			g2.drawImage(mainBackground, 5, 5, null);

			Font font = g.getFont().deriveFont(18.0f);

			g.setFont(font);
			g.drawString("Press 'Z' To Clear Staff", 967, 660);
			g.drawString("Press 'X' To Stop Playback", 954, 700);
			g.drawString("Press 'C' To Save Song", 968, 740);
			if (!learningMode) {
				for (int i = 0; i < notes.size(); i++) {
					int xLoc = getXLocation(i + 1);
					g2.drawRenderedImage(noteImages.get(i),
							AffineTransform.getTranslateInstance(xLoc, notes.get(i).getKey().getStaffLoc()));
				}
			} else {
				for (int i = 0; i < learnSongs.get(learnSongToPlay).size(); i++) {
					int xLoc = getXLocation(i + 1);
					g2.drawRenderedImage(learnSongImages.get(learnSongToPlay).get(i), AffineTransform
							.getTranslateInstance(xLoc, learnSongs.get(learnSongToPlay).get(i).getKey().getStaffLoc()));
				}
			}

		} else if (learnSongScreenOpen) {
			int imageWidth = mainBackground.getWidth();
			int imageHeight = mainBackground.getHeight();
			g2.drawImage(mainBackground, (1235 - imageWidth) / 2, (835 - imageHeight) / 2 - 10, null);
		}
	}

	private int getXLocation(int i) { // used to place a note on the staff
		if (i == 1 || i == 17 || i == 33)
			return 225;
		if (i == 2 || i == 18 || i == 34)
			return 280;
		if (i == 3 || i == 19 || i == 35)
			return 340;
		if (i == 4 || i == 20 || i == 36)
			return 395;
		if (i == 5 || i == 21 || i == 37)
			return 450;
		if (i == 6 || i == 22 || i == 38)
			return 505;
		if (i == 7 || i == 23 || i == 39)
			return 563;
		if (i == 8 || i == 24 || i == 40)
			return 618;
		if (i == 9 || i == 25 || i == 41)
			return 673;
		if (i == 10 || i == 26 || i == 42)
			return 728;
		if (i == 11 || i == 27 || i == 43)
			return 786;
		if (i == 12 || i == 28 || i == 44)
			return 841;
		if (i == 13 || i == 29 || i == 45)
			return 896;
		if (i == 14 || i == 30)
			return 951;
		if (i == 15 || i == 31)
			return 115;
		if (i == 16 || i == 32)
			return 170;
		if (i == 46)
			return 946;
		return -1;
	}

	private class PanelListener extends KeyAdapter {//allows the program to read the keyboard
		public void keyPressed(KeyEvent e) {// based on the button pressed a note will be created and a pitch will be played
			char temp = e.getKeyChar();
			if (temp == 'z' || temp == 'x' || temp == 'c' || noteCount < 46 && !learningMode) {
				if (temp == 'a') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "CNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14) // determines the position of the note
						notes.add(new Note(61, "C4"));
					else if (noteCount < 30)
						notes.add(new Note(193, "C4"));
					else
						notes.add(new Note(326, "C4"));
					noteCount++;
				} else if (temp == 'w') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsFlat.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(51, "Db4"));
					else if (noteCount < 30)
						notes.add(new Note(183, "Db4"));
					else
						notes.add(new Note(316, "Db4"));
					noteCount++;
				} else if (temp == 's') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(51, "D4"));
					else if (noteCount < 30)
						notes.add(new Note(183, "D4"));
					else
						notes.add(new Note(316, "D4"));
					noteCount++;
				} else if (temp == 'e') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsFlat.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(41, "Eb4"));
					else if (noteCount < 30)
						notes.add(new Note(173, "Eb4"));
					else
						notes.add(new Note(306, "Eb4"));
					noteCount++;
				} else if (temp == 'd') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(41, "E4"));
					else if (noteCount < 30)
						notes.add(new Note(173, "E4"));
					else
						notes.add(new Note(306, "E4"));
					noteCount++;
				} else if (temp == 'f') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(31, "F4"));
					else if (noteCount < 30)
						notes.add(new Note(162, "F4"));
					else
						notes.add(new Note(295, "F4"));
					noteCount++;
				} else if (temp == 't') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsFlat.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(21, "Gb4"));
					else if (noteCount < 30)
						notes.add(new Note(153, "Gb4"));
					else
						notes.add(new Note(286, "Gb4"));
					noteCount++;
				} else if (temp == 'g') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(21, "G4"));
					else if (noteCount < 30)
						notes.add(new Note(153, "G4"));
					else
						notes.add(new Note(286, "G4"));
					noteCount++;
				} else if (temp == 'y') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsFlat.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(9, "Ab4"));
					else if (noteCount < 30)
						notes.add(new Note(140, "Ab4"));
					else
						notes.add(new Note(273, "Ab4"));
					noteCount++;
				} else if (temp == 'h') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "UpwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(9, "A4"));
					else if (noteCount < 30)
						notes.add(new Note(140, "A4"));
					else
						notes.add(new Note(273, "A4"));
					noteCount++;
				} else if (temp == 'u') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "DownwardsFlat.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(48, "Bb4"));
					else if (noteCount < 30)
						notes.add(new Note(180, "Bb4"));
					else
						notes.add(new Note(313, "Bb4"));
					noteCount++;
				} else if (temp == 'j') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "DownwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(48, "B4"));
					else if (noteCount < 30)
						notes.add(new Note(180, "B4"));
					else
						notes.add(new Note(313, "B4"));
					noteCount++;
				} else if (temp == 'k') {
					try {
						noteImages.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "DownwardsNatural.png")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (noteCount < 14)
						notes.add(new Note(37, "C5"));
					else if (noteCount < 30)
						notes.add(new Note(169, "C5"));
					else
						notes.add(new Note(302, "C5"));
					noteCount++;
				} else if (temp == 'z') {
					notes = new ArrayList<Note>();
					counter = 0;
					noteImages = new ArrayList<BufferedImage>();
					noteCount = 0;
				} else if (temp == 'x') {
					stopPlayback();
				} else if (temp == 'c') {
					saveSong();

				}

				piano.playNote(temp);
			} else if (counter < learnSongs.get(learnSongToPlay).size()
					&& (temp == 'a' || temp == 'w' || temp == 's' || temp == 'e' || temp == 'd' || temp == 'f'
							|| temp == 't' || temp == 'g' || temp == 'y' || temp == 'h' || temp == 'u' || temp == 'j'
							|| temp == 'k')
					&& piano.getValueFromKeyboard(temp)
							.equals(learnSongs.get(learnSongToPlay).get(counter).getKey().getPitch() + "s")) {

				try {
					if (counter < learnSongs.get(learnSongToPlay).size() - 1)
						learnSongImages.get(learnSongToPlay).set(counter + 1, ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + "Lit"
								+ getImagePath(learnSongs.get(learnSongToPlay).get(counter + 1).getKey().getPitch()))));

					learnSongImages.get(learnSongToPlay).set(counter, ImageIO.read(
							new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + getImagePath(learnSongs.get(learnSongToPlay).get(counter).getKey().getPitch()))));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				counter++;
				piano.playNote(temp);
			}

			repaint();
		}
	}

	public void initLearnSongs() { // creates all the learn songs for the user
		ArrayList<Note> twinkleTwinkle = new ArrayList<Note>();
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

		learnSongImages = new ArrayList<ArrayList<BufferedImage>>(4);
		learnSongs = new ArrayList<ArrayList<Note>>(4);

		twinkleTwinkle.add(new Note(61, "C4"));
		twinkleTwinkle.add(new Note(61, "C4"));
		twinkleTwinkle.add(new Note(21, "G4"));
		twinkleTwinkle.add(new Note(21, "G4"));
		twinkleTwinkle.add(new Note(9, "A4"));
		twinkleTwinkle.add(new Note(9, "A4"));
		twinkleTwinkle.add(new Note(21, "G4"));
		twinkleTwinkle.add(new Note(31, "F4"));
		twinkleTwinkle.add(new Note(31, "F4"));
		twinkleTwinkle.add(new Note(41, "E4"));
		twinkleTwinkle.add(new Note(41, "E4"));
		twinkleTwinkle.add(new Note(51, "D4"));
		twinkleTwinkle.add(new Note(51, "D4"));
		twinkleTwinkle.add(new Note(61, "C4"));

		twinkleTwinkle.add(new Note(153, "G4"));
		twinkleTwinkle.add(new Note(153, "G4"));
		twinkleTwinkle.add(new Note(162, "F4"));
		twinkleTwinkle.add(new Note(162, "F4"));
		twinkleTwinkle.add(new Note(173, "E4"));
		twinkleTwinkle.add(new Note(173, "E4"));
		twinkleTwinkle.add(new Note(183, "D4"));

		twinkleTwinkle.add(new Note(153, "G4"));
		twinkleTwinkle.add(new Note(153, "G4"));
		twinkleTwinkle.add(new Note(162, "F4"));
		twinkleTwinkle.add(new Note(162, "F4"));
		twinkleTwinkle.add(new Note(173, "E4"));
		twinkleTwinkle.add(new Note(173, "E4"));
		twinkleTwinkle.add(new Note(183, "D4"));

		twinkleTwinkle.add(new Note(193, "C4"));
		twinkleTwinkle.add(new Note(193, "C4"));
		twinkleTwinkle.add(new Note(286, "G4"));
		twinkleTwinkle.add(new Note(286, "G4"));
		twinkleTwinkle.add(new Note(273, "A4"));
		twinkleTwinkle.add(new Note(273, "A4"));
		twinkleTwinkle.add(new Note(286, "G4"));
		twinkleTwinkle.add(new Note(295, "F4"));
		twinkleTwinkle.add(new Note(295, "F4"));
		twinkleTwinkle.add(new Note(306, "E4"));
		twinkleTwinkle.add(new Note(306, "E4"));
		twinkleTwinkle.add(new Note(316, "D4"));
		twinkleTwinkle.add(new Note(316, "D4"));
		twinkleTwinkle.add(new Note(326, "C4"));

		for (int i = 0; i < twinkleTwinkle.size(); i++) {
			try {
				images.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + getImagePath(twinkleTwinkle.get(i).getKey().getPitch()))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		learnSongImages.add(images);
		learnSongs.add(twinkleTwinkle);

		ArrayList<Note> maryHadALittleLamb = new ArrayList<Note>();
		images = new ArrayList<BufferedImage>();

		maryHadALittleLamb.add(new Note(41, "E4"));
		maryHadALittleLamb.add(new Note(51, "D4"));
		maryHadALittleLamb.add(new Note(61, "C4"));
		maryHadALittleLamb.add(new Note(51, "D4"));
		maryHadALittleLamb.add(new Note(41, "E4"));
		maryHadALittleLamb.add(new Note(41, "E4"));
		maryHadALittleLamb.add(new Note(41, "E4"));
		maryHadALittleLamb.add(new Note(51, "D4"));
		maryHadALittleLamb.add(new Note(51, "D4"));
		maryHadALittleLamb.add(new Note(51, "D4"));
		maryHadALittleLamb.add(new Note(41, "E4"));
		maryHadALittleLamb.add(new Note(21, "G4"));
		maryHadALittleLamb.add(new Note(21, "G4"));

		maryHadALittleLamb.add(new Note(41, "E4"));
		maryHadALittleLamb.add(new Note(183, "D4"));
		maryHadALittleLamb.add(new Note(193, "C4"));
		maryHadALittleLamb.add(new Note(183, "D4"));
		maryHadALittleLamb.add(new Note(173, "E4"));
		maryHadALittleLamb.add(new Note(173, "E4"));
		maryHadALittleLamb.add(new Note(173, "E4"));
		maryHadALittleLamb.add(new Note(173, "E4"));
		maryHadALittleLamb.add(new Note(183, "D4"));
		maryHadALittleLamb.add(new Note(183, "D4"));
		maryHadALittleLamb.add(new Note(173, "E4"));
		maryHadALittleLamb.add(new Note(183, "D4"));
		maryHadALittleLamb.add(new Note(193, "C4"));

		for (int i = 0; i < maryHadALittleLamb.size(); i++) {
			try {
				images.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + getImagePath(maryHadALittleLamb.get(i).getKey().getPitch()))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		learnSongImages.add(images);
		learnSongs.add(maryHadALittleLamb);

		ArrayList<Note> allThroughTheNight = new ArrayList<Note>();
		images = new ArrayList<BufferedImage>();

		allThroughTheNight.add(new Note(48, "B4"));
		allThroughTheNight.add(new Note(48, "B4"));
		allThroughTheNight.add(new Note(9, "A4"));
		allThroughTheNight.add(new Note(9, "A4"));
		allThroughTheNight.add(new Note(21, "G4"));
		allThroughTheNight.add(new Note(21, "G4"));
		allThroughTheNight.add(new Note(21, "G4"));
		allThroughTheNight.add(new Note(21, "G4"));
		allThroughTheNight.add(new Note(48, "B4"));
		allThroughTheNight.add(new Note(48, "B4"));
		allThroughTheNight.add(new Note(9, "A4"));
		allThroughTheNight.add(new Note(9, "A4"));
		allThroughTheNight.add(new Note(21, "G4"));
		allThroughTheNight.add(new Note(21, "G4"));
		allThroughTheNight.add(new Note(153, "G4")); // 1
		allThroughTheNight.add(new Note(153, "G4"));

		allThroughTheNight.add(new Note(153, "G4"));
		allThroughTheNight.add(new Note(153, "G4"));
		allThroughTheNight.add(new Note(140, "A4"));
		allThroughTheNight.add(new Note(140, "A4"));
		allThroughTheNight.add(new Note(180, "B4"));
		allThroughTheNight.add(new Note(180, "B4"));
		allThroughTheNight.add(new Note(140, "A4"));
		allThroughTheNight.add(new Note(140, "A4"));

		allThroughTheNight.add(new Note(180, "B4"));
		allThroughTheNight.add(new Note(180, "B4"));
		allThroughTheNight.add(new Note(140, "A4"));
		allThroughTheNight.add(new Note(140, "A4"));
		allThroughTheNight.add(new Note(153, "G4"));

		for (int i = 0; i < allThroughTheNight.size(); i++) {
			try {
				images.add(ImageIO.read(new File("C:\\Users\\markm\\Desktop\\Java Projects\\ComputerPiano!\\ComputerPiano!\\lib\\" + getImagePath(allThroughTheNight.get(i).getKey().getPitch()))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		learnSongImages.add(images);
		learnSongs.add(allThroughTheNight);

	}

	public String getImagePath(String pitch) {// gets and image based on the pitch of the note
		if (pitch.equals("C4"))
			return "CNatural.png";
		else if (pitch.equals("Db4"))
			return "UpwardsFlat.png";
		else if (pitch.equals("D4"))
			return "UpwardsNatural.png";
		else if (pitch.equals("Eb4"))
			return "UpwardsFlat.png";
		else if (pitch.equals("E4"))
			return "UpwardsNatural.png";
		else if (pitch.equals("F4"))
			return "UpwardsNatural.png";
		else if (pitch.equals("Gb4"))
			return "UpwardsFlat.png";
		else if (pitch.equals("G4"))
			return "UpwardsNatural.png";
		else if (pitch.equals("Ab4"))
			return "UpwardsFlat.png";
		else if (pitch.equals("A4"))
			return "UpwardsNatural.png";
		else if (pitch.equals("Bb4"))
			return "DownwardsFlat.png";
		else if (pitch.equals("B4"))
			return "DownwardsNatural.png";
		else if (pitch.equals("C5"))
			return "DownwardsNatural.png";
		else
			return "";
	}

	public void playback() {// plays the users song on the staff
		if (!learningMode) {
			for (int i = 0; i < notes.size(); i++) {
				piano.playThis(notes.get(i).getKey().getPitch());
			}
		} else {
			for (int i = 0; i < learnSongs.get(learnSongToPlay).size(); i++) {
				piano.playThis(learnSongs.get(learnSongToPlay).get(i).getKey().getPitch());
			}
		}
	}

	public void saveSong() {
		String songName = "";

		songName = JOptionPane.showInputDialog(null, "Enter The Name Of The Song To Save", "Name Your Song", 3);

		BufferedImage screenCapture = null;
		try {
			screenCapture = new Robot().createScreenCapture(
					new Rectangle(GUI.getX() + 105, GUI.getY() + 50, GUI.getWidth() - 294, GUI.getHeight() - 420));
		} catch (HeadlessException | AWTException e) {
			e.printStackTrace();
		}

		File customSongFile = new File(songName + ".jpg");
		try {
			ImageIO.write(screenCapture, "jpg", customSongFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stopPlayback() {
		System.out.println("stopstop");
		isStopped = true;
	}
}