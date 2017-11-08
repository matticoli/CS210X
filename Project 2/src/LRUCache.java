import java.util.HashMap;
//TODO: Optimize variable privacy and add setters/getters?

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */


public class LRUCache<T, U> implements Cache<T, U> {
    private HashMap<T, Node<T, U>> map;
    // Max capacity of cache
    private int capacity;
    // Number of cache misses since instantiation
    private int numMisses = 0;
    private Node<T, U> headNode = null;
    private Node<T, U> tailNode = null;
    // Data provider to query from on cache miss
    private DataProvider<T, U> provider;

    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    public LRUCache(DataProvider<T, U> provider, int capacity) {
        map = new HashMap<>(capacity * 2);
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
            set(key, provider.get(key)); //add to cache
            numMisses++;
        }
        //cache hit
        final Node<T, U> n = map.get(key);
        removeNode(n);
        setHead(n);
        return n.getNodeValue();
    }

    //removes specified node from the list.
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


    //moves a node to the head of the list.
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


    //sets the value of a node. if the node does not exist, creates one.
    //if the created node makes the list too big, drops the tail node.
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

    public String toString() {
        String s = "";
        for (Node<T, U> cursor = headNode; cursor != null; cursor = cursor.nextNode) {
            s = s.concat(", " + cursor.getNodeValue());
        }
        return s.substring(2);
    }

    private class Node<X, Y> {
        X nodeKey;
        Y nodeValue;
        Node<X, Y> previousNode;
        Node<X, Y> nextNode;

        public Node(X key, Y val) {
            this.nodeKey = key;
            this.nodeValue = val;
        }

        public Y getNodeValue() {
            return nodeValue;
        }
    }
}