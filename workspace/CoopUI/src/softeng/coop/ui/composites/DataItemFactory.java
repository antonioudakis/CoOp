package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.ui.IBeanItemFactory;
import softeng.coop.ui.data.DataItem;

public class DataItemFactory<M> implements IBeanItemFactory<M>
{
	private ManagerBase manager;
	
	public DataItemFactory(ManagerBase manager)
	{
		if (manager == null) 
			throw new IllegalArgumentException("Argument 'manager' must not be null.");
		
		this.manager = manager;
	}

	@Override
	public BeanItem<M> createBeanItem(M bean)
	{
		return new DataItem<M>(bean, this.manager);
	}

}
