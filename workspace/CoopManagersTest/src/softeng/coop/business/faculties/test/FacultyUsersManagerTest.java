package softeng.coop.business.faculties.test;

import softeng.coop.business.*;
import softeng.coop.business.faculties.*;
import softeng.coop.business.universities.*;
import softeng.coop.business.test.*;
import softeng.coop.dataaccess.*;

import org.junit.*;
import java.util.*;

public class FacultyUsersManagerTest extends ManagerTestBase
{
	@Test
	public void acquireManager()
	{
		getFacultyUsersManager();
	}
	
	@Test
	public void polymorphicFacultySearch()
	{
		FacultyUsersManager facultyUsersManager = getFacultyUsersManager();
		
		FacultyUserSearchCriteria criteria = new FacultyUserSearchCriteria();
		
		criteria.setSurname("corleon");

		SearchResult<FacultyUser> result = 
			facultyUsersManager.searchFacultyUsers(criteria);
		
		Assert.assertTrue(
				"Professor was not found.", 
				result.getList().size() > 0);
		
		FacultyUser facultyUser = result.getList().get(0);
		
		Assert.assertTrue(
				"The faculty user found should have been of type Professor.", 
				facultyUser instanceof Professor);
		
		Professor professor = (Professor)facultyUser;
		
		System.out.printf("Professor's rank: %s.\n", professor.getRank());
	}
	
	@Test
	public void searchBySurname()
	{
		FacultyUsersManager facultyUsersManager = getFacultyUsersManager();
		
		ProfessorSearchCriteria criteria = new ProfessorSearchCriteria();
		
		criteria.setSurname("corleon");
		
		SearchResult<Professor> result = facultyUsersManager.searchProfessors(criteria);
		
		Assert.assertTrue(
				"Professor was not found.", 
				result.getList().size() > 0);
		
		Professor professor = result.getList().get(0);
		
		Assert.assertTrue(
				"Professor's name should be 'corleone'", 
				professor.getSurname().equals("corleone"));
	}
	
	@Test
	public void searchByDepartment()
	{
		UniversitiesManager universitiesManager = getUniversitiesManager();
		
		DepartmentsSearchCriteria departmentsCriteria = 
			new DepartmentsSearchCriteria();
		
		departmentsCriteria.setDepartmentsName("сглл");
		
		SearchResult<Department> departmentsResult =
			universitiesManager.searchDepartments(departmentsCriteria);
		
		Assert.assertTrue(
				"University department was not found", 
				departmentsResult.getList().size() > 0);
		
		Department department = departmentsResult.getList().get(0);
		
		Assert.assertTrue(
				"Department name should be сгллу", 
				universitiesManager.getLiteral(department.getName()).equals("сгллу"));
		
		FacultyUsersManager facultyUsersManager = getFacultyUsersManager();
		
		ProfessorSearchCriteria professorsCriteria = 
			new ProfessorSearchCriteria();
		
		professorsCriteria.setDepartment(department);
		
		SearchResult<Professor> result = 
			facultyUsersManager.searchProfessors(professorsCriteria);
		
		Assert.assertTrue(
				"Professor was not found.", 
				result.getList().size() > 0);
		
		Professor professor = result.getList().get(0);
		
		Assert.assertTrue(
				"Professor's name should be 'corleone'", 
				professor.getSurname().equals("corleone"));
	}
	
	@Test
	public void searchByUniversity()
	{
		UniversitiesManager universitiesManager = getUniversitiesManager();
		
		UniversitiesSearchCriteria universitiesCriteria =
			new DepartmentsSearchCriteria();
		
		universitiesCriteria.setUniversitiesName("NTU");
		
		SearchResult<University> universitiesResult = 
			universitiesManager.searchUniversities(universitiesCriteria);
		
		List<University> universities = universitiesResult.getList();
		
		Assert.assertTrue(
				"No university was found.", 
				universities.size() > 0);
		
		University university = universities.get(0);
		
		String universityName = universitiesManager.getLiteral(university.getName());
		
		Assert.assertTrue(
				"University name should be 'NTUA'.", 
				universityName.equals("NTUA"));
		
		FacultyUsersManager facultyUsersManager = getFacultyUsersManager();
		
		FacultyUserSearchCriteria facultyUsersCriteria =
			new FacultyUserSearchCriteria();
		
		facultyUsersCriteria.setUniversity(university);
		
		SearchResult<FacultyUser> facultyUsersResult =
			facultyUsersManager.searchFacultyUsers(facultyUsersCriteria);
		
		List<FacultyUser> facultyUsers = facultyUsersResult.getList();
		
		Assert.assertTrue(
				"No user was found.", 
				facultyUsers.size() > 0);
		
		FacultyUser facultyUser = facultyUsers.get(0);
		
		Assert.assertTrue(
				"User surname was not 'corleone'.", 
				facultyUser.getSurname().equals("corleone"));
	}
	
	private FacultyUsersManager getFacultyUsersManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);

		FacultyUsersManager manager = session.getFacultyUsersManager();
		
		Assert.assertNotNull("aqcuisition of faculty users manager failed", manager);

		return manager;
	}

	private UniversitiesManager getUniversitiesManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);

		UniversitiesManager manager = session.getUniversitiesManager();
		
		Assert.assertNotNull("aqcuisition of faculty users manager failed", manager);

		return manager;
	}
}
