import java.util.ArrayList;
import java.util.Collection;

public class Actor implements Node {
    private String name;

    private ArrayList<Movie> movies;

    public Actor(String name) {
        movies = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void addMovie(Movie a) {
        movies.add(a);
    }

    @Override
    public Collection<? extends Node> getNeighbors() {
        return movies;
    }

    public String toString() {
        return this.name;
    }
}
