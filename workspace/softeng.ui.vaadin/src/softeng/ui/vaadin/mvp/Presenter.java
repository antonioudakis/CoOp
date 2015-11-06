package softeng.ui.vaadin.mvp;

import java.util.*;

/**
 * Base class for all presenters.
 * @param <M> The type of the model.
 * @param <C> The type of the application context. 
 * @param <V> The type of the view.
 */
public abstract class Presenter<M, C, V extends IView<M, C>>
{
	/**
	 * The application context as provided by the view.
	 * This is initialized after the view is attached to the application.
	 * This is available in methods attachToView, setupView 
	 * and subsequent operations,
	 * such as event handling.
	 */
	private C context;
	
	/**
	 * The view associated with this presenter.
	 */
	private V view;
	
	private ResourceBundle resourceBundle;
	
	private boolean isAttachedToView;
	
	/**
	 * Utility class to append localized messages separated by HTML break.
	 */
	protected class LocalizedMessageAppender
	{
		private StringBuilder builder;
		
		public LocalizedMessageAppender()
		{
			builder = new StringBuilder();
		}

		/**
		 * Add a localized message.
		 * @param key The resource key of the message.
		 */
		public void add(String key)
		{
			if (key == null) 
				throw new IllegalArgumentException("Argument 'key' must not be null.");
			
			if (builder.length() > 0) builder.append("<br />");
			builder.append(getLocalizedString(key));
		}
		
		/**
		 * Get the combined message created so far.
		 */
		@Override
		public String toString()
		{
			return builder.toString();
		}
		
		/**
		 * Returns true if the appender contains non-empty string.
		 */
		public boolean isEmpty()
		{
			return builder.length() == 0;
		}
		
		/**
		 * Returns the length of the contained string.
		 */
		public int length()
		{
			return builder.length();
		}
	}
	
	/**
	 * Create and associate a presenter with a view.
	 */
	public Presenter(V view)
	{
		this.view = view;
		this.isAttachedToView = false;
		
		// Subscribe to the attach event of the view.
		view.addAttachListener(new IViewListener<M, C, IView<M, C>>()
		{
			@Override
			public void onEvent(ViewEvent<M, C, IView<M, C>> event)
			{
				// Acquire the application context as supplied by the view.
				context = event.getView().getContext();
				
				if (!isAttachedToView)
				{
					attachToView();
					isAttachedToView = true;
				}
				
				// Now that the view is attached to the application,
				// move on to setup the model. This will eventually lead to the
				// view's data-binding. 
				setupView();
			}
		});
		
		view.addDetachListener(new IViewListener<M, C, IView<M,C>>()
		{
			@Override
			public void onEvent(ViewEvent<M, C, IView<M, C>> event)
			{
				if (isAttachedToView)
				{
					detachFromView();
					isAttachedToView = false;
				}
			}
		});
		
	}
	
	/**
	 * This is the method to override in order to subscribe to the view's
	 * events. It is called when the view is attached to the application.
	 */
	protected abstract void attachToView();
	
	/**
	 * Presenter implementations of this method are expected to 
	 * provide the logic required to set the model of 
	 * the view and to data-bind it.
	 * This includes the update of the model 
	 * and re-data-bind, if required.    
	 */
	protected abstract void setupView();
	
	/**
	 * The view associated with this presenter.
	 */
	protected V getView()
	{
		return this.view;
	}

	/**
	 * The application context as provided by the view.
	 * This is initialized after the view is attached to the application.
	 * This is available in methods attachToView, setupView 
	 * and subsequent operations,
	 * such as event handling.
	 */
	protected C getContext()
	{
		return this.context;
	}
	
	/**
	 * Get localized string that is appropriate
	 * to the user's language. 
	 * Must be called after the component has been attached.
	 * The resource file must match the class's simple name and
	 * must be in the same folder with the class.
	 */
	public String getLocalizedString(String key)
	{
		ResourceBundle bundle = this.getResourceBundle();
		
		return bundle.getString(key);
	}
	
	/**
	 * Get a ResourceBundle for localization that is appropriate
	 * to the user's language. 
	 * Must be called after the component has been attached.
	 * The resource file must match the class's simple name and
	 * must be in the same folder with the class.
	 */
	protected ResourceBundle getResourceBundle()
	{
		Locale locale = view.getLocale();
		
		if (locale == null)
			throw new RuntimeException(
					"Method must not be called before the component is attached.");
		
		String resourceName = getResourceBaseName();
		
		if (resourceName == null)
			throw new RuntimeException(
					"Class must not be anonymous.");
		
		if (this.resourceBundle == null)
		{
			this.resourceBundle = 
				ResourceBundle.getBundle(
						resourceName, 
						locale);
		}
		else
		{
			if (!locale.equals(this.resourceBundle.getLocale()))
			{
				this.resourceBundle = 
					ResourceBundle.getBundle(
							resourceName, 
							locale);
			}
		}
		
		return this.resourceBundle;
	}
	
	/**
	 * This is the method to override in order to unsubscribe from 
	 * events. It is called when the view is detached from the application.
	 */
	protected void detachFromView()
	{
		
	}
	
	/**
	 * Supply the base name of the resource associated with this presenter.
	 * By default, it is the simple class name of the presenter, residing in 
	 * the same package. 
	 */
	protected String getResourceBaseName()
	{
		return this.getClass().getCanonicalName();
	}
	
}
