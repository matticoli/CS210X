import java.io.IOException;

public class IMDBActorsGraph extends IMDBGraph implements Graph {
    IMDBActorsGraph(String actorFileName, String actressFileName) throws IOException {
        super(actorFileName, actressFileName);

    }
}
