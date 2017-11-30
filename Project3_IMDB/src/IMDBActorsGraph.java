import java.io.IOException;
import java.util.HashMap;

public class IMDBActorsGraph extends IMDBGraph implements Graph {

    /**
     * Initialize a new ActorsGraph
     *
     * @param actorFileName Path to actors.list
     * @param actressFileName Path to actresses.list
     *
     * @throws IOException
     */
    IMDBActorsGraph(String actorFileName, String actressFileName) throws IOException {
        super(actorFileName, actressFileName);
    }

    /**
     * @return Map of string names to Actors in graph
     */
    @Override
    protected HashMap<String, Node> getActorMap(){
        return nodeMap;
    }

    /**
     * @return Map of string names to Movies in graph
     */
    @Override
    protected HashMap<String, Node> getMovieMap(){
        return Movie.getInstancesAsNodes();
    }

}
