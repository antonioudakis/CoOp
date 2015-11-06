package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.*;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class InvitationMembersTableComponent
extends MembersTableComponent<Invitation>
{

	@Override
	protected void addToParent(Registration item)
	{
		Session session = getContext().getSession();
		
		if (session.isLoaded(item, "receivedInvitations"))
			item.getReceivedInvitations().add(getParentModel());
	}


	@Override
	protected void removeFromParent(Registration item)
	{
		Session session = getContext().getSession();
		
		if (session.isLoaded(item, "receivedInvitations"))
			item.getReceivedInvitations().remove(getParentModel());
	}

	@Override
	protected Presenter<Collection<Registration>, ICoopContext, ? extends IView<Collection<Registration>, ICoopContext>> supplyPresenter()
	{
		return new InvitationMembersTablePresenter(this);
	}

	@Override
	protected Collection<Registration> getExcludedRegistrations()
	{
		Invitation invitation = getParentModel();
		
		if (invitation != null)
		{
			HashSet<Registration> excludedRegistrations = new HashSet<Registration>();
			
			excludedRegistrations.addAll(getContainer().getItemIds());
			
			Registration registration = invitation.getSender();
			
			if (registration != null && registration.getGroup() != null)
			{
				excludedRegistrations.addAll(registration.getGroup().getRegistrations());
			}
			
			return excludedRegistrations;
		}

		return getContainer().getItemIds();
	}
}
