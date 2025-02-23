package main;

import view.GamePanel;
import java.util.concurrent.Semaphore;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class GameClose implements Runnable {
	
	private Semaphore semaphore;
	Thread thread;
	GamePanel panel;
	
	/**
	 * metodo costruttore
	 * @param semaphore semaforo per la gestione dei thread
	 * @param panel panello di gioco
	 */
	public GameClose(Semaphore semaphore, GamePanel panel) {
		this.semaphore = semaphore;
		thread = new Thread(this);
		this.panel  = panel;
		thread.start();
		
	}
	
	
	/**
	 * Thread di chiusura del gioco
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Closing the game...");
		System.exit(0);
		
	}
	
	
}
