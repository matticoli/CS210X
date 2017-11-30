import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public abstract class IMDBGraph implements Graph {

    protected File actorFile;
    protected File actressFile;
    protected HashMap<String, Node> nodeMap;

    /**
     *
     * @param actorFileName absolute path of the file containing actors
     * @param actressFileName absolute path of the file containing actresses
     * @throws IOException
     */
    IMDBGraph(String actorFileName, String actressFileName) throws IOException{
        nodeMap = new HashMap<>();
        actorFile = new File(actorFileName);
        actressFile = new File(actressFileName);
        parseFile(actorFile);
        parseFile(actressFile);
    }

    /**
     * Creates a graph of actor/actress nodes connected to movie nodes by parsing the IMDB file.
     * @param f either actorFile or actressFile
     * @throws FileNotFoundException
     */
    public void parseFile(File f) throws FileNotFoundException {
        final Scanner s = new Scanner(f, "ISO-8859-1");
        Actor actor = null;
        while (s.hasNextLine() && !s.nextLine().startsWith("----\t"));
        while(s.hasNextLine()) {
            final String line = s.nextLine();
            if(line.length() == 0) {
                continue;
            }
            if(line.charAt(0) != '\t') { //If this line starts a new actor's list of movies
                try {
                    final String actorName = line.substring(0, line.indexOf('\t'));
                    actor = Actor.getOrCreateActor(actorName);
                    getActorMap().put(actorName, actor);
                } catch (IndexOutOfBoundsException e) {
                    if(line.startsWith("------")) {
                        // Reached end of list, done now.
                        break;
                    } else {
                        //Malformed actor, keep going til the next one
                        while(s.hasNextLine() && s.nextLine().length() != 0);
                    }
                }
            }
            if(!line.contains("(TV)") && !line.contains("\"")) { //If this is not a TV show or TV movie
                final Movie movie;
                try {
                    final String movieTitle = line.substring(line.lastIndexOf('\t') + 1, line.indexOf(")") + 1);
                    if(getMovieMap().containsKey(movieTitle)) {
                        movie = (Movie) getMovieMap().get(movieTitle);
                        movie.addActor(actor);
                    }
                    else {
                        movie = Movie.getOrCreateMovie(movieTitle);
                        movie.addActor(actor);
                        getMovieMap().put(movieTitle, movie);
                    }
                    actor.addMovie(movie);
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }

    /**
     * @return Map of string names to Actors in graph
     */
    protected abstract HashMap<String, Node> getActorMap();

    /**
     * @return Map of string names to Movies in graph
     */
    protected abstract HashMap<String, Node> getMovieMap();

    /**
     * @return Collection of nodes in graph
     */
    @Override
    public Collection<? extends Node> getNodes() {
        return nodeMap.values();
    }

    /**
     * @param name the name of the requested Node
     * @return Node corresponding to name
     */
    @Override
    public Node getNodeByName(String name) {
        return nodeMap.get(name);
    }

    /**
     * @return Stringified map of string names to nodes
     */
    public String toString() {
        return nodeMap.toString();
    }
}
