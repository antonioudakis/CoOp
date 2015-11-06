package softeng.ui.vaadin.data;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

/**
 * Extends BeanItemContainer to allow specification of which bean fields to expose.
 * The available fields are given by method getSupportedPropertyIds, and
 * the exposed subset is defined via setContainerPropertyIds. 
 * @param <B> The type of the bean.
 */
public class RestrictedBeanItemContainer<B> extends BeanItemContainer<B>
implements IGenericSortableContainer<B>
{
	private static final long serialVersionUID = 1L;
	
	protected Set<String> propertyIds;

	protected Set<String> allPropertyIds;
	
	protected Map<String, VaadinPropertyDescriptor<B>> addedProperties;

	public RestrictedBeanItemContainer(Class<? super B> type) throws IllegalArgumentException
	{
		super(type);
		
		this.allPropertyIds = new LinkedHashSet<String>(super.getContainerPropertyIds());
		this.propertyIds = new LinkedHashSet<String>(this.allPropertyIds);
		this.addedProperties = new HashMap<String, VaadinPropertyDescriptor<B>>();
	}

	public RestrictedBeanItemContainer(Class<? super B> type, Collection<? extends B> collection) 
	throws IllegalArgumentException
	{
		super(type);

		this.allPropertyIds = new HashSet<String>(super.getContainerPropertyIds());
		this.propertyIds = new HashSet<String>(this.allPropertyIds);
		this.addedProperties = new HashMap<String, VaadinPropertyDescriptor<B>>();
		
		this.addAll(collection);
	}

	public RestrictedBeanItemContainer(
			Class<? super B> type, 
			Collection<? extends B> collection, 
			Collection<String> propertyIds) 
	throws IllegalArgumentException
	{
		this(type, collection);

		this.setContainerPropertyIds(propertyIds);
	}

	@Override
	public Collection<String> getContainerPropertyIds()
	{
		return this.propertyIds;
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId)
	{
		if (propertyId == null) 
			throw new IllegalArgumentException("Argument 'propertyId' must not be null.");
		
		if (!this.propertyIds.contains(propertyId))
			return null;
		
		return super.getContainerProperty(itemId, propertyId);
	}
	
	/**
	 * Specify a set of property IDs to be offered by this container.
	 * It must be a subset of the ones returned by getSupportedPropertyIds.
	 */
	public void setContainerPropertyIds(Collection<String> propertyIds)
	{
		if (propertyIds == null) 
			throw new IllegalArgumentException("Argument 'propertyIds' must not be null.");
		
//		Set<String> allPropertyIds = new HashSet<String>(super.getContainerPropertyIds());
		
		if (!allPropertyIds.containsAll(propertyIds))
			throw new IllegalArgumentException(
					"Not all requested property IDs are offered by the items' type");
		
		this.propertyIds = new HashSet<String>(propertyIds);
	}
	
	/**
	 * Get the set of property IDs that can be offered by this container.
	 * Initially, all of them are offered. In order to specify a subset,
	 * call setContainerPropertyIds.
	 */
	public Set<String> getSupportedPropertyIds()
	{
		return this.allPropertyIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addNestedContainerProperty(String propertyId)
	{
		VaadinPropertyDescriptor<B> descriptor =
			new NestedPropertyDescriptor<B>(
				propertyId, 
				(Class<B>) this.getBeanType());
		
		if (this.addContainerProperty(propertyId, descriptor))
		{
			this.allPropertyIds.add(propertyId);
			this.propertyIds.add(propertyId);
			this.addedProperties.put(propertyId, descriptor);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Adds a nested container property for the container, e.g. "manager.address.street". 
	 * If any intermediate getters return null 
	 * values then the property becomes null and read-only.
	 */
	@SuppressWarnings("unchecked")
	public boolean addNullableNestedContainerProperty(String propertyId)
	{
		VaadinPropertyDescriptor<B> descriptor =
			new NullableNestedPropertyDescriptor<B>(
				propertyId, 
				(Class<B>) this.getBeanType());
		
		if (this.addContainerProperty(propertyId, descriptor))
		{
			this.allPropertyIds.add(propertyId);
			this.propertyIds.add(propertyId);
			this.addedProperties.put(propertyId, descriptor);
			
			return true;
		}
		
		return false;
	}
	
	@Override
  public boolean removeContainerProperty(Object propertyId)
  throws UnsupportedOperationException
  {
		if (propertyIds.remove(propertyId))
		{
			super.removeContainerProperty(propertyId);
			this.allPropertyIds.remove(propertyId);
			this.addedProperties.remove(propertyId);
			
			return true;
		}
		
		return false;
  }

	@Override
	protected BeanItem<B> createBeanItem(B bean)
	{
		if (bean == null) return null;
		
		BeanItem<B> item = new BeanItem<B>(bean);
		
		for (VaadinPropertyDescriptor<B> addedDescriptor : addedProperties.values())
		{
			item.addItemProperty(
					addedDescriptor.getName(), 
					addedDescriptor.createProperty(bean));
		}
		
		return item;
	}

	@Override
	public void sort(Comparator<B> comparator)
	{
		if (comparator == null) 
			throw new IllegalArgumentException("Argument 'comparator' must not be null.");
		
		List<B> items = getAllItemIds();
		
		Collections.sort(items, comparator);
	}

}
