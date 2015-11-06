package softeng.coop.business.test;

import softeng.coop.business.*;
import org.junit.*;

public class ManagerTestBase
{
	protected Session session;
	
	@Before
	public void openSession()
	{
		SessionFactory sessionFactory = SessionRepository.getSessionFactory();
		
		this.session = sessionFactory.login();
	}
	
	@After
	public void closeSession()
	{
		this.session.dispose();
	}

}
