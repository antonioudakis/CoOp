package softeng.coop.ui.viewdefinitions;

import javax.validation.constraints.Digits;

import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public interface IUserManagementCardView extends IFormView<BeanItem<Department>> 
{
	/** 
	 *Represents the searchCriteria for the filtering the Groups in the GroupManagementCardComponent
	 *
	 */
	public static class FilterOptions
	{
		private boolean inSelectedCoop = true;
		private boolean withoutUsername;
		
		private boolean noAma;
		private boolean noIban;
		private boolean notAssignedToJob;
		
		private String surnamePrefix;
		
		@Digits(integer = 4, fraction = 0)
		private Integer academicYear;
		
		private University university;
		
		private Department department;
		
		private Division division;

		// This is defined as a string because of a Vaadin limitation:
		// Vaadin cannot parse an empty nullable TextField into a null Integer.
		@Digits(integer = 4, fraction = 0)
		private String creationYear;
		
		private boolean allRequiredFieldsComplete;
		
		public boolean isInSelectedCoop() 
		{
			return inSelectedCoop;
		}
		
		public void setInSelectedCoop(boolean inSelectedCoop) 
		{
			this.inSelectedCoop = inSelectedCoop;
		}
		
		public boolean isWithoutUsername() 
		{
			return withoutUsername;
		}
		
		public void setWithoutUsername(boolean withoutUsername) 
		{
			this.withoutUsername = withoutUsername;
		}

		public boolean isNoAma() {
			return noAma;
		}

		public void setNoAma(boolean noAma) {
			this.noAma = noAma;
		}

		public boolean isNoIban() {
			return noIban;
		}

		public void setNoIban(boolean noIban) {
			this.noIban = noIban;
		}

		public boolean isNotAssignedToJob() {
			return notAssignedToJob;
		}

		public void setNotAssignedToJob(boolean notAssignedToJob) {
			this.notAssignedToJob = notAssignedToJob;
		}

		public void setSurnamePrefix(String surnamePrefix)
		{
			this.surnamePrefix = surnamePrefix;
		}

		public String getSurnamePrefix()
		{
			return surnamePrefix;
		}

		public void setAcademicYear(Integer academicYear)
		{
			this.academicYear = academicYear;
		}

		public Integer getAcademicYear()
		{
			return academicYear;
		}

		public University getUniversity()
		{
			return university;
		}

		public void setUniversity(University university)
		{
			this.university = university;
		}

		public Department getDepartment()
		{
			return department;
		}

		public void setDepartment(Department department)
		{
			this.department = department;
		}

		public Division getDivision()
		{
			return division;
		}

		public void setDivision(Division division)
		{
			this.division = division;
		}

		public void setCreationYear(String creationYear)
		{
			this.creationYear = creationYear;
		}

		public String getCreationYear()
		{
			return creationYear;
		}

		public boolean isAllRequiredFieldsComplete()
		{
			return allRequiredFieldsComplete;
		}

		public void setAllRequiredFieldsComplete(boolean allRequiredFieldsComplete)
		{
			this.allRequiredFieldsComplete = allRequiredFieldsComplete;
		}
	}
	
	UserType getSelectedUserType();
	
	void addSelectedUserTypeChangeListener(IViewListener<BeanItem<Department>, ICoopContext, IUserManagementCardView> listener);
	void removeSelectedUserTypeChangeListener(IViewListener<BeanItem<Department>, ICoopContext, IUserManagementCardView> listener);
	
	public BeanItem<AuthenticatedUser> getSelectedUser();
	
	public Registration getSelectedRegistration();
	
	public UsersTableComponent getUserTable();
	
	BeanItem<FilterOptions> getFilterOptions();
	
	public IOkCancelView getOkCancelView();
	
	public Button getButtonRegisterCurrentCoop();
	public Button getButtonRemoveSelectedRegistration();
	
	public ListSelect getCoOpsRegistrationTable();
	
}
