package softeng.coop.ui.data;

import com.vaadin.data.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

@SuppressWarnings("serial")
public class MultilingualLiteralsValidator
extends MultilingualValidator
{
	private static final int MAX_LENGTH = 510; //510;
	
	@SuppressWarnings("rawtypes")
	private DataItem.MultilingualProperty property;

	@SuppressWarnings("rawtypes")
	public MultilingualLiteralsValidator(Property property)
	{
		if (property == null) 
			throw new IllegalArgumentException("Argument 'property' must not be null.");
		
		if (property instanceof DataItem.MultilingualProperty)
		{
			this.property = (DataItem.MultilingualProperty)property;
		}
	}

	@Override
	public boolean isValid(Object value)
	{
		if (property == null) return true;
		
		Multilingual multilingual = property.getMultilingual();
		
		return accumulateErrors(multilingual, (String)value) == null;
	}

	@Override
	public void validate(Object value) throws InvalidValueException
	{
		if (property == null) return;
		
		Multilingual multilingual = property.getMultilingual();
		
		String errorMessage = accumulateErrors(multilingual, (String)value);
		
		if (errorMessage != null)
			throw new MultilineInvalidValueException(errorMessage);
	}
	
	private String accumulateErrors(Multilingual multilingual, String value)
	{
		if (multilingual == null) return null;
		
		ICoopContext context = CoopApplicationServlet.getCurrentApplication();
		
		Language currentLanguage = context.getSession().getAuthenticatedUser().getPreferredLanguage();
		
		StringBuilder builder = null;
		
		boolean isCurrentValueChecked = false;
		
		for (Literal literal : multilingual.getLiterals())
		{
			String text = null;
			
			if (literal.getLanguage() == currentLanguage)
			{
				text = value;
				isCurrentValueChecked = true;
			}
			else
			{
				text = literal.getText();
			}
			
			if (text != null && text.length() > MAX_LENGTH)
			{
				if (builder == null) 
					builder = new StringBuilder();
				else
					builder.append("\n");
				
				builder.append(
						String.format(getMessage(), literal.getLanguage().getName(), MAX_LENGTH));
			}
		}
		
		if (!isCurrentValueChecked && value != null && value.length() > MAX_LENGTH)
		{
			if (builder == null) 
				builder = new StringBuilder();
			else
				builder.append("\n");
			
			builder.append(
					String.format(getMessage(), currentLanguage.getName(), MAX_LENGTH));
		}
		
		if (builder != null)
			return builder.toString();
		else
			return null;
	}

}
