import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Movie implements Node {

    /**
     * Name of movie
     */
    private String name;

    /**
     * Actors in movie
     */
    private ArrayList<Actor> actors;

    /**
     * Maps movie names to corresponding instance of Movie
     */
    private static HashMap<String, Node> instances;

    /**
     * @param name Name of movie
     */
    public Movie(String name) {
        actors = new ArrayList<>();
        this.name = name;
    }

    /**
     * If an movie with a given name has been created, return the Movie instance.
     * Otherwise, create a new movie with the given name.
     *
     * @param name The name of the movie to get an Movie instance for
     * @return An Movie with name <@param name />
     */
    public static Movie getOrCreateMovie(String name) {
        if(instances == null) {
            instances = new HashMap<>();
        } else if(instances.containsKey(name)) {
            return (Movie)instances.get(name);
        }
        Movie m = new Movie(name);
        instances.put(name, m);
        return m;
    }

    /**
     * @return Map of String Movie names to corresponding instances of Movie
     */
    public static HashMap<String,Node> getInstancesAsNodes() {
        if(instances == null) {
            instances = new HashMap<>();
        }
        return instances;
    }

    /**
     * @return Movie name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Add an actor to the cast of the movie
     *
     * @param a Actor to add to movie's actor list
     */
    public void addActor(Actor a) {
        actors.add(a);
    }

    /**
     * Returns adjecent nodes
     *
     * @return Collection of Actors in movie
     */
    @Override
    public Collection<? extends Node> getNeighbors() {
        return actors;
    }

    /**
     * Check if objects are the same
     *
     * @param o Object to compare
     * @return true if o is an Movie with the same name as this, else false
     */
    @Override
    public boolean equals(Object o) {
        if(! (o instanceof Movie)) {
            return false;
        }
        return ((Movie) o).getName().equals(name);
    }

    /**
     * @return Name of Movie
     */
    public String toString() {
        return this.name;
    }
}
