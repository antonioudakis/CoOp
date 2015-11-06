package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;

import softeng.ui.vaadin.mvp.*;

public interface IChooseProfessorView
	extends IView<BeanItem<Professor>, ICoopContext>
{
	public static class FilterOptions
	{
		private University university;
		
		private Department department;
		
		private Division division;

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
	}
	
	DataItem<FilterOptions> getFilterOptionsItem();
	
	DataItemContainer<University> getUniversitiesContainer();
	void setUniversitiesContainer(DataItemContainer<University> container);
	
	DataItemContainer<Department> getDepartmentsContainer();
	void setDepartmentsContainer(DataItemContainer<Department> container);
	
	DataItemContainer<Division> getDivisionsContainer();
	void setDivisionsContainer(DataItemContainer<Division> container);
	
	DataItemContainer<Professor> getProfessorsContainer();
	void setProfessorsContainer(DataItemContainer<Professor> container);
}
