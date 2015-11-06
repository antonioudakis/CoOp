package softeng.coop.business;

/**
 * Occurs when access is denied to an entity of the application's domain.
 */
public class AccessDeniedException extends CoOpException
{
	private static final long serialVersionUID = 1311024347494136366L;
	
	private Object entity;
	
	public AccessDeniedException(Object entity)
	{
		super(
				String.format(
						"Access to entity of type '%s' is denied", 
						entity.getClass().getSimpleName()));
		
		this.entity = entity;
	}

	
	/**
	 * The entity whose access is denied.
	 * SECURITY NOTE: Don't offer this back to main application, log instead.
	 */
	public Object getEntity()
	{
		return entity;
	}
	
}
