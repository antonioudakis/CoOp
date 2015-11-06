package softeng.coop.business.authentication.sso;

import javax.persistence.*;

import softeng.coop.business.authentication.*;
import softeng.coop.dataaccess.*;

class SsoStudentEnrollmentStrategy extends SsoEnrollmentStrategy
{

	@Override
	protected AuthenticatedUser createAuthenticatedUser(
			IUser user, 
			EntityManager entityManager,
			Language preferredLanguage,
			Department department)
	{
//		Student student = new Student();
//		
//		student.setPreferredLanguage(preferredLanguage);
//		student.setDepartment(department);
//		
//		student.setUserName(user.getUserName());
//		student.setName(user.getFirstName());
//		student.setSurname(user.getLastName());
//		student.setGender(Gender.Unspecified);
//		
//		entityManager.persist(student);
//		
//		Tracking tracking = new Tracking();
//		tracking.setCreatedBy(student);
//		tracking.setModifiedBy(student);
//		Date now = new Date();
//		tracking.setCreated(now);
//		tracking.setModified(now);
//		
//		student.setTracking(tracking);
//		
//		Set<Role> studentRoles = 
//			getEntityReferences(
//					Role.class, 
//					String.format(
//							"/settings/roleAssignments/affiliation[@name=\"%s\"]/role", 
//							user.getPrimaryAffiliation().toLowerCase().trim()), 
//					entityManager);
//		
//		student.setRoles(studentRoles);
//		
//		student.setEmail(user.getEmail());
		
		Student student = new Student();
		
		this.basicUserSetup(
				user, 
				student, 
				entityManager, 
				preferredLanguage, 
				department);
		
		student.setSerialNumber(extractSerialNumber(user));
		
		entityManager.persist(student);
		
		return student;
	}

	private String extractSerialNumber(IUser user)
	{
		String userName = user.getUserName();
		
		int delimiterPosition = userName.indexOf('@');
		
		if (delimiterPosition == -1) return userName;
		
		return userName.substring(0, delimiterPosition);
	}
	
}
