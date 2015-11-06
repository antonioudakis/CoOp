package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.ui.vaadin.mvp.*;

public interface IJobPostingsCardView extends  IFormView<BeanItem<CoOp>>
{
	IOkCancelView getOkCancelView();
	
	JobPosting getSelectedJobPosting();
	
	void addSelectedCompanyChangedListener(IListener<ModelEvent<Company>> listener);
	void removeSelectedCompanyChangedListener(IListener<ModelEvent<Company>> listener);
	
	void setJobPostings(Collection<JobPosting> jobPostings);
	Collection<JobPosting> getJobPostings();
}
