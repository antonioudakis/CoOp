package softeng.coop.ui.data;

import com.vaadin.data.Validator.*;

/**
 * An exception for validators that translates the \r characters
 * of the message into HTML breaks.
 */
public class MultilineInvalidValueException
extends InvalidValueException
{
	private static final long serialVersionUID = 1L;

	public MultilineInvalidValueException(String message)
	{
		super(message);
	}

	@Override
	protected String getHtmlMessage()
	{
		String originalMessage = super.getHtmlMessage();
		
		if (originalMessage != null)
		{
			return originalMessage
				.replaceAll("&#13;", "<br />")
				.replaceAll("&#10;", "<br />");
		}
		else
		{
			return null;
		}
	}

}
