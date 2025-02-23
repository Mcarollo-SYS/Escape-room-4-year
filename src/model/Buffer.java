package model;
/**
 * @author Matteo-carollo, Andrea-Santoro;
 * @version 2 maggio 2024;
 */
public class Buffer {
	/**
	 * messaggio
	 */
    private String message;

    /**
     * per sincronizzare 
     * @param message messaggio
     */
    public synchronized void deposita(String message) {
        this.message = message;
    }

    /**
     * per sincronizzare 
     * @return il messaggio 
     */
    public synchronized String preleva() {
        return message;
    }
}
