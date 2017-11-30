import java.io.IOException;
import java.util.HashMap;

public class IMDBMoviesGraph extends IMDBGraph implements Graph {

    IMDBMoviesGraph(String actorFileName, String actressFileName) throws IOException {
        super(actorFileName, actressFileName);
    }

    /**
     * @return Map of string names to Actors in graph
     */
    protected HashMap<String, Node> getActorMap(){
        return Actor.getInstancesAsNodes();
    }

    /**
     * @return Map of string names to Movies in graph
     */
    protected HashMap<String, Node> getMovieMap(){
        return nodeMap;
    }

}
