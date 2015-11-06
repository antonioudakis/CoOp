package softeng.coop.business;

import java.sql.SQLException;

/**
 * Specifies that an exception occurred during a database operation.
 */
public class DatabaseException extends CoOpException
{
	private static final long serialVersionUID = 1L;
	
	private String sqlState;

	/**
	 * Create.
	 */
	public DatabaseException(SQLException cause)
	{
		this(formatMessage(cause), cause);
	}

	
	/**
	 * Used by subclasses in order for them to be able to modify the message.
	 * @param message The message of the exception, typically formatted by a 
	 * static method.
	 * @param cause The SQLException being the cause.
	 */
	protected DatabaseException(String message, SQLException cause)
	{
		super(message, cause);
		
		this.sqlState = cause.getSQLState();
	}
	
	/**
	 * The ANSI SQLSTATE error code.
	 */
	public String getSQLState()
	{
		return this.sqlState;
	}
	
	private static String formatMessage(SQLException cause)
	{
		if (cause == null) 
			throw new IllegalArgumentException("Argument 'cause' must not be null.");
		
		return
			String.format(
					"Database error, ANSI code: %s, message: %s", 
					cause.getSQLState(), 
					cause.getMessage());
	}

}
