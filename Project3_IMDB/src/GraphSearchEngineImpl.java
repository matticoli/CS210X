import java.util.*;

public class GraphSearchEngineImpl implements GraphSearchEngine {

    /**
     * Finds a shortest path, if one exists, between nodes s and
     * t. The path will be a list: (s, ..., t). If no
     * path exists, then this method will return null.
     *
     * @param s the start node.
     * @param t the target node.
     * @return a shortest path in the form of a List of Node objects
     * or null if no path exists.
     */
    @Override
    public List<Node> findShortestPath(Node s, Node t) {
        HashMap<Node, Integer> distancesToS = new HashMap<>(); //distance from each key to s.
        if(!findT(s, t, distancesToS)) { //Fill distanceToS with relevant key-value pairs.
            return null; //if path does not exist
        }
        // Path does exist, backtrack:
        return makePath(t, distancesToS);
    }

    /**
     * Fills distancesToS with all nodes closer than t to s as keys.
     * Corresponding values will be the length of the shortest path from s to the key.
     * @param s the start node.
     * @param t the target node.
     * @param distancesToS empty hashmap. All nodes closer than t to s will be added as keys to distancesToS.
     * @return true if a path exists from s to t. false otherwise
     */
    private static boolean findT(Node s, Node t, HashMap<Node, Integer> distancesToS) {
        final Queue<Node> toSearch = new LinkedList<Node>(); //nodes that need to be examined
        final Queue<Integer> distances = new LinkedList<Integer>(); //distances from s to each node in toSearch
        toSearch.add(s);
        distances.add(0);

        Node n;
        do {
            n = toSearch.poll();
            final Integer d = distances.poll();
            if(n == null) { //All nodes connected to s have been examined, but t has not been found.
                return false; //No path exists from s to t.
            }

            if(!distancesToS.containsKey(n)) { //If n has not been visited yet:
                distancesToS.put(n, d); //n is distance d from s.
                for(Node neighbor: n.getNeighbors()) {
                    distances.add(d + 1); //n's neighbors are distance d+1 from s.
                    toSearch.add(neighbor);
                }
            }
        } while(! n.equals(t));
        return true;
    }
    /**
     * Generates a shortest path from s to t, assuming that one exists.
     * @param t the target node.
     * @param distancesToS hashMap filled with all nodes closer than t to s as keys. Corresponding values are the length of the shortest path from s to the key.
     * @return a shortest path in the form of a List of Node objects.
     */
    private List<Node> makePath(Node t, HashMap<Node, Integer> distancesToS) {
        final List<Node> path = new LinkedList<Node>();
        path.add(t);
        for(int i = distancesToS.get(t) - 1; i >= 0; i--) { //backtracking
            for(Node n : path.get(0).getNeighbors()) { //Out of all of path.get(i + 1)'s neighbors,
                if(distancesToS.get(n) == i) { //which is 1 step closer to s?
                    path.add(0, n); //Add it to the path.
                    break;
                }
            }
        }
        return path;
    }
}
