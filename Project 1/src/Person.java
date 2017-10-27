import java.util.ArrayList;
import java.util.HashMap;

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

    public Person getFriendWithWhomIAmHappiest() {
        HashMap<Person, Float> happiness = new HashMap<>();

        for(Moment m : Moment.getInstances()) {
            if(m.getParticipants().contains(this)) {
                for(LivingEntity e : m.getParticipants()) {
                    if(e instanceof Person) {
                        Person p = (Person)e;
                        if(!happiness.containsKey(p)) {
                            happiness.put(p,m.getHappiness(p));
                        } else {
                            happiness.replace(p, happiness.get(p) + m.getHappiness(this));
                        }
                    }
                }
            }
        }

        Person bff = null;
        float maxHappiness = 0;
        for(Person p : happiness.keySet()) {
            if(happiness.get(p) > maxHappiness) {
                maxHappiness = happiness.get(p);
                bff = p;
            }
        }
        return bff;
    }
}
