package softeng.coop.ui.viewdefinitions.viewmodels;

import softeng.coop.ui.ICoopContext;


/**
 * Specifies a single tab. 
 */
public class TabSpecification
{
	private String captionKey;
	private String descriptionKey;
	private IComponentFactory componentFactory;
	private IComponentVisibility componentVisibility;
	
	private static class DefaultComponentVisibility
	implements IComponentVisibility
	{
		@Override
		public boolean isVisible(ICoopContext context)
		{
			return true;
		}
	}
	
	private static IComponentVisibility defaultComponentVisibility =
		new DefaultComponentVisibility();
	
	public TabSpecification(
			String captionKey, 
			String descriptionKey, 
			IComponentFactory componentFactory,
			IComponentVisibility componentVisibility)
	{
		if (captionKey == null) 
			throw new IllegalArgumentException("Argument 'captionKey' must not be null.");
		
		if (descriptionKey == null) 
			throw new IllegalArgumentException("Argument 'descriptionKey' must not be null.");
		
		if (componentFactory == null) 
			throw new IllegalArgumentException("Argument 'componentFactory' must not be null.");
		
		if (componentVisibility == null) 
			componentVisibility = defaultComponentVisibility;
		
		this.captionKey = captionKey;
		this.descriptionKey = descriptionKey;
		this.componentFactory = componentFactory;
		this.componentVisibility = componentVisibility;
	}
	
	public TabSpecification(
			String captionKey, 
			String descriptionKey, 
			IComponentFactory componentFactory)
	{
		this(captionKey, descriptionKey, componentFactory, null);
	}
	
	/**
	 * The resource key for the caption.
	 */
	public String getCaptionKey()
	{
		return captionKey;
	}

	/**
	 * The resource key for the description.
	 */
	public String getDescriptionKey()
	{
		return descriptionKey;
	}
	
	/**
	 * Factory that can create the component to be shown inside the tab.
	 */
	public IComponentFactory getComponentFactory()
	{
		return componentFactory;
	}
	
	/**
	 * Defines the visibility of the tab in with respect to current context.
	 */
	public IComponentVisibility getComponentVisibility()
	{
		return componentVisibility;
	}
	
}
