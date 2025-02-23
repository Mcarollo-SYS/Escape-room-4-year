package view;


import java.awt.Cursor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import controller.CollisionChecker;
import controller.KeyHandler;
import controller.MouseControl;
import main.GameClose;
import main.Music;
import main.Sound;
import main.SoundWalk;
import model.Player;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class GamePanel extends JPanel implements Runnable{
	
	/**
	 * grandezza originale del tile
	 */
	final int originalTileSize = 16; //16x16 size
	/**
	 * scala per elementi di gioco
	 */
	public final int scale = 3;

	/**
	 * grandezza effettiva del tile
	 */
	public final int tileSize = originalTileSize * scale;  //48x48 tile
	/**
	 * massimo numero di tile per larghezza
	 */
	public final int maxScreenCol = 32;
	/**
	 * massimo numero di tile per lunghezza
	 */
	public final int maxScreenRow = 18;
	/**
	 * larghezza dello schermo
	 */
	public final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	/**
	 * altezza dello schermo
	 */
	public final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	
	//Tile Manager
	/**
	 *  Gestore della mappatura
	 */
    public TileManager tileM = new TileManager(this);

    /**
     *  Gestore della User Interface
     */
    public UI ui = new UI(this);
    
    /**
     *  Gestore degli input in tastiera
     */
	KeyHandler keyH = new KeyHandler(this, ui);
	
	/**
	 *  Gestore del elementi del player
	 */
	public Player player = new Player(this, keyH);
    //Collision Check
	/**
	 * Controllore di collisioni
	 */
    public CollisionChecker cChecker = new CollisionChecker(this, ui, player);
    
    /**
     * Stato di gioco
     */
    public int gameState;
    
    /**
     * stato di gioco nel Main menu
     */
    public final int titleState = 0;
    /**
     * Stato in gioco
     */
    public final int playState = 1;
    /**
     * Stato pausa del gioco
     */
    public final int pauseState = 2;
    /**
     * stato di interazione e interuzione del thread di gioco
     */
    public final int interactState = 3;
    /**
     * easter egg che invoca i movimenti moonwalk del player
     */
    public boolean moonWalk = false;
    /**
     * rileva la presenza della chiave per l'accesso alla porta
     */
    public boolean hasKey = false;
    
    /**
     * rileva la chisura del programma
     */
    public boolean isClosed = false;
    
	//FPS
    /**
     * Frame per second di gioco
     */
	public final int FPS = 60;
	
	
	/**
	 * Gestore per la musica
	 */
	public Music music = new Music();
	/**
	 * Gestore per gli effetti sonori
	 */
	public Sound sound = new Sound();
	/**
	 * Gestore per gli passi del player
	 */
	public SoundWalk soundWalk = new SoundWalk();
	
	//keyListener for movements
	/**
	 * Gestore degli eventi del mouse
	 */
	MouseControl mouseM = new MouseControl(this, ui);
	
	/**
	 * Semaforo locale di game panel
	 */
	private Semaphore semaphore;
	
	//Thread for the game
	/**
	 * Thread di gioco
	 */
	Thread gameThread;
	
	/**
	 * metodo costruttore
	 * @param semaphore semaforo per la gestione dei thread
	 */
	public GamePanel(Semaphore semaphore) {
		
		
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setDoubleBuffered(true);
		addMouseListener(mouseM);
        addMouseMotionListener(mouseM);
		this.semaphore = semaphore;
        
		addKeyListener(keyH);
		setFocusable(true);
		
		startGameThread();
		
		
	}
	
	
	
	/**
	 *  running del gameThread
	 */
	//running the gameThread
	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	/**
	 * implementazione dei valori default
	 */
	public void setup() {
		gameState = titleState;
		tileM.mapStream = "/maps/map01.txt";
		player.x = screenWidth/2;
		player.y = screenHeight/2;
		//playMusic(1);
	}
	
	/**
	 * Thread del gioco con algoritmo delta
	 */
	@Override
	public void run() {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		setup();
		
		while (gameThread != null) {

			//GameLoop FPS SystemTime
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				
				//UPDATE GAME
				update();
				
				//DRAW SPRITES
				repaint();
				
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
			//	System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			if(isClosed == true) semaphore.release();
		}
	}
	
	/**
	 * Update del gioco
	 */
	public void update() {
		if(gameState == playState) {
			player.update();
			
			this.setCursor(this.getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));
		}else if(gameState == pauseState) {
			this.setCursor(Cursor.getDefaultCursor());
		}else if(gameState == titleState) {
				this.setCursor(Cursor.getDefaultCursor());
		} else if (gameState == interactState) {
			//none
		}
		
	}
	
	/**
	 * componete base su disegno
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == titleState) {
			ui.draw(g2);
			//setup();
			playMusic(2);
		}else if(gameState == pauseState){
			tileM.draw(g2);
			player.draw(g2);
			ui.draw(g2);
		}else {
			//draw chest 

			
			//for(int i=0;i<obj.length;i++) {
			
			if(moonWalk) {
				playMusic(0);	
				player.speed = -1;							
			}else{
				playMusic(1);
				player.speed = 2;
			}
				
			
			tileM.draw(g2);
			player.draw(g2);
			//}
			//player.draw(g2);
			ui.draw(g2);
		}
		
	
	}
	
	/**
	 * uscita del programma 
	 */
	public void exitProgram() {
		isClosed = true;
	}
	
	/**
	 * invoca musica di sottofondo
	 * @param i indice di musica
	 */
	public void playMusic(int i) {
	    if (!music.isPlaying()) {
	        music.setFile(i);
	        music.play();
	    }
	}
	
	/**
	 * invoca gli effetti sonori di sottofondo
	 * @param i indice di musica
	 */
	public void playSE(int i) {
		if(!sound.isPlaying()) {
			sound.setFile(i);
	        sound.play();
		}    
	}
	
	/**
	 * invoca il loop della musica di sottofondo
	 * @param i indice di musica
	 */
	public void playSELoop(int i) {
		if(!sound.isPlaying()) {
			sound.setFile(i);
	        sound.play();
	        sound.loop();
		}    
	}

/**
 * termina l'invocazione della musica
 */
	public void stopMusic() {
		if(music.isPlaying()) {
			music.stop();
		}
	}
	
	/**
	 * termina l'invocazione dei effetti sonori
	 */
	public void stopSE() {
		if(sound.isPlaying()) {
			sound.stop();
		}
	}
	
	/**
	 * termina il loop dei effetti sonori
	 */
	public void stopSELoop() {
		if(sound.isPlaying()) {
			sound.stop();
		}
	}
	
	/**
	 * invoca l'effetto sonoro dei passi
	 * @param i indice di musica
	 */
	public void playWalk(int i) {
		if(!soundWalk.isPlaying()) {
			soundWalk.setFile(i);
	        soundWalk.play();
		}    
	}


	/**
	 *  termina l'effetto sonoro dei passi
	 */
	public void stopSoundWalk() {
		if(soundWalk.isPlaying()) {
			soundWalk.stop();
		}
	}
	
}

