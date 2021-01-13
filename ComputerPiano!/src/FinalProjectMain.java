/* FINAL PROJECT: COMPUTER PIANO PLAYER
 * by Rahul Narain and Mark Mossien
 * 
 * MAIN CLASS
 * - This class initializes the graphics window used and creates all of the buttons
 * used for both screens in the program.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class FinalProjectMain {
	public static void main(String[] args) throws IOException {
		GraphicsWindow mainStaff = new GraphicsWindow("Piano.png", true);
		addStaffButtons(mainStaff);
	}

	public static void addStaffButtons(GraphicsWindow mainStaff) {
		JButton learnButton = new JButton("Learn");
		JButton playbackButton = new JButton("Playback");
		JButton exitButton = new JButton("Exit");

		learnButton.setBounds(1010, 465, 100, 30);
		playbackButton.setBounds(1010, 525, 100, 30);
		exitButton.setBounds(1010, 585, 100, 30); // initializes and adds 3
													// buttons

		mainStaff.add(learnButton);
		mainStaff.add(playbackButton);
		mainStaff.add(exitButton);

		learnButton.addActionListener(new ActionListener() { // creates the new screen when the learn button is pressed
			public void actionPerformed(ActionEvent e) {
				mainStaff.remove(learnButton);
				mainStaff.remove(playbackButton);
				mainStaff.remove(exitButton);
				mainStaff.getGUI().dispose(); // for the learn button, the old
												// screen must be removed
				mainStaff.initGUI("LearnSongSelectionScreen.png", false);
				mainStaff.getGUI().setVisible(true);
				mainStaff.getGUI().repaint();
				addLearnScreenButtons(mainStaff);
			}
		});
		playbackButton.addActionListener(new ActionListener() {// will call the method when playback button is used
			public void actionPerformed(ActionEvent e) {
				mainStaff.playback();
			}
		});
		exitButton.addActionListener(new ActionListener() {// exits the program
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public static void addLearnScreenButtons(GraphicsWindow mainStaff) {// the menu for learning songs adds the buttons and songs
		JButton selectButtonOne = new JButton("Select");
		JButton selectButtonTwo = new JButton("Select");
		JButton selectButtonThree = new JButton("Select");
		JButton exitButton = new JButton("Exit");
		// the select buttons have identical action listeners except for
		// the setSong method, which takes a different argument for each.

		selectButtonOne.setBounds(800, 205, 100, 30);
		selectButtonTwo.setBounds(800, 320, 100, 30);
		selectButtonThree.setBounds(800, 435, 100, 30);
		exitButton.setBounds(800, 550, 100, 30);

		mainStaff.add(selectButtonOne);
		mainStaff.add(selectButtonTwo);
		mainStaff.add(selectButtonThree);
		mainStaff.add(exitButton);

		selectButtonOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainStaff.setSong(0);
				mainStaff.remove(selectButtonOne);
				mainStaff.remove(selectButtonTwo);
				mainStaff.remove(selectButtonThree);
				mainStaff.remove(exitButton);
				mainStaff.getGUI().dispose();
				mainStaff.initGUI("Piano.png", true);
				mainStaff.getGUI().setVisible(true);
				mainStaff.getGUI().repaint();
				addStaffButtons(mainStaff);
			}
		});

		selectButtonTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainStaff.setSong(1);
				mainStaff.remove(selectButtonOne);
				mainStaff.remove(selectButtonTwo);
				mainStaff.remove(selectButtonThree);
				mainStaff.remove(exitButton);
				mainStaff.getGUI().dispose();
				mainStaff.initGUI("Piano.png", true);
				mainStaff.getGUI().setVisible(true);
				mainStaff.getGUI().repaint();
				addStaffButtons(mainStaff);
			}
		});

		selectButtonThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainStaff.setSong(2);
				mainStaff.remove(selectButtonOne);
				mainStaff.remove(selectButtonTwo);
				mainStaff.remove(selectButtonThree);
				mainStaff.remove(exitButton);
				mainStaff.getGUI().dispose();
				mainStaff.initGUI("Piano.png", true);
				mainStaff.getGUI().setVisible(true);
				mainStaff.getGUI().repaint();
				addStaffButtons(mainStaff);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}
}