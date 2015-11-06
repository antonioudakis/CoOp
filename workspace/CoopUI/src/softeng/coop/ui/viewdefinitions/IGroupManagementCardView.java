package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Group;

public interface IGroupManagementCardView 
	extends IFormView<BeanItem<CoOp>> 
{
	/**
	 * Represents the searchCriteria for the filtering the Groups in the GroupManagementCardComponent
	 */
	public static class FilterOptions
	{
		private boolean empty;
		private boolean graded;
		private boolean notGraded;
		private boolean assignedJobs;
		private boolean notAssignedJobs;
		private boolean passed;
		private boolean incomplete;
		
		public boolean isEmpty() 
		{
			return empty;
		}
		
		public void setEmpty(boolean empty) 
		{
			this.empty = empty;
		}
		
		public boolean isAssignedJobs() 
		{
			return assignedJobs;
		}
		
		public void setAssignedJobs(boolean assignedJobs) 
		{
			this.assignedJobs = assignedJobs;
		}
		
		public boolean isPassed() 
		{
			return passed;
		}
		
		public void setPassed(boolean passed) 
		{
			this.passed = passed;
		}
		
		public boolean isIncomplete() 
		{
			return incomplete;
		}
		
		public void setIncomplete(boolean incomplete) 
		{
			this.incomplete = incomplete;
		}

		public boolean isGraded() 
		{
			return graded;
		}

		public void setGraded(boolean graded) 
		{
			this.graded = graded;
		}

		public boolean isNotGraded() 
		{
			return notGraded;
		}

		public void setNotGraded(boolean notGraded) 
		{
			this.notGraded = notGraded;
		}

		public boolean isNotAssignedJobs() {
			return notAssignedJobs;
		}

		public void setNotAssignedJobs(boolean notAssignedJobs) {
			this.notAssignedJobs = notAssignedJobs;
		}

	}
	
	BeanItem<FilterOptions> getFilterOptions();
	
	public IOkCancelView getOkCancelView();
	
	public ITableView<CoOp, Group> getGroupTableView();
	
	public BeanItem<Group> getSelectedGroup();
}
