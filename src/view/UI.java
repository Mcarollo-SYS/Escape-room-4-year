package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.KeyHandler;
import controller.MouseControl;
import model.Player;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_40;
	Font antiquity, m12;
	
	private MouseControl mouseH;
	private BufferedImage title;
	private Rectangle btnPlay;
	private Rectangle btnExitMain;
	private Rectangle option;
	private Rectangle btnQuit;
	private Rectangle btnResume;
	private Rectangle btnOptionMain;
	private BufferedImage key, command;
	/**
	 * Controlla se i bottoni del main menu sono nascosti
	 */
	public boolean control;

	/**
	 * Indica lo stato del volume audio.
	 */
	public boolean volume = false;

	/**
	 * Indica se la chiave è attiva.
	 */
	public boolean activateKey = true;

	/**
	 * Il numero del comando attuale.
	 */
	public int commandNum = 0;

	/**
	 * Il numero di hover attuale.
	 */
	public int hover = 0;

	
	

	/**
	 * getter di play bounds
	 * @return ritorna play bounds
	 */
	public Rectangle getPlay() {
		return btnPlay;
	}
	
	/**
	 * getter di optionMain bounds
	 * @return ritorna optionMain bounds
	 */
	public Rectangle getOptionMain() {
		return btnOptionMain;
	}
	
	/**
	 * getter di title bounds
	 * @return ritorna title bounds
	 */
	public BufferedImage getTitle() {
		return title;
	}
	
	/**
	 * getter di option bounds
	 * @return ritorna option bounds
	 */
	public Rectangle getoption() {
		return option;
	}

	/**
	 * getter di quit bounds
	 * @return ritorna quit bounds
	 */
	public Rectangle getQuit() {
		return btnQuit;
	}

	/**
	 * getter di resume bounds
	 * @return ritorna resume bounds
	 */
	public Rectangle getResume() {
		return btnResume;
	}

	/**
	 * getter di exit bounds
	 * @return ritorna exit bounds
	 */
	public Rectangle getExitMain() {
		return btnExitMain;
	}

	/**
	 * metodo costruttore
	 * @param gp panello di gioco
	 */
	public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Antiquity print", Font.BOLD, 40);
        antiquity=new Font("Antiquity print",Font.BOLD,40);
        m12=new Font("M12_MACH BIKER",Font.BOLD,100);
        innitBtnBounds();
       
        try {
			key=ImageIO.read(getClass().getResourceAsStream("/key/key.png"));
			command=ImageIO.read(getClass().getResourceAsStream("/Tiles/command.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        		
        
     
    }
	
	/**
	 * inizializzazione dei bounds degli bottoni
	 */
	private void innitBtnBounds() {
		 btnPlay = new Rectangle(680, 280, 170, 50);
		 btnOptionMain = new Rectangle(625, 380, 270, 50);
		 btnExitMain = new Rectangle(680, 480, 170, 50);
		 btnResume= new Rectangle(655, 325, 180, 35);
		 option=new Rectangle(670, 415, 155, 50);
		 btnQuit = new Rectangle(575, 515, 350, 50);
	}
	
	/**
	 * disegnatore del Ui
	 * @param g2 componente grafico
	 */
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(arial_40);
		
		
		
		if(gp.gameState == gp.titleState) {
			mainMenu();
			if(control) {
				this.command();
			}
			
		}
		if(gp.gameState == gp.playState) {
			if(activateKey) key();
		}
		if(gp.gameState == gp.pauseState) {
			
			if(volume) {
				musicVolume();
			}else {
				pauseScreen();
			}
		}
		
		
	}
	
	/**
	 * metodo che disegna il main menu
	 */
	public void mainMenu() {
	    
	    g2.setColor(Color.BLACK);
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	    Color color = new Color(0x4d354b);
	    
	    String text = "PLAY";
	    String option = "KEYBIND";
	    String exit="EXIT";
	    g2.setColor(Color.white);
	    //System.out.println(control);

	    FontMetrics fm = g2.getFontMetrics();
	    int textWidth = fm.stringWidth(text);

	    int centerX = gp.screenWidth/2;
	    int centerY = gp.screenHeight/2;
	    //g2.drawRect(680, 280, 170, 50);
	    //g2.drawRect(625, 380, 270, 50);
	    g2.setFont(m12);
	    g2.setColor(color);
	    g2.drawString("ELISYUM", centerX - 320, 200);
	    g2.setFont(antiquity);
	    
	    if(!control) {
	    	if(hover == 0) {
		    	g2.setColor(Color.WHITE);
		    	g2.drawString(text, centerX-(textWidth/2), centerY-100);
			    g2.drawString(option, centerX-(textWidth/2+60), centerY);
			    g2.drawString(exit, centerX-(textWidth/2), centerY+100);
		    } else if (hover == 1) {
		    	g2.setColor(new Color(90,90,90));
		    	g2.drawString(text, centerX-(textWidth/2), centerY-100);
		    	g2.setColor(Color.WHITE);
			    g2.drawString(option, centerX-(textWidth/2+60), centerY);
			    g2.drawString(exit, centerX-(textWidth/2), centerY+100);
		    } else if (hover == 2) {
		    	g2.setColor(new Color(90,90,90));
		    	g2.drawString(option, centerX-(textWidth/2+60), centerY);
		    	g2.setColor(Color.WHITE);
			    g2.drawString(text, centerX-(textWidth/2), centerY-100);
			    g2.drawString(exit, centerX-(textWidth/2), centerY+100);
		    } else if (hover == 3) {
		    	g2.setColor(new Color(90,90,90));
		    	g2.drawString(exit, centerX-(textWidth/2), centerY+100);
		    	g2.setColor(Color.WHITE);
			    g2.drawString(text, centerX-(textWidth/2), centerY-100);
			    g2.drawString(option, centerX-(textWidth/2+60), centerY);
			   
		    }
	    	
	    }
	    
	    
	}
	
	/**
	 * metodo che disegna keyBinds menu
	 */
	public void command() {
	    

	    int x = 110;
	    int y = 250;
	    int width = 1300;
	    int height = 500;
	    int arcWidth = 30;
	    int arcHeight = 30;
	    
	    
	    g2.setColor(new Color(128, 128, 128, 50));
	    g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	    
	    g2.setColor(Color.WHITE);
	    
	    g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	    
	    g2.drawImage(command, -30, 200, 1600, 600, gp);
	}


	/**
	 * metodo che regola il volume di gioco in options
	 */
	public void musicVolume() {
		
		 String music = "music";
		 String sound = "sound";
		 
		 FontMetrics fm = g2.getFontMetrics();
		 int textWidth = fm.stringWidth(music);
		 int centerX = ((gp.screenWidth - textWidth) / 2);
		 g2.setColor(new Color(30, 30, 30, 128)); 
		 g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		 	//music
		 		g2.setColor(Color.white);
		 		g2.drawString("music", centerX-20, gp.screenHeight/2-25);
		 		g2.setColor(Color.black);
				g2.fillRect(centerX + 150, gp.screenHeight / 2 - 50, 120, 24);
				g2.setColor(Color.white);
				int volumeWidth = 24 * gp.music.volumeScale;
	 			g2.fillRect(centerX+150, gp.screenHeight/2 - 50, volumeWidth, 24);
	 			g2.drawRect(centerX+150, gp.screenHeight/2 - 50, 120, 24);
		 		if(commandNum == 0) {
		 			g2.drawString(">", centerX-70, gp.screenHeight/2-25);	
		 		}
		 		
		 	//sound	
		 		g2.setColor(Color.white);
		 		g2.drawString(sound, centerX-20, gp.screenHeight/2+75);
		 		g2.setColor(Color.BLACK);
				g2.fillRect(centerX + 150, gp.screenHeight / 2+50, 120, 24); 
				g2.setColor(Color.white);
				int volumeWidthSE = 24 * gp.sound.volumeScaleSE;
				g2.fillRect(centerX + 150, gp.screenHeight/2+50, volumeWidthSE, 24);
				g2.drawRect(centerX+150, gp.screenHeight/2+50, 120, 24);
				if(commandNum == 1) {
					
					g2.drawString(">", centerX-70, gp.screenHeight/2+75);
					
		 		}
	
		 }

	
	/**
	 * metodo che disegna la schermata di pause
	 */
	public void pauseScreen() {
		
		
	    
	    String restart="resume";
	    String quit = "safe and quit";
	    String option="option";
	    g2.setColor(new Color(30, 30, 30, 128));
	    
	    
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	    FontMetrics fm = g2.getFontMetrics();
	    int textWidth = fm.stringWidth(restart);
	    int textHeight = fm.getHeight();
	    
	    int textWidth2 = fm.stringWidth(quit);
	    int textWidth3 = fm.stringWidth(option);
	    

	    int centerX = ((gp.screenWidth - textWidth) / 2);
	    int centerY = (gp.screenHeight - textHeight) / 2 + fm.getAscent();
	    int centerX2 = ((gp.screenWidth - textWidth2) / 2);
	    int centerX3 = (gp.screenWidth - textWidth3) / 2;
	    

	    if(hover == 4) {
	    	g2.setColor(new Color(90,90,90, 200));
	    	g2.drawString(restart, centerX-20,centerY-100);
	    	g2.setColor(Color.white);
	    	g2.drawString(quit, centerX2-20, centerY+100);
	 	    g2.drawString(option, centerX3-20, centerY);
	    } else if (hover == 5) {
	    	g2.setColor(new Color(90,90,90, 200));
	    	g2.drawString(option, centerX3-20, centerY);
	    	g2.setColor(Color.white);
	    	g2.drawString(quit, centerX2-20, centerY+100);
	    	g2.drawString(restart, centerX-20,centerY-100);
	    } else if (hover == 6) {
	    	g2.setColor(new Color(90,90,90, 200));
	    	g2.drawString(quit, centerX2-20, centerY+100);    	
	    	g2.setColor(Color.white);
	    	g2.drawString(restart, centerX-20,centerY-100);
	 	    g2.drawString(option, centerX3-20, centerY);
	    } else {
	    	g2.setColor(Color.white);
	    	g2.drawString(restart, centerX-20,centerY-100);
	    	g2.drawString(quit, centerX2-20, centerY+100);
		    g2.drawString(option, centerX3-20, centerY);
	    }
	    
	
	}
	
	/**
	 * metodo che disegna la chiave e la sua scritta
	 */
	public void key() {
		
		  
		String message="missing";
		String message1="found";
		Font oj = antiquity.deriveFont(35f);
		g2.setFont(oj);

		g2.drawImage(key, 100, 100, 64, 64, null);		
			
		if(gp.hasKey) {
			g2.setColor(Color.BLACK);
			g2.drawString(message1, 175, 150);
			g2.setColor(new Color(125, 92, 80));
			g2.drawString(message1, 175, 145);
			//g2.drawString(count1, 160, 145);
		}else {
			g2.setColor(Color.BLACK);
			g2.drawString(message, 175, 150);
			g2.setColor(new Color(125, 92, 80));
			g2.drawString(message, 175, 145);
		}
			
		
		
		
	}
	

	
	
}
