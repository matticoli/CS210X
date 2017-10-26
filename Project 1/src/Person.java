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

    public Moment getOverallHappiestMoment() {
        float maxHappiness = 0;
        Moment happiestMoment = null;
        for(Moment m : Moment.getInstances()) {
            float happiness = 0;
            for(LivingEntity e : m.getParticipants()) {
                if(e instanceof Person) {
                    happiness += m.getHappiness(((Person)e));
                }
            }
            if(happiness > maxHappiness) {
                maxHappiness = happiness;
                happiestMoment = m;
            }
        }
        return happiestMoment;
    }
}
