import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Code to test Project 3; you should definitely add more tests!
 */
public class GraphPartialTester {
    Graph actorsGraph, moviesGraph;
    GraphSearchEngine searchEngine;

    @Before
    /**
     * Instantiates the actors and movies graphs
     */ public void setUp() throws IOException {
        actorsGraph = new IMDBActorsGraph("Project3_IMDB/actorsmini.list", "Project3_IMDB/actresses.list");
        moviesGraph = new IMDBMoviesGraph("Project3_IMDB/actorsmini.list", "Project3_IMDB/actressesmini.list");
        System.out.println(actorsGraph);
        System.out.println(moviesGraph);
        //actorsGraph = new IMDBActorsGraph("actorsmini.list", "actressesmini.list");
        //moviesGraph = new IMDBMoviesGraph("actorsmini.list", "actressesmini.list");
        searchEngine = new GraphSearchEngineImpl();
    }

    @Test
    /**
     * Just verifies that the graphs could be instantiated without crashing.
     */ public void finishedLoading() {
        assertTrue(true);
        // Yay! We didn't crash
    }

    @Test
    /**
     * Verifies that a specific movie has been parsed.
     */ public void testSpecificMovie() {
        testFindNode(moviesGraph, "Movie1 (2001)");
    }

    @Test
    /**
     * Verifies that a specific actress has been parsed.
     */ public void testSpecificActress() {
        testFindNode(actorsGraph, "Actress2");
    }

    @Test
    /**
     * Verifies that a specific actress has been parsed.
     */ public void testSpecificActor() {
        testFindNode(actorsGraph, "Actor2");
    }

    @Test(timeout = 5000)
    /**
     * Verifies that there is no shortest path between a specific and actor and actress.
     */ public void assertNoShortestPath() {
        final Node actor1 = actorsGraph.getNodeByName("Actor1");
        final Node actress2 = actorsGraph.getNodeByName("Actress2");
        final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actress2);
        assertNull(shortestPath);  // there is no path between these people
    }

    @Test(timeout = 5000)
    /**
     * Verifies that there is a shortest path between a specific and actor and actress.
     */ public void assertShortestPath() {
        final Node actor1 = actorsGraph.getNodeByName("Actor2");
        final Node actress2 = actorsGraph.getNodeByName("Actress2");
        final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actress2);
        assertEquals(shortestPath.get(0), new Movie("Movie 2"));  // there is a path between these people
    }

    /**
     * Verifies that the specific graph contains a node with the specified name
     *
     * @param graph the Graph to search for the node
     * @param name  the name of the Node
     */
    private static void testFindNode(Graph graph, String name) {
        final Collection<? extends Node> nodes = graph.getNodes();
        boolean found = false;
        for (Node node : nodes) {
            if (node.getName().trim().equals(name)) {
                found = true;
            }
        }
        assertTrue(found);
    }
}
