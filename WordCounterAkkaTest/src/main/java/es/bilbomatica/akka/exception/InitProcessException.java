package es.bilbomatica.akka.exception;

public class InitProcessException extends RuntimeException {

	private static final long serialVersionUID = -7645137773154957722L;

	public InitProcessException(String message) {
		super(message);
	}
	
	public InitProcessException(Throwable th) {
		super(th);
	}
	
	public InitProcessException(String message, Throwable th) {
		super(message, th);
	}
	
	public static void assertTrue(boolean condition, String message) {
		if (!condition)
		{
			throw new InitProcessException(message);
		}
	}
	
	public static void assertFalse(boolean condition, String message) {
		if (condition)
		{
			throw new InitProcessException(message);
		}
	}
}
