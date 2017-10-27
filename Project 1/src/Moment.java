import java.util.ArrayList;
import java.util.HashMap;

public class Moment extends Entity {

    private HashMap<LivingEntity, Float> participants;
    private static ArrayList<Moment> instances;

    public Moment(String name, Image image, ArrayList<LivingEntity> participants, ArrayList<Float> smileValues) {
        super(name, image);
        this.participants = new HashMap<>(); // IntelliJ linter says not to specify types in constructor call\
        this.participants = new HashMap<>(); // IntelliJ linter says not to specify types in constructor call\

        // Load participants with corresponding smile values into HashMap
        for(int index = 0; index < participants.size(); index++) {
            this.participants.put(participants.get(index), smileValues.get(index));
        }

        if(this.instances == null) {
            this.instances = new ArrayList<>();
        }
        this.instances.add(this);
    }

    public float getHappiness(Person p) {
        if(participants.get(p)!=null) {
            return participants.get(p);
        } else {
            return 0.0f;
        }
    }

    public ArrayList<LivingEntity> getParticipants() {
        ArrayList<LivingEntity> participants = new ArrayList<LivingEntity>();
        participants.addAll(this.participants.keySet());
        return participants;
    }

    public static ArrayList<Moment> getInstances() {
        return Moment.instances;
    }
}
