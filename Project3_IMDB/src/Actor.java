import java.util.ArrayList;
import java.util.Collection;

public class Actor implements Node {
    ArrayList<Movie> appearances;
    String name;
    Actor(String n){
        this.name = n;
    }
    public void addMovie(Movie m){
        appearances.add(m);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends Node> getNeighbors() {
        return null;
    }
}
