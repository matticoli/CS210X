import java.util.ArrayList;
import java.util.HashMap;

public class Moment extends Entity {

    HashMap<LivingEntity, Float> participants;

    public Moment(String name, Image image, ArrayList<LivingEntity> participants, ArrayList<Float> smileValues) {
        super(name, image);
        this.participants = new HashMap<>(); // IntelliJ linter says not to specify types in constructor call\

        // Load participants with corresponding smile values into HashMap
        for(int index = 0; index < participants.size(); index++) {
            this.participants.put(participants.get(index), smileValues.get(index));
        }
    }
}
