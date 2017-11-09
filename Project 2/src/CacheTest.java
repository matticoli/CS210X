import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
    @Test
    public void leastRecentlyUsedIsCorrect() {
        // Instantiate DataProvider
        DataProvider<Integer, String> provider = new DiskReader();
        // Create cache of size 3
        Cache<Integer, String> cache = new LRUCache<Integer, String>(provider, 3);

        // Query value in provider, but not in cache
        assertEquals(cache.get(1), "1");
        assertEquals(cache.getNumMisses(), 1);

        // Query another value in provider, but not in cache
        assertEquals(cache.get(2), "2");
        assertEquals(cache.getNumMisses(), 2);

        // Query another value in provider, but not in cache
        assertEquals(cache.get(3), "3");
        assertEquals(cache.getNumMisses(), 3);
        // Cache is now full

        // Query an item in cache
        assertEquals(cache.get(2), "2");
        // Misses should not have increased
        assertEquals(cache.getNumMisses(), 3);

        // Query item in provider but not cache, LRU must be evicted
        assertEquals(cache.get(4), "4");
        assertEquals(cache.getNumMisses(), 4);

        // Query previous LRU, ensure it was evicted correctly
        assertEquals(cache.get(1), "1");
        assertEquals(cache.getNumMisses(), 5);

        // Query key not in provider
        // This should be a cache miss, but the cache should remain unmodified
        assertEquals(cache.get(101), null);
        assertEquals(cache.getNumMisses(), 6);

        // Query LRU to make sure it was not evicted by query for non-existent key
        assertEquals(cache.get(2), "2");
        // Cache misses should not have increased
        assertEquals(cache.getNumMisses(), 6);
    }
}
