import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
    @Test
    public void leastRecentlyUsedIsCorrect() {
        DataProvider<Integer, String> provider = null; // Need to instantiate an actual DataProvider
        Cache<Integer, String> cache = new LRUCache<Integer, String>(provider, 5);
    }
}
