/**
 * 
 */
package softeng.coop.business;

import java.sql.SQLException;

/**
 * Thrown when an integrity constraint violation occurs during persistence
 * to the database. It corresponds to SQLSTATE codes 23XXX.
 * When known specific codes occur, a specific subclass of this exception 
 * is thrown. For example, when the code is 23502, a 
 * NotNullViolationException is thrown. 
 * Otherwise, this parent exception is thrown.  
 */
public class IntegrityConstraintViolationException extends DatabaseException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create.
	 */
	public IntegrityConstraintViolationException(SQLException cause)
	{
		this(formatMessage(cause), cause);
	}

	/**
	 * Used by subclasses in order for them to be able to modify the message.
	 * @param message The message of the exception, typically formatted by a 
	 * static method.
	 * @param cause The SQLException being the cause.
	 */
	protected IntegrityConstraintViolationException(String message, SQLException cause)
	{
		super(message, cause);
	}
	
	private static String formatMessage(SQLException cause)
	{
		return
			String.format(
					"Constraint violation error, ANSI code: %s, message: %s", 
					cause.getSQLState(), 
					cause.getMessage());
	}
}
