package softeng.coop.ui.presenters;

import softeng.coop.business.SearchResult;
import softeng.coop.business.jobpostings.JobPostingsManager;
import softeng.coop.business.jobpostings.JobPostingsSearchCriteria;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.JobPosting;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.viewdefinitions.IChooseJobPostingView;
import softeng.ui.vaadin.mvp.Presenter;

public class ChooseJobPostingPresenter 
	extends Presenter<DataItemContainer<JobPosting>, 
		ICoopContext, 
		IChooseJobPostingView> 
{

	public ChooseJobPostingPresenter(IChooseJobPostingView view) 
	{
		super(view);
	}

	@Override
	protected void setupView() 
	{
		JobPostingsManager manager = this.getContext().getSession().getJobPostingsManager();
		
		if (manager == null)
		{
			getView().setModel(null);
			return;
		}
		
		CoOp selectedCoOp = this.getContext().getSelectedCoop();
		
		JobPostingsSearchCriteria criteria = new JobPostingsSearchCriteria();
		criteria.setCoop(selectedCoOp);
		
		SearchResult<JobPosting> result = manager.searchJobPostings(criteria);
		
		getView().setModel(new DataItemContainer<JobPosting>(JobPosting.class, result.getList(), manager));
	}

	@Override
	protected void attachToView()
	{
	}

}
