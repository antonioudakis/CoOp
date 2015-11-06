package softeng.coop.business.authentication.sso;

import softeng.coop.business.authentication.*;

public class SsoRegistrationStrategy implements IRegistrationStrategy
{
	@Override
	public IEnrollmentByAffiliationStrategy getEnrollmentStrategy(IUser user)
	{
		return XmlSettings.getEnrollmentStrategy(user);
	}
}
