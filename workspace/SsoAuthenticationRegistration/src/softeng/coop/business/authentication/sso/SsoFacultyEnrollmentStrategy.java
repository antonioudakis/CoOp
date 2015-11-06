package softeng.coop.business.authentication.sso;

import javax.persistence.*;

import softeng.coop.business.authentication.*;

import softeng.coop.dataaccess.*;

public class SsoFacultyEnrollmentStrategy extends SsoEnrollmentStrategy
{
	@Override
	protected AuthenticatedUser createAuthenticatedUser(IUser user, EntityManager entityManager, Language preferredLanguage, Department department)
	{
		FacultyUser facultyUser = new FacultyUser();
		
		this.basicUserSetup(user, facultyUser, entityManager, preferredLanguage, department);
		
		entityManager.persist(facultyUser);
		
		return facultyUser;
	}
}
