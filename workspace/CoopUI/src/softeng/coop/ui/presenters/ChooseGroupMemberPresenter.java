package softeng.coop.ui.presenters;

import java.util.Collection;
import java.util.HashSet;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IChooseGroupMemberDialogView.*;

public class ChooseGroupMemberPresenter
extends Presenter<BeanItem<Registration>, ICoopContext, IChooseGroupMemberDialogView>
{
	private StudentsManager manager;
	
	private PropertyChangeEventSubscription<FilterOptions> filterOptionsChangeSubscription =
		new PropertyChangeEventSubscription<FilterOptions>();

	public ChooseGroupMemberPresenter(IChooseGroupMemberDialogView view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		manager = session.getStudentsManager();
		
		if (manager == null)
		{
			throw new CoopUIAccessDeniedException(getContext());
		}
		
		filterOptionsChangeSubscription.add(new IListener<PropertyChangeEvent<FilterOptions>>()
		{
			@Override
			public void onEvent(PropertyChangeEvent<FilterOptions> event)
			{
				refreshRegistrationsList();
			}
		});
		
		filterOptionsChangeSubscription.startListeningTo(getView().getFilterOptionsItem());
		
	}

	@Override
	protected void setupView()
	{
		refreshRegistrationsList();
	}

	private void refreshRegistrationsList()
	{
		if (manager == null) return;
		
		RegistrationsSearchCriteria criteria = new RegistrationsSearchCriteria();

		criteria.setCoop(getContext().getSelectedCoop());
		
		if (getView().getFilterOptionsItem() != null)
		{
			FilterOptions filterOptions = getView().getFilterOptionsItem().getBean();
			
			if (filterOptions.getSurnamePrefix() != null)
				criteria.setSurname(filterOptions.getSurnamePrefix());
			
			if (filterOptions.getPreferredCategory() != null)
				criteria.setCategory(filterOptions.getPreferredCategory());
			
			if (filterOptions.getPreferredLocation() != null)
				criteria.setLocation(filterOptions.getPreferredLocation());
			
			if (filterOptions.isNotAssignedToJob())
				criteria.setAssignedToJob(false);
		}
		
		SearchResult<Registration> result = manager.searchRegistrations(criteria);
		
		Collection<Registration> filteredRegistrations = new HashSet<Registration>(result.getList().size());
		
		Collection<Registration> excludedRegistrations = getView().getExcludedRegistrations();
		
		if (excludedRegistrations != null)
		{
			for (Registration registration : result.getList())
			{
				if (excludedRegistrations.contains(registration)) continue;
				
				filteredRegistrations.add(registration);
			}
		}
		else
		{
			filteredRegistrations.addAll(result.getList());
		}
		
		getView().getTableView().setModel(filteredRegistrations);
		
		getView().getTableView().dataBind();
	}
}
