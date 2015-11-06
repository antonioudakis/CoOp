package softeng.coop.business.authentication.sso;

import softeng.coop.business.authentication.*;
import dk.itst.oiosaml.sp.*;

public class SsoAuthenticationProvider implements IAuthenticationProvider
{

	@Override
	public IUser getAthenticatedUser()
	{
		UserAssertion userAssertion = UserAssertionHolder.get();
		
		if (userAssertion == null) return null;
		
		if (!userAssertion.isAuthenticated()) return null;
		
		return new SsoUser(userAssertion);
	}

}
