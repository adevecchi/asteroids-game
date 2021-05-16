package game;

import javax.swing.SwingUtilities;

import game.ui.AsteroidsUI;

public class Asteroids {
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AsteroidsUI().start();
			}
    	});
               
    }

}
