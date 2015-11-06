package softeng.caching;

import java.util.*;

/**
 * A specialized LinkedHashMap that stores CacheEntry[K, V] items.
 * Uses the information in CacheEntry[K, V] to override the LRU purge mechanism of the base class. 
 * @param <K> The type of the key. Must implement 'hashCode' and 'equals' methods properly.
 * @param <V> The type of value being cached.
 */
public class CacheStorage<K, V> extends LinkedHashMap<K, CacheEntry<K, V>>
{
	private static final long serialVersionUID = 1L;
	
	private int maxEntriesCount;
	
	/**
	 * Create.
	 * @param maxEntriesCount The maximum number of entries.
	 */
	public CacheStorage(int maxEntriesCount)
	{
		this.maxEntriesCount = maxEntriesCount;
	}

	@Override
	public CacheEntry<K, V> put(K key, CacheEntry<K, V> value)
	{
		CacheEntry<K, V> entry = super.put(key, value);
		
		if (entry != null) return entry;
		
		int excess = this.size() - this.maxEntriesCount;
		
		if (excess > 7) removeLRUItems(excess);
		
		return null;
	}

	@Override
	public CacheEntry<K, V> get(Object key)
	{
		CacheEntry<K, V> entry = super.get(key);
		
		if (entry == null) return null;
		
		if (entry.getExpires() == null) return entry;
		
		Date now = new Date();
		
		if (now.compareTo(entry.getExpires()) > 0)
		{
			this.remove(entry.getKey());
			return null;
		}
		else
		{
			return entry;
		}
		
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<K, V>> eldest)
	{
		return false;
	}
	
	/**
	 * Remove a number of LRU items, if possible.
	 * @param count The count of LRU items to remove. 
	 */
	private void removeLRUItems(int count)
	{
		if (count == 0) return;
		
		Date now = new Date();
		
		ArrayList<K> keysToBeRemoved = new ArrayList<K>(count); 
		
		// First, try to find any expired entries to remove.
		for (CacheEntry<K, V> entry : this.values())
		{
			if (entry.getExpires() != null)
			{
				if (now.compareTo(entry.getExpires()) > 0)
				{
					keysToBeRemoved.add(entry.getKey());

					if (--count == 0) break;
				}
			}
		}
		
		// If the expired entries were not enough, remove the 
		// least recently used non-expirable entries up to remaining count.
		if (count > 0)
		{
			for (CacheEntry<K, V> entry : this.values())
			{
				if (entry.getExpires() == null)
				{
					keysToBeRemoved.add(entry.getKey());

					if (--count == 0) break;
				}
			}
		}

		for (K key : keysToBeRemoved)
		{
			this.remove(key);
		}
	}
	
}

