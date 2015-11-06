package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import com.vaadin.ui.Window.Notification;

public class InvitationsBoxPresenter
extends StudentRegistrationPresenter<IInvitationsBoxView>
{

	public InvitationsBoxPresenter(IInvitationsBoxView view)
	{
		super(view);
	}
	
	@Override
	protected void attachToView()
	{
		super.attachToView();
		
		getView().addAcceptInvitationListener(new IListener<ElementExecutionVote<Invitation>>()
		{
			
			@Override
			public void onEvent(ElementExecutionVote<Invitation> event)
			{
				onAcceptInvitation(event);
			}
		});
	}

	protected void onAcceptInvitation(ElementExecutionVote<Invitation> event)
	{
		if (getView().getInvitationType() == InvitationType.SentInvitations)
		{
			event.markAsFailed();
			
			return;
		}
		
		if (getView().getModel() == null)
		{
			event.markAsFailed();
			
			return;
		}
		
		Invitation invitation = event.getElement();
		
		if (invitation == null)
		{
			event.markAsFailed();
			
			return;
		}
		
		Registration registration = getView().getModel().getBean();
		
		Group group = invitation.getGroup();
		
		if (group == registration.getGroup())
		{
			getContext().showNotification(
					getLocalizedString("ALREADY_MEMBER_CAPTION"), 
					getLocalizedString("ALREADY_MEMBER_DESCRIPTION"), 
					Notification.TYPE_WARNING_MESSAGE);

			event.markAsFailed();
			
			return;
		}
		
		CoOp coop = group.getCoOp();
		
		Session session = getContext().getSession();
		
		session.refreshEntity(group);
		
		if (group.getRegistrations().size() >= coop.getMaxGroupSize())
		{
			getContext().showNotification(
					getLocalizedString("GROUP_IS_FULL_CAPTION"), 
					getLocalizedString("GROUP_IS_FULL_DESCRIPTION"), 
					Notification.TYPE_ERROR_MESSAGE);
			
			event.markAsFailed();
			
			return;
		}
		
		try
		{
			manager.acceptInvitation(registration, invitation);
		}
		catch (BusinessRuleViolationException ex)
		{
			event.markAsFailed();
			
			getContext().showNotification(
					getLocalizedString("ACCEPT_FAILED_CAPTION"), 
					ex.getMessage(), 
					Notification.TYPE_ERROR_MESSAGE);
			
			session.revertOutstanding();
			
			getView().dataBind();
		}
		catch (RuntimeException ex)
		{
			session.revertOutstanding();
			
			getView().dataBind();
			
			throw ex;
		}
		
		getContext().showNotification(
				getLocalizedString("INVITATION_ACCEPTED_CAPTION"), 
				getLocalizedString("INVITATION_ACCEPTED_DESCRIPTION"), 
				Notification.TYPE_HUMANIZED_MESSAGE);
		
	}

}
