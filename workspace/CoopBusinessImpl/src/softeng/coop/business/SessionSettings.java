package softeng.coop.business;

public class SessionSettings
{
	private boolean autoContext;

	/**
	 * If true, the entity manager used inside Session objects
	 * tries to set the current Context to the session's AuthorizationContext
	 * before each operation. If false, then an ordinary entity manager
	 * is used inside Session, which doesn't set any current context. In the
	 * latter case, the context should be manually set by the thread working with 
	 * the Session via CurrentContext.set method before any actions in order
	 * to enable Session security. 
	 */
	public boolean isAutoContext()
	{
		return autoContext;
	}

	public void setAutoContext(boolean autoContext)
	{
		this.autoContext = autoContext;
	}
}
