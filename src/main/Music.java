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
public class Music {
	
	/**
	 * attributo base per il suono
	 */
	public Clip clip;
	/**
	 * volume di suono
	 */
	public int volumeScale = 1;
	FloatControl fc;
	float volume;
	
	URL musicURL[] = new URL[30];
	
	/**
	 * metodo costruttore
	 */
	public Music() {
	
		musicURL[0] = getClass().getResource("/music/Thriller.wav");
		musicURL[1] = getClass().getResource("/music/Interstellar.wav");
		musicURL[2] = getClass().getResource("/music/RainInYourBlackEyes.wav");
		
	}
	
	/**
	 * Imposta il file audio da riprodurre
	 * @param i indice di file musicale
	 */
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
			//clip.close();
		}catch(Exception e) {
			
		}
	}
	
	
	/**
	 * fa partire la Clip corrente
	 */
	public void play() {
		clip.start();
	}
	
	/**
	 * manda in loop la clip 
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * ferma la canzone corrente 
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
	 * aumenta o diminuisce il volume della musica 
	 */
	public void checkVolume() {
		switch(volumeScale) {
		case 0 : volume = -80f; break;
		case 1 : volume = -20f; break;
		case 2 : volume = -12f; break;
		case 3 : volume = -5f; break;
		case 4 : volume = 1f; break;
		case 5 : volume = 6f; break;
		}
		fc.setValue(volume);
	}
	
	/**
	 * aumenta o diminuisce il volume dei SE(Sound Effects)
	 */
	

	
}
