import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class IMDBGraph implements Graph {
    protected File actorFile;
    protected File actressFile;

IMDBGraph(String actorFileName, String actressFileName) throws IOException{
    actorFile = new File(actorFileName);
    actressFile = new File(actressFileName);
}



    @Override
    public Collection<? extends Node> getNodes() {
        return null;
    }

    @Override
    public Node getNodeByName(String name) {
        return null;
    }
}
