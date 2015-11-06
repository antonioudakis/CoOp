package softeng.coop.business.authentication.mock;

import softeng.coop.business.authentication.*;
import softeng.coop.business.*;

import org.springframework.beans.factory.*;

public class MockRegistrationStrategy implements IRegistrationStrategy
{
	private static BeanFactory factory = initializeFactory();

	@Override
	public IEnrollmentByAffiliationStrategy getEnrollmentStrategy(IUser user)
	{
		if (user.getPrimaryAffiliation().equals("student"))
		{
//			return new MockStudentEnrollmentStrategy();
			
			return factory.getBean("studentEnrollmentStrategy", MockStudentEnrollmentStrategy.class);
		}
		
		return null;
	}

	private static BeanFactory initializeFactory()
	{
		return FactoriesRepository.createChildFactory("mockFactory.xml");
	}

}
