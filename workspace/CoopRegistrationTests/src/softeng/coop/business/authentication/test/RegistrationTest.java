package softeng.coop.business.authentication.test;

import java.util.Locale;

import org.junit.*;

import softeng.coop.business.*;
import softeng.coop.business.authentication.*;

public class RegistrationTest
{
	@Test
	public void authenticateUser()
	{
		IAuthenticationProvider authenticationProvider = AuthenticationFactory.getAuthenticationProvider();

		if (authenticationProvider == null) 
			throw new CoOpException("No authentication provider is configured in authenticationProvider.xml.");

		IUser user = authenticationProvider.getAthenticatedUser();
		
		Assert.assertNotNull("Should have found authenticated user.", user);
	}
	
	@Test
	public void getRegistrationStrategy()
	{
		IRegistrationStrategy registrationStrategy = 
			RegistrationFactory.getRegistrationStrategy();
		
		Assert.assertNotNull("Should have found registration strategy.", registrationStrategy);
	}
	
	@Test
	public void registerUser()
	{
		SessionFactory sessionFactory = SessionRepository.getSessionFactory();
		
		RegistrationResult result = sessionFactory.register(Locale.getDefault());
		
		StringBuilder messageBuilder = new StringBuilder();
		
		for (InformationMessage message : result.getInformationMessages())
		{
			messageBuilder.append('\r');

			switch (message.getLevel())
			{
				case Error:
					messageBuilder.append("Error - ");
					break;
					
				case Warning:
					messageBuilder.append("Warning - ");
					break;

				case Information:
					messageBuilder.append("Information - ");
					break;
			}
			
			messageBuilder.append(message.getText());
		}
		
		Assert.assertTrue(
				"Registration failed. Messages: " + messageBuilder.toString(),
				result.getStatus() == RegistrationStatus.RegistrationSucceeded);
		
	}
}
