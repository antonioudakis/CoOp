package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.JobPostingPart;
import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;

public interface IJobPostingPartView extends IView<BeanItem<JobPostingPart>, ICoopContext> 
{
	public void setCompanyForJobPostingPart(Company company);
	
	public Company getCompanyForJobPostingPart();
}
