package softeng.coop.ui.presenters;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.DataItem;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

public class LessonsCardPresenter
extends Presenter<BeanItem<Department>, ICoopContext, ILessonsCardView>
{
	private CoOpsWriterManager manager;

	public LessonsCardPresenter(ILessonsCardView view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session  == null)
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
		
		getView().setModel(
				new DataItem<Department>(
						session.getAuthenticatedUser().getDepartment(), 
						this.manager));
		
		getView().dataBind();
	}

	@Override
	protected void attachToView()
	{
		
		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				save();
			}
		});
		
		getView().getOkCancelView().addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				cancel();
			}
		});
		
		getView().getOkCancelView().addOkFailedListener(new IListener<RuntimeException>()
		{
			@Override
			public void onEvent(RuntimeException event)
			{
				revert();
			}
		});
	}

	private void cancel()
	{
		getView().discardChanges();
	}

	private void save()
	{
		if (manager == null) return;
		
		if (getView().getModel() == null) return;
		
		Lesson lesson = getView().getSelectedLesson();
		
		if (lesson == null) return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		manager.persistLesson(lesson);

		getContext().showDataSavedNotification();
	}
	
	private void revert()
	{
		getContext().getSession().revertOutstanding();
		
		getView().dataBind();
	}
}
