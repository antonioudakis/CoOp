package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.ui.Window.Notification;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;
import softeng.coop.business.coops.*;
import softeng.coop.business.jobpostings.JobPostingsManager;
import softeng.coop.business.jobpostings.JobPostingsSearchCriteria;

import softeng.ui.vaadin.mvp.*;

public class CompaniesSelectCardPresenter
extends Presenter<DataItemContainer<Company>, ICoopContext, ICompaniesSelectView>
{
	private final class SelectedCoopChangeListener implements IListener<CoOp>
	{
		@Override
		public void onEvent(CoOp event)
		{
			bindCoopCompanies();
		}
	}

	private CoOpsWriterManager coopsManager;
	
	private CompaniesManager companiesManager;
	
	private SelectedCoopChangeListener selectedCoopChangeListener;
	
	private static ArrayList<String> companyPropertyIds =
		getCompanyPropertyIds();
	
	private static String[] defaultSortFields = { "name" };
	private static boolean[] defaultSortDirections = { true };

	public CompaniesSelectCardPresenter(ICompaniesSelectView view)
	{
		super(view);
		
		selectedCoopChangeListener = new SelectedCoopChangeListener();
	}

	private static ArrayList<String> getCompanyPropertyIds()
	{
		ArrayList<String> propertyIds = new ArrayList<String>();
		
		propertyIds.add("name");
		propertyIds.add("taxCode");
		propertyIds.add("category.name");
		
		return propertyIds;
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getCoOpsManager() == null || 
				!session.getCoOpsManager().isWriteable() ||
				session.getCompaniesManager() == null)
		{
			getContext().showAccessDeniedNotification();
			
			return;
		}
		
		coopsManager = session.getCoOpsManager().getWriterManager();
		
		companiesManager = session.getCompaniesManager();
		
		getView().addSearchListener(new IViewListener<DataItemContainer<Company>, ICoopContext, ICompaniesSelectView>()
		{
			
			@Override
			public void onEvent(ViewEvent<DataItemContainer<Company>, ICoopContext, ICompaniesSelectView> event)
			{
				onSearch();
			}
		});
		
		getView().addAddCompanyListener(new IListener<ModelEvent<Company>>()
		{
			@Override
			public void onEvent(ModelEvent<Company> event)
			{
				onAddCompany(event.getModel());
			}
		});
		
		getView().addRemoveCompanyListener(new IListener<ModelEvent<Company>>()
		{
			@Override
			public void onEvent(ModelEvent<Company> event)
			{
				onRemoveCompany(event.getModel());
			}
		});
		
		getView().addCanRemoveCompanyListener(new IListener<ElementExecutionVote<Company>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Company> vote)
			{
				onCanRemoveCompany(vote);
			}
		});
		
		// Listen to global coop change events
		getContext().addSelectedCoopChangedListener(selectedCoopChangeListener);
	}

	protected void onCanRemoveCompany(ElementExecutionVote<Company> vote)
	{
		CoOp selectedCoop = getContext().getSelectedCoop();
		
		if (selectedCoop == null || vote.getElement() == null)
		{
			vote.markAsFailed();
			return;
		}
		
		// Does the company have any job postings defined for this coop?
		
		JobPostingsManager jobPostingsManager = getContext().getSession().getJobPostingsManager();
		
		if (jobPostingsManager != null)
		{
			
			JobPostingsSearchCriteria jobPostingsSearchCriteria = new JobPostingsSearchCriteria();
			
			jobPostingsSearchCriteria.setCoop(selectedCoop);
			jobPostingsSearchCriteria.setCompany(vote.getElement());
			jobPostingsSearchCriteria.setPageSize(1);
			
			SearchResult<JobPosting> jobPostingsSearchResult = 
				jobPostingsManager.searchJobPostings(jobPostingsSearchCriteria);
			
			if (jobPostingsSearchResult.getList().size() > 0)
			{
				getContext().showNotification(
						getLocalizedString("COMPANY_HAS_JOB_POSTINGS_CAPTION"), 
						getLocalizedString("COMPANY_HAS_JOB_POSTINGS_DESCRIPTION"), 
						Notification.TYPE_ERROR_MESSAGE);
				
				vote.markAsFailed();
				
				return;
			}
		}
		
	}

	protected void onRemoveCompany(Company company)
	{
		CoOp selectedCoop = getContext().getSelectedCoop();
		
		if (coopsManager != null && selectedCoop != null)
		{
			TransactionScope transactionScope = coopsManager.beginTransaction();
			
			try
			{
				selectedCoop.getCompanies().remove(company);
	
				coopsManager.persistCoOp(selectedCoop);
				
				transactionScope.commit();
			}
			finally
			{
				transactionScope.dispose();
			}
			
			getContext().showDataSavedNotification();
		}
	}

	protected void onAddCompany(Company company)
	{
		CoOp selectedCoop = getContext().getSelectedCoop();
		
		if (coopsManager != null && selectedCoop != null)
		{
			selectedCoop.getCompanies().add(company);

			coopsManager.persistCoOp(selectedCoop);
			
			getContext().showDataSavedNotification();
		}
	}
	
	protected void onSearch()
	{
		String companyName = 
			(String)getView().getCompanyNameSearchField().getValue();
		
		Category category = getView().getCategoriesView().getSelectedValue();
		
		CompanySearchCriteria criteria = new CompanySearchCriteria();
		
		criteria.setCategory(category);
		
		if (companyName != null && companyName.length() > 0)
			criteria.setCompanyName(companyName);
		
		SearchResult<Company> result = companiesManager.searchCompanies(criteria);
		
		DataItemContainer<Company> container = 
			new DataItemContainer<Company>(
					Company.class, 
					result.getList(), 
					companiesManager);
		
		container.addNestedContainerProperty("category.name");
		
		container.setContainerPropertyIds(companyPropertyIds);
		
		container.sort(defaultSortFields, defaultSortDirections);

		getView().setFoundCompanies(container);
	}

	@Override
	protected void setupView()
	{
		bindCoopCompanies();
		onSearch();
	}
	
	private void bindCoopCompanies()
	{
		if (coopsManager == null) return;
		
		CoOp coop = getContext().getSelectedCoop();
		
		if (coop != null)
		{
			DataItemContainer<Company> companiesContainer = 
				new DataItemContainer<Company>(
						Company.class, 
						coop.getCompanies(), 
						coopsManager);
			
			companiesContainer.addNestedContainerProperty("category.name");
			
			companiesContainer.setContainerPropertyIds(companyPropertyIds);
			
			companiesContainer.sort(defaultSortFields, defaultSortDirections);
			
			getView().setModel(companiesContainer);
		}
		else
		{
			getView().setModel(null);
		}
		
		getView().dataBind();
		
	}
	
	@Override
	protected void detachFromView()
	{
		getContext().removeSelectedCoopChangedListener(selectedCoopChangeListener);
	}

}
