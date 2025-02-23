package main;

import java.util.concurrent.Semaphore;

import model.Buffer;
import model.Consumatore;
import model.Produttore;
import model.StampaNomi;
import view.GameWindow;

/**
 * @author Matteo-carollo, Andrea-Santoro, Wang-jun-tao;
 * @version 1 maggio 2024;
 */

public class Main {
	/**
	 *  metodo principale del programma
	 * @param args args
	 */
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Semaphore deposita = new Semaphore(1);
        Semaphore preleva = new Semaphore(0);
        Semaphore semaphore = new Semaphore(1);
        
        
        StampaNomi stampaNomi = new StampaNomi(deposita, preleva, buffer);
        Thread stampaNomiThread = new Thread(stampaNomi);

        Produttore p1 = new Produttore(deposita, preleva, buffer, "Developed by");
        Produttore p2 = new Produttore(deposita, preleva, buffer, "Matteo Carollo");
        Produttore p3 = new Produttore(deposita, preleva, buffer, "Andrea Santoro");
        Produttore p4 = new Produttore(deposita, preleva, buffer, "Wang Jun Tao");

        Thread produttore1 = new Thread(p1);
        Thread produttore2 = new Thread(p2);
        Thread produttore3 = new Thread(p3);
        Thread produttore4 = new Thread(p4);

        Consumatore consumatore = new Consumatore(deposita, preleva, buffer);
        Thread c1 = new Thread(consumatore);
        Thread c2 = new Thread(consumatore);
        Thread c3 = new Thread(consumatore);

        stampaNomiThread.start();
        produttore1.start();
        produttore2.start();
        produttore3.start();
        produttore4.start();
        c1.start();
        c2.start();
        c3.start();
        
        GameWindow window = new GameWindow(semaphore);
    }
}

   

