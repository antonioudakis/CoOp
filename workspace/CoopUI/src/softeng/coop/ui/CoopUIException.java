package softeng.coop.ui;

/**
 * Contract for exceptions thrown by the UI itself,
 * not the underlying systems. 
 */
public abstract class CoopUIException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public CoopUIException()
	{
	}

	public CoopUIException(String message)
	{
		super(message);
	}

	public CoopUIException(Throwable cause)
	{
		super(cause);
	}

	public CoopUIException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
