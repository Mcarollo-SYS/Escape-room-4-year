package model;

import java.util.concurrent.Semaphore;
/**
 * @author Matteo-carollo, Andrea-Santoro;
 * @version 2 maggio 2024;
 */

public class StampaNomi implements Runnable {
	/**
	 * semaforodeposita
	 */
    private Semaphore deposita;
    /**
     * semaforo preleva 
     */
    private Semaphore preleva;
    /**
     * crezione di un buffer
     */
    private Buffer buffer;

    /**
     * metodo costruttore 
     * @param deposita depositato
     * @param preleva prelevato
     * @param buffer buffer
     */
    public StampaNomi(Semaphore deposita, Semaphore preleva, Buffer buffer) {
        this.deposita = deposita;
        this.preleva = preleva;
        this.buffer = buffer;
    }

    /**
     * metodo run della classe runnable
     */
    @Override
    public void run() {
        try {
            preleva.acquire(); // Attendiamo che il messaggio sia stato depositato
            String message = buffer.preleva(); // Preleviamo il messaggio dal buffer
            System.out.println(message); // Stampa il messaggio
            deposita.release(); // Rilasciamo il semaforo di deposito
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


