package softeng.ui.vaadin.mvp;

import java.util.*;

import com.vaadin.*;
import com.vaadin.ui.*;

/**
 * Base implementation of a IView<M, C>.
 * Typically, it will be further specialized in a project
 * before being sub-classed to concrete components
 * to use a specific ContextProvider that meets the project's demands.
 * @param <M> The type of the model associated with the view.
 * @param <C> The type of the application context associated with the view.
 */
public abstract class ModelWindow<M, C> 
	extends Window
	implements IView<M, C>
{
	/**
	 * Create the view component.
	 * @param contextProvider The context provider which will specify 
	 * the context after the attachment.
	 */
	public ModelWindow()
	{
		supplyPresenter();
	}
	
	private static final long serialVersionUID = 1L;
	
	private ViewEventSubscription<M, C, IView<M, C>> attachSubscription =
		new ViewEventSubscription<M, C, IView<M,C>>();
	
	private ViewEventSubscription<M, C, IView<M, C>> detachSubscription =
		new ViewEventSubscription<M, C, IView<M,C>>();

	private ViewEventSubscription<M, C, IView<M, C>> modelChangeSubscription =
		new ViewEventSubscription<M, C, IView<M,C>>();

	private M model;
	
	private ResourceBundle resourceBundle;
	
	/**
	 * Concrete views can optionally supply their 
	 * associated presenter through this method.
	 */
	protected abstract Presenter<M, C, ? extends IView<M, C> > supplyPresenter();
	
	@Override
	public void addAttachListener(IViewListener<M, C, IView<M, C>> listener)
	{
		this.attachSubscription.add(listener);
	}

	@Override
	public void removeAttachListener(IViewListener<M, C, IView<M, C>> listener)
	{
		this.attachSubscription.remove(listener);
	}

	@Override
	public void addModelChangeListener(IViewListener<M, C, IView<M, C>> listener)
	{
		this.modelChangeSubscription.add(listener);
	}

	@Override
	public void removeModelChangeListener(IViewListener<M, C, IView<M, C>> listener)
	{
		this.modelChangeSubscription.remove(listener);
	}

	@Override
	public void attach()
	{
		super.attach();
		
		this.setupUI();

		// Right after the above 'attach' call, 
		// the application is available.
		Application application = this.getApplication();
		
		this.setupLocalizedCaptions(application.getLocale());
		
		ViewEvent<M, C, IView<M, C>> event = 
			new ViewEvent<M, C, IView<M, C>>(this);

		this.attachSubscription.fire(event);
	}
	
	/**
	 * This method is called my the MVP mechanism to setup the visual
	 * components in this view. If the component is produced by the visual
	 * designer, this provides an option to refine the visual elements
	 * in aspects which are not supported by the designer.
	 */
	protected void setupUI()
	{
	}
	
	/**
	 * This method is called by the MVP mechanism to allow optional
	 * internationalization of the visual elements in this view.
	 * @param locale The locale of the requested internationalization.
	 */
	protected void setupLocalizedCaptions(Locale locale)
	{
	}
	
	@Override
	public M getModel()
	{
		return this.model;
	}

	@Override
	public void setModel(M model)
	{
		this.model = model;
		this.fireModelChangeEvent();
	}

	@Override
	public abstract void dataBind();
	
	@Override
	public void requestRepaintAll()
	{
		if (this.getApplication() != null)
			this.setupLocalizedCaptions(this.getApplication().getLocale());
		
		super.requestRepaintAll();
	}

	@Override
	public abstract C getContext();
	
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
		Application application = this.getApplication();
		
		if (application == null)
			throw new RuntimeException(
					"Method must not be called before the component is attached.");
		
		String resourceBaseName = this.getResourceBaseName();
		
		if (resourceBaseName == null)
			throw new RuntimeException(
					"Class must not be anonymous.");
		
		if (this.resourceBundle == null)
		{
			this.resourceBundle = 
				ResourceBundle.getBundle(
						resourceBaseName, 
						application.getLocale());
		}
		else
		{
			Locale currentLocale = application.getLocale();
			
			if (!currentLocale.equals(this.resourceBundle.getLocale()))
			{
				this.resourceBundle = 
					ResourceBundle.getBundle(
							resourceBaseName, 
							currentLocale);
			}
		}
		
		return this.resourceBundle;
	}

	/**
	 * Call to fire the model change event.
	 */
	protected void fireModelChangeEvent()
	{
		this.modelChangeSubscription.fire(
				new ViewEvent<M, C, IView<M,C>>(this));
	}
	
	/**
	 * Supply the base name of the resource associated with this view.
	 * By default, it is the simple class name of the view, residing in 
	 * the same package. 
	 */
	protected String getResourceBaseName()
	{
		return this.getClass().getCanonicalName();
	}
	
	@Override
	public void detach()
	{
		this.detachSubscription.fire(new ViewEvent<M, C, IView<M,C>>(this));
	}
	
	@Override
	public void addDetachListener(IViewListener<M, C, IView<M, C>> listener)
	{
		this.detachSubscription.add(listener);
	}

	@Override
	public void removeDetachListener(IViewListener<M, C, IView<M, C>> listener)
	{
		this.detachSubscription.remove(listener);
	}
	
//	// By default, returns true. Override to change behavior. 
//	@Override
//	public boolean isValid()
//	{
//		return true;
//	}


}
