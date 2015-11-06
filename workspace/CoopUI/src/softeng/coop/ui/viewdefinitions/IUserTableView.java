package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.viewmodels.*;

public interface IUserTableView extends ITableView<Department, AuthenticatedUser> 
{
	public void setUserType(UserType userType);
	public UserType getUserType();
}
