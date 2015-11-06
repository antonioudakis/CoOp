package softeng.coop.business;

import java.sql.SQLException;

/**
 * Thrown when a foreign key relationship is violated.
 */
public class RelationshipViolationException 
	extends IntegrityConstraintViolationException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create.
	 */
	public RelationshipViolationException(SQLException cause)
	{
		super(formatMessage(cause), cause);
	}

	private static String formatMessage(SQLException cause)
	{
		return
			String.format(
					"Foreign key constraint violation, ANSI code: %s, message: %s", 
					cause.getSQLState(), 
					cause.getMessage());
	}
}
