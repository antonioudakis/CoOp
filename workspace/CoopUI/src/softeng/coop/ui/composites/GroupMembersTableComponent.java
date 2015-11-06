package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.presenters.GroupMembersTablePresenter;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.viewdefinitions.*;

@SuppressWarnings("serial")
public class GroupMembersTableComponent 
	extends MembersTableComponent<Group> 
{
	private boolean removingOrphanGroups;
	
	private ITableView<CoOp, Group> groupsTableView;
	
	private Set<Group> orphanGroups;
	
	public GroupMembersTableComponent() 
	{
		this.removingOrphanGroups = false;
		this.groupsTableView = null;
		this.orphanGroups = new HashSet<Group>();
	}
	
	@Override
	protected void addToParent(Registration addedItem) 
	{
		Group previousParent = addedItem.getGroup();
		
		Group parent = getParentModel();
		
		if (previousParent != null && previousParent != parent)
		{
			previousParent.getRegistrations().remove(addedItem);
			
			if (removingOrphanGroups && groupsTableView != null && previousParent.getRegistrations().size() == 0)
			{
				orphanGroups.add(previousParent);
			}
		}
		
		addedItem.setGroup(parent);
	}
	
	@Override
	protected void removeFromParent(Registration removedItem)
	{
		removedItem.setGroup(null);
	}

	@Override
	protected Presenter<Collection<Registration>, ICoopContext, ? extends IView<Collection<Registration>, ICoopContext>> supplyPresenter()
	{
		return new GroupMembersTablePresenter(this);
	}

	@Override
	protected Collection<Registration> getExcludedRegistrations()
	{
		return getContainer().getItemIds();
	}

	public void setRemovingOrphanGroups(boolean removingOrphanGroups)
	{
		this.removingOrphanGroups = removingOrphanGroups;
	}

	public boolean isRemovingOrphanGroups()
	{
		return removingOrphanGroups;
	}

	public void setGroupsTableView(ITableView<CoOp, Group> groupsTableView)
	{
		this.groupsTableView = groupsTableView;
	}

	public ITableView<CoOp, Group> getGroupsTableView()
	{
		return groupsTableView;
	}

	@Override
	public void commit()
	{
		super.commit();
		
		try
		{
			if (removingOrphanGroups && groupsTableView != null)
			{
				for (Group orphanGroup : orphanGroups)
				{
					if (orphanGroup.getJob() == null)
					{
						groupsTableView.deleteElement(orphanGroup);
					}
				}
			}
		}
		finally
		{
			orphanGroups.clear();
		}
	}

}
