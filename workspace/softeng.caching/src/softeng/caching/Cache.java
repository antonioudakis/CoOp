package softeng.caching;

import java.util.*;

/**
 * A cache of items of type V by key of type K. Class K should implement 'hashCode' and 'equals' methods properly.
 * During cache misses, items are loaded using a supplied strategy of type ILoadStrategy[K, V]. 
 * The strategy builds the item based on the requested key of type K.
 * @param <K> The type of the key. Must implement 'hashCode' and 'equals' methods properly.
 * @param <V> The type of value being cached.
 */
public class Cache<K, V>
{
	/**
	 * The strategy used to load an item during a cache miss.
	 * @param <K> The type of the key. Must implement 'hashCode' and 'equals' methods properly.
	 * @param <V> The type of value being cached.
	 */
	public interface ILoadStrategy<K, V>
	{
		/**
		 * Build a cache entry that corresponds to the supplied key.
		 * @param key The key of the item.
		 * @return Returns an cache entry having the key, value and expiration fields set. Must not be null.
		 */
		CacheEntry<K, V> loadItem(K key);
	}
	
	/**
	 * This holds a synchronized CacheStorage[K, V]. 
	 */
	private Map<K, CacheEntry<K, V>> storage;
	
	/**
	 * The load strategy used during cache miss. 
	 */
	private ILoadStrategy<K, V> loadStrategy;
	
	/**
	 * @param maxEntriesCount The maximum entries to be held in the cache.
	 * @param loadStrategy The entry load strategy to be used during cache miss. 
	 */
	public Cache(int maxEntriesCount, ILoadStrategy<K, V> loadStrategy)
	{
		if (loadStrategy == null) throw new IllegalArgumentException("Argument 'loadStrategy' must not be null.");
		
		this.storage = Collections.synchronizedMap(new CacheStorage<K, V>(maxEntriesCount));
		this.loadStrategy = loadStrategy;
	}
	
	/**
	 * Get an item by key. If it is a cache miss, the item will be created
	 * and inserted in the cache automatically.
	 * @param key The key specifying the item.
	 * @return Returns the item corresponding to the key.
	 */
	public V get(K key)
	{
		CacheEntry<K, V> entry = this.storage.get(key);
		
		if (entry == null)
		{
			// Cache miss.
			entry = this.loadStrategy.loadItem(key);
			
			if (entry == null) throw new CacheException("The cache item load strategy should not return null entry.");
			
			this.storage.put(key, entry);
		}
		
		return entry.getValue();
	}
	
	/**
	 * Flush all the contents of the cache.
	 */
	public void flushAll()
	{
		this.storage.clear();
	}
	
	/**
	 * Flush the cached item under the specified key, if any.
	 */
	public void flush(K key)
	{
		this.storage.remove(key);
	}
}
