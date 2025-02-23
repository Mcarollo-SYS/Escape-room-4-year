package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */
public class Sound {
	
	/**
	 * attributi base di suono
	 */
	public Clip clip;
	
	/**
	 * volume di suono
	 */
	public int volumeScaleSE = 3;
	FloatControl es;
	float soundVolume;
	
	URL musicURL[] = new URL[30];
	
	/**
	 * metodo costruttore
	 */
	public Sound() {
	
		musicURL[0] = getClass().getResource("/Sfx/door.wav");
		musicURL[1] = getClass().getResource("/Sfx/lamp.wav");
		musicURL[2] = getClass().getResource("/Sfx/chest.wav");
		musicURL[3] = getClass().getResource("/Sfx/drawer.wav");
		musicURL[4] = getClass().getResource("/Sfx/bookship.wav");
		musicURL[5] = getClass().getResource("/Sfx/lockChest.wav");
		musicURL[6] = getClass().getResource("/Sfx/safeLock.wav");
		musicURL[7] = getClass().getResource("/Sfx/jumpscare1.wav");
		musicURL[8] = getClass().getResource("/Sfx/jumpscare2.wav");
		musicURL[9] = getClass().getResource("/Sfx/clockHands.wav");
		musicURL[10] = getClass().getResource("/Sfx/backon1.wav");
		
	}
	
	/**
	 * imposta il file da riprodurre 
	 * @param i indice del file del'effetto sonoro
	 */
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			es = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			soundVolume();
			//clip.close();
		}catch(Exception e) {
			
		}
	}
	
	
	/**
	 * mette in riproduzione l'SE(Sound Effects) 
	 */
	public void play() {
		clip.start();
	}
	
	/**
	 * mette in loop l'SE(Sound Effects) 
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * mette in pausa l'SE(Sound Effects) 
	 */
	public void stop() {
		clip.stop();
	}
	
	/**
	 * 
	 * @return ritorna true se il clip non e' vuoto e clip sta riproducendo
	 */
	public boolean isPlaying() {
	    return clip != null && clip.isRunning();
	}
	
	/**
	 * 
	 *  aumenta o diminuisce il volume dei SE(Sound Effects)
	 */
	public void soundVolume() {
		switch(this.volumeScaleSE) {
		case 0 : soundVolume = -80f; break;
		case 1 : soundVolume = -20f; break;
		case 2 : soundVolume = -12f; break;
		case 3 : soundVolume = -5f; break;
		case 4 : soundVolume = 1f; break;
		case 5 : soundVolume = 6f; break;
		
		}
		es.setValue(soundVolume);	
		
	}
	
}
