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
public class SoundWalk {
	
	/**
	 * attributo base del suono
	 */
	public Clip clip;
	FloatControl fc;
	/**
	 * volume di suono
	 */
	public int volumeScale = 3;
	float soundVolume;
	
	URL musicURL[] = new URL[30];
	
	/**
	 * metodo costruttore
	 */
	public SoundWalk() {
		
		musicURL[0] = getClass().getResource("/Sfx/walk.wav");		
	
	}
	
	/**
	 * imposta il file da riprodurre 
	 * @param i indice del file musicale
	 */
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			soundVolume();
			//clip.close();
		}catch(Exception e) {
			
		}
	}
	
	
	/**
	 * mette in riproduzione il suono
	 */
	public void play() {
		clip.start();
	}
	
	/**
	 * mette in loop il suono
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * ferma il suono
	 */
	public void stop() {
		clip.stop();
	}
	
	/**
	 * 
	 * @return ritorna true se clip non e' vuoto e il clip sta producendo
	 */
	public boolean isPlaying() {
	    return clip != null && clip.isRunning();
	}
	
	/**
	 * impostare il volume del suono della camminata 
	 * 
	 */
	public void soundVolume() {
		switch(volumeScale) {
		case 0 : soundVolume = -80f; break;
		case 1 : soundVolume = -20f; break;
		case 2 : soundVolume = -12f; break;
		case 3 : soundVolume = -5f; break;
		case 4 : soundVolume = 1f; break;
		case 5 : soundVolume = 6f; break;
		}
		fc.setValue(soundVolume);
	}
}
