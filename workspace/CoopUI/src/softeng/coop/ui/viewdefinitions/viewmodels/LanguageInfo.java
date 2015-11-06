package softeng.coop.ui.viewdefinitions.viewmodels;

import java.util.*;

import com.vaadin.terminal.*;

public class LanguageInfo
{
	private String languageName;
	private ThemeResource icon;
	private Locale locale;
	
	public LanguageInfo(Locale locale, String name)
	{
		if (locale == null) 
			throw new IllegalArgumentException("Argument 'locale' must not be null.");
		if (name == null) 
			throw new IllegalArgumentException("Argument 'name' must not be null.");
		
		this.locale = locale;
		this.languageName = name;
		this.icon = new ThemeResource(String.format("../images/flags/%s.gif", locale.getLanguage()));
	}

	public String getLanguageName()
	{
		return languageName;
	}

	public ThemeResource getIcon()
	{
		return icon;
	}

	public Locale getLocale()
	{
		return locale;
	}
}
