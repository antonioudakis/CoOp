package softeng.coop.business.coops.test;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.test.*;
import softeng.coop.business.universities.*;
import softeng.coop.business.coops.*;
import softeng.coop.business.faculties.FacultyUsersManager;
import softeng.coop.business.faculties.ProfessorSearchCriteria;
import softeng.coop.dataaccess.*;

import org.junit.*;

public class CoOpsWriterManagerTest extends ManagerTestBase
{
	@Test
	public void createCoop()
	{
		CoOpsWriterManager manager = getCoopsManager();
		
		CoOp coop = new CoOp();
		
		Department department = findDepartment();
		
		Professor professor = findProfessor(department);
		
		coop.setName(new Multilingual());
		manager.setDefaultLiteral(coop.getName(), "GULAG");
		
		Lesson lesson = new Lesson();
		lesson.setName(new Multilingual());
		lesson.setDepartment(department);
		manager.setDefaultLiteral(lesson.getName(), "Practical socialism");
		manager.persistLesson(lesson);
		
		coop.setAcademicYear(2011);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(2012, 8, 1);
		coop.setStartDate(startCalendar.getTime());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(2013, 7, 30);
		coop.setEndDate(endCalendar.getTime());
		coop.setActive(true);
		coop.setSupervisingProfessors(new HashSet<Professor>());
		coop.getSupervisingProfessors().add(professor);
		coop.setJobDurationDays(3000);
		coop.setInstitutionalDirector(professor);
		coop.setAcademicDirector(professor);
		coop.setScientificDirector(professor);
		coop.setMaxGroupSize(6);
		coop.setLesson(lesson);
		coop.setRequirements(new HashSet<Requirement>());
		
		Requirement requirement = new Requirement();
		requirement.setName(new Multilingual());
		requirement.setType(RequirementType.Completion);
		coop.getRequirements().add(requirement);
		manager.markAsChanged(requirement);
		
		manager.persistCoOp(coop);
	}
	
	private Department findDepartment()
	{
		UniversitiesManager manager = getUniversitiesManager();

		DepartmentsSearchCriteria criteria = new DepartmentsSearchCriteria();
		
		criteria.setUniversitiesName("NTU");
		
		criteria.setDepartmentsName("сглл");
		
		SearchResult<Department> result = manager.searchDepartments(criteria);
		
		List<Department> departments = result.getList();
		
		Assert.assertTrue("A Department should have been found.", departments.size() > 0);
		
		Department department = departments.get(0);
		
		Assert.assertTrue(
				"Department name should be сгллу", 
				manager.getLiteral(department.getName()).equals("сгллу"));

		return department;
	}

	private Professor findProfessor(Department department)
	{
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

		return professor;
	}

	private FacultyUsersManager getFacultyUsersManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);

		FacultyUsersManager manager = session.getFacultyUsersManager();
		
		Assert.assertNotNull("aqcuisition of faculty users manager failed", manager);

		return manager;
	}

	private CoOpsWriterManager getCoopsManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		CoOpsManager manager = session.getCoOpsManager();
		
		Assert.assertNotNull("aqcuisition of universities manager failed", manager);

		CoOpsWriterManager writerManager = manager.getWriterManager();
		
		Assert.assertNotNull("Should have access to writer manager.", writerManager);
		
		return writerManager;
	}

	private UniversitiesManager getUniversitiesManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		UniversitiesManager universitiesManager = session.getUniversitiesManager();
		
		Assert.assertNotNull("aqcuisition of universities manager failed", universitiesManager);

		return universitiesManager;
	}

}
