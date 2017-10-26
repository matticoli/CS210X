import java.util.ArrayList;

public class LivingEntity extends Entity {
    private ArrayList<LivingEntity> friends;
    private ArrayList<Moment> moments;

    public LivingEntity(String name, Image image) {
        super(name, image);
    }

    public ArrayList<LivingEntity> getFriends() {
        return this.friends;
    }


    public void setFriends(ArrayList<LivingEntity> friends) {
        this.friends = friends;
    }

    public void setMoments(ArrayList<Moment> friends) {
        this.moments = moments;
    }
}
