import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class IMDBGraph implements Graph {
    protected File actorFile;
    protected File actressFile;

    protected HashMap<String, Node> nodeMap;

    IMDBGraph(String actorFileName, String actressFileName) throws IOException{
        nodeMap = new HashMap<>();
        actorFile = new File(actorFileName);
        actressFile = new File(actressFileName);
    }

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
}
