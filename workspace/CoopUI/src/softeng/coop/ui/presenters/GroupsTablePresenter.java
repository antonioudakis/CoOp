package softeng.coop.ui.presenters;

import java.util.Collection;

import softeng.coop.business.Session;
import softeng.coop.business.students.StudentsManager;
import softeng.coop.business.students.StudentsWriterManager;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Group;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.ITableView;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;
import softeng.ui.vaadin.mvp.IListener;
import softeng.ui.vaadin.mvp.ModelEvent;
import softeng.ui.vaadin.mvp.Presenter;

public class GroupsTablePresenter extends Presenter<Collection<Group>, ICoopContext, ITableView<CoOp, Group>> 
{
	StudentsWriterManager manager;
	
	public GroupsTablePresenter(ITableView<CoOp, Group> view) 
	{
		super(view);
	}

	@Override
	protected void attachToView() 
	{
		getView().addAddingListener(new IListener<ModelEvent<Group>>() 
		{
			
			@Override
			public void onEvent(ModelEvent<Group> event) 
			{
				onAdd(event.getModel());
				
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Group>>() 
		{
			
			@Override
			public void onEvent(ModelEvent<Group> event) 
			{
				onDelete(event.getModel());
			}
		});
				
		getView().addEditingListener(new IListener<ModelEvent<Group>>() 
		{
			
			@Override
			public void onEvent(ModelEvent<Group> event) 
			{
				onEdit(event.getModel());
				
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<Group>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Group> event)
			{
				canDelete(event);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<Group> event)
	{
		Group group = event.getElement();
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (!group.getRegistrations().isEmpty())
		{
			appender.add("GROUP_CONTAINS_REGISTRATIONS_CAPTION");
			
			event.markAsFailed();
		}
		if (group.getJob() != null)
		{
			appender.add("GROUP_IS_ASSIGNED_TO_JOB_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!appender.isEmpty())
		{
			getContext().showEntityInUseNotification(appender.toString());
		}
	}

	protected void onEdit(Group model) 
	{
		manager.markAsChanged(model);
		
	}

	protected void onDelete(Group model) 
	{
		try
		{
			if (model.getId() > 0) manager.deleteGroup(model);
			
			getContext().showDataSavedNotification();
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}
		
	}

	protected void onAdd(Group model) 
	{
		manager.markAsChanged(model);
		
	}

	@Override
	protected void setupView() 
	{
		if (getContext() == null) return;
		
		Session session = getContext().getSession();
		
		if (session == null)
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		StudentsManager studentManager = session.getStudentsManager();
		
		if (studentManager == null || !studentManager.isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		this.manager = studentManager.getWriterManager();
	}

}
