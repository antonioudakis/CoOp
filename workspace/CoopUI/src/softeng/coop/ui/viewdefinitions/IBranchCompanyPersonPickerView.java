package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.CompanyPerson;
import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;

public interface IBranchCompanyPersonPickerView 
	extends IView<CompanyPerson, ICoopContext> 
{
	public void setBranch(Branch branch);
	
	public Branch getBranch();
}
