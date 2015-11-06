package softeng.coop.business.authentication.mock;

import softeng.coop.business.authentication.*;

public class MockAuthenticationProvider implements IAuthenticationProvider
{

	@Override
	public IUser getAthenticatedUser()
	{
		return new MockUndergraduateUser();
	}

}
