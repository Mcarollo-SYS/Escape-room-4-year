package view;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class TileManager {
	
	GamePanel panel;
	/**
	 * Array di oggetti Tile che rappresentano i singoli tile della mappa.
	 */
	public Tile[] tile;

	/**
	 * Array di oggetti InternalObject che rappresentano gli oggetti sulla mappa.
	 */
	public InternalObject[] object;

	/**
	 * Il numero massimo di oggetti consentiti sulla mappa.
	 */
	public final int maxObjects;

	/**
	 * Matrice che tiene traccia dei numeri dei tile sulla mappa.
	 */
	public int mapTileNum[][];

	/**
	 * Stringa che rappresenta il file dati della mappa.
	 */
	public String mapStream;
	
	/**
	 * metodo costruttore
	 * @param gp pannello di gioco
	 */
	public TileManager(GamePanel gp) {
		
		this.panel = gp;
		
		tile = new Tile[40];
		maxObjects = 40;
		object = new InternalObject[maxObjects];
		mapTileNum = new int[panel.maxScreenCol][panel.maxScreenRow];
		mapStream = "/maps/map01.txt";
		
		getTileImage();
		getObjectImage();
		loadMap();
		
	}
	
	/**
	 * importa le immagini dei tile
	 */
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/blank00.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall00.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/plank00.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall01.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall02.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall03.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall04.png"));
			tile[6].collision = true;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall05.png"));
			tile[7].collision = true;
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall06.png"));
			tile[8].collision = true;
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall07.png"));
			tile[9].collision = true;
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/door00.png"));
			tile[10].collision = true;
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/plank00.png"));
			tile[11].collision = true;
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/plank00.png"));
			tile[12].collision = true;
			
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/blank00.png"));
			tile[12].collision = true;
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/plank00.png"));
			tile[13].collision = true;
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/plank00.png"));
			tile[14].collision = true;
			
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/blank00.png"));
			tile[15].collision = true;
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/blank00.png"));
			tile[16].collision = true;
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall00.png"));
			tile[17].collision = true;
			
			tile[18] = new Tile();
			tile[18].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall00.png"));
			tile[18].collision = true;
			
			tile[19] = new Tile();
			tile[19].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall00.png"));
			tile[19].collision = true;
			
			tile[20] = new Tile();
			tile[20].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/plank00.png"));
			
			tile[21] = new Tile();
			tile[21].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall00.png"));
			tile[21].collision = true;
			
			tile[22] = new Tile();
			tile[22].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall00.png"));
			tile[22].collision = true;
			
			tile[23] = new Tile();
			tile[23].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/blank00.png"));
			
			tile[24] = new Tile();
			tile[24].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/ground00.png"));
			
			tile[25] = new Tile();
			tile[25].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wheat00.png"));
			tile[25].collision = true;
			
			tile[26] = new Tile();
			tile[26].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/ground00.png"));
			
			tile[27] = new Tile();
			tile[27].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/ground00.png"));
			tile[27].collision = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * importa le immagini degli oggetti
	 */
	private void getObjectImage() {
		
		try {
			
			object[11] = new InternalObject();
			object[11].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_chair.png"));

			
			object[12] = new InternalObject();
			object[12].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_shelf.png"));

			
			object[13] = new InternalObject();
			object[13].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_table.png"));

			
			object[15] = new InternalObject();
			object[15].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_shelf.png"));
			object[15].interactImage[1] = ImageIO.read(getClass().getResourceAsStream("/display/wrapper_page.png"));
			object[15].interactImage[2] = ImageIO.read(getClass().getResourceAsStream("/display/wrapper_page_2.png"));
			
			object[16] = new InternalObject();
			object[16].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_shelf.png"));

			
			object[17] = new InternalObject();
			object[17].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_chest.png"));			
			object[17].interactImage[1] = ImageIO.read(getClass().getResourceAsStream("/display/lock.png"));
			
			object[18] = new InternalObject();
			object[18].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_coach.png"));			
			
			object[19] = new InternalObject();
			object[19].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_lamp.png"));	
			
			object[20] = new InternalObject();
			object[20].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_carpet.png"));	
			
			object[21] = new InternalObject();
			object[21].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_cabinet.png"));
			object[21].interactImage[1] = ImageIO.read(getClass().getResourceAsStream("/display/drawer_with_battery.png"));	
			object[21].interactImage[2] = ImageIO.read(getClass().getResourceAsStream("/display/drawer_without_battery.png"));

			object[22] = new InternalObject();
			object[22].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_sigma.png"));
			object[22].interactImage[1] = ImageIO.read(getClass().getResourceAsStream("/display/wall_written.png"));	
			object[22].visibility = false;
			
			object[23] = new InternalObject();
			object[23].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_clock.png"));
			for(int i=1;i<13;i++) {
				object[23].interactImage[i] = ImageIO.read(getClass().getResourceAsStream("/display/clock_"+i+".png"));
			}
			
			object[26] = new InternalObject();
			object[26].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/full_house.png"));
			object[26].visibility = false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * carica le posizioni degli oggetti in mappa
	 * @param tileNum
	 * @param x
	 * @param y
	 */
	private void loadObjectPositions(int tileNum, int x, int y) {
		
		if(object[tileNum] != null) {
			object[tileNum].x = x;
			object[tileNum].y = y;
		}
			
	}
	
	/**
	 * carica le grandezze degli oggetti in mappa
	 * @param tileNum
	 */
	private void loadObjectSize(int tileNum) {
		
		if(object[tileNum] != null) {
			object[tileNum].xSize = object[tileNum].image.getWidth();
			object[tileNum].ySize = object[tileNum].image.getHeight();
		}
	}
	
	/**
	 * carica e aggiorna la mappatura
	 */
	public void loadMap() {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(mapStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < panel.maxScreenCol && row < panel.maxScreenRow) {
				
				String line = br.readLine();
				
				while(col < panel.maxScreenCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if(col == panel.maxScreenCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
			
		} catch (Exception e) {
			System.out.println("adasdaf");
		}
	}
	
	/**
	 *  disegnatore della mappa
	 * @param g2 componente grafico
	 */
	public void draw(Graphics2D g2) {
		
		//g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < panel.maxScreenCol && row < panel.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, panel.tileSize, panel.tileSize, null);
			loadObjectPositions(tileNum, x, y);
			loadObjectSize(tileNum);
			col++;
			x += panel.tileSize;
			
			if(col == panel.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += panel.tileSize;
			}
			
		}
		
		int width = 0;
		int height = 0;
		
		if(mapStream == "/maps/map01.txt") {
			object[11].visibility = true;
			object[18].visibility = true;
			object[17].visibility = true;
			object[21].visibility = true;
			object[23].visibility = false;
			for(int i=0;i<maxObjects;i++) {
				if(object[i] != null) {
					width = object[i].image.getWidth();
					height = object[i].image.getHeight();
					if(object[i].visibility) g2.drawImage(object[i].image, object[i].x, object[i].y, width*panel.scale, height*panel.scale, null);
				}
			}
			
		} else if (mapStream == "/maps/map02.txt") {
			object[11].visibility = false;
			object[18].visibility = false;
			object[17].visibility = false;
			object[21].visibility = false;
			object[22].visibility = false;
			object[23].visibility = true;
			for(int i=0;i<maxObjects;i++) {
				if(object[i] != null) {
					width = object[i].image.getWidth();
					height = object[i].image.getHeight();
					if(object[i].visibility) g2.drawImage(object[i].image, object[i].x, object[i].y, width*panel.scale, height*panel.scale, null);
				}
			}
		} else if (mapStream == "/maps/map03.txt") {
				object[11].visibility = false;
				object[12].visibility = false;
				object[13].visibility = false;
				object[15].visibility = false;
				object[16].visibility = false;
				object[17].visibility = false;
				object[18].visibility = false;
				object[19].visibility = false;
				object[20].visibility = false;
				object[21].visibility = false;
				object[22].visibility = false;
				object[23].visibility = false;
				object[26].visibility = true;
				panel.player.lighted = false;
				for(int i=0;i<maxObjects;i++) {
					if(object[i] != null) {
						width = object[i].image.getWidth();
						height = object[i].image.getHeight();
						if(object[i].visibility) g2.drawImage(object[i].image, object[i].x, object[i].y, width*panel.scale, height*panel.scale, null);
					}
				}
		}
		
		
		
	}
}
