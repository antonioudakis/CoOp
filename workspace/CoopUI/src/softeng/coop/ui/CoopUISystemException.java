package softeng.coop.ui;

/**
 * Thrown when there is an error due to a fault
 * in the UI system.
 */
public class CoopUISystemException extends CoopUIException
{
	private static final long serialVersionUID = 1L;

	public CoopUISystemException()
	{
	}

	public CoopUISystemException(String message)
	{
		super(message);
	}

	public CoopUISystemException(Throwable cause)
	{
		super(cause);
	}

	public CoopUISystemException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
