package controller;

import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.FlatteningPathIterator;

import main.Music;
import view.GamePanel;
import view.UI;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class KeyHandler implements KeyListener{
	
	/** Indica se il tasto "su" e' premuto. */
    public boolean upPressed;

    /** Indica se il tasto "giù" e' premuto. */
    public boolean downPressed;

    /** Indica se il tasto "sinistra" e' premuto. */
    public boolean leftPressed;

    /** Indica se il tasto "destra" e' premuto. */
    public boolean rightPressed;

    /** Indica se il tasto "E" e' premuto. */
    public boolean ePressed;

    /** Indica se il tasto "E" e' stato rilasciato. */
    public boolean eUp;

    /** Indica se il pannello e' attivo. */
    public boolean activePanel;

    /** Indica se l'interazione interna e' attiva. */
    public boolean internalInteract;

    /** Il numero selezionato. */
    public int selectedNum = 0;

    /** Il numero dell'orologio. */
    public int clockNum = 9;
	
	GamePanel gp;
	UI ui;
	
	/**
	 * metodo costruttore;
	 * @param gp pannello di gioco
	 * @param ui interfaccia user di gioco
	 */
	public KeyHandler(GamePanel gp, UI ui) {
		this.gp = gp;
		this.ui = ui;
		activePanel = false;
	}
	
	/**
	 * metodo non utilizzato 
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		//Unused Method
	}

	/**
	 * metodo tramite la pressione di un determinato tasto che genererà un evento e verrà gestito  
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode(); //getting key code
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.moonWalk = true;
			gp.stopMusic();
		}
		if (code == KeyEvent.VK_O) {
			gp.moonWalk = false;
			gp.stopMusic();
		}
		if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if(code == KeyEvent.VK_L) {
			if(gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			}else if(gp.gameState == gp.pauseState) {
				gp.gameState = gp.playState;
			}
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
		if (code == KeyEvent.VK_RIGHT) {
			if(ui.volume && ui.commandNum == 0 && !gp.player.lockOpen) {
				if(gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
			}
			
			if(ui.volume && ui.commandNum == 1 && !gp.player.lockOpen) {
				if(gp.sound.volumeScaleSE < 5) {
					gp.sound.volumeScaleSE++;
					gp.sound.soundVolume();
				}
			}
			
		}
		
		if (code == KeyEvent.VK_LEFT) {
			if(ui.volume && ui.commandNum == 0 && !gp.player.lockOpen) {
				if(gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
			}
			
			if(ui.volume && ui.commandNum == 1 && !gp.player.lockOpen) {
				if(gp.sound.volumeScaleSE > 0) {
					gp.sound.volumeScaleSE--;
					gp.sound.soundVolume();
				}
			}
			
		}
		
		if (code == KeyEvent.VK_E) {
			ePressed = true;
			eUp = true;
			if(gp.player.interacted) {
				activePanel = activePanel ? false : true;
				/*if(activePanel == true) {
					gp.gameState = gp.interactState;
				} else {
					gp.gameState = gp.playState;
				}*/

			} else {
				activePanel = false;
				eUp = false;
			}
			
		}
		
		if(code == KeyEvent.VK_UP && !gp.player.lockOpen) {
			ui.commandNum = 0;
		}
		
		if(code == KeyEvent.VK_DOWN && !gp.player.lockOpen) {
			ui.commandNum = 1;
		}
		
		if(code == KeyEvent.VK_F) {
			if(gp.player.interacted) internalInteract = true;
		}
		
		if(gp.player.lockOpen) {
			
			if(code == KeyEvent.VK_UP && !gp.hasKey) {
				if(selectedNum == 0) {
					if(gp.player.num1 < 9) {
						gp.player.num1++;
						gp.playSE(6);
					} else {
						gp.playSE(6);
						gp.player.num1 = 0;
					}
				} else if(selectedNum == 1) {
					if(gp.player.num2 < 9) {
						gp.player.num2++;
						gp.playSE(6);
					} else {
						gp.playSE(6);
						gp.player.num2 = 0;
					}
				} else if(selectedNum == 2) {
					if(gp.player.num3 < 9) {
						if(gp.player.num1 == 5 && gp.player.num2 == 4 && gp.player.num3 == 1) {
							gp.player.num3++;
							gp.playSE(2);
						}else{
							gp.player.num3++;
							gp.playSE(6);
						}
					} else {
						gp.playSE(6);
						gp.player.num3 = 0;
					}
				}
			}
			
			if(code == KeyEvent.VK_DOWN && !gp.hasKey) {
				if(selectedNum == 0) {
					if(gp.player.num1 > 0) {
						gp.player.num1--;
						gp.playSE(6);
					} else {
						gp.playSE(6);
						gp.player.num1 = 9;
					}
				} else if(selectedNum == 1) {
					if(gp.player.num2 > 0) {
						gp.player.num2--;
						gp.playSE(6);
					} else {
						gp.playSE(6);
						gp.player.num2 = 9;
					}
				} else if(selectedNum == 2) {
					if(gp.player.num3 > 0) {
						if(gp.player.num1 == 5 && gp.player.num2 == 4 && gp.player.num3 == 3) {
							gp.player.num3--;
							gp.playSE(2);
						}else{
							gp.player.num3--;
							gp.playSE(6);
						}
							
					} else {
						gp.playSE(6);
						gp.player.num3 = 9;
					}
				}
			}
			
			if(code == KeyEvent.VK_LEFT) {
				if(selectedNum == 0) {
					selectedNum = 2;
				} else if(selectedNum == 1) {
					selectedNum = 0;
				} else if(selectedNum == 2) {
					selectedNum =  1;
				}
			}
			if(code == KeyEvent.VK_RIGHT) {
				if(selectedNum == 0) {
					selectedNum = 1;
				} else if(selectedNum == 1) {
					selectedNum = 2;
				} else if(selectedNum == 2) {
					selectedNum =  0;
				}
			}
		}
		
		if(gp.player.clockOpen) {
			if(code == KeyEvent.VK_LEFT) {
				gp.playSE(9);
				if(clockNum == 12) {
					clockNum = 1;
				} else {
					clockNum++;
				}
			}
			if(code == KeyEvent.VK_RIGHT) {
				gp.playSE(9);
				if(clockNum == 1) {
					clockNum = 12;
				} else {
					clockNum--;
				}
			}
		}
	}

	/**
	 * metodo che quando verrà rilascito un determinato tasto genererà un evento e verrà gestito
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode(); //getting key code
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
		if (code == KeyEvent.VK_D) {
			ePressed = false;
		}
		if(code == KeyEvent.VK_E) {
			ePressed = false;
		}
		
	}
	
	
}
