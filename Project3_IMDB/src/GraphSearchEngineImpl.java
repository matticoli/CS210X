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
        final HashMap<Node, Integer> distancesToS = new HashMap<Node, Integer>(); //distance from each key to s.
        final boolean pathExists = findT(s, t, distancesToS); //fill distanceToS with relevant key, value pairs. Find out if a path exists.
        if(!pathExists) {
            return null;
        }
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
        boolean done = false;
        while(!done) {
            final Node n = toSearch.poll();
            final Integer d = distances.poll();
            if(n == null) { //All nodes connected to s have been examined, but t has not been found.
                return false; //No path exists from s to t.
            }
            if(n.equals(t)) {
                done = true;
            }
            if(!distancesToS.containsKey(n)) { //If n has not been visited yet:
                distancesToS.put(n, d); //n is distance d from s.
                for(Node n2: n.getNeighbors()) {
                    distances.add(d + 1); //n's neighbors are distance d+1 from s.
                    toSearch.add(n2);
                }
            }
        }
        return true;
    }
    /**
     * Generates a shortest path from s to t, assuming that one exists.
     * @param t the target node.
     * @param distancesToS hashMap filled with all nodes closer than t to s as keys. Corresponding values are the length of the shortest path from s to the key.
     * @return a shortest path in the form of a List of Node objects.
     */
    private static List<Node> makePath(Node t, HashMap<Node, Integer> distancesToS) {
        final List<Node> path = new ArrayList<Node>(distancesToS.get(t) + 1);
        path.set(path.size() - 1, t);
        for(int i = path.size() - 2; i >= 0; i--) { //backtracking
            for(Node n : path.get(i + 1).getNeighbors()) { //out of all of path.get(i + 1)'s neighbors,
                if(distancesToS.get(n) == i) { // which is 1 step closer to s?
                    path.set(i, n); //add it to the path
                    break;
                }
            }
        }
        return path;
    }
}
