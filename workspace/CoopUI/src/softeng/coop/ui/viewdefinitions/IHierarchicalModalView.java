package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.*;
import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public interface IHierarchicalModalView<M> 
	extends IView<M, ICoopContext>
{
	public interface IHierarchyProvider<M>
	{
		HierarchicalBeanItemContainer<M> getHierarchy();
		M getDefault();
	}
	
	void setHierarchyProvider(IHierarchyProvider<M> hierarchyProvider);
	IHierarchyProvider<M> getHierarchyProvider();
}
