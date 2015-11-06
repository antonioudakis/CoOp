package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.JobPart;

public interface IJobPartFormView 
	extends IFormView<BeanItem<JobPart>>
{
	public void setCompanyForJobPart(Company company);
	public Company getCompanyForJobPart();
}
