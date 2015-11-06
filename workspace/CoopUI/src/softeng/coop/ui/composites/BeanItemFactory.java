package softeng.coop.ui.composites;

import softeng.coop.ui.IBeanItemFactory;

import com.vaadin.data.util.BeanItem;

public class BeanItemFactory<M> implements IBeanItemFactory<M>
{
	@Override
	public BeanItem<M> createBeanItem(M bean)
	{
		return new BeanItem<M>(bean);
	}
}
