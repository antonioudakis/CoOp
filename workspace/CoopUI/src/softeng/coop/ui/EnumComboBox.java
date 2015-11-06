package softeng.coop.ui;

import java.util.*;

import softeng.coop.ui.data.DataUtilities;

import com.vaadin.ui.*;

public class EnumComboBox
extends ComboBox
{
	private static final long serialVersionUID = 1L;
	
	private Class<?> enumType;
	
	boolean isRepainting = false;
	
	private Locale localeShown = null;

	public EnumComboBox(Class<?> enumType)
	{
		if (enumType == null) 
			throw new IllegalArgumentException("Argument 'enumType' must not be null.");
		
		this.enumType = enumType;
		
		setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);

		DataUtilities.fillComboBox(this, enumType);
		
		setTextInputAllowed(false);
	}
	
	public EnumComboBox(String caption, Class<?> enumType)
	{
		this(enumType);
		setCaption(caption);
	}
	
	@Override
	public void requestRepaint()
	{
		if (isRepainting) return;
		
		isRepainting = true;
		
		try
		{
			refreshItemCaptions();
			
			super.requestRepaint();
		}
		finally
		{
			isRepainting = false;
		}
	}
	
	protected void refreshItemCaptions()
	{
		if (getLocale() == null || getLocale() == localeShown) return;
		
		localeShown = getLocale();
		
		try
		{
			String enumComboBoxClassName = EnumComboBox.class.getName();
			
			String prefix = 
				enumComboBoxClassName.substring(0, enumComboBoxClassName.lastIndexOf('.'));
			
			String resourceBaseName = String.format("%s.enums.%s", prefix, enumType.getSimpleName());
			
			ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBaseName, getLocale());
			
			for (Object value : getContainerDataSource().getItemIds())
			{
				if (value == null) continue;
				
				String name = value.toString();
				
				String friendlyName = resourceBundle.getString(name);
				
				setItemCaption(value, friendlyName);
			}
			
			DataUtilities.updateMultilingual(this);
		}
		catch (RuntimeException ex)
		{
			// swallow.
		}
		
	}
}
