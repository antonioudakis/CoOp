package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.faculties.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IChooseProfessorView.FilterOptions;

public class ChooseProfessorPresenter
extends Presenter<BeanItem<Professor>, ICoopContext, IChooseProfessorView>
{
	private class FilterOptionsChangeListener 
	implements IListener<PropertyChangeEvent<IChooseProfessorView.FilterOptions>>
	{
		@Override
		public void onEvent(PropertyChangeEvent<FilterOptions> event)
		{
			onFilterOptionsChanged(event);
		}
	}
	
	private PropertyChangeEventSubscription<IChooseProfessorView.FilterOptions> filterOptionsSubscription =
		new PropertyChangeEventSubscription<IChooseProfessorView.FilterOptions>();
	
	private FacultyUsersManager facultyUsersManager;
	
	private AccessCheck accessCheck;
	
	private FilterOptionsChangeListener filterOptionsChangeListener =
		new FilterOptionsChangeListener();
	
	public ChooseProfessorPresenter(IChooseProfessorView view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		discoverPermissions();
		
		Session session = getContext().getSession();
		
		if (session == null)
			throw new CoopUISystemException("Session should be available.");
		
		AuthenticatedUser user = session.getAuthenticatedUser();
		
		DataItem<IChooseProfessorView.FilterOptions> filterOptionsItem =
			getView().getFilterOptionsItem();
		
		if (filterOptionsItem == null || filterOptionsItem.getBean() == null)
			throw new CoopUISystemException("filterOptions not yet setup");
		
		IChooseProfessorView.FilterOptions filterOptions = filterOptionsItem.getBean();
		
		if (!accessCheck.canBrowseDepartments() || !accessCheck.canBrowseUniversities())
			filterOptions.setDepartment(user.getDepartment());
		
		filterOptions.setUniversity(user.getDepartment().getUniversity());
		
		DataItemContainer<University> universitiesContainer = accessCheck.getUniversitiesContainer();
		
		getView().setUniversitiesContainer(universitiesContainer);

		DataItemContainer<Department> departmentsContainer = accessCheck.getDepartmentsContainer(filterOptions.getUniversity());
		
		getView().setDepartmentsContainer(departmentsContainer);
		
		DataItemContainer<Division> divisionsContainer = accessCheck.getDivisionsContainer(filterOptions.getDepartment());
		
		getView().setDivisionsContainer(divisionsContainer);
		
		filterOptionsSubscription.startListeningTo(filterOptionsItem);
		
		filterOptionsSubscription.add(filterOptionsChangeListener);
	}
	
	private void discoverPermissions()
	{
		Session session = getContext().getSession();
		
		if (session == null)
			throw new CoopUISystemException("Session should be available.");
		
		facultyUsersManager = session.getFacultyUsersManager();
		
		if (facultyUsersManager == null)
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		accessCheck = new AccessCheck(session);
	}

	protected void onFilterOptionsChanged(PropertyChangeEvent<FilterOptions> event)
	{
		Property property = event.getProperty();
		
		BeanItem<IChooseProfessorView.FilterOptions> filterOptionsItem = event.getItem();
		
		IChooseProfessorView.FilterOptions filterOptions = filterOptionsItem.getBean();
		
		if (property == filterOptionsItem.getItemProperty("university"))
		{
			// University changed.
			// Thus, will change department and division to null. Stop listening
			// to property changes to prevent re-entrance.
//			filterOptionsSubscription.remove(filterOptionsChangeListener);
			
			DataItemContainer<Department> departmentsContainer = accessCheck.getDepartmentsContainer(filterOptions.getUniversity());
			
			getView().setDepartmentsContainer(departmentsContainer);
			
			DataItemContainer<Division> divisionsContainer = accessCheck.getDivisionsContainer((Department)null); 
			
			getView().setDivisionsContainer(divisionsContainer);
			
//			filterOptionsItem.getItemProperty("department").setValue(null);
//			filterOptionsItem.getItemProperty("division").setValue(null);
			
			// Resume listening.
//			filterOptionsSubscription.add(filterOptionsChangeListener);
		}
		else if (property == filterOptionsItem.getItemProperty("department"))
		{
			// Department changed.
			// Thus, will change division to null. Stop listening
			// to property changes to prevent re-entrance.
//			filterOptionsSubscription.remove(filterOptionsChangeListener);
			
			Department department = filterOptions.getDepartment();
			
			DataItemContainer<Division> divisionsContainer = accessCheck.getDivisionsContainer(department);
			
			getView().setDivisionsContainer(divisionsContainer);
			
//			filterOptionsItem.getItemProperty("division").setValue(null);
			
			// Resume listening.
//			filterOptionsSubscription.add(filterOptionsChangeListener);
		}
		
		updateProfessorsContainer();
	}

	protected void updateProfessorsContainer()
	{
		if (getView().getFilterOptionsItem() == null) return;
		
		ProfessorSearchCriteria criteria = new ProfessorSearchCriteria();
		
		IChooseProfessorView.FilterOptions filterOptions = 
			getView().getFilterOptionsItem().getBean();
		
		if (filterOptions == null) return;
		
		criteria.setUniversity(filterOptions.getUniversity());
		criteria.setDepartment(filterOptions.getDepartment());
		criteria.setDivision(filterOptions.getDivision());
		
		SearchResult<Professor> result = facultyUsersManager.searchProfessors(criteria);
		
		ArrayList<String> propertyIds = getProfessorPropertyIds();
		
		DataItemContainer<Professor> container = 
			new DataItemContainer<Professor>(
					Professor.class, 
					result.getList(), 
					facultyUsersManager, 
					propertyIds); 
		
		getView().setProfessorsContainer(container);
	}

	private ArrayList<String> getProfessorPropertyIds()
	{
		ArrayList<String> propertyIds = new ArrayList<String>();
		
		propertyIds.add("name");
		propertyIds.add("surname");
		
		return propertyIds;
	}

	@Override
	protected void setupView()
	{
		updateProfessorsContainer();
	}
}
