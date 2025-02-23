package controller;

import model.Entity;
import model.Player;
import view.GamePanel;
import view.UI;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class CollisionChecker {
	
	GamePanel panel;
	UI ui;
	Player player;
	
	/**
	 * metodo costruttore
	 * @param gp pannello di gioco
	 * @param ui interfaccia user di gioco
	 * @param player oggetto player del gioco
	 */
	public CollisionChecker(GamePanel gp, UI ui, Player player) {
		this.panel = gp;
		this.ui = ui;
	    this.player = player;
	}
	
	/**
	 * creo un oggetto di tipo keyHandler 
	 */
	KeyHandler k = new KeyHandler(panel, ui);
	
	/**
	 * controllo il tile dove il personaggio(entità) si trova nello spazio
	 * @param entity entita di collisione
	 */
	public void checkTile(Entity entity) {
		
		int entityLeftX = entity.x + entity.solidArea.x;
		int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
		int entityTopY = entity.y + entity.solidArea.y;
		int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftX/panel.tileSize;
		int entityRightCol = entityRightX/(panel.tileSize+1);
		int entityTopRow = entityTopY/panel.tileSize;
		int entityBottomRow = entityBottomY/(panel.tileSize+1);
		
		int tileNum1, tileNum2;
		
		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopY - entity.speed)/panel.tileSize;
			tileNum1 = panel.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = panel.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
				changeSceneCollision(tileNum1, tileNum2);
			}		
			
			break;
		case "left":
			entityLeftCol = (entityLeftX - entity.speed)/panel.tileSize;
			tileNum1 = panel.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = panel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
				changeSceneCollision(tileNum1, tileNum2);
			}
			break;
		case "down":
			entityBottomRow = (entityBottomY - entity.speed)/panel.tileSize;
			tileNum1 = panel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = panel.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
				changeSceneCollision(tileNum1, tileNum2);
			}
			break;
		case "right":
			entityRightCol = (entityRightX - entity.speed)/panel.tileSize;
			tileNum1 = panel.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = panel.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
				changeSceneCollision(tileNum1, tileNum2);
			}
			break;
		}
	}
	
	/**
	 * tramite il numero di tile che il player cammina cambio mappa
	 * @param tileNum1 primo tile di collisione
	 * @param tileNum2 secondo tile di collisione
	 */
	public void changeSceneCollision(int tileNum1, int tileNum2) {
		if(tileNum1 == 10 || tileNum2 == 10) {
		if(panel.player.secondLighted == true) {
				panel.player.escaped = true;
				panel.ui.activateKey = false;
		} else {
			if(panel.tileM.mapStream == "/maps/map01.txt" && panel.hasKey == true && panel.player.escaped == false) {
				
				panel.playSE(0);
				panel.player.lighted = false;
				panel.tileM.mapStream = "/maps/map02.txt";
				panel.player.x = 770;
				panel.player.y = 515;
			} else if(panel.tileM.mapStream == "/maps/map02.txt"){
				panel.player.x = 860;
				panel.player.y = 220;
				panel.player.times++;
				if (panel.player.times == 1 && !panel.player.secondLighted) {
					panel.tileM.mapStream = "/maps/map04.txt";
					panel.player.lighted = false;
					panel.ui.activateKey = false;
					panel.player.scareInvoked = true;
				} else {
					panel.playSE(0);
					panel.tileM.mapStream = "/maps/map01.txt";
				}
			}	
		}
					
			
			

			
			if(panel.hasKey == false) {
				panel.player.invokeDoor = true;
			}
			panel.tileM.loadMap();
		}
		
		
		
	}
}
