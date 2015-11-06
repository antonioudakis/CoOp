package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.ui.vaadin.mvp.*;

import javax.validation.constraints.*;

import java.util.*;

public interface IRegistrationsCardView
extends ICardFormView<BeanItem<CoOp>>
{
	/**
	 * Represents the search criteria for the list of coops.
	 */
	public static class FilterOptions
	{
		@DecimalMin(value="0.0")
		@DecimalMax(value="10.0")
		private Float minGrade;
		
		@DecimalMin(value="0.0")
		@DecimalMax(value="10.0")
		private Float maxGrade;

		private boolean noAma;
		private boolean noIban;
		private boolean notAssignedToJob;
		private boolean notQualifiedForAssignment;
		private boolean notQualifiedForCompletion;
		private boolean notPassed;
		private boolean noInsuranceContract;
		private boolean noSocialSecurityId;
		
		private boolean notGraded;
		
		public boolean isNoAma()
		{
			return noAma;
		}
		public void setNoAma(boolean noAma)
		{
			this.noAma = noAma;
		}
		public boolean isNoIban()
		{
			return noIban;
		}
		public void setNoIban(boolean noIban)
		{
			this.noIban = noIban;
		}
		public boolean isNotAssignedToJob()
		{
			return notAssignedToJob;
		}
		public void setNotAssignedToJob(boolean notAssignedToJob)
		{
			this.notAssignedToJob = notAssignedToJob;
		}
		public boolean isNotQualifiedForAssignment()
		{
			return notQualifiedForAssignment;
		}
		public void setNotQualifiedForAssignment(boolean notQualifiedForAssignment)
		{
			this.notQualifiedForAssignment = notQualifiedForAssignment;
		}
		public boolean isNotQualifiedForCompletion()
		{
			return notQualifiedForCompletion;
		}
		public void setNotQualifiedForCompletion(boolean notQualifiedForCompletion)
		{
			this.notQualifiedForCompletion = notQualifiedForCompletion;
		}
		public boolean isNotPassed()
		{
			return notPassed;
		}
		public void setNotPassed(boolean notPassed)
		{
			this.notPassed = notPassed;
		}
		public Float getMinGrade()
		{
			return minGrade;
		}
		public void setMinGrade(Float minGrade)
		{
			this.minGrade = minGrade;
		}
		public Float getMaxGrade()
		{
			return maxGrade;
		}
		public void setMaxGrade(Float maxGrade)
		{
			this.maxGrade = maxGrade;
		}
		public boolean isNotGraded()
		{
			return notGraded;
		}
		public void setNotGraded(boolean notGraded)
		{
			this.notGraded = notGraded;
		}
		public void setNoInsuranceContract(boolean noInsuranceContract)
		{
			this.noInsuranceContract = noInsuranceContract;
		}
		public boolean isNoInsuranceContract()
		{
			return noInsuranceContract;
		}
		public void setNoSocialSecurityId(boolean noSocialSecurityId)
		{
			this.noSocialSecurityId = noSocialSecurityId;
		}
		public boolean isNoSocialSecurityId()
		{
			return noSocialSecurityId;
		}
		
	}
	
	BeanItem<FilterOptions> getFilterOptions();
	
	ITableView<CoOp, Registration> getRegistrationsTableView();
	
	void addSendMailListener(IListener<Collection<AuthenticatedUser>> listener);
	void removeSendMailListener(IListener<Collection<AuthenticatedUser>> listener);
}
