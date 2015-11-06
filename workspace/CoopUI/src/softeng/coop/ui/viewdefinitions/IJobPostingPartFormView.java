package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.JobPostingPart;

import com.vaadin.data.util.BeanItem;

public interface IJobPostingPartFormView extends IFormView<BeanItem<JobPostingPart>> 
{
	public void setCompanyForJobPostingPart(Company company);
	public Company getCompanyForJobPostingPart();
}
