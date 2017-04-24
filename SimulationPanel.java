
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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class SimulationPanel extends JPanel {
	
	private ControlsPanel controlsPanel;
	
	// Calcuate the width for the GamePanel .
	public static final int WIDTH = GUI.SCREEN_WIDTH - ControlsPanel.WIDTH;

	private static int GROUND_HEIGHT = 50;
	private static int WIDTH_PADDING = 30;

	private double angle;
	private double velocity;
	private double height;

	private boolean hasStarted;

	private Font font;
	
	private ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
	private ArrayList<Point> scaledPoints = new ArrayList<Point>();
	private int index = 0;
	
	DecimalFormat fmt = new DecimalFormat("0.00");

	double duration = flightDuration();
	double tick;

	double widthScale;
	double heightScale;
	double scale;
	
	Timer timer;

	public SimulationPanel() {
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));
		setBackground(new Color(30, 144, 255));

		font = new Font("Comic Sans", Font.BOLD, 16);

		// Add the GameMouseListener to listen for mouse clicks and moves.
		addMouseListener(new GameMouseListener());
		addMouseMotionListener(new GameMouseListener());
	}
	
	public void setControlsPanel(ControlsPanel panel) {
		this.controlsPanel = panel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(50, 205, 50));

		g2.fillRect(0, getHeight() - GROUND_HEIGHT, getWidth(), GROUND_HEIGHT);

		if (!hasStarted)
			return;

		System.out.printf("Biggest X: %f\n", getX(duration));
		System.out.printf("Highest Y: %f\n", highestY());
		System.out.printf("Duration: %f\n", duration);
		System.out.printf("Width Scale: %f\n", widthScale);
		System.out.printf("Height Scale: %f\n", heightScale);

		g2.setColor(Color.GRAY);

		if (height > 0) {
			g2.fillRect(WIDTH_PADDING, getHeight() - GROUND_HEIGHT - (int) (height * scale), 20,
					(int) (height * scale));
		}

		g2.setColor(Color.BLACK);
		
		System.out.println("INDEX" + index);
		
		for(int i = 0; i <= index; i++) {
			g2.fillOval(scaledPoints.get(i).x, scaledPoints.get(i).y, 4, 4);
			
			if(i == index) {
				String x = fmt.format(points.get(i).x);
				String y = fmt.format(points.get(i).y);
				
				g2.drawString("(" + x + ", " + y + ")", scaledPoints.get(i).x, scaledPoints.get(i).y);
			}
		}

		g2.setColor(Color.RED);

		g2.fillOval((int) (WIDTH_PADDING + getX(duration) * scale),
				getHeight() - (int) (GROUND_HEIGHT + getY(duration) * scale), 12, 12);

		DecimalFormat fmt = new DecimalFormat("0.00");

		g2.setColor(Color.BLACK);
		g2.setFont(font);

		g2.drawString("Y: " + fmt.format(getX(duration)) + " m", 10, 20);
		
		int xTextWidth = g2.getFontMetrics().stringWidth("X: " + fmt.format(getX(duration)) + " m");
		
		g2.drawString("X: " + fmt.format(getX(duration)) + " m", getWidth() - xTextWidth - 20, getHeight() - 10);
	}

	public void simulate(double angle, double velocity, double height) {
		hasStarted = true;

		this.angle = (Math.PI / 180) * angle;
		this.velocity = velocity;
		this.height = height;

		duration = flightDuration();
		tick = duration / 1000;

		heightScale = (getHeight() - GROUND_HEIGHT) / (highestY() * 1.2);
		widthScale = (getWidth() - 2 * WIDTH_PADDING) / (getX(duration));

		scale = Math.min(heightScale, widthScale);
		
		points.clear();
		scaledPoints.clear();
		index = 0;

		for (double t = 0; t < duration; t += tick) {
			points.add(new Point2D.Double(getX(t), getY(t)));
			
			scaledPoints.add(new Point((int) (WIDTH_PADDING + getX(t) * scale),
					getHeight() - (int) (GROUND_HEIGHT + getY(t) * scale)));
		}
		
		controlsPanel.setSimulationInfo(duration, getX(duration), highestY());
		
		if(timer != null) {
			timer.cancel();
		}
		
		timer = new Timer();
		
		TimerTask myTask = new TimerTask() {
			@Override
			public void run() {
				repaint();

				index++;

				if (index == scaledPoints.size() - 1) {
					timer.cancel();
					timer = null;
				}
			}
		};
		
		System.out.println(tick * 1000);
		timer.schedule(myTask, 0, (int)Math.ceil(tick * 1000));

		repaint();
	}

	private double getX(double time) {
		return velocity * Math.cos(angle) * time;
	}

	private double getY(double time) {
		return -0.5 * 9.81 * Math.pow(time, 2) + velocity * Math.sin(angle) * time + height;
	}

	private double highestY() {
		return getY(velocity * Math.sin(angle) / 9.81);
	}

	private double flightDuration() {
		return (-1 * velocity * Math.sin(angle)
				- Math.sqrt(Math.pow(velocity * Math.sin(angle), 2) + 2 * 9.81 * height)) / (-9.81);
	}

	// Private inner class that is used to handle mouse presses and mouse
	// movements in the GamePanel.
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