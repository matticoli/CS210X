import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
    // Data provider to query from on cache miss
    private DataProvider<T,U> provider;
    // Max capacity of cache
    private int capacity;
    // Number of cache misses since instantiation
    private int numMisses = 0;

    // Cached Data
    private HashMap<T,U> cache;
    // Maps key to number of recent queries for key within last [capacity] queries
    private HashMap<T,Integer> queries;
    // FIFO Queue containing last [capacity] queried keys
    private Queue<T> queue;


    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    public LRUCache(DataProvider<T, U> provider, int capacity) {
        cache = new HashMap<T,U>(capacity);
        queue = new LinkedList<T>(capacity);
        this.provider = provider;
        this.capacity = capacity;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with the key
     */
    public U get(T key) {
        U x = null;
        if (!cache.containsKey(key)) {
            // Cache Miss
            numMisses++;
            if (cache.size() == capacity) {
                // Remove tail key in queue from cache if there isn't a more recent call
                maybeRemove(queue.poll());
            }
            queue.add(key);
            cache.put(key, provider.get(key));
            if(queries.containsKey(key)) {
                queries.replace(key, queries.get(key)+1);
            } else {
                queries.put(key, 1);
            }
        }
        else{
            x = cache.get(key);
            queue.poll();
            queue.add(key);
        }
        return x;
    }

    public void maybeRemove(T key) {
        // Note: Queries should always contain key, as an item is never added to the cache without being tracked
        int recentQueries = queries.get(key);
        if(recentQueries<=0) {
            cache.remove(key);
            queries.remove(key);
        } else {
            queries.replace(key, recentQueries-1);
        }
    }

    /**
     * Returns the number of cache misses since the object's instantiation.
     *
     * @return the number of cache misses since the object's instantiation.
     */
    public int getNumMisses() {
        return numMisses;
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
