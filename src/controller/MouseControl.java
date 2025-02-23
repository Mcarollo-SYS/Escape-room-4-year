package controller;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Player;
import view.GamePanel;
import view.UI;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class MouseControl implements MouseListener, MouseMotionListener {
	GamePanel gp;
	UI ui;
	Player p;
	CollisionChecker c;
	
	/**
	 * metodo costruttore
	 * @param gp pannello di gioco
	 * @param ui interfaccia user di gioco
	 */
	public MouseControl(GamePanel gp,UI ui) {
		this.gp = gp;
		this.ui=ui;
	}

	/**
	 * metodo non utilizzato
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	/**
	 * metodo per indicare la sovrascrittura della scritta
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		
		if(gp.gameState == gp.titleState) {
			if(ui.getPlay().contains(mouseX, mouseY)) {
				gp.ui.hover = 1;
			} else if(ui.getOptionMain().contains(mouseX,mouseY)) {
				gp.ui.hover = 2;
			} else if(ui.getExitMain().contains(mouseX, mouseY)) {
				gp.ui.hover = 3;
			} else {
				gp.ui.hover = 0;
			}
				
		}else if(gp.gameState==gp.pauseState){
			
			if(ui.getResume().contains(mouseX,mouseY)) {
				gp.ui.hover = 4;
			}else if(ui.getoption().contains(mouseX,mouseY)) {
				gp.ui.hover = 5;
			}else if(ui.getQuit().contains(mouseX,mouseY)) {
				gp.ui.hover = 6;
			} else {
				gp.ui.hover = 0;
			}
		
			
		}
	
		
	}

	/**
	 * metodo che gestisce gli eventi creati dai click del mouse
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		ui.volume = false;
		
		
		if(gp.gameState == gp.titleState) {
			if(!ui.control) {
				if(ui.getPlay().contains(mouseX, mouseY)) {
					gp.gameState = gp.playState;
					gp.stopMusic();
				}else if(ui.getExitMain().contains(mouseX,mouseY)) {
					System.exit(0);
				}
			}
			if(ui.getOptionMain().contains(mouseX, mouseY)) {
				
				ui.control = true;		
				
			}else {
				ui.control = false;
			}
				
		}else if(gp.gameState==gp.pauseState){
			
		if(ui.getResume().contains(mouseX,mouseY)) {
				gp.gameState = gp.playState;
			}else if(ui.getoption().contains(mouseX,mouseY)) {
				ui.volume = true;
			}else if(ui.getQuit().contains(mouseX,mouseY)) {
				gp.gameState=gp.titleState;
				gp.stopMusic();
			
				
			}
		
			
		}
		

		
	}

	/**
	 * metodo non utilizzato
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	   
	}

	/**
	 * metodo non utilizzato
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * metodo non utilizzato
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	/**
	 * metodo non utilizzato
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
