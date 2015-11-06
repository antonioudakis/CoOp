package softeng.coop.business.contextjpa;

import softeng.coop.business.*;

/**
 * Holds the current AuthorizationContext for a thread.
 * Static usage, no instances allowed.
 */
public class CurrentContext
{
	private static final ThreadLocal<Session> threadLocal =
		new ThreadLocal<Session>();
	
	/**
	 * Prevent instance creation.
	 */
	private CurrentContext()
	{
		
	}
	
	public static Session get()
	{
		return threadLocal.get();
	}
	
	public static void set(Session context)
	{
		threadLocal.set(context);
	}
}
