package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.coops.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.ITableView;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

public class LessonsTablePresenter
extends Presenter<Collection<Lesson>, ICoopContext, ITableView<Department, Lesson>>
{
	private CoOpsWriterManager manager;

	public LessonsTablePresenter(ITableView<Department, Lesson> view)
	{
		super(view);
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
		
		CoOpsManager manager = session.getCoOpsManager();
		
		if (manager == null || !manager.isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		this.manager = manager.getWriterManager();
			
	}

	@Override
	protected void attachToView()
	{
		
		
		getView().addAddingListener(new IListener<ModelEvent<Lesson>>()
		{
			@Override
			public void onEvent(ModelEvent<Lesson> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Lesson>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Lesson> event)
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Lesson>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Lesson> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<Lesson>>()
		{
			
			@Override
			public void onEvent(ElementExecutionVote<Lesson> vote)
			{
				canDelete(vote);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<Lesson> vote)
	{
		Lesson lesson = vote.getElement();
		
		if (!lesson.getCoOps().isEmpty())
		{
			getContext().showEntityInUseNotification(
					getLocalizedString("LESSON_HAS_COOPS_CAPTION"));
			
			vote.markAsFailed();
		}
		
	}

	protected void onDelete(Lesson lesson)
	{
		try
		{
			if (lesson.getId() > 0) manager.deleteLesson(lesson);
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}
	}

	protected void onEdit(Lesson lesson)
	{
		manager.markAsChanged(lesson);
	}

	protected void onAdd(Lesson lesson)
	{
		manager.markAsChanged(lesson);
	}

}
