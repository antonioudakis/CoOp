package softeng.coop.business;

import java.sql.SQLException;

/**
 * Thrown when an entity is persisted having at least one NULL field
 * violating a not-null constraint.
 */
public class NotNullViolationException 
	extends IntegrityConstraintViolationException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create.
	 */
	public NotNullViolationException(SQLException cause)
	{
		super(formatMessage(cause), cause);
	}

	private static String formatMessage(SQLException cause)
	{
		return
			String.format(
					"At least one null field violates a not-null contraint, ANSI code: %s, message: %s", 
					cause.getSQLState(), 
					cause.getMessage());
	}

}
