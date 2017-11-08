import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
    @Test
    public void leastRecentlyUsedIsCorrect() {
        DataProvider<Integer, String> provider = new DiskReader(); // Need to instantiate an actual DataProvider
        Cache<Integer, String> cache = new LRUCache<Integer, String>(provider, 3);
        assertEquals(cache.get(1), "1");
        System.out.println(cache);
        assertEquals(cache.getNumMisses(), 1);
        assertEquals(cache.get(2), "2");
        System.out.println(cache);
        assertEquals(cache.getNumMisses(), 2);
        assertEquals(cache.get(3), "3");
        System.out.println(cache);
        assertEquals(cache.getNumMisses(), 3);
        assertEquals(cache.get(2), "2");
        System.out.println(cache);
        assertEquals(cache.getNumMisses(), 3);
        assertEquals(cache.get(4), "4");
        System.out.println(cache);
        assertEquals(cache.getNumMisses(), 4);
        assertEquals(cache.get(1), "1");
        System.out.println(cache);
        assertEquals(cache.getNumMisses(), 5);
    }
}
