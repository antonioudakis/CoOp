package softeng.ui.vaadin.mvp;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

/**
 * Event that specifies a change in an object's property.
 * Properties are monitored through BeanItem's ValueChangeNotifier interface.
 * See PropertyChangeEventSubscription.
 * @param <M> The type of instance being listened to.
 */
public class PropertyChangeEvent<M>
{
	private BeanItem<M> item;
	private Property property;
	
	PropertyChangeEvent(BeanItem<M> item, Property property)
	{
		if (item == null) 
			throw new IllegalArgumentException("Argument 'item' must not be null.");
		
		if (property == null) 
			throw new IllegalArgumentException("Argument 'property' must not be null.");
		
		this.item = item;
		this.property = property;
	}

	/**
	 * The instance where the property belongs.
	 */
	public BeanItem<M> getItem()
	{
		return item;
	}

	/**
	 * The property that changed.
	 */
	public Property getProperty()
	{
		return property;
	}
	
}
