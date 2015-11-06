package softeng.coop.business;

import java.sql.SQLException;

/**
 * Thrown during persistence of an entity when 
 * a field has a duplicate value violating a unique constraint.
 */
public class UniqueViolationException 
	extends IntegrityConstraintViolationException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create.
	 */
	public UniqueViolationException(SQLException cause)
	{
		super(formatMessage(cause), cause);
	}

	private static String formatMessage(SQLException cause)
	{
		return
			String.format(
					"A field has a duplicate value violating a unique constraint, ANSI code: %s, message: %s", 
					cause.getSQLState(), 
					cause.getMessage());
	}
}
