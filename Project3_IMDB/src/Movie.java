import java.util.ArrayList;
import java.util.Collection;

public class Movie implements Node {
    String title;
    int year;
    ArrayList<Actor> cast;
    Movie(String t) {
        this.title = t;

    }
    Movie(String t, int y) {
        this.title = t;
        this.year = y;

    }

    String getTitle() {
        return title;
    }

    int getYear() {
        return year;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public Collection<? extends Node> getNeighbors() {
        return null;
    }
}
