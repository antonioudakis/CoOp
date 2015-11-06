package softeng.coop.ui;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;

import java.util.*;

/**
 * A combo box that contains all the languages as item IDs of type Language.
 * Suitable for binding to a property of type Language. 
 */
@SuppressWarnings("serial")
public class LanguageComboBox
extends ComboBox
{
	public LanguageComboBox()
	{
		Session session = CoopApplicationServlet.getCurrentApplication().getSession();
		
		if (session == null) return;
		
		Set<Language> languages = session.getBaseManager().getAvailableLanguages();
		
		for (Language language : languages)
		{
			addItem(language);
			
			setItemIcon(
					language, 
					new ThemeResource(String.format("../images/flags/%s.gif", language.getLocaleCode())));
			
			setItemCaption(language, language.getName());
		}
	}
	
	public LanguageComboBox(String caption)
	{
		this();
		setCaption(caption);
	}
}
