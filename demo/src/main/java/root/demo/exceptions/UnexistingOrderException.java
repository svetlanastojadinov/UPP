package root.demo.exceptions;

public class UnexistingOrderException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnexistingOrderException(long id){
		super("Could not find order with id: "+id);
	}

}
