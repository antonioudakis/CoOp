package softeng.coop.business;

/**
 * This is throws when a business rule is violated during
 * the execution of an action. The exception messages 
 * are expected to be friendly
 * for the end user, suitable to be shown in a UI.
 */
public class BusinessRuleViolationException extends CoOpException
{
	private static final long serialVersionUID = 1L;

	public BusinessRuleViolationException(String message)
	{
		super(message);
	}

	public BusinessRuleViolationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
