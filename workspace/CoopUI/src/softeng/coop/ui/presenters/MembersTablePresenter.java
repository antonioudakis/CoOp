package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

/**
 * Base class shared by presenters for lists of registration members.
 * The derived presenters are GroupMembersTablePresenter for group members 
 * and InvitationsMembersTablePresenters for recepients of invitations.
 * @param <P> The type of the parent of the registrations collection. Typically it is Group or Invitation.
 */
public abstract class MembersTablePresenter<P> 
	extends Presenter<Collection<Registration>, ICoopContext, ITableView<P, Registration>> 
{
	public MembersTablePresenter(ITableView<P, Registration> view) 
	{
		super(view);
	}

	@Override
	protected void attachToView() 
	{
		getView().addAddingListener(new IListener<ModelEvent<Registration>>() 
		{
			@Override
			public void onEvent(ModelEvent<Registration> event) 
			{
				onAdd(event.getModel());
				
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Registration>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Registration> event)
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addPreAddListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				if (!canAdd())
				{
					event.markAsFailed();
				}
			}
		});
		
	}

	protected void onDelete(Registration model) 
	{
		// NOP.
	}

	protected void onAdd(Registration model) 
	{
		if (model.getGroup() != null)
		{
			if (model.getGroup().getJob() != null)
			{
				getView().getContext().getMainWindow()
					.showNotification(getView()
							.getLocalizedString("ALREADY_HAS_JOB_MESSAGE")
							, Window.Notification.TYPE_HUMANIZED_MESSAGE);
			}
		}
		
	}

	@Override
	protected void setupView() 
	{
	}
	
	protected abstract boolean canAdd();

}
