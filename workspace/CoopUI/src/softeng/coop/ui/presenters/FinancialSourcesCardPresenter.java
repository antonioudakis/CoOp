package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.payments.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;

import softeng.ui.vaadin.mvp.*;

public class FinancialSourcesCardPresenter
extends Presenter<Collection<FinancialSource>, ICoopContext, IFinancialSourcesCardView>
{
	private PaymentsWriterManager manager;
	
	public FinancialSourcesCardPresenter(IFinancialSourcesCardView view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getPaymentsManager() == null ||
				!session.getPaymentsManager().isWriteable())
		{
			throw new CoopUIAccessDeniedException(getContext());
		}
		
		manager = session.getPaymentsManager().getWriterManager();
		
		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote vote)
			{
				onSave(vote);
			}
		});
		
		getView().getOkCancelView().addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				onCancel();
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

	protected void revert()
	{
		getContext().getSession().revertOutstanding();
		
		getView().dataBind();
	}

	protected void onSave(CommandExecutionVote vote)
	{
		FinancialSource financialSource = getView().getSelectedFinancialSource();
		
		if (financialSource == null) return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			vote.markAsFailed();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		manager.persistFinancialSource(financialSource);
		
		getContext().showDataSavedNotification();
	}

	protected void onCancel()
	{
		getView().discardChanges();
	}

	@Override
	protected void setupView()
	{
		if (manager == null) return;
		
		getView().setModel(manager.getFinancialSources());
		
		getView().dataBind();
	}
}
