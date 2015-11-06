package softeng.coop.ui.data;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.Multilingual;
import softeng.ui.vaadin.data.*;

public class DataItemContainer<T> extends RestrictedBeanItemContainer<T>
{
	private static final long serialVersionUID = 1L;
	
	private ManagerBase manager;

	public DataItemContainer(Class<? super T> type, ManagerBase manager) throws IllegalArgumentException
	{
		super(type);
		
		if (manager == null) 
			throw new IllegalArgumentException("Argument 'manager' must not be null.");
		
		this.manager = manager;
	}

	public DataItemContainer(Class<? super T> type, Collection<? extends T> collection, ManagerBase manager) 
	{
		super(type);

		if (manager == null) 
			throw new IllegalArgumentException("Argument 'manager' must not be null.");
		
		this.manager = manager;
		
		this.addAll(collection);
	}

	public DataItemContainer(
			Class<? super T> type, 
			Collection<? extends T> collection, 
			ManagerBase manager,
			Collection<String> propertyIds) 
	{
		this(type, collection, manager);

		setContainerPropertyIds(propertyIds);
	}

	@Override
	protected BeanItem<T> createBeanItem(T bean)
	{
		if (bean == null) return null;
		
		DataItem<T> item = new DataItem<T>(bean, manager);
		
		for (VaadinPropertyDescriptor<T> addedDescriptor : addedProperties.values())
		{
			item.addItemProperty(
					addedDescriptor.getName(), 
					addedDescriptor.createProperty(bean));
		}
		
		return item;
	}
	
  protected Collection<?> getSortablePropertyIds() 
  {
    LinkedList<Object> sortables = new LinkedList<Object>();
    
    for (Object propertyId : getContainerPropertyIds()) 
    {
			Class<?> propertyType = getType(propertyId);
			if (Comparable.class.isAssignableFrom(propertyType) ||
					Multilingual.class.isAssignableFrom(propertyType) || 
					propertyType.isPrimitive()) 
			{
				sortables.add(propertyId);
			}
    }
    
    return sortables;
  }

}
