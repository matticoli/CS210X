import java.util.ArrayList;

public class Person extends LivingEntity {

    private ArrayList<Possession> possessions;
    private ArrayList<Pet> pets;

    public Person(String name, Image image) {
        super(name, image);
        this.possessions = new ArrayList<>();
        this.pets = new ArrayList<>();
    }


    public ArrayList<Possession> getPossessions() {
        return possessions;
    }

    public void setPossessions(ArrayList<Possession> possessions) {
        this.possessions = possessions;
    }

    public void setPets(ArrayList pets) {
        this.pets = pets;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }
}
