import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */

public class LRUCache<T, U> implements Cache<T, U> {
    // Cached data
    private HashMap<T, Node<T, U>> map;
    // Max capacity of cache
    private int capacity;
    // Number of cache misses since instantiation
    private int numMisses = 0;

    // LinkedList End Pointers
    private Node<T, U> headNode = null;
    private Node<T, U> tailNode = null;

    // Data provider to query from on cache miss
    private DataProvider<T, U> provider;

    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    public LRUCache(DataProvider<T, U> provider, int capacity) {
        this.map = new HashMap<>(capacity * 2);
        this.capacity = capacity;
        this.provider = provider;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with the key
     */
    public U get(T key) {
        if (!map.containsKey(key)) { //cache miss
            U val = provider.get(key);
            if(val != null) {
                set(key, val); //add to cache
            }
            numMisses++;
            return val;
        }
        //cache hit
        final Node<T, U> n = map.get(key);
        removeNode(n);
        setHead(n);
        return n.getValue();
    }

    /**
     * Removes a node from the linked list that tracks queries
     *
     * @param n Node to remove
     */
    private void removeNode(Node<T, U> n) {
        if (n.previousNode != null) {
            n.previousNode.nextNode = n.nextNode;
        } else {
            headNode = n.nextNode;
        }
        if (n.nextNode != null) {
            n.nextNode.previousNode = n.previousNode;
        } else {
            tailNode = n.previousNode;
        }
    }


    /**
     * Moves a node to the head of the query list.
     *
     * @param n Node to add to head of list
     */
    private void setHead(Node<T, U> n) {
        n.nextNode = headNode;
        n.previousNode = null;
        if (headNode != null) {
            headNode.previousNode = n;
        }
        headNode = n;
        if (tailNode == null) {
            tailNode = headNode;
        }
    }


    /**
     * Sets the value of a node. if the node does not exist, creates one.
     * If the created node makes the list too big, drops the tail node.
     *
     * @param key Key to add to cache
     * @param value Value from provider corresponding to Key
     */
    private void set(T key, U value) {
        if (map.containsKey(key)) {
            final Node<T, U> oldNode = map.get(key);
            oldNode.nodeValue = value;
        } else {
            final Node<T, U> createdNode = new Node<>(key, value);
            if (map.size() >= capacity) {
                map.remove(tailNode.nodeKey);
                removeNode(tailNode);
            }
            setHead(createdNode);
            map.put(key, createdNode);
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
        String s = "";
        for (Node<T, U> cursor = headNode; cursor != null; cursor = cursor.nextNode) {
            s = s.concat(", " + cursor.getValue());
        }
        return s.substring(2);
    }


    /**
     * Defines a cache Node (key-value pair corresponding to provider data) with references to
     * adjacent queries for tracking order
     *
     * @param <X> Key type
     * @param <Y> Value type
     */
    private class Node<X, Y> {
        // NOTE: Since Node class is defined within LRUCache, private fields of nodes can be accessed directly
        // without getters/setters.
        private X nodeKey;
        private Y nodeValue;
        private Node<X, Y> previousNode, nextNode;

        public Node(X key, Y val) {
            this.nodeKey = key;
            this.nodeValue = val;
        }

        /**
         * @return The value corresponding to the node's key
         */
        public Y getValue() {
            return nodeValue;
        }
    }
}