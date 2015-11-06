package softeng.coop.business;

public class SessionRepository
{
	private static SessionFactoryImpl sessionFactory = new SessionFactoryImpl();
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}
