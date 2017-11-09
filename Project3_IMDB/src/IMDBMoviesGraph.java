import java.io.IOException;

public class IMDBMoviesGraph extends IMDBGraph implements Graph {

    IMDBMoviesGraph(String actorFileName, String actressFileName) throws IOException {
        super(actorFileName, actressFileName);
    }
}
