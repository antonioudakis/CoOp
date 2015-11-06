package softeng.coop.ui;

public class CoopUIAccessDeniedException extends CoopUIUserException
{
	private static final long serialVersionUID = 1L;

	public CoopUIAccessDeniedException(ICoopContext context)
	{
		super(context.getLocalizedString("ACCESS_DENIED_CAPTION"));
	}

	public CoopUIAccessDeniedException(ICoopContext context, Throwable cause)
	{
		super(context.getLocalizedString("ACCESS_DENIED_CAPTION"), cause);
	}
}
