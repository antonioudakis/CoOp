package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.*;

import com.vaadin.ui.Window.Notification;

public class InvitationMembersTablePresenter
extends MembersTablePresenter<Invitation>
{
	public InvitationMembersTablePresenter(ITableView<Invitation, Registration> view)
	{
		super(view);
	}

	@Override
	protected boolean canAdd()
	{
		CoOp currentCoOp = getContext().getSelectedCoop();
		
		if (currentCoOp == null) return false;
		
		int candidateMembersCount;
		
		AuthenticatedUser user = getContext().getSession().getAuthenticatedUser();
		
		if (!(user instanceof Student)) return false;
		
		Student student = (Student)user;
		
		if (student.getActiveRegistration() == null) return false;
		
		Group group = student.getActiveRegistration().getGroup();
		
		if (group == null) return false;
		
		candidateMembersCount = getView().getContainer().size() + group.getRegistrations().size();

		if (candidateMembersCount >= currentCoOp.getMaxGroupSize())
		{
			getContext().showNotification(
					getLocalizedString("CANNOT_ADD_INVITATION_CAPTION"), 
					String.format(
							getLocalizedString("CANNOT_ADD_INVITATION_DESCRIPTION"), 
							currentCoOp.getMaxGroupSize(), 
							group.getRegistrations().size()), 
					Notification.TYPE_WARNING_MESSAGE);
			
			return false;
		}
		
		return true;
	}
}
