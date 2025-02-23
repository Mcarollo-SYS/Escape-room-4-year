package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Semaphore;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.text.AbstractDocument.BranchElement;

import controller.KeyHandler;
import view.GamePanel;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class Player extends Entity{
	
	
	/**
	 * Rappresenta un pannello di gioco.
	 */
	private GamePanel panel;

	/**
	 * Gestisce gli eventi della tastiera.
	 */
	private KeyHandler keyH;

	/**
	 * Indice per le animazioni del personaggio.
	 */
	private int animationIndex = 0;

	/**
	 * Indica se il personaggio ha interagito.
	 */
	public boolean interacted;

	/**
	 * Indica se il personaggio ha la batteria.
	 */
	public boolean hasBattery;

	/**
	 * Indica se la luce è accesa.
	 */
	public boolean lighted;

	/**
	 * Indica se la cassaforte è aperta.
	 */
	public boolean lockOpen;

	/**
	 * Indica se il messaggio è stato invocato.
	 */
	public boolean messageInvoked;

	/**
	 * Indica se e' necessario invocare il jumpscare.
	 */
	public boolean scareInvoked;

	/**
	 * Indica se e' necessario invocare il messaggio di warning sulla porta.
	 */
	public boolean invokeDoor;

	/**
	 * Indica se e' necessario invocare il messaggio di warning sull'orologio.
	 */
	public boolean clockOpen;

	/**
	 * Indica se e' possibile accedere all'orologio.
	 */
	public boolean clockAccess;

	/**
	 * Indica se la seconda luce e' accesa.
	 */
	public boolean secondLighted;

	/**
	 * Indica se il personaggio e' scappato.
	 */
	public boolean escaped;

	/**
	 * Numero 1 lucchetto.
	 */
	public int num1 = 0;

	/**
	 * Numero 2 lucchetto.
	 */
	public int num2 = 0;

	/**
	 * Numero 3 lucchetto.
	 */
	public int num3 = 0;

	/**
	 * Valore numerico per il tempo.
	 */
	public int times = 0;

	/**
	 * Conteggio dei frame.
	 */
	public int frameCount = 0;

	/**
	 * Tempo di visualizzazione di un messaggio.
	 */
	public int messageTime = 0;

	/**
	 * Tempo di visualizzazione del jumpscare.
	 */
	public int scareTime = 0;

	/**
	 * Conteggio del tempo dell'animazione di fuga.
	 */
	public int escapeCount = 0;

	/**
	 * Dimensione x.
	 */
	int xSize = 15;

	/**
	 * Dimensione y.
	 */
	int ySize = 23;

	/**
	 * Numero di interazione.
	 */
	int interactNum = 1;

	/**
	 * Array di immagini.
	 */
	BufferedImage image[] = new BufferedImage[10];

	/**
	 * Ultima direzione.
	 */
	String lastDirection;
	
	/**
	 * metodo costruttore
	 * @param gp pannello di gioco 
	 * @param kh ricevitore di input tastiera
	 */
	public Player(GamePanel gp, KeyHandler kh) {
		
		panel = gp;
		keyH = kh;
		solidArea = new Rectangle(0, 30, 45, 45);
		hasBattery = false;
		
		setDefaultValues();
		importPlayerImage();
		loadScareResource();
	}
	
	/**
	 * imposta le variabili x e y a dei valori predefiniti,e importa la velocità ad un determinato valore,e la direzione del player 
	 */
	public void setDefaultValues() {
		
		x = panel.screenWidth/2;
		y = (panel.screenHeight/2)+20;
		speed = 2;
		direction = "idle";
		lastDirection = "down";
	}
	
	/**
	 * metodo per importare le immagini 
	 */
	public void importPlayerImage() {
		
		try {
			idleImage = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
			walkImage = ImageIO.read(getClass().getResourceAsStream("/player/walk.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * metodo per impostare la direzione del player e animazione
	 * @param direction la direzione del player
	 * @return frazione dell'immagine totale del player
	 */
	public BufferedImage playerSprite(String direction) {
		BufferedImage image = null;
		int imageX = 0;
		int imageY = 0;
		
		if(direction != "idle") lastDirection = direction;
		
		//walk image positions
		if(direction == "up") {
			imageY += 3*ySize;
			imageX += animationIndex*xSize;
		}
		if(direction == "left") {
			imageY += 2*ySize;
			imageX += animationIndex*xSize;
		}
		if(direction == "down") {
			imageY = 0;
			imageX += animationIndex*xSize;
		}
		if(direction == "right") {
			imageY += ySize;
			imageX += animationIndex*xSize;
		}
		
		//idle image positions
		if(direction == "idle") {
			if(lastDirection == "up") {
				imageY += 2*ySize;
				imageX += animationIndex*xSize;
			}
			if(lastDirection == "left") {
				imageY += 3*ySize;
				imageX += animationIndex*xSize;
			}
			if(lastDirection == "down") {
				imageY = 0;
				imageX += animationIndex*xSize;
			}
			if(lastDirection == "right") {
				imageY += ySize;
				imageX += animationIndex*xSize;
			}
		}
		
		if(direction != "idle") {
			image = walkImage.getSubimage(imageX, imageY, xSize, ySize);	
		} else {	
			image = idleImage.getSubimage(imageX, imageY, xSize, ySize);
		}
		return image;
	}
	
	/**
	 * metodo per aggiornare la posizione del player e aggiornare i counter 
	 */
	public void update() {
	
		direction = "idle";
		if(keyH.upPressed) {
			direction = "up";
			panel.playWalk(0);
		} else if(keyH.leftPressed) {
			direction = "left";
			panel.playWalk(0);
		} else if(keyH.downPressed) {
			direction = "down";
			panel.playWalk(0);
		} else if(keyH.rightPressed) {
			direction = "right";
			panel.playWalk(0);
		}else {
			panel.stopSoundWalk();
		}
		
		
		//System.out.println("PlayerPosition: x "+(x+(xSize*3/2))+", y "+y);
		//check tile collision
		collisionOn = false;
		panel.cChecker.checkTile(this);
		
		
		
		//if the collision is false, the player can move
		if(!collisionOn) {
			
			switch(direction) {
			case "up": y -= speed; break;
			case "left": x -= speed; break;
			case "down": y += speed; break;
			case "right": x += speed; break;
			
			}
		}
		
		frameCount++;
		if(frameCount > 8) {
			if(animationIndex < 5) {
				animationIndex++;
			} else {
				animationIndex = 0;
			}
			frameCount = 0;
		}
		
		
		if(messageTime < 120 && messageInvoked == true) {
			messageTime++;
		}
		
		if(scareTime < 1280 && scareInvoked == true) {
			scareTime++;
		}
		
		if(escapeCount < 600 && escaped == true) {
			escapeCount++;
		}
	}
	
	/**
	 * metodo che serve ad intersagire con gli oggetti tramite il player 
	 * @param g2 componete grafico
	 * @param objectNum numero dell'oggetto interagito
	 */
	public void interactObject(Graphics2D g2, int objectNum) {
		g2.setColor(new Color(0, 0, 0, 0.5f));
		if(x+(xSize*3/2) > panel.tileM.object[objectNum].x && x+(xSize*3/2) < panel.tileM.object[objectNum].x+panel.tileM.object[objectNum].xSize*3 && y+(xSize*3/2) > panel.tileM.object[objectNum].y && y+(xSize*3/2) < panel.tileM.object[objectNum].y+panel.tileM.object[objectNum].ySize*3) {
			interacted = true;
			if(keyH.activePanel) {
				if(panel.tileM.mapStream == "/maps/map01.txt") drawPanel(g2, objectNum);
				if(panel.tileM.mapStream == "/maps/map02.txt") drawPanel2(g2, objectNum);
			}
		} else {
			interacted = false;
		}
		
		
		if(objectNum == 17 && keyH.ePressed && interacted == true) {
			panel.playSE(5);
		}
		if(objectNum == 19 && keyH.ePressed && interacted == true) {
			panel.playSE(1);
		}
		if(objectNum == 21 && keyH.ePressed && interacted == true) {
			panel.playSE(3);
		}
		if(objectNum == 15 && keyH.ePressed && interacted == true) {
			panel.playSE(4);
		}
		
		
		//object collision box
		g2.setColor(Color.WHITE);
		//g2.fillRect(panel.tileM.object[objectNum].x, panel.tileM.object[objectNum].y, panel.tileM.object[objectNum].image.getWidth()*3, panel.tileM.object[objectNum].image.getHeight()*3);
	}
	
	/**
	 * chiusura del panello di esposizione alla fine dell'interazione
	 */
	public void clearInteraction() {
		if(interacted == false) {
			keyH.activePanel = false;
		}
	}
	
	/**
	 * disegna il pannnelo di interazione nella prima stanza
	 * @param g2 componete grafico
	 * @param objNum numero dell'oggetto di interazione
	 */
	public void drawPanel(Graphics2D g2, int objNum) {
		int recWidth = 250;
		int recHeight = 250;
		int panelX = (panel.screenWidth/2);
		int panelY = (panel.screenHeight/2);

		
		if(objNum == 21) {
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			g2.drawImage(panel.tileM.object[objNum].interactImage[interactNum], panelX-recWidth, panelY-recHeight, recWidth*2, recHeight*2, null);
			if(keyH.internalInteract && interactNum == 1) {
				interactNum = 2;
				hasBattery = true;
			}
		}
		if(objNum == 19) {
			if(hasBattery) {
				lighted = true;
				keyH.activePanel = false;
				panel.tileM.object[22].visibility = true;
			} else {
				messageInvoked = true;
				if(messageInvoked == true) InvokeMessage(g2, "Something is missing...");
			}
		}
		
		if(objNum == 22) {
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			g2.drawImage(panel.tileM.object[objNum].interactImage[1], panelX-recWidth, panelY-recHeight, recWidth*2, recHeight*2, null);
			
		}
		
		if(objNum == 15) {
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			g2.drawImage(panel.tileM.object[objNum].interactImage[1], panelX-recWidth, panelY-recHeight, recWidth*2, recHeight*2, null);
		}
		
		if(objNum == 17) {
			
			if(num1 == 5 && num2 == 4 && num3 == 2) {
				panel.hasKey = true;
				lockOpen = false;
			}else {
				lockOpen = true;
			}
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			g2.drawImage(panel.tileM.object[objNum].interactImage[1], panelX-recWidth, panelY-recHeight, recWidth*2, recHeight*2, null);
			g2.setFont(new Font("Antiquity print",Font.BOLD,100));
			g2.drawString(String.valueOf(num1), 576, 462);
			g2.drawString(String.valueOf(num2), 738, 462);
			g2.drawString(String.valueOf(num3), 902, 462);
			g2.setColor(new Color( 255, 255, 255, 100));
			//System.out.println(keyH.selectedNum);
			g2.setStroke(new BasicStroke(5));
			if(keyH.selectedNum == 0) {
				g2.drawRect(576, 387, 59, 75);
			}
			if (keyH.selectedNum == 1) {
				g2.drawRect(738, 387, 59, 75);
			}
			if (keyH.selectedNum == 2) {
				g2.drawRect(902, 387, 59, 75);
			}
		}
		
		//System.out.println(lockOpen);
		
	}
	
	/**
	 * disegna il panello di interazione per la seconda stanza
	 * @param g2 componete grafico 
	 * @param objNum numero dell'oggetto interagito
	 */
	public void drawPanel2(Graphics2D g2, int objNum) {
		int recWidth = 250;
		int recHeight = 250;
		int panelX = (panel.screenWidth/2);
		int panelY = (panel.screenHeight/2);

		
		if(objNum == 19) {
			if(lighted == false) {
				messageInvoked = true;
				if(messageInvoked == true) InvokeMessage(g2, "Something is missing...");
			}
		}
		
		if(objNum == 15) {
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			g2.drawImage(panel.tileM.object[objNum].interactImage[2], panelX-recWidth, panelY-recHeight, recWidth*2, recHeight*2, null);
			clockAccess = true;
		}
		
		if(objNum == 23) {
			if(keyH.clockNum == 12) {
				lighted = true;
				secondLighted = true;
			}
			clockOpen = true;
			if(clockAccess == true) {
				g2.setColor(new Color(0, 0, 0, 128));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
				g2.drawImage(panel.tileM.object[objNum].interactImage[keyH.clockNum], panelX-recWidth, panelY-recHeight, recWidth*2, recHeight*2, null);
			} else {
				messageInvoked = true;
				if(messageInvoked == true) InvokeMessage(g2, "No clues are related!");
			}
			
		}
		
		
	}
	
	/**
	 * apertura e disegno della luce
	 * @param g2 componente grafico
	 */
	private void openLight(Graphics2D g2) {
		if(lighted) {
			g2.setColor(new Color(255, 200, 0, 40));
			g2.fillOval(panel.tileM.object[19].x-12, panel.tileM.object[19].y, 48+24, 48+24);
		}
	}
	
	/**
	 * invocazione del messaggio message
	 * @param g2 componete grafico
	 * @param message messaggio invocato
	 */
	private void InvokeMessage(Graphics2D g2, String message) {
		//System.out.println("messageTime: "+messageTime);
		if(messageTime != 120) {
			g2.setColor(new Color(0, 0, 0, 128));
			g2.fillRect(panel.screenWidth/2-200, 700, 400, 100);
			g2.setColor(new Color(255, 255, 255, 200));
			g2.setFont(new Font("Antiquity print",Font.BOLD,20));
			g2.drawString(message, panel.screenWidth/2-200+20, 700+50);
			//animazione
			if(!escaped) {
				g2.setColor(new Color(19, 10, 13));
				g2.fillRect(panel.screenWidth/2-200+20+messageTime*5, 700+20, 300-messageTime*5, 50);
			}
		} else {
			messageInvoked = false;
			invokeDoor = false;
			interacted = false;
			keyH.activePanel = false;
			messageTime = 0;
		}	
	}
	
	/**
	 * carica le risorse per il jumpscare
	 */
	private void loadScareResource() {
		
		try {
			image[0] = ImageIO.read(getClass().getResourceAsStream("/display/scare1.png"));
			image[1] = ImageIO.read(getClass().getResourceAsStream("/display/scare2.png"));
			image[2] = ImageIO.read(getClass().getResourceAsStream("/display/scare3.png"));
			image[3] = ImageIO.read(getClass().getResourceAsStream("/display/scare4.png"));
			image[4] = ImageIO.read(getClass().getResourceAsStream("/player/entity.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch dock
			e.printStackTrace();
		}
		
	}
	
	/**
	 * invocazione dell'animazione del jumpscare
	 * @param g2 componete grafico
	 */
	public void invokeJumpScare(Graphics2D g2) {
		
		//System.out.println("scrren wid "+ panel.screenWidth + panel.screenHeight);
		if(scareTime <= 60) {
			g2.setColor(new Color(0, 0, 0, scareTime*4));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			panel.playSELoop(10);
		}
		
		if(scareTime <= 240 && scareTime > 60) {
			panel.stopSELoop();
			panel.playSELoop(7);
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			
		}
		
		if(scareTime <= 480 && scareTime > 240) {
			g2.drawImage(image[0], 0, 0, image[1].getWidth(), image[1].getHeight(), null);
			/*if(scareTime <= 280 && scareTime > 241	) {
				g2.setColor(new Color(0, 0, 0));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			}*/
		}
		
		if(scareTime <= 640 && scareTime > 480) {
			
			g2.drawImage(image[1], 0, 0, image[2].getWidth(), image[2].getHeight(), null);
			/*if(scareTime <= 520 && scareTime > 481	) {
				g2.setColor(new Color(0, 0, 0));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			}*/
		}
		
		if(scareTime <= 840 && scareTime > 640	) {
			
			g2.drawImage(image[2], 0, 0, image[3].getWidth(), image[3].getHeight(), null);
			/*if(scareTime <= 680 && scareTime > 641	) {
				g2.setColor(new Color(0, 0, 0));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			}*/
		}
		
		if(scareTime < 880 && scareTime > 840	) {
			panel.stopSELoop();
			panel.playSE(8);
			int i = ((scareTime-840)*240);
			g2.drawImage(image[3], 0-i/2, 0-i/4, image[3].getWidth()+i, image[3].getHeight()+i/2-200, null);
			if(scareTime%2 == 0) {
				g2.setColor(new Color(0, 0, 255, 160));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			} else if (scareTime%3 == 0){
				g2.setColor(new Color(0, 0, 255, 160));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			} else {
				g2.setColor(new Color(255, 255, 255, 160));
				g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
			}
		}
		
		if(scareTime > 880) {
			panel.exitProgram();
		}
		
	}
	
	/**
	 * Disegna l'entita che segue il player
	 * @param g2 componete grafico
	 */
	private void drawEntity(Graphics2D g2) {
		
		if(times == 1 && !secondLighted) {
			g2.drawImage(image[4], x, y-150+scareTime*2, image[4].getWidth()*3, image[4].getHeight()*3, null);
		}
	}
	
	/**
	 * invoca il scenario del player uscito dall'escape room
	 * @param g2 componente grafico
	 */
	private void invokeEscaped(Graphics2D g2) {
		
		messageInvoked = true;
		
		if(escapeCount <= 60) {
			g2.setColor(new Color(0, 0, 0, escapeCount*4));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
		}
		
		
		if(escapeCount <= 120 && escapeCount > 60) {
			g2.setColor(new Color(0, 0, 0, 255-(escapeCount-60)*4));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);

			panel.player.x = panel.screenWidth/2;
			panel.player.y = panel.screenHeight/2;
			panel.tileM.mapStream = "/maps/map03.txt";
			panel.tileM.loadMap();
			
		}
		
		if(escapeCount <= 240 && escapeCount > 60) InvokeMessage(g2 , "You have escaped!");
		
		if(escapeCount <= 495 && escapeCount > 240) {
			g2.setColor(new Color(0, 0, 0, (escapeCount-240)));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
		}
		
		if(escapeCount > 495) {
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
		}
		
		if(escapeCount == 599) {
			panel.exitProgram();
		}
		
	}
	
	/**
	 * disegnatore del player
	 * @param g2 componente grafico
	 */
	public void draw(Graphics2D g2) {
		
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, panel.tileSize, panel.tileSize);
		
		BufferedImage image = playerSprite("idle");
		
		switch (direction) {
		case "up":
			image = playerSprite("up");
			break;
		case "left":
			image = playerSprite("left");
			break;
		case "down":
			image = playerSprite("down");
			break;
		case "right":
			image = playerSprite("right");
			break;
		}
		
		
		g2.drawImage(image, x, y, xSize*panel.scale, ySize*panel.scale, null);
		if(panel.tileM.mapStream == "/maps/map01.txt") {
			interactObject(g2, 21);
			if(!interacted) interactObject(g2, 17);
			if(!interacted) interactObject(g2, 19);
			if(!interacted) interactObject(g2, 15);
			if(!interacted) interactObject(g2, 22);
			/*if(!interactObject(g2, 17)) {
				if(!interactObject(g2, 21)) {
					if(!interactObject(g2, 19)) {
						if(!interactObject(g2, 15)) {
							interactObject(g2, 22);
						}
					}
				}
			}*/
			
			clearInteraction();
		}
		
		if(panel.tileM.mapStream == "/maps/map02.txt") {
			interactObject(g2, 19);
			if(!interacted) interactObject(g2, 15);
			if(!interacted) interactObject(g2, 23);
			
			clearInteraction();
		}
		//interaction point
		//g2.setColor(Color.white);
		//g2.fillRect(x+(xSize*3/2), y+(xSize*3/2), 2, 2);
		
		//collider box
		//g2.fillRect(x+solidArea.x, y+solidArea.y, solidArea.width, solidArea.height);
		if(keyH.activePanel == false) openLight(g2);
		
		if(invokeDoor == true) {
			messageInvoked = true;
			if(messageInvoked == true) InvokeMessage(g2, "You need a key for this!");
		}
		
		drawEntity(g2);
		
		if(scareInvoked == true) {
			panel.stopMusic();
			invokeJumpScare(g2);
		}
		
		if(escaped == true) {
			invokeEscaped(g2);
		}
				
		
		
	}

}
