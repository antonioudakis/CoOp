package softeng.coop.business;
import java.sql.*;

import javax.persistence.*;

class TransactionHelper
{
	public static RuntimeException translateException(PersistenceException ex)
	{
		if (ex == null) throw new IllegalArgumentException("Argument 'ex' must not be null.");

		for (Throwable parentException = ex; parentException != null; parentException = parentException.getCause())
		{
//			if (parentException instanceof javax.validation.ValidationException)
//			{
//				return (RuntimeException)parentException;
//			}
			if (parentException instanceof OptimisticLockException)
			{
				OptimisticLockException optimisticLockException = (OptimisticLockException)parentException;
				
				return new OptimisticLockViolationException(
						optimisticLockException.getLocalizedMessage(), 
						optimisticLockException.getEntity(), 
						optimisticLockException);
			}
			if (parentException instanceof SQLException)
			{
				return translateException((SQLException)parentException);
			}
			else if (parentException instanceof CoOpException)
			{
				return (RuntimeException)parentException;
			}
		}
		
		return new CoOpException(
				String.format("Transaction handling failed, reason: %s", ex.getMessage()),
				ex);
	}
	
	public static RuntimeException translateException(SQLException exception)
	{
		if (exception == null) throw new IllegalArgumentException("Argument 'exception' must not be null.");
		
		String sqlState = exception.getSQLState();
		
		// The ANSI SQLSTATE codes are listed in
		// ftp://ftp.software.ibm.com/ps/products/db2/info/vr6/htm/db2m0/db2state.htm
		// The codes having values 23xxxx are dedicated to constraint violations.
		if (sqlState.substring(0, 2).equals("23"))
		{
			String subcode = sqlState.substring(2);
			
			if (subcode.equals("502"))
				return new NotNullViolationException(exception);
			else if (subcode.equals("503") || subcode.equals("511"))
				return new RelationshipViolationException(exception);
			else if (subcode.equals("505"))
				return new UniqueViolationException(exception);
			
			return new IntegrityConstraintViolationException(exception);
		}
		
		return new DatabaseException(exception);
	}
}
