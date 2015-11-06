package softeng.ui.vaadin.mvp;

import com.vaadin.*;

/**
 * Provider contract in order to specify the application context.
 * @param <C>
 */
public abstract class ContextProvider<C>
{
	private C context;
	
	/**
	 * Concrete providers must implement this method in order
	 * to specify the application context.
	 * @param application The Vaadin application.
	 * @return Must return the application context based on the given application.
	 */
	protected abstract C initialize(Application application);
	
	/**
	 * This is called from the MVP framework when a view is attached
	 * to the application. Not to be called from the user.
	 * @param application
	 */
	final void attach(Application application)
	{
		this.context = initialize(application);
	}
	
	/**
	 * Returns the application context associated with the view,
	 * if the view has been previously attached to the application,
	 * else returns null.
	 */
	public final C getContext()
	{
		return this.context;
	}
}
