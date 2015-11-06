package softeng.coop.business.students.test;

import org.junit.Assert;
import org.junit.Test;

import softeng.coop.business.SearchResult;
import softeng.coop.business.students.StudentsManager;
import softeng.coop.business.students.StudentsSearchCriteria;
import softeng.coop.business.test.ManagerTestBase;
import softeng.coop.dataaccess.Invitation;
import softeng.coop.dataaccess.Registration;
import softeng.coop.dataaccess.Student;

public class StudentsManagerTest extends ManagerTestBase
{
	@Test
	public void acquireManager()
	{
		getStudentManager();
	}

	private StudentsManager getStudentManager() {
		Assert.assertNotNull("Session aquisition failed", session);
		
		StudentsManager  studentsManager = session.getStudentsManager();
		
		Assert.assertNotNull("aqcuisition of student manager failed", studentsManager);

		return studentsManager;
	}
	
	@Test
	public void searchStudentsWithNoIBAN()
	{
		StudentsManager manager = getStudentManager();
		
		StudentsSearchCriteria studentsSearchCriteria = new StudentsSearchCriteria();
		
		studentsSearchCriteria.setCoop(session.getDefaultCoop());
		studentsSearchCriteria.setHasIBAN(false);
		SearchResult<Student> result = manager.searchStudents(studentsSearchCriteria);
		
		Student student = result.getList().get(0);
		System.out.println(student.getName());
		Assert.assertNotNull(result);
	}
	
	@Test
	public void searchStudentsWithNoRequirements()
	{
		StudentsManager manager = getStudentManager();
		
		StudentsSearchCriteria studentsSearchCriteria = new StudentsSearchCriteria();
		
		studentsSearchCriteria.setCoop(session.getAuthenticatedUser().getDefaultCoOp());
		SearchResult<Student> result = manager.searchStudents(studentsSearchCriteria);
		
		if (result.getList().size()>0) 
		{
			Student student = result.getList().get(0);
			System.out.println(student.getName());
		}
		Assert.assertNotNull(result);
	}
	
	@Test
	public void getStudentRegistrationUsingCodeAndCoop()
	{
		StudentsManager manager = getStudentManager();
		
		Registration registration = manager.getRegistration("el96696", session.getAuthenticatedUser().getDefaultCoOp());
		
		Assert.assertNotNull(registration);
		
		
	}
	
	@Test
	public void getRegistrationByCode()
	{
		StudentsManager manager = getStudentManager();
		
		Invitation invitation = manager.getInvitationByCode("el96696");
		
		Assert.assertNull(invitation);
	}
	
	@Test
	public void findStudentByCode()
	{
		StudentsManager manager = getStudentManager();
		
		Student student = manager.getStudentByCode("el96696");
		
		Assert.assertNotNull(student);
	}
}
