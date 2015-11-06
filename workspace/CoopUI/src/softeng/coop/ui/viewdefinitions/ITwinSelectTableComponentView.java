package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.ui.ICoopContext;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.dataaccess.*;

public interface ITwinSelectTableComponentView 
	extends IView<CoOp, ICoopContext>
{
	/** 
	 * Represents the search criteria for JobPostings and Groups
	 *
	 */
	public static class FilterOptions
	{
		private Location locationFilter;
		private Category categoryFilter;
		private boolean qualifiedForAssignmentOnly;
		
		public FilterOptions()
		{
			this.qualifiedForAssignmentOnly = true;
		}
		
		public Location getLocationFilter() 
		{
			return locationFilter;
		}
		
		public void setLocationFilter(Location locationFilter) 
		{
			this.locationFilter = locationFilter;
		}
		
		public Category getCategoryFilter() 
		{
			return categoryFilter;
		}
		
		public void setCategoryFilter(Category categoryFilter) 
		{
			this.categoryFilter = categoryFilter;
		}

		public void setQualifiedForAssignmentOnly(boolean qualifiedForAssignmentOnly)
		{
			this.qualifiedForAssignmentOnly = qualifiedForAssignmentOnly;
		}

		public boolean isQualifiedForAssignmentOnly()
		{
			return qualifiedForAssignmentOnly;
		}	
	}
	
	JobPosting getSelectedJobPosting();
	
	Group getSelectedGroup();
	
	Date getJobStartDate();
	
	Job getSelectedJob();
	
	BeanItem<FilterOptions> getJobPostingsFilterOptions();
	BeanItem<FilterOptions> getGroupFilterOptions();
	
	void removeJobPostingFromContainer(JobPosting jobPosting);
	void addJobPostingToContainer(JobPosting jobPosting);
	void refreshJobPostingsTable();
	
	void removeGroupFromContainer(Group group);
	void addGroupToContainer(Group group);
	
	void addJobToContainer(Job job);
	void removeJobFromContainer(Job job);
	
	//Add, remove Listener to the assign button
	void addJobAssignedListener(IViewListener<CoOp, ICoopContext, ITwinSelectTableComponentView> listener);
	void removeJobAssignedListener(IViewListener<CoOp, ICoopContext, ITwinSelectTableComponentView> listener);
	
	//Add, remove Listener to the remove assignment button
	void addJobAssignmentRemovedListener(IViewListener<CoOp, ICoopContext, ITwinSelectTableComponentView> listener);
	void removeJobAssignmentRemovedListener(IViewListener<CoOp, ICoopContext, ITwinSelectTableComponentView> listener);
	
	public List<JobPosting> getJobPostings();
	public void setJobPostings(List<JobPosting> jobPostingsList);
	
	public List<Group> getGroups();
	public void setGroups(List<Group> groupsList);
	
	public List<Job> getJobs();
	public void setJobs(List<Job> jobsList);
}
