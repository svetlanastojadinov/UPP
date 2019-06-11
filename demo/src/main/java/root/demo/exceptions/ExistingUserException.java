package root.demo.exceptions;

public class ExistingUserException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingUserException(String credentials) {
		// TODO Auto-generated constructor stub
		super("User with username/email "+ credentials+ " already exist!" );
	}

}
