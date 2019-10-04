import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
// batball game image
public class GameViewer{

	public static void main (String[] args) {
		GameFrame gameFrame = new GameFrame ();
		gameFrame.setVisible (true);

		String textPaneContent = "Welcome to Jo's Bizzare Adventure!\n"
		+ "\nPlay as Jo, who attacks with Star Platinum to destroy the enemies (minions).\n You get points when you destroy enemies, after a certain amount of points, you will get upgrades.\n Survive for 1 minute and acquire as many points as possible before facing the big boss (dio).\n";

		JFrame controls = new JFrame();
		JTextPane textPane = new JTextPane();
		textPane.setText(textPaneContent);
		textPane.setEditable(false);

		controls.add(textPane);
		controls.setVisible(true);
		controls.setSize (900, 600);
		controls.setTitle ("Intro and Controls");
		controls.setResizable(true);
		controls.setLocationRelativeTo(null);
		controls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// gameFrame.getGamePanel().loadMenu();
	}
}