import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
// batball game image
public class GameViewer{

	public static void main (String[] args) {
		GameFrame gameFrame = new GameFrame ();
		gameFrame.setVisible (true);

		String textPaneContent = "Welcome to Jo's Bizarre Adventure!\n"
		+ "\nPlay as Jo, who attacks with Star Platinum to destroy the enemies (minions).\n You get points when you destroy enemies, after a certain amount of points, you will get upgrades.\n Survive for 1 minute and acquire as many points as possible before facing the big boss (dio).\n\n"
		+ "How to Win/Lose:\n"
		+"1.	You lose if your hp drops to 0, you lose hp when a minion or dio touches you.\n"
		+"2.	You win if you defeat dio (the red rectangle).\n"
		+ "\nThings to Note:\n\n"
		+"1.	Try to get as many points as possible.\n"
		+"2.	At 120 points, you get a damage increase.\n"
		+"3.	At 240 points, you get a health bonus and a small damage bonus.\n"
		+"4.	At 300 points, +health, +dmg, +move speed bonus.\n"
		+ "General Tips:\n\n"
		+"1.	Try to get all the upgrades to increase your chances of winning.\n"
		+"2.	DO NOT underestimate Dio. Once his health drops under 20, he becomes enraged, moving much" +"quicker and doing more damage. \n"
		+ "Controls:\n\n"
		+"Movement:\n" 
		+"-	Left: left arrow key\n"
		+"-	Right: right arrow key\n"
		+"-	Float: up arrow key\n"
		+"-	Fly: up arrow key (hold)\n"
		+"Combat:\n"
		+"-	Shoot: spacebar\n;";

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