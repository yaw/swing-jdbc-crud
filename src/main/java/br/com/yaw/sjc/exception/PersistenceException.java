package br.com.yaw.sjc.exception;

/**
 * Exceção para problemas com o componente de persistencia.
 * Trata-se de uma RuntimeException!
 * 
 * @author YaW Tecnologia
 */
public class PersistenceException extends RuntimeException {

	public PersistenceException(String msg) {
		super(msg);
	}
	
	public PersistenceException(Exception ex) {
		super(ex);
	}
	
	public PersistenceException(String msg, Exception ex) {
		super(msg, ex);
	}
	
}
