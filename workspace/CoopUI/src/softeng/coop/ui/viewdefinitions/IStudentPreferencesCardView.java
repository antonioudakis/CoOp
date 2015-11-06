package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.*;

public interface IStudentPreferencesCardView 
	extends IFormView<BeanItem<Student>>
{
	public IRegistrationPreferencesView getRegistrationPreferencesView();
	
//	public BeanItem<Registration> getSelectedRegistration();
//	
//	public void setSelectedRegistration(BeanItem<Registration> registration);
	
	IOkCancelView getOkCancelView();
}
