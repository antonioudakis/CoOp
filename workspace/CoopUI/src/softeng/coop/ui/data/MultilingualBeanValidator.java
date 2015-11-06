package softeng.coop.ui.data;

import com.vaadin.addon.beanvalidation.*;

/**
 * Corrects the BeanValidationValidator to report multiple errors 
 * in different lines instead of printing 'br'.
 */
public class MultilingualBeanValidator
extends BeanValidationValidator
{
	private static final long serialVersionUID = 1L;

	public MultilingualBeanValidator(Class<?> beanClass, String propertyName)
	{
		super(beanClass, propertyName);
	}

	@Override
	public void validate(Object value) throws InvalidValueException
	{
		try
		{
			super.validate(value);
		}
		catch (InvalidValueException ex)
		{
			String correctedMessage = ex.getLocalizedMessage().replaceAll("<br/>", "\r");

			throw new MultilineInvalidValueException(correctedMessage);
		}
	}
	
}
