import java.util.ArrayList;
import java.util.Collection;

public class Movie implements Node {
    private String name;

    private ArrayList<Actor> actors;

    public Movie(String name) {
        actors = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void addActor(Actor a) {
        actors.add(a);
    }

    @Override
    public Collection<? extends Node> getNeighbors() {
        return actors;
    }

    public String toString() {
        return this.name+ actors.toString();
    }
}
