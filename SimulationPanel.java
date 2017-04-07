// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the panel on the right of the GUI that displays
//              the map and events that occur in the game. The panel is updated every 
//              iteration of the game.

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JPanel;

public class SimulationPanel extends JPanel {
	
	//Calcuate the width for the GamePanel.
	public static final int WIDTH = GUI.SCREEN_WIDTH - ControlsPanel.WIDTH;

	public SimulationPanel() {
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));
		setBackground(Color.WHITE);
		
		
		//Add the GameMouseListener to listen for mouse clicks and moves.
		addMouseListener(new GameMouseListener());
		addMouseMotionListener(new GameMouseListener());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	//Private inner class that is used to handle mouse presses and mouse movements in the GamePanel.
	private class GameMouseListener extends MouseAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
	}
}