import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public abstract class IMDBGraph implements Graph {
    protected File actorFile;
    protected File actressFile;
    protected HashMap<String, Node> nodeMap;

    /**
     *
     * @param actorFileName absolute path of the
     * @param actressFileName
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
        Actor actor = new Actor("");
        while (s.hasNextLine() && !s.nextLine().startsWith("----\t"));
        while(s.hasNextLine()) {
            final String line = s.nextLine();
            if(line.length() == 0) {
                continue;
            }
            if(line.charAt(0) != '\t') { //If this line starts a new actor's list of movies
                final String actorName = line.substring(0, line.indexOf('\t'));
                actor = new Actor(actorName);
                getActorHash().put(actorName, actor);
            }
            if(!line.contains("(TV)") && !line.contains("\"")) { //If this is not a TV show or TV movie
                final Movie movie;
                try {
                    final String movieTitle = line.substring(line.lastIndexOf('\t') + 1, line.indexOf(")") + 1);
                    if(getMovieHash().containsKey(movieTitle)) {
                        movie = (Movie) getMovieHash().get(movieTitle);
                        movie.addActor(actor);
                    }
                    else {
                        movie = new Movie(movieTitle);
                        movie.addActor(actor);
                        getMovieHash().put(movieTitle, movie);
                    }
                    actor.addMovie(movie);
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }

    protected abstract HashMap<String, Node> getActorHash();

    protected abstract HashMap<String, Node> getMovieHash();

    public void put(String key, Node value) {
        nodeMap.put(key, value);
    }

    @Override
    public Collection<? extends Node> getNodes() {
        return nodeMap.values();
    }

    @Override
    public Node getNodeByName(String name) {
        return nodeMap.get(name);
    }

    public String toString() {
        return nodeMap.toString();
    }
}
