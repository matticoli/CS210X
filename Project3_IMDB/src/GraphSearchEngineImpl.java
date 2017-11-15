import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphSearchEngineImpl implements GraphSearchEngine {
    @Override
    public List<Node> findShortestPath(Node s, Node t) {
        HashMap<Node, Integer> distancesToS = new HashMap<Node, Integer>();
        Queue<Node> toSearch = new LinkedList<Node>();
        toSearch.add(s);
        boolean done = false;
        while(!done) {

        }
        return null;
    }
}
