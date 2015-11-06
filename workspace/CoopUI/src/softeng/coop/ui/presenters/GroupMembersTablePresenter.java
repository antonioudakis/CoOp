package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.*;

import com.vaadin.ui.Window.Notification;

public class GroupMembersTablePresenter
extends MembersTablePresenter<Group>
{

	public GroupMembersTablePresenter(ITableView<Group, Registration> view)
	{
		super(view);
	}

	@Override
	protected boolean canAdd()
	{
		CoOp currentCoOp = getContext().getSelectedCoop();
		
		if (currentCoOp == null) return false;
		
		int candidateMembersCount;
		
		candidateMembersCount = getView().getContainer().size();

		if (candidateMembersCount >= currentCoOp.getMaxGroupSize())
		{
			getContext().showNotification(
					getLocalizedString("CANNOT_ADD_MEMBER_CAPTION"), 
					String.format(
							getLocalizedString("CANNOT_ADD_MEMBER_DESCRIPTION"), 
							currentCoOp.getMaxGroupSize()), 
					Notification.TYPE_WARNING_MESSAGE);
			
			return false;
		}
		
		return true;
	}

}
