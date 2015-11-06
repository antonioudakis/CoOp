package softeng.coop.ui;

/**
 * Exception thrown when the user performs an illegal action.
 * This is expected to be shown to the user as a notification.
 */
public class CoopUIUserException extends CoopUIException
{
	public static enum Severity
	{
		Error,
		Warning,
		Information,
		Reminder
	}
	
	private static final long serialVersionUID = 1L;
	
	private Severity severity;

	public CoopUIUserException(String message)
	{
		this(Severity.Error, message);
	}

	public CoopUIUserException(String message, Throwable cause)
	{
		this(Severity.Error, message, cause);
	}

	public CoopUIUserException(Severity severity, String message)
	{
		this(severity, message, null);
	}

	public CoopUIUserException(Severity severity, String message, Throwable cause)
	{
		super(message, cause);
		
		this.severity = severity;
	}
	
	public Severity getSeverity()
	{
		return this.severity;
	}
}
