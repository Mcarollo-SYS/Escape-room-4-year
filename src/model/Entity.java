package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class Entity {
	
	
	/**
	 * La coordinata x dell'entita.
	 */
	public int x;

	/**
	 * La coordinata y dell'entita.
	 */
	public int y;

	/**
	 * La velocità dell'entita.
	 */
	public int speed;

	/**
	 * L'immagine quando l'entita e' fermo.
	 */
	public BufferedImage idleImage;

	/**
	 * L'immagine quando l'entita sta camminando.
	 */
	public BufferedImage walkImage;

	/**
	 * La direzione in cui si muove l'entita.
	 */
	public String direction;

	/**
	 * L'area solida dell'entita per la gestione delle collisioni.
	 */
	public Rectangle solidArea;

	/**
	 * Indica se le collisioni sono abilitate per l'entita.
	 */
	public boolean collisionOn = false;
}
