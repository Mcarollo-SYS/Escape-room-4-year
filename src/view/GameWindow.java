package view;

import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import main.GameClose;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class GameWindow extends JFrame{
	
	/**
	 * Logo gioco
	 */
	
	ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
	/**
	 * crea un pannello
	 */
	private GamePanel panel;
	
	/**
	 *  Thread per la chisura del gioco
	 */
	GameClose gameCloseThread;
	/**
	 * metodo costruttore
	 * @param semaphore semaforo per gestione dei thread
	 */
	public GameWindow(Semaphore semaphore) {
		
		panel = new GamePanel(semaphore);
		this.gameCloseThread = new GameClose(semaphore, panel);
		add(panel);
		pack(); //setting the size of the window to panelSize
		
		
		
		//title of the window
		setTitle("Escaping Fantasy");
		this.setIconImage(logo.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //set the window maximized for all sides
		//dispose(); //clean frame cache
		setUndecorated(true); //Removes Window Borders
		
		//Setting full Screen
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		//Hides the mouse pointer

		
	}
	
	/**
	 * getter per il pannello
	 * @return ritorna il pannello di gioco
	 */
	public GamePanel getPanel() {
		return panel;
	}
}
