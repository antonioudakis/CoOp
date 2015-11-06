package softeng.caching;

/**
 * Exception thrown during cache operations.
 */
public class CacheException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	CacheException(String message)
	{
		super(message);
	}
}
