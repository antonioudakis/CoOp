package softeng.coop.business.students.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import softeng.coop.business.TransactionScope;
import softeng.coop.business.students.StudentsManager;
import softeng.coop.business.students.StudentsWriterManager;
import softeng.coop.business.test.ManagerTestBase;
import softeng.coop.dataaccess.Group;
import softeng.coop.dataaccess.Invitation;
import softeng.coop.dataaccess.Multilingual;
import softeng.coop.dataaccess.Registration;

public class StudentsWriterManagerTest extends ManagerTestBase
{
	@Test
	public void acquireManager()
	{
		getManager();
	}
	
	private StudentsWriterManager getManager() 
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		StudentsManager manager = session.getStudentsManager();
		
		Assert.assertNotNull("aqcuisition of locations manager failed", manager);
		
		Assert.assertTrue("Manager should be writable", manager.isWriteable());
		
		StudentsWriterManager writerManager = manager.getWriterManager();
		
		Assert.assertNotNull("Should be able to get the writer manager.", writerManager);
		
		return writerManager;
	}
	
	@Test
	public void createGroupAndInviteSelf()
	{	
		StudentsWriterManager manager = getManager();
		Group group = new Group();
		group.setComments("My 'createGroupAndInviteSelf' Junit Created Group");
		group.setPendingFormation(true);
		group.setRegistrations(new HashSet<Registration>());
		Registration registration = manager.getRegistration("el96696", session.getDefaultCoop());
		group.getRegistrations().add(registration);
		manager.persistGroup(group);
		
		registration.setGroup(group);
		
		//manager.persistRegistration(registration);
		
		List<String> recepients = new ArrayList<String>();
		
		recepients.add("el96696");
		
		Multilingual text = new Multilingual();
		
		manager.setDefaultLiteral(text, "hello");
		
		Invitation invitation = new Invitation();
		
		invitation.setDate(new Date());
		invitation.setRecepients(new HashSet<Registration>());
		invitation.getRecepients().add(registration);
		invitation.setSender(registration);
		invitation.setText(text);
		invitation.setGroup(group);
		
		manager.postInvitation(invitation);
	}
	
	@Test
	public void createGroupAndAddToRegistration()
	{
		StudentsWriterManager manager = getManager();
		
		TransactionScope scope = manager.beginTransaction();
		
		try
		{
			Group group = new Group();
			group.setComments("My 'createGroupAndAddToRegistration' Junit Created Group");
			group.setPendingFormation(true);
			group.setRegistrations(new HashSet<Registration>());
			Registration registration = manager.getRegistration("el96696", session.getDefaultCoop());
			group.getRegistrations().add(registration);
			
			
			registration.setGroup(group);
			
			manager.persistRegistration(registration);
			
			manager.persistGroup(group);
			
			scope.commit();
		}
		finally
		{
			scope.dispose();
		}
		
		
	}
	
//	@Test
//	public void createStudentAddRegistrationInviteAndAcceptInvitation()
//	{
//		StudentsWriterManager manager = getManager();
//		
//		TransactionScope transactionScope = manager.beginTransaction();
//		
//		Student student = new Student();
//		
//		try
//		{
//			
//			student.setAddresses(new HashSet<Address>());
//			Address stAddress = new Address();
//			stAddress.setCity("Glyka nera");
//			stAddress.setCountry("Greece");
//			stAddress.setStreet("Skais 12");
//			stAddress.setPoBox("14565");
//			GeoLocation geo = new GeoLocation();
//			geo.setLatitude(0);
//			geo.setLongtitude(0);
//			stAddress.setGeoLocation(geo);
//			student.setEmail("emm@gmail.com");
//			
//			student.getAddresses().add(stAddress);
//			
//			stAddress.setPerson(student);
//			
//			student.setPreferredLanguage(this.session.getCurrentUser().getPreferredLanguage());
//			
//			student.setDefaultCoOp(this.session.getDefaultCoop());
//			
//			student.setDepartment(this.session.getCurrentUser().getDepartment());
//			
////			Nationality nationality = new Nationality();
////			nationality.setName("Ελληνική");
////			nationality.setStudents(new HashSet<Student>());
////
////			nationality.getStudents().add(student);
////			student.setNationality(nationality);
//			
//			student.setName("Sakis");
//			student.setSurname("Moralis");
//			student.setSerialNumber("el94647");
//			
//			student.setRegistrations(new HashSet<Registration>());
//			Registration registration = new Registration();
//			registration.setCoop(this.session.getCurrentUser().getDefaultCoOp());
//			registration.setStudent(student);
//			
//			manager.persistRegistration(registration);
//			
//			student.getRegistrations().add(registration);
//			
//			manager.persistStudent(student);			
//			
//			transactionScope.commit();
//			
//		}
//		finally
//		{
//			transactionScope.dispose();
//		}
//		
//		
//		TransactionScope newScope = manager.beginTransaction();
//		
//		try
//		{
//			EntityManager entityManager = this.session.getEntityManager();
//
//			//find group of user 
//			TypedQuery<Group> getgroupbystudentcode = 
//				entityManager.createQuery(
//						"SELECT gr FROM Group gr INNER JOIN gr.registrations reg INNER JOIN reg.student st WHERE st.serialNumber = :snumber"
//						, Group.class).setParameter("snumber", "el96696");
//			
//			
//			Group group = QueryHelper.getFirstOrDefault(getgroupbystudentcode);
//			
//			List<String> recepients = new ArrayList<String>();
//			
//			recepients.add("el94647");
//			
//			Multilingual text = new Multilingual();
//			
//			manager.setDefaultLiteral(text, "hello");
//			
//			manager.postInvitation("el96696", recepients, group, text);
//			
//			
//			newScope.commit();
//		}
//		finally
//		{
//			newScope.dispose();
//		}
//	
//		
//		
//		TransactionScope new2scope = manager.beginTransaction();
//			
//		try
//		{
//			//find invitation
//			Invitation invitation = manager.getInvitationByCode("el94647");
//			manager.acceptInvitation(student, invitation);
//			
//			new2scope.commit();
//		}
//		finally
//		{
//			new2scope.dispose();
//		}
//		
//	}
//	
	
	@Test
	public void deleteStudent()
	{
		StudentsWriterManager manager = getManager();
		
		manager.deleteStudent(1);
	}
	
	@Test
	public void closeAllGroups()
	{
		StudentsWriterManager manager = getManager();
		
		manager.closeGroups(session.getAuthenticatedUser().getDefaultCoOp());
	}
	
}
