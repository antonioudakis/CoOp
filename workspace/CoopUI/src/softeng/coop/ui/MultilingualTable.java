package softeng.coop.ui;

import java.text.*;
import java.util.*;

import softeng.coop.ui.data.DataUtilities;
import softeng.ui.vaadin.ui.SortableDynamicTable;

import com.vaadin.data.*;

/**
 * A table that attempts to render dates and enumerations
 * using friendly names with respect to current locale.
 * It doesn't take care of the Multilingual columns, these should be
 * bound to the table using DataItemContainer.
 */
public class MultilingualTable
extends SortableDynamicTable
{
	private static final long serialVersionUID = 1L;
	
	public MultilingualTable()
	{
		
	}
	
	public MultilingualTable(String caption)
	{
		super(caption);
	}

	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property property)
	{
		Locale locale = getLocale();
		
		Class<?> type = property.getType();
		Object value = property.getValue();
		
		if (locale != null && type != null)
		{
			if (type == Date.class && value != null)
			{
				DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
				
				return formatter.format(value);
			}
			else if (type.getEnumConstants() != null)
			{
				String friendlyName = getFriendlyName(type, value, locale);
				
				if (friendlyName != null) return friendlyName;
			}
			else if ((type == Boolean.class || type == boolean.class) && value != null)
			{
				ICoopContext context = CoopApplicationServlet.getCurrentApplication();
				
				String resourceKey = value.toString().toUpperCase() + "_CAPTION";
				
				return context.getLocalizedString(resourceKey);
			}
		}
		
		return super.formatPropertyValue(rowId, colId, property);
	}

	private String getFriendlyName(Class<?> type, Object value, Locale locale)
	{
		if (value == null) return null;
		
		ResourceBundle resourceBundle = DataUtilities.getResourceBundleForEnum(type, locale);
		
		if (resourceBundle == null) return null;
		
		try
		{
			String friendlyName = resourceBundle.getString(value.toString());
			
			return friendlyName;
		}
		catch (RuntimeException ex)
		{
			return null;
		}
	}
}
