package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.JobPart;
import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;

public interface IJobPartDialogView extends IView<BeanItem<JobPart>, ICoopContext> 
{
	public void setCompanyForJobPart(Company company);
	public Company getCompanyForJobPart();
}
