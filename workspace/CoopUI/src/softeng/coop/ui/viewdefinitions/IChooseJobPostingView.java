package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.JobPosting;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.data.DataItemContainer;
import softeng.ui.vaadin.mvp.*;

public interface IChooseJobPostingView 
	extends IView<DataItemContainer<JobPosting>, ICoopContext>
{
	JobPosting getSelectedJobPosting();
}
