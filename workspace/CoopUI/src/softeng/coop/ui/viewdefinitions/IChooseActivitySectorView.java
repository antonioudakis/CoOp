package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public interface IChooseActivitySectorView
extends IModalView<BeanItem<ActivitySector>>
{
	/**
	 * The activity sector code currently entered.
	 */
	String getCurrentCode();
	
	void addSearchListener(IViewListener<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> listener);
	void removeSearchListener(IViewListener<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> listener);
	
	HierarchicalBeanItemContainer<ActivitySector> getHierarchicalContainer();
	void setHierarchicalContainer(HierarchicalBeanItemContainer<ActivitySector> container);
}
