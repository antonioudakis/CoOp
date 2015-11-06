package softeng.coop.ui.data;

/**
 * A validator manually added to a field to enforce it to be non-null,
 * having internationalized error message.
 * This is applied when JSR-303 validation is either
 * not available or if the field has no NoNull specification.
 */
public class NotNullMultilingualValidator
extends MultilingualValidator
{
	public NotNullMultilingualValidator()
	{
	}

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid(Object value)
	{
		return value != null;
	}

}
