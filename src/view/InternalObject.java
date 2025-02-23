package view;

import java.awt.image.BufferedImage;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class InternalObject {
	

	/**
	 * La coordinata x dell'oggetto.
	 */
	public int x;

	/**
	 * La coordinata y dell'oggetto.
	 */
	public int y;

	/**
	 * La dimensione x dell'oggetto.
	 */
	public int xSize;

	/**
	 * La dimensione y dell'oggetto.
	 */
	public int ySize;

	/**
	 * L'immagine dell'oggetto.
	 */
	public BufferedImage image;

	/**
	 * Array di immagini per le interazioni.
	 */
	public BufferedImage interactImage[] = new BufferedImage[20];

	/**
	 * Indica se l'oggetto ha interagito con qualcosa.
	 */
	public boolean interacted;

	/**
	 * Indica se l'oggetto è visibile.
	 */
	public boolean visibility = true;
}
