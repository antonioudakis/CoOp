package softeng.coop.business;

/**
 * Thrown when there is a discrepancy between the entities of the session and the 
 * contents of the database.
 */
public class OptimisticLockViolationException
extends CoOpException
{
	private static final long serialVersionUID = 1L;
	
	private Object entity;

	public OptimisticLockViolationException(String message, Object entity, Throwable cause)
	{
		super(message, cause);
		
		this.entity = entity;
	}
	
	public OptimisticLockViolationException(String message, Object entity)
	{
		this(message, entity, null);
	}
	
	public Object getEntity()
	{
		return entity;
	}

}
