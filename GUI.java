import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.Timer;


public class GUI extends JFrame {
	//Constants for the size of the GUI.
	public static int SCREEN_WIDTH = 820;
	public static int SCREEN_HEIGHT = 600;
	
	//Constant defining how quickly the timer will tick.
	public static int MILLISECONDS_PER_TICK = 50;
	private static Timer timer;
	
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Projectile Motion Simulator (Kaan Aksoy)");
		
		//The GUI should not be resizable by the user.
		setResizable(false);
		
		//Instantiate the two panels used in the GUI.
		ControlsPanel controlsPanel = new ControlsPanel();
		SimulationPanel simulationPanel = new SimulationPanel();
		
		controlsPanel.setSimulationPanel(simulationPanel);
		simulationPanel.setControlsPanel(controlsPanel);
		
		//Use a BorderLayout to add the ControlsPanel to the left and the GamePanel to the right.
		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.add(controlsPanel, BorderLayout.WEST);
		cont.add(simulationPanel, BorderLayout.CENTER);
	}
}
