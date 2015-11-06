package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

import javax.validation.constraints.*;

import softeng.coop.dataaccess.*;

public interface ICoopSettingsCardView
	extends IFormView<BeanItemContainer<CoOp>>
{
	/**
	 * Represents the search criteria for the list of coops.
	 */
	public static class FilterOptions
	{
		@Digits(integer = 4, fraction = 0)
		private Integer academicYear;
		
		private boolean activeOnly;
		
		private boolean inRegistrationOnly;
		
		private boolean setupOnly;
		
		private Lesson lesson;
		
		private boolean includingOtherDepartments;
		
		private boolean includingOtherUniversities;
		
		public FilterOptions()
		{
			Calendar now = Calendar.getInstance();
			
			this.academicYear = now.get(Calendar.YEAR);
		}

		public Integer getAcademicYear()
		{
			return academicYear;
		}

		public void setAcademicYear(Integer academicYear)
		{
			this.academicYear = academicYear;
		}

		public boolean isActiveOnly()
		{
			return activeOnly;
		}

		public void setActiveOnly(boolean active)
		{
			this.activeOnly = active;
		}

		public boolean isInRegistrationOnly()
		{
			return inRegistrationOnly;
		}

		public void setInRegistrationOnly(boolean inRegistration)
		{
			this.inRegistrationOnly = inRegistration;
		}

		public boolean isSetupOnly()
		{
			return setupOnly;
		}

		public void setSetupOnly(boolean setup)
		{
			this.setupOnly = setup;
		}

		public void setLesson(Lesson lesson)
		{
			this.lesson = lesson;
		}

		public Lesson getLesson()
		{
			return lesson;
		}

		public boolean isIncludingOtherDepartments()
		{
			return includingOtherDepartments;
		}

		public void setIncludingOtherDepartments(boolean includingOtherDepartments)
		{
			this.includingOtherDepartments = includingOtherDepartments;
		}

		public boolean isIncludingOtherUniversities()
		{
			return includingOtherUniversities;
		}

		public void setIncludingOtherUniversities(boolean includingOtherUniversities)
		{
			this.includingOtherUniversities = includingOtherUniversities;
		}
	}

	BeanItem<FilterOptions> getFilterOptions();
	
	Department getDepartment();
	void setDepartment(Department department);
	
	IOkCancelView getOkCancelView();
	
	CoOp getSelectedCoop();
	
	Set<FinancialSource> getAvailableFinancialSources();
	void setAvailableFinancialSources(Set<FinancialSource> sources);
}
