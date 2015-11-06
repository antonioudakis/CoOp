package softeng.ui.vaadin.data;

import java.util.*;

public interface HierarchyModel<M>
{
	M getParent(M item);

	boolean hasChildren(M item);
	
	List<M> getChildren(M item);
	
	void addChild(M parent, M newChild);
	
	boolean removeChild(M child);
	
	boolean moveChild(M child, M newParent);
}
