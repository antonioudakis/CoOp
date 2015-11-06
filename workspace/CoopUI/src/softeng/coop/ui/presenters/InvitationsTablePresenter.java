package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.ui.Window.Notification;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class InvitationsTablePresenter
extends Presenter<Collection<Invitation>, ICoopContext, IInvitationsTableView>
{
	private StudentsWriterManager manager;
	
	private static final int MAX_INVITATIONS_SENT_COUNT = 8;

	public InvitationsTablePresenter(IInvitationsTableView view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getStudentsManager() != null && session.getStudentsManager().isWriteable())
		{
			manager = session.getStudentsManager().getWriterManager();
		}
		else
		{
			getContext().showAccessDeniedNotification();
			
			return;
		}
		
		getView().addPreAddListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				canAdd(event);
			}
		});
		
		getView().addAddingListener(new IListener<ModelEvent<Invitation>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Invitation> event)
			{
				onAdd(event);
			}
		});
			
	}

	protected void canAdd(CommandExecutionVote event)
	{
		if (getView().getInvitationType() != InvitationType.SentInvitations)
		{
			event.markAsFailed();
			return;
		}
		
		Collection<Invitation> sentInvitations = getView().getModel();
		
		if (sentInvitations.size() >= MAX_INVITATIONS_SENT_COUNT)
		{
			event.markAsFailed();
			
			getContext().showNotification(
					getLocalizedString("MAX_SENT_INVITATIONS_REACHED_CAPTION"), 
					getLocalizedString("MAX_SENT_INVITATIONS_REACHED_DESCRIPTION"), 
					Notification.TYPE_WARNING_MESSAGE);
		}
		
	}

	protected void onAdd(ModelEvent<Invitation> event)
	{
		manager.postInvitation(event.getModel());
		
		getContext().showNotification(
				getLocalizedString("INVITATION_SENT_CAPTION"), 
				getLocalizedString("INVITATION_SENT_DESCRIPTION"), 
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	@Override
	protected void setupView()
	{
		
	}

}
