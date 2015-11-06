package softeng.coop.ui.presenters;

import com.vaadin.data.util.BeanItem;

import softeng.coop.business.SearchResult;
import softeng.coop.business.Session;
import softeng.coop.business.jobpostings.JobPostingsSearchCriteria;
import softeng.coop.business.jobpostings.JobPostingsWriterManager;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.JobPosting;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.IJobPostingsCardView;
import softeng.coop.ui.viewdefinitions.IOkCancelView;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.mvp.IListener;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ModelEvent;
import softeng.ui.vaadin.mvp.Presenter;
import softeng.ui.vaadin.mvp.ViewEvent;

public class JobPostingsCardPresenter 
	extends Presenter<BeanItem<CoOp>, ICoopContext, IJobPostingsCardView>
{
	private class CoopChangedListener implements IListener<CoOp>
	{

		@Override
		public void onEvent(CoOp event)
		{
			bindSelectedCoopToView();
		}
		
	}
	
	private JobPostingsWriterManager manager;
	
	private CoopChangedListener coopChangedListener;
	
	public JobPostingsCardPresenter(IJobPostingsCardView view) 
	{
		super(view);
		
		coopChangedListener = new CoopChangedListener();
	}

	@Override
	protected void attachToView() 
	{
		getContext().addSelectedCoopChangedListener(coopChangedListener);
		
		getView().addSelectedCompanyChangedListener(new IListener<ModelEvent<Company>>()
		{
			@Override
			public void onEvent(ModelEvent<Company> event)
			{
				onSelectedCompanyChanged(event.getModel());
			}
		});
		
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
			public void onEvent(
					ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event) 
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
	
	protected void onSelectedCompanyChanged(Company selectedCompany)
	{
		if (selectedCompany == null) 
		{
			getView().setJobPostings(null);
			return;
		}
		else
		{
			JobPostingsSearchCriteria criteria = new JobPostingsSearchCriteria();
			
			criteria.setCoop(getContext().getSelectedCoop());
			criteria.setCompany(selectedCompany);
			
			SearchResult<JobPosting> result = manager.searchJobPostings(criteria);
			
			getView().setJobPostings(result.getList());
		}
	}

	@Override
	protected void detachFromView()
	{
		getContext().removeSelectedCoopChangedListener(coopChangedListener);
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
		
		manager = session.getJobPostingsManager().getWriterManager();
		
		if (manager == null)
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		bindSelectedCoopToView();
	}

	private void bindSelectedCoopToView()
	{
		CoOp selectedCoop = getContext().getSelectedCoop();
		
		if (selectedCoop != null)
			getView().setModel(new BeanItem<CoOp>(selectedCoop));
		else
			getView().setModel(null);
		
		getView().dataBind();
	}

	private void save()
	{
		if (manager == null) return;
	
		if (getView().getSelectedJobPosting() == null) return;
		
		if (getView().getModel() == null) return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		manager.persistJobPosting(getView().getSelectedJobPosting());
			
		getContext().showDataSavedNotification();

	}
	
	private void revert()
	{
		getContext().getSession().revertOutstanding();
		
		getView().dataBind();
	}
	
	private void cancel()
	{
		getView().discardChanges();
	}
}
