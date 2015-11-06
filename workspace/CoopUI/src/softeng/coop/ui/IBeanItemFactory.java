package softeng.coop.ui;

import com.vaadin.data.util.BeanItem;

public interface IBeanItemFactory<M>
{
	BeanItem<M> createBeanItem(M bean);
}
