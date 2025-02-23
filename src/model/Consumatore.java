package model;
import java.util.concurrent.Semaphore;
/**
 * @author Matteo-carollo, Andrea-Santoro;
 * @version 2 maggio 2024;
 */
public class Consumatore implements Runnable {
	/**
	 * semafor deposita
	 */
    private Semaphore deposita;
    /**
     * semafor preleva
     */
    private Semaphore preleva;
    /**
     * crezione buffer 
     */
    private Buffer buffer;

    /**
     * metodo costruttore
     * @param deposita depositato
     * @param preleva prelevato 
     * @param buffer buffer
     */
    public Consumatore(Semaphore deposita, Semaphore preleva, Buffer buffer) {
        this.deposita = deposita;
        this.preleva = preleva;
        this.buffer = buffer;
    }

    /**
     * metodo run interfaccia runnable
     */
    @Override
    public void run() {
        try {
            preleva.acquire(); // Acquisisce il semaforo di prelievo
            String message = buffer.preleva(); // Preleva il messaggio dal buffer
            System.out.println(message); // Stampa il messaggio
            deposita.release(); // Rilascia il semaforo di deposito
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
