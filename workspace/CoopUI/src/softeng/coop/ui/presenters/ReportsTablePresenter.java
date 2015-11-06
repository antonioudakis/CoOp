package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.reporting.*;

import softeng.coop.dataaccess.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

public class ReportsTablePresenter<P>
extends Presenter<Collection<Report>, ICoopContext, IReportsTableView<P>>
implements IReportsTableView.IReportCreator
{
	private class ElementExecutionVoteListener
	implements IListener<ElementExecutionVote<Report>>
	{
		@Override
		public void onEvent(ElementExecutionVote<Report> event)
		{
			if (!allowedReportTypes.contains(event.getElement().getReportType()))
			{
				getContext().showAccessDeniedNotification();
				
				event.markAsFailed();
				return;
			}
		}
	}
	
	private ElementExecutionVoteListener elementExecutionVoteListener =
		new ElementExecutionVoteListener();
	
	private ReportsWriterManager manager;
	
	private Session session;
	
	private Set<ReportType> allowedReportTypes;

	public ReportsTablePresenter(IReportsTableView<P> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		session = getContext().getSession();
		
		if (session.getReportsManager() != null && 
				session.getReportsManager().isWriteable())
		{
			manager = session.getReportsManager().getWriterManager();
			
			allowedReportTypes = 
				manager.getAllowedReportTypes(getView().getReportScope());
		}
		else
		{
			allowedReportTypes = new HashSet<ReportType>();
		}
		
		getView().addAddingListener(new IListener<ModelEvent<Report>>()
		{
			@Override
			public void onEvent(ModelEvent<Report> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Report>>()
		{
			@Override
			public void onEvent(ModelEvent<Report> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Report>>()
		{
			@Override
			public void onEvent(ModelEvent<Report> event)
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addCanAddListener(elementExecutionVoteListener);
		getView().addCanEditListener(elementExecutionVoteListener);
		getView().addCanDeleteListener(elementExecutionVoteListener);
		
	}

	protected void onAdd(Report model)
	{
		manager.markAsChanged(model);
	}

	protected void onEdit(Report model)
	{
		manager.markAsChanged(model);
		
	}

	protected void onDelete(Report model)
	{
	}

	@Override
	protected void setupView()
	{
		getView().setReportCreator(this);
	}

	@Override
	public Report createReport(ReportType reportType, Map<String, Object> parametersMap)
	{
		if (manager == null) return null;
		
		return manager.createReport(reportType, parametersMap);
	}
	
}
