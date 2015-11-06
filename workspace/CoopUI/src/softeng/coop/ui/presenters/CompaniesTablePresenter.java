package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

public class CompaniesTablePresenter
extends Presenter<Collection<Company>, ICoopContext, ITableView<Category, Company>>
{
	private CompaniesWriterManager manager;

	public CompaniesTablePresenter(ITableView<Category, Company> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		getView().addAddingListener(new IListener<ModelEvent<Company>>()
		{
			@Override
			public void onEvent(ModelEvent<Company> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Company>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Company> event)
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Company>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Company> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<Company>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Company> event)
			{
				canDelete(event);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<Company> event)
	{
		Company company = event.getElement();
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (!company.getCoOps().isEmpty())
		{
			appender.add("COMPANY_USED_BY_COOP_CAPTION");
			
			event.markAsFailed();
		}
		
		boolean companyParticipatesInJobs = company.getJobPostings().size() > 0;
		
		if (!companyParticipatesInJobs)
		{
			for (Branch branch : company.getBranches())
			{
				if (!branch.getJobParts().isEmpty() || !branch.getJobPostingParts().isEmpty())
				{
					companyParticipatesInJobs = true;
					break;
				}
			}
		}
		
		if (companyParticipatesInJobs)
		{
			appender.add("COMPANY_PARTICIPATES_IN_JOBS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!appender.isEmpty())
		{
			getContext().showEntityInUseNotification(appender.toString());
		}
		
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

	protected void onDelete(Company company)
	{
		try
		{
			if (company.getId() > 0) manager.deleteCompany(company);
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}
		
		getContext().showDataSavedNotification();
	}

	protected void onEdit(Company company)
	{
		manager.markAsChanged(company);
	}

	protected void onAdd(Company company)
	{
		manager.markAsChanged(company);
	}

}
