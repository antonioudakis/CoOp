package softeng.coop.business.authentication.test;

import org.junit.*;

import softeng.coop.business.*;

public class SessionTest
{
	@Before
	public void registerStudent()
	{
		RegistrationTest registrationTest = new RegistrationTest();
		
		registrationTest.registerUser();
	}
	
	@Test
	public void loginTest()
	{
		SessionFactory sessionFactory = SessionRepository.getSessionFactory();
		
		Session session = sessionFactory.login();

		Assert.assertNotNull("User login should have succeeded.", session);
	}
	
}
