package softeng.coop.ui.data;

import java.util.*;

import com.vaadin.data.*;

import softeng.coop.ui.*;

/**
 * Base class for all multilingual validators added manually to fields,
 * where JSR-303 validation is not available or insufficient for the scenario.
 * Concrete subclasses must specify the 'isValid' method. 
 */
@SuppressWarnings("serial")
public abstract class MultilingualValidator
implements Validator
{
	private Locale cachedLocale;
	
	private String cachedMessage;
	
	public MultilingualValidator()
	{
	}

	@Override
	public void validate(Object value) throws InvalidValueException
	{
		if (!isValid(value))
			throw new MultilineInvalidValueException(getMessage());
	}

	/**
	 * Return the error message for this validator,
	 * taking into account the current locale as supplied
	 * by the current context.
	 * Default implementation looks up in MultilingualValidatorMessages[_XX].properties
	 * file for string keys named after the concrete validation class.
	 */
	protected String getMessage()
	{
		ICoopContext context = CoopApplicationServlet.getCurrentApplication();
		
		Locale currentLocale = Locale.getDefault();
		
		if (context != null) currentLocale = context.getLocale();
		
		if (currentLocale != cachedLocale)
		{
			cachedLocale = context.getLocale();

			String bundleName = 
				String.format(
						"%s.MultilingualValidatorMessages", 
						MultilingualValidator.class.getPackage().getName());
			
			ResourceBundle resourceBundle = 
				ResourceBundle.getBundle(bundleName, cachedLocale);
			
			cachedMessage = resourceBundle.getString(this.getClass().getName());
		}
		
		return cachedMessage;
	}

}
