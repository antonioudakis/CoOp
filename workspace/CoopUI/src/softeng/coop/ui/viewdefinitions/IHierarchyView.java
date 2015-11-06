package softeng.coop.ui.viewdefinitions;

import com.vaadin.event.dd.*;
import com.vaadin.ui.Tree.TreeDragMode;

import softeng.coop.ui.*;
import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public interface IHierarchyView<M>
extends ISelectable<M>, IView<HierarchicalBeanItemContainer<M>, ICoopContext>
{
	DropHandler getDropHandler();
	void setDropHandler(DropHandler dropHandler);
	
	TreeDragMode getDragMode();
	void setDragMode(TreeDragMode dragMode);
	
	void expand(M element);
}
