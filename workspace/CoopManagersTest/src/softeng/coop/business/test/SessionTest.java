package softeng.coop.business.test;

import softeng.coop.dataaccess.*;
import org.junit.*;

public class SessionTest extends ManagerTestBase
{
	@Test
	public void TestAuthenticatedUserPolymorphism()
	{
		AuthenticatedUser user = this.session.getAuthenticatedUser();
		
		Assert.assertNotNull(
				"Session should be associated with authenticated user.", 
				user);
		
		System.out.printf("User's type: %s.", user.getClass().getSimpleName());
		
		Assert.assertTrue(
				"Authenticated user should have been of type Student.", 
				user instanceof Student);
	}
}
