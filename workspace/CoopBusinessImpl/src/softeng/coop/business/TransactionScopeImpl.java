package softeng.coop.business;

public class TransactionScopeImpl extends TransactionScope
{
	private Session session;
	private boolean shouldCommit;
	
	public TransactionScopeImpl(Session session)
	{
		if (session == null) 
			throw new IllegalArgumentException("Argument 'session' must not be null.");
		
		this.shouldCommit = false;
		this.session = session;
	}

	@Override
	public void dispose()
	{
		this.session.exitTransactionScope(this.shouldCommit);
	}

	@Override
	public void commit()
	{
		this.shouldCommit = true;
	}

}
