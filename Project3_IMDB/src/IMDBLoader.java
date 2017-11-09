import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public abstract class IMDBLoader {
    protected File actorFile;
    protected File actressFile;
    ArrayList<Actor> actors;
    ArrayList<Movie> movies;
    final int NUM_ELEMENTS_LINE_WITH_ACTOR_NAME = 2;
    final int NUM_ELEMENTS_LINE_WITHOUT_ACTOR_NAME = 1;

    IMDBLoader(String actorFileName, String actressFileName) throws IOException {
        actorFile = new File(actorFileName);
        actressFile = new File(actressFileName);
    loadFromFile(actorFile, actors);
    loadFromFile(actressFile, actors);

    }

    private void loadFromFile(File actorFile, ArrayList<Actor> actors) throws FileNotFoundException {
        Scanner listScanner = new Scanner(actorFile, "ISO-8859-1");
        String line = "";
        String[] lineElements;
        Actor currentActor = null;
        Movie currentMovie = null;
        boolean listStarted = false;
        while (listScanner.hasNext()) {
            line = listScanner.nextLine();
            if (line == "===============") {
                listStarted = true;
            }
            if (listStarted) {
                lineElements = line.split("\t");
                if (lineElements.length == NUM_ELEMENTS_LINE_WITH_ACTOR_NAME) {
                    currentActor = new Actor(lineElements[0]);
                    currentMovie = new Movie(lineElements[1], parseForYear(lineElements[2]));
                    currentActor.addMovie(currentMovie);
                    movies.add(currentMovie);
                    actors.add(currentActor);

                } else if (lineElements.length == NUM_ELEMENTS_LINE_WITHOUT_ACTOR_NAME) {
                    currentMovie = new Movie(lineElements[0], parseForYear(lineElements[1]));
                    currentActor.addMovie(currentMovie);
                    movies.add(currentMovie);
                } else {

                }
            }

        }
    }

    private int parseForYear(String lineElement) {
        return Integer.parseInt(lineElement.substring(lineElement.indexOf("(")).replaceAll("[^\\d.]", ""));
    }



}
