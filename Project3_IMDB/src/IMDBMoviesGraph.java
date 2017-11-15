import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class IMDBMoviesGraph extends IMDBGraph implements Graph {

    IMDBMoviesGraph(String actorFileName, String actressFileName) throws IOException {
        super(actorFileName, actressFileName);
        parseFile(actorFile);
        parseFile(actressFile);
    }
    private static void parseFile(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f, "ISO-8859-1");
        String currentActor;
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.charAt(0) != '\t') { //If this line starts a new actor's list of movies
                currentActor = line.substring(0, line.indexOf('\t'));
            }
            if(!line.contains("(TV)") && !line.contains("\"")) { //If this is not a TV show or TV movie
                String movieTitle = line.substring(line.lastIndexOf('\t'), line.indexOf(")") + 1);
            }
        }
    }

}
