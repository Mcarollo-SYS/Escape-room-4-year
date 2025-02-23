package model;
import java.util.concurrent.Semaphore;
/**
 * @author Matteo-carollo, Andrea-Santoro;
 * @version 2 maggio 2024;
 */
public class Produttore implements Runnable {
	/**
	 * semaforo deposita
	 */
    private Semaphore deposita;
    /**
     * semaforo preleva
     */
    private Semaphore preleva;
    /**
     * buffer per depositare la stringa
     */
    private Buffer buffer;
    /**
     * stringa messaggio
     */
    private String message;

    /**
     * metodo costruttore
     * @param deposita depositatop
     * @param preleva prelevato 
     * @param buffer buffer
     * @param message messeggio
     */ 
    public Produttore(Semaphore deposita, Semaphore preleva, Buffer buffer, String message) {
        this.deposita = deposita;
        this.preleva = preleva;
        this.buffer = buffer;
        this.message = message;
    }

    /**
     * metodo interfaccia runnable 
     */
    @Override
    public void run() {
        try {
            deposita.acquire(); // Acquisisce il semaforo di deposito
            buffer.deposita(message); // Deposita il messaggio nel buffer
            preleva.release(); // Rilascia il semaforo di prelievo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
