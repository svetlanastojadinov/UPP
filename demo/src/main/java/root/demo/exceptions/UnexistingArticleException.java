package root.demo.exceptions;

public class UnexistingArticleException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnexistingArticleException(long id) {
		// TODO Auto-generated constructor stub
		super("Could not find article with id: "+id);
	}

}
