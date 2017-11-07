import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
class Node<T, U> {
    T nodeKey;
    U nodeValue;
    Node previousNode;
    Node nextNode;

    Node(T key, U val) {
        this.nodeKey = key;
        this.nodeValue = val;
    }

    public U getNodeValue() {
        return nodeValue;
    }
}

public class LRUCache<T, U> implements Cache<T, U> {
    HashMap<T, Node> map = new HashMap<T, Node>();
    // Max capacity of cache
    private int capacity;
    // Number of cache misses since instantiation
    private int numMisses = 0;
    Node headNode = null;
    Node tailNode = null;
    // Data provider to query from on cache miss
    private DataProvider<T, U> provider;

    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    public LRUCache(DataProvider<T, U> provider, int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with the key
     */
    public U get(T key) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            removeNode(n);
            setHead(n);
            return (U) n.getNodeValue(); //should be returning U, is returning object. Parsing back to U
        }
        return null;
    }

    //removes specified node from the list.
    private void removeNode(Node n) {
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
    private void setHead(Node n) {
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
            Node oldNode = map.get(key);
            oldNode.nodeValue = value;
            removeNode(oldNode);
            setHead(oldNode);
        } else {
            Node createdNode = new Node(key, value);
            if (map.size() >= capacity) {
                map.remove(tailNode.nodeKey);
                removeNode(tailNode);
                setHead(createdNode);
            } else {
                setHead(createdNode);
            }
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
}
