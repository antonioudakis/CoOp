package softeng.coop.ui.presenters;

import softeng.coop.business.*;
import softeng.coop.business.jobs.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

public class JobDetailsComponentPresenter 
extends Presenter<DataItem<Job>, ICoopContext, IJobDetailsComponentView>
{
	private JobsWriterManager manager;

	public JobDetailsComponentPresenter(IJobDetailsComponentView view) 
	{
		super(view);
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

	@Override
	protected void setupView() 
	{
		Session session = getContext().getSession();
		
		if (session  == null)
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		JobsManager manager = session.getJobsManager();
		
		if (manager != null && manager.isWriteable())
		{
			this.manager = manager.getWriterManager();
		}
		
	}
	
	private void cancel()
	{
		getView().discardChanges();
	}

	private void save()
	{
		if (manager == null)
		{
			getContext().showAccessDeniedNotification();
			
			return;
		}
		
		if (getView().getModel() == null) return;
		
		Job job = getView().getModel().getBean();
		
		if (job == null) return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		manager.persistJob(job);

		getContext().showDataSavedNotification();
	}
	
	private void revert()
	{
		getContext().getSession().revertOutstanding();
		
		getView().dataBind();
	}

}
