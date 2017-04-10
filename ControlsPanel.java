
// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the panel on the left of the GUI that the user
//              can use to upgrade their turrets and to see how much money they have.
//              The panel is updated every iteration of the game.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ControlsPanel extends JPanel {

	// Instance fields for the various labels and buttons used in the
	// ControlsPanel.
	private JLabel angleLabel;
	private JLabel velocityLabel;
	private JLabel heightLabel;
	
	private JTextField angleInput;
	private JTextField velocityInput;
	private JTextField heightInput;

	private JLabel warningLabel;
	private JLabel positionEquationLabel;
	private JLabel velocityEquationLabel;
	private JLabel accelerationEquationLabel;

	private SimulationPanel simulationPanel;

	// Constant defining how wide the ControlsPanel should be.
	public static final int WIDTH = 220;

	public ControlsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));

		setBorder(new EmptyBorder(10, 10, 10, 10));

		// Create the label showing money and set it to be centered
		// horizontally.

		JPanel anglePanel = new JPanel(new GridLayout(1, 2));

		angleLabel = new JLabel("Angle (deg): ");

		anglePanel.add(angleLabel);

		angleInput = new JTextField("10");

		anglePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, angleInput.getPreferredSize().height));
		anglePanel.add(angleInput);

		add(anglePanel);

		JPanel velocityPanel = new JPanel(new GridLayout(1, 2));

		velocityLabel = new JLabel("Velocity (m/s): ");

		velocityPanel.add(velocityLabel);

		velocityInput = new JTextField("5");

		velocityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, velocityInput.getPreferredSize().height));
		velocityPanel.add(velocityInput);

		add(velocityPanel);
		
		JPanel heightPanel = new JPanel(new GridLayout(1, 2));

		heightLabel = new JLabel("Height (m): ");

		heightPanel.add(heightLabel);

		heightInput = new JTextField("0");

		heightPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, heightInput.getPreferredSize().height));
		heightPanel.add(heightInput);

		add(heightPanel);

		add(Box.createRigidArea(new Dimension(0, 10)));
		
		warningLabel = new JLabel();
		
		warningLabel.setForeground(Color.RED);
		warningLabel.setVisible(false);
		
		warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(warningLabel);

		JButton runButton = new JButton("Run simulation");
		
		runButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(Box.createRigidArea(new Dimension(0, 10)));

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String warning = validateInput();
				
				if(warning == null) {
					warningLabel.setVisible(false);
					
					double angle = Double.parseDouble(angleInput.getText());
					double velocity = Double.parseDouble(velocityInput.getText());
					simulationPanel.simulate(angle, velocity, 0);
					
				} else {
					warningLabel.setVisible(true);
					warningLabel.setText(warning);
				}
			}
		});

		add(runButton);

		// Panel that contains the components necessary to upgrade turrets.
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Put a border around the upgrades panel.
		infoPanel.setBorder(BorderFactory.createTitledBorder("Simulation Info"));
		
		positionEquationLabel = new JLabel("x(t) = .5at^2 + vt + x0");
		
		infoPanel.add(positionEquationLabel);
		
		velocityEquationLabel = new JLabel("v(t) = at + v0");
		
		infoPanel.add(velocityEquationLabel);

		accelerationEquationLabel = new JLabel("a(t) = a0");
		
		infoPanel.add(accelerationEquationLabel);

		add(Box.createRigidArea(new Dimension(0, 30)));

		add(infoPanel);
		add(Box.createVerticalGlue());
	}

	public String validateInput() {
		try {
			double angle = Double.parseDouble(angleInput.getText());
			double velocity = Double.parseDouble(velocityInput.getText());

			if (angle <= 0 || angle >= 90) {
				return "<html>The angle must be greater than 0 and less than 90 degrees.</html>";
			}

			if (velocity <= 0) {
				return "<html>The velocity must be greater than 0.</html>";
			}

			return null;
		} catch (Exception e) {
			return "<html>Both the angle and velocity must be numbers.</html>";
		}
	}

	// Setter for gamePanel.
	public void setSimulationPanel(SimulationPanel panel) {
		this.simulationPanel = panel;
	}
}