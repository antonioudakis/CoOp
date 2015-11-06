package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

public class CompaniesCardPresenter
extends Presenter<HierarchicalBeanItemContainer<Category>, ICoopContext, ICompaniesCardView>
{
	private CompaniesWriterManager manager;

	public CompaniesCardPresenter(ICompaniesCardView view)
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
		
		if (session.getCompaniesManager() == null || 
				!session.getCompaniesManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		manager = session.getCompaniesManager().getWriterManager();
	}

	private void cancel()
	{
		getView().discardChanges();
	}

	private void save()
	{
		if (manager == null) return;
		
		Company lesson = getView().getSelectedCompany();
		
		if (lesson == null) return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		manager.persistCompany(lesson);

		getContext().showDataSavedNotification();
	}
	
	private void revert()
	{
		getContext().getSession().revertOutstanding();
		
		if (getView().getSelectedCompany().getId() == 0)
			getView().dataBind();
	}
}
