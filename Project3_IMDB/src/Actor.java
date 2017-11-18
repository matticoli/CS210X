import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Actor implements Node {

    /**
     * Name of actor
     */
    private String name;

    /**
     * Movies that the actor has participated in
     */
    private ArrayList<Movie> movies;

    /**
     * Maps actor names to the corresponding instance of Actor
     */
    private static HashMap<String, Node> instances;


    /**
     * @param name The name of the actor
     */
    private Actor(String name) {
        movies = new ArrayList<>();
        this.name = name;
    }


    /**
     * If an actor with a given name has been created, return the Actor instance.
     * Otherwise, create a new actor with the given name.
     *
     * @param name The name of the actor to get an Actor instance for
     * @return An Actor with name <@param name />
     */
    public static Actor getOrCreateActor(String name) {
        if(instances == null) {
            instances = new HashMap<>();
        } else if(instances.containsKey(name)) {
             return (Actor)instances.get(name);
        }
        Actor a = new Actor(name);
        instances.put(name, a);
        return a;
    }

    /**
     * @return Map of String Actor names to corresponding instances of Actor
     */
    public static HashMap<String,Node> getInstancesAsNodes() {
        if(instances == null) {
            instances = new HashMap<>();
        }
        return instances;
    }

    /**
     * @return Actor's name
     */
    @Override
    public String getName() {
        return name;
    }


    /**
     * Adds a movie to the list of movies that the actor has participated in
     *
     * @param a Movie to add to the actor's list of movies
     */
    public void addMovie(Movie a) {
        movies.add(a);
    }

    /**
     * Returns adjecent nodes (i.e. movies that the actor has participated in)
     *
     * @return Collection of Movies that the actor has participated in
     */
    @Override
    public Collection<? extends Node> getNeighbors() {
        return movies;
    }

    /**
     * Check if objects are the same
     *
     * @param o Object to compare
     * @return true if o is an Actor with the same name as this, else false
     */
    @Override
    public boolean equals(Object o) {
        if(! (o instanceof Actor)) {
            return false;
        }
        return ((Actor) o).getName().equals(name);
    }

    /**
     * @return Name of Actor
     */
    @Override
    public String toString() {
        return this.name;
    }
}
