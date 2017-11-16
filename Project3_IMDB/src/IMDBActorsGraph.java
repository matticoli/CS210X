import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class IMDBActorsGraph extends IMDBGraph implements Graph {
    private HashMap<String, Movie> movieNodes;
    IMDBActorsGraph(String actorFileName, String actressFileName) throws IOException {
        super(actorFileName, actressFileName);
        movieNodes = new HashMap<>();
        parseFile(actorFile);
        parseFile(actressFile);
    }
    private void parseFile(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f, "ISO-8859-1");
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
                put(actorName, actor);
            }
            if(!line.contains("(TV)") && !line.contains("\"")) { //If this is not a TV show or TV movie
                final String movieTitle = line.substring(line.lastIndexOf('\t') + 1, line.indexOf(")") + 1);
                final Movie movie;
                if(movieNodes.containsKey(movieTitle)) {
                    movie = (Movie) movieNodes.get(movieTitle);
                    movie.addActor(actor);
                }
                else {
                    movie = new Movie(movieTitle);
                    movie.addActor(actor);
                    movieNodes.put(movieTitle, movie);
                }
                actor.addMovie(movie);
            }
        }
    }
}
