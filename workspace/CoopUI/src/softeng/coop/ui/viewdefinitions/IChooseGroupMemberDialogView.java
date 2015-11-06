package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.ui.vaadin.mvp.*;

import java.util.*;

public interface IChooseGroupMemberDialogView 
extends IView<BeanItem<Registration>, ICoopContext> 
{
	/**
	 * Search criteria for the displayed list of registrations.
	 */
	public static class FilterOptions
	{
		private String surnamePrefix;
		
		private Location preferredLocation;
		
		private Category preferredCategory;
		
		private boolean notAssignedToJob;
		
		public FilterOptions()
		{
			setNotAssignedToJob(true);
		}

		public String getSurnamePrefix()
		{
			return surnamePrefix;
		}

		public void setSurnamePrefix(String surnamePrefix)
		{
			this.surnamePrefix = surnamePrefix;
		}

		public Location getPreferredLocation()
		{
			return preferredLocation;
		}

		public void setPreferredLocation(Location preferredLocation)
		{
			this.preferredLocation = preferredLocation;
		}

		public Category getPreferredCategory()
		{
			return preferredCategory;
		}

		public void setPreferredCategory(Category preferredCategory)
		{
			this.preferredCategory = preferredCategory;
		}

		public void setNotAssignedToJob(boolean notAssignedToJob)
		{
			this.notAssignedToJob = notAssignedToJob;
		}

		public boolean isNotAssignedToJob()
		{
			return notAssignedToJob;
		}
	}
	
	/**
	 * Don't list this set of registrations.
	 * Null means nothing is excluded.
	 */
	Collection<Registration> getExcludedRegistrations();
	/**
	 * Don't list this set of registrations.
	 * Null means nothing is excluded.
	 */
	void setExcludedRegistrations(Collection<Registration> excludedRegistrations);
	
	ITableView<Group, Registration> getTableView(); 
	
	/**
	 * The search filters item.
	 * Listeners can read bind the the item's change events.
	 */
	BeanItem<FilterOptions> getFilterOptionsItem();
}
