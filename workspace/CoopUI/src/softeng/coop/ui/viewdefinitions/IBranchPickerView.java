package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.Company;
import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;

public interface IBranchPickerView extends IView<Branch, ICoopContext> 
{
	Company getCompany();
	void setCompany(Company company);
	
	/**
	 * Default is false.
	 */
	boolean isCompanyBrowsingAllowed();
	void setCompanyBrowsingAllowed(boolean companyBrowsingAllowed);
}
