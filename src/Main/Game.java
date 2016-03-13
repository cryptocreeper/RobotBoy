package Main;

import javax.swing.JFrame;
import java.awt.*;

public class Game {
	static GraphicsDevice device = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static void main(String[] args) {
		JFrame window = new JFrame("Robot Boy");
		window.setUndecorated(true);
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		device.setFullScreenWindow(window);
		window.setVisible(true);
	}
	
}
