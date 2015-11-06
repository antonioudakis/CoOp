package softeng.ui.vaadin.data;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

public class HierarchicalBeanItemContainer<M> 
	implements Container.Hierarchical, Container.ItemSetChangeNotifier
{
	private static final long serialVersionUID = 1L;

	private HierarchyModel<M> hierarchyModel;
	private List<M> rootItems;
	
	private FlatContainer<M> flatContainer;

	public HierarchicalBeanItemContainer(
			Class<? super M> type,
			Collection<? extends M> rootBeans,
			HierarchyModel<M> hierarchyModel)
	
	{
		this(new BeanItemContainer<M>(type, rootBeans), hierarchyModel);
	}

	public HierarchicalBeanItemContainer(
			BeanItemContainer<M> rootItems,
			HierarchyModel<M> hierarchyModel)
	{
		if (hierarchyModel == null)
			throw new IllegalArgumentException("hierarchyModel must not be null.");
		
		this.flatContainer = new FlatContainer<M>(rootItems);
		this.hierarchyModel = hierarchyModel;
		
		this.rootItems = new ArrayList<M>(rootItems.getItemIds());
	}
	
	@Override
	public Item getItem(Object itemId)
	{
		return this.flatContainer.getItem(itemId);
	}

	@Override
	public Collection<?> getContainerPropertyIds()
	{
		return this.flatContainer.getContainerPropertyIds();
	}

	@Override
	public Collection<?> getItemIds()
	{
		return this.flatContainer.getItemIds();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId)
	{
		return this.flatContainer.getContainerProperty(itemId, propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId)
	{
		return this.flatContainer.getType(propertyId);
	}

	@Override
	public int size()
	{
		return this.flatContainer.size();
	}

	@Override
	public boolean containsId(Object itemId)
	{
		return this.flatContainer.containsId(itemId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException
	{
		if (itemId == null)
			throw new IllegalArgumentException("Argument itemId must not be null.");

		this.rootItems.add((M)itemId);
		return this.flatContainer.addItem(itemId);
	}

	@Override
	public Object addItem() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<?> getChildren(Object itemId)
	{
		if (itemId == null)
			throw new IllegalArgumentException("Argument itemId must not be null.");

		BeanItem<M> item = this.flatContainer.getItem(itemId);
		
		if (item == null) return null;
		
		List<M> children = this.hierarchyModel.getChildren(item.getBean());
		
		try
		{
			this.flatContainer.inhibitItemSetChangeEvent();
			
			for (M child : children)
			{
				this.flatContainer.addBean(child);
			}
		}
		finally
		{
			this.flatContainer.allowItemSetChangeEvent();
		}
		
		return children;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getParent(Object itemId)
	{
		if (itemId == null)
			throw new IllegalArgumentException("Argument itemId must not be null.");

		return this.hierarchyModel.getParent((M)itemId);
	}

	@Override
	public Collection<?> rootItemIds()
	{
		return this.rootItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setParent(Object itemId, Object newParentId)
			throws UnsupportedOperationException
	{
		if (itemId == null) 
			throw new IllegalArgumentException("Argument itemId must not be null.");

		if (newParentId != null && !this.flatContainer.containsId(newParentId)) 
			return false;
		
		M child = (M)itemId;
		M newParent = (M)newParentId;
		M oldParent = this.hierarchyModel.getParent(child);
		
		if (!this.hierarchyModel.moveChild(child, newParent)) return false;
		
		if (oldParent == null)
		{
			this.rootItems.remove(child);
		}
		
		if (newParent == null)
		{
			this.rootItems.add(child);
		}
		
		this.flatContainer.removeItem(child);
		//if (!this.flatContainer.containsId(child))
		this.flatContainer.addBean(child);
		
		return true;
	}

	@Override
	public boolean areChildrenAllowed(Object itemId)
	{
		return true;
	}

	@Override
	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
			throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRoot(Object itemId)
	{
		BeanItem<M> item = this.flatContainer.getItem(itemId);
		
		if (item == null) return false;
		
		return this.hierarchyModel.getParent(item.getBean()) == null;
	}

	@Override
	public boolean hasChildren(Object itemId)
	{
		BeanItem<M> item = this.flatContainer.getItem(itemId);
		
		if (item == null) return false;
		
		return this.hierarchyModel.hasChildren(item.getBean());
		
		//return !this.hierarchyModel.getChildren(item.getBean()).isEmpty();
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException
	{
		BeanItem<M> item = this.flatContainer.getItem(itemId);
		
		if (item == null) return false;
		
		boolean removeSuccess = this.hierarchyModel.removeChild(item.getBean());
		
		if (removeSuccess) this.flatContainer.removeItem(itemId);
		
		return removeSuccess;
	}

	@Override
	public void addListener(ItemSetChangeListener listener)
	{
		this.flatContainer.addListener(listener);
	}

	@Override
	public void removeListener(ItemSetChangeListener listener)
	{
		this.flatContainer.removeListener(listener);
	}
	
	/**
	 * Add a new object to the hierarchical container.
	 * @param parent The parent of the new object or null if the item is root.
	 * @param child The new object.
	 */
	public void addItem(M parent, M child)
	{
		if (child == null) 
			throw new IllegalArgumentException("Argument 'child' must not be null.");
		
		// Main implementation. Robust enough to handle already added items.
//			this.setParent(child, parent);

		// Alternative implementation. 
		// Does not handle already added items correctly.
		if (parent != null)
		{
			this.hierarchyModel.addChild(parent, child);
		}
		else
		{
			this.rootItems.add(child);
		}
		
		if (!this.flatContainer.containsId(child))
			this.flatContainer.addBean(child);

	}

}
