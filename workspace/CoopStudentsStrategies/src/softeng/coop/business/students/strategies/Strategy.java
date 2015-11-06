package softeng.coop.business.students.strategies;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.mail.client.*;

/**
 * Base class for strategy implementations. Supports internationalization.
 */
public abstract class Strategy
{
	private static final String mailerAccount = "noreply@praktiki.ntua.gr";
	
	private static Map<String, Locale> languageLocalesMap = new HashMap<String, Locale>();
	
	/**
	 * Return the localized resources associated with this manager.
	 * The current locale is as defined in the current user's session.
	 */
	protected ResourceBundle getResources(Locale locale)
	{
		return ResourceBundle.getBundle(
				this.getResourceBundleBaseName(), 
				locale);
	}
	
	/**
	 * Get the localized string that corresponds to a given resource key.
	 * Throws MissingResourceException when the key is not found.
	 */
	protected String getLocalizedString(Locale locale, String key)
	{
		return this.getResources(locale).getString(key);
	}
	
	/**
	 * Get the localized string that corresponds to a given resource key.
	 * Throws MissingResourceException when the key is not found.
	 */
	protected String getLocalizedString(String key)
	{
		return this.getResources(Locale.getDefault()).getString(key);
	}
	
	/**
	 * Specify the base name for the resources associated with this manager.
	 */
	protected String getResourceBundleBaseName()
	{
		return this.getClass().getCanonicalName();
	}
	
	/**
	 * Get the literal which is most suitable to given locale.
	 * Falls back to default literal or any literal if no literal is found for the
	 * specific locale.
	 * @param locale The desired locale.
	 * @param multilingual The multilingual text.
	 * @return Returns the best appropriate text or null if no text is found at all.
	 */
	protected String getLiteral(Locale locale, Multilingual multilingual)
	{
		if (locale == null) 
			throw new IllegalArgumentException("Argument 'locale' must not be null.");
		
		if (multilingual == null) 
			throw new IllegalArgumentException("Argument 'multilingual' must not be null.");
		
		Literal defaultLiteral = null;
		Literal anyLiteral = null;
		
		for (Literal literal : multilingual.getLiterals())
		{
			if (literal.getLanguage().getLocaleCode().equals(locale.getLanguage()))
				return literal.getText();
			
			if (literal.isDefault()) defaultLiteral = literal;
			
			if (anyLiteral == null) anyLiteral = literal;
		}
		
		if (defaultLiteral != null) return defaultLiteral.getText();
		
		if (anyLiteral != null) return anyLiteral.getText();
		
		return null;
	}
	
	/**
	 * Get the locale which corresponds to a language.
	 */
	protected Locale getLanguageLocale(Language language)
	{
		if (language == null) 
			Locale.getDefault();
		
		String code = language.getLocaleCode();
		
		Locale locale = null;
		
		synchronized (languageLocalesMap)
		{
			locale = languageLocalesMap.get(code);
			
			if (locale == null)
			{
				locale = new Locale(code);
				
				languageLocalesMap.put(code, locale);
			}
		}
		
		return locale; 
	}

	/**
	 * Send mail to a single recepient.
	 * @param recepient The recepient's e-mail address.
	 * @param title The message subject.
	 * @param body The message content in HTML format.
	 */
	protected void sendMail(String recepient, String title, String body)
	{
		MailClient mailClient = new MailClient();
		
		mailClient.setContentType("text/html; charset=utf-8");

		ArrayList<String> recepients = new ArrayList<String>(1);
		
		recepients.add(recepient);
		
		mailClient.postMail(recepients, title, body, mailerAccount);
	}
	
	/**
	 * Send mail to multiple recepients.
	 * @param recepients The recepients' e-mail addresses.
	 * @param title The message subject.
	 * @param body The message content.
	 */
	protected void sendMail(Collection<String> recepients, String title, String body)
	{
		MailClient mailClient = new MailClient();

		mailClient.postMail(recepients, title, body, mailerAccount);
	}

}
