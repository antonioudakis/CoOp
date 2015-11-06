package softeng.ui.vaadin.data;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

class FlatContainer<M>
	implements Container, Container.ItemSetChangeNotifier
{
	private static final long serialVersionUID = 1L;
	
	private BeanItemContainer<M> innerContainer;

	private int itemSetChangeBlock = 0;
	
	private Set<ItemSetChangeListener> listeners =
		new HashSet<ItemSetChangeListener>();

	public FlatContainer(Class<? super M> type,
			Collection<? extends M> collection)
	{
		this.innerContainer = new BeanItemContainer<M>(type, collection);
		this.listenToInnerContainer();
	}
	
	public FlatContainer(BeanItemContainer<M> innerContainer)
	{
		this.innerContainer = innerContainer;
		this.listenToInnerContainer();
	}
	
	@SuppressWarnings("serial")
	private void listenToInnerContainer()
	{
		this.innerContainer.addListener(new Container.ItemSetChangeListener()
		{
			@Override
			public void containerItemSetChange(ItemSetChangeEvent event)
			{
				if (itemSetChangeBlock == 0)
					fireItemSetChange(event);
			}
		});
	}

	private void fireItemSetChange(ItemSetChangeEvent event)
	{
		for (ItemSetChangeListener listener : this.listeners)
		{
			listener.containerItemSetChange(event);
		}
	}

	public void inhibitItemSetChangeEvent()
	{
		this.itemSetChangeBlock++;
	}
	
	public void allowItemSetChangeEvent()
	{
		this.itemSetChangeBlock--;
	}

	@Override
	public void addListener(ItemSetChangeListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(ItemSetChangeListener listener)
	{
		this.listeners.remove(listener);
	}

	@Override
	public BeanItem<M> getItem(Object itemId)
	{
		return this.innerContainer.getItem(itemId);
	}

	@Override
	public Collection<?> getContainerPropertyIds()
	{
		return this.innerContainer.getContainerPropertyIds();
	}

	@Override
	public Collection<?> getItemIds()
	{
		return this.innerContainer.getItemIds();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId)
	{
		return this.innerContainer.getContainerProperty(itemId, propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId)
	{
		return this.innerContainer.getType(propertyId);
	}

	@Override
	public int size()
	{
		return this.innerContainer.size();
	}

	@Override
	public boolean containsId(Object itemId)
	{
		return this.innerContainer.containsId(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException
	{
		return this.innerContainer.addItem(itemId);
	}
	
	public BeanItem<M> addBean(M bean)
	{
		return this.innerContainer.addBean(bean);
	}

	@Override
	public Object addItem() throws UnsupportedOperationException
	{
		return this.innerContainer.addItem();
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException
	{
		return this.innerContainer.removeItem(itemId);
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException
	{
		return this.innerContainer.addContainerProperty(propertyId, type, defaultValue);
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException
	{
		return this.innerContainer.removeContainerProperty(propertyId);
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException
	{
		return this.innerContainer.removeAllItems();
	}

}
