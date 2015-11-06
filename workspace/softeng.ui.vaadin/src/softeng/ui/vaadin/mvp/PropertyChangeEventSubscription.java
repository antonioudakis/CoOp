package softeng.ui.vaadin.mvp;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.*;

/**
 * Event subscription specialized for monitoring property changes of
 * instances. The monitoring is accomplished via listening to ValueChange 
 * events originating from a BeanItem containing the instance.  
 * @param <M> The type of instance being monitored.
 */
public class PropertyChangeEventSubscription<M>
	extends EventSubscription<PropertyChangeEvent<M>, IListener<PropertyChangeEvent<M>>>
{
	private class PropertyValueChangeListener 
		implements Property.ValueChangeListener
	{
		private static final long serialVersionUID = 1L;

		private BeanItem<M> item;
		
		public PropertyValueChangeListener(BeanItem<M> item)
		{
			this.item = item;
		}
		
		@Override
		public void valueChange(ValueChangeEvent event)
		{
			PropertyChangeEvent<M> propertyChangeEvent = 
				new PropertyChangeEvent<M>(item, event.getProperty());
			
			fire(propertyChangeEvent);
		}
	}
	
	private Hashtable<BeanItem<M>, PropertyValueChangeListener> valueChangeListenersDictionary;
	
	public PropertyChangeEventSubscription()
	{
		valueChangeListenersDictionary =
			new Hashtable<BeanItem<M>, PropertyValueChangeListener>();
	}

	/**
	 * Start listening to changes in the properties of an instance.
	 * @param item The item wrapper of the instance.
	 */
	public void startListeningTo(BeanItem<M> item)
	{
		if (item == null) 
			throw new IllegalArgumentException("Argument 'item' must not be null.");
		
		// I we are already listening to this instance, do nothing. 
		if (this.valueChangeListenersDictionary.containsKey(item)) return;
		
		PropertyValueChangeListener propertyValueChangeListener =
			new PropertyValueChangeListener(item);
		
		this.valueChangeListenersDictionary.put(item, propertyValueChangeListener);
		
		for (Object propertyId : item.getItemPropertyIds())
		{
			Property property = item.getItemProperty(propertyId);
			
			if (!(property instanceof Property.ValueChangeNotifier)) continue;

			Property.ValueChangeNotifier notifier = 
				(Property.ValueChangeNotifier)property;

			notifier.addListener(propertyValueChangeListener);
		}
	}
	
	/**
	 * Stop listening to changes in the properties of an instance.
	 * @param item The item wrapper of the instance.
	 */
	public void stopListeningTo(BeanItem<M> item)
	{
		if (item == null) 
			throw new IllegalArgumentException("Argument 'item' must not be null.");
		
		// I we were not listening to this instance, do nothing. 
		PropertyValueChangeListener propertyValueChangeListener =
			this.valueChangeListenersDictionary.get(item);
		
		if (propertyValueChangeListener == null) return;
		
		for (Object propertyId : item.getItemPropertyIds())
		{
			Property property = item.getItemProperty(propertyId);
			
			if (!(property instanceof Property.ValueChangeNotifier)) continue;

			Property.ValueChangeNotifier notifier = 
				(Property.ValueChangeNotifier)property;

			notifier.removeListener(propertyValueChangeListener);
		}
	}
}
