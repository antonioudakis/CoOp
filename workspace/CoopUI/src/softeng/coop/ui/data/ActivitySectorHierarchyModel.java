package softeng.coop.ui.data;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.ui.vaadin.data.*;

public class ActivitySectorHierarchyModel
implements HierarchyModel<ActivitySector>
{
	public ActivitySectorHierarchyModel()
	{
	}

	@Override
	public ActivitySector getParent(ActivitySector item)
	{
		if (item != null) return item.getParentActivitySector();
		
		return null;
	}

	@Override
	public boolean hasChildren(ActivitySector item)
	{
		return !item.getChildActivitySectors().isEmpty();
	}

	@Override
	public List<ActivitySector> getChildren(ActivitySector item)
	{
		ArrayList<ActivitySector> list = new ArrayList<ActivitySector>(item.getChildActivitySectors());
		
		return list;
	}

	@Override
	public void addChild(ActivitySector parent, ActivitySector newChild)
	{
		throw new CoopUISystemException("Not supported");
	}

	@Override
	public boolean removeChild(ActivitySector child)
	{
		throw new CoopUISystemException("Not supported");
	}

	@Override
	public boolean moveChild(ActivitySector child, ActivitySector newParent)
	{
		// NOP.
		return false;
	}

}
