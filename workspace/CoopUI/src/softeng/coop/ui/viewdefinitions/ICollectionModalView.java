package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

public interface ICollectionModalView<M>
	extends IModalView<BeanItem<M>>
{
	public interface ICollectionProvider<M>
	{
		Collection<M> getContainer();
		
		M getDefault();
	}
	
	void setCollectionProvider(ICollectionProvider<M> collectionProvider);
	ICollectionProvider<M> getCollectionProvider();
}
