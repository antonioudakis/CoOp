package softeng.coop.ui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Prevents Hibernate Validator from caching messages and neglecting
 * specified locales during message requests. 
 * It also has has the client's locale as default, instead of the server's 
 * as Hibernate Validator does.
 */
@SuppressWarnings("deprecation")
public class MultilingualMessageInterpolator
extends org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator
{
	// Somehow we also need to override Hibernate's resource handling,
	// as it doesn't honor the supplied locale during resource loading. 
	public static class MultilingualResourceBundleLocator
		implements org.hibernate.validator.resourceloading.ResourceBundleLocator
	{
		private static final String baseName = "ValidationMessages";

		@Override
		public ResourceBundle getResourceBundle(Locale locale)
		{
			return ResourceBundle.getBundle(baseName, locale);
		}
		
	}
	
	public MultilingualMessageInterpolator()
	{
		// Somehow we also need to override Hibernate's resource handling,
		// as it doesn't honor the supplied locale during resource loading. 
		super(new MultilingualResourceBundleLocator(), false);
	}

	@Override
	public String interpolate(String message, Context context)
	{
		CoopApplication application = CoopApplicationServlet.getCurrentApplication();
		
		if (application != null)
		{
			return this.interpolate(message, context, application.getLocale());
		}
		else
		{
			return super.interpolate(message, context);
		}
	}
}
