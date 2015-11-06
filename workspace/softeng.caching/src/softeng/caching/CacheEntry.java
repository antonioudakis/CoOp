package softeng.caching;

import java.util.*;

/**
 * A cache entry. This is the item stored in CacheStorage[K, V]. It is supplied
 * by Cache[K, V].ILoadStrategy implementers during cache miss.
 * @param <K> The type of the key. Must implement 'hashCode' and 'equals' methods properly.
 * @param <V> The type of value being cached.
 */
public class CacheEntry<K, V>
{
	private V value;
	
	private K key;
	
	private Date expires;
	
	public CacheEntry(K key, V value, long expiration)
	{
		if (key == null) throw new IllegalArgumentException("Argument 'key' must not be null.");
		
		this.key = key;
		this.value = value;
		
		if (expiration != 0)
		{
			Date now = new Date();
			this.expires =  new Date(now.getTime() + expiration);
		}
	}
	
	public CacheEntry(K key, V value, Date expires)
	{
		if (key == null) throw new IllegalArgumentException("Argument 'key' must not be null.");
		if (value == null) throw new IllegalArgumentException("Argument 'value' must not be null.");

		this.key = key;
		this.value = value;
		this.expires = expires;
	}
	
	public Date getExpires()
	{
		return expires;
	}

	public V getValue()
	{
		return value;
	}

	public K getKey()
	{
		return key;
	}

}

