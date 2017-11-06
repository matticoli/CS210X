import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    private DataProvider<T,U> provider;
    private int capacity;
    private int numMisses = 0;
    private HashMap<T,U> dataset;
    private LinkedList<T> queue;


    public LRUCache(DataProvider<T, U> provider, int capacity) {
        dataset = new HashMap<T,U>(capacity);
        queue = new LinkedList<T>();
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
        if (!dataset.containsKey(key)){
            if (dataset.size() == capacity) {
                    dataset.remove(queue.remove());
            }
          x = provider.get(key);
            dataset.put(key,x);
            queue.add(key);
            numMisses++;
        }
        else{
            x = dataset.get(key);
            queue.remove(queue.indexOf(key));
            queue.add(key);
        }
        return x;  // TODO -- implement!
    }

    /**
     * Returns the number of cache misses since the object's instantiation.
     *
     * @return the number of cache misses since the object's instantiation.
     */
    public int getNumMisses() {
        return numMisses;
    }
}
