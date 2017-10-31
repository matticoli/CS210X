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

    public void setMoments(ArrayList<Moment> moments) {
        this.moments = moments;
    }

    public ArrayList<LivingEntity> findMaximumCliqueOfFriends() {
        //returns a list containing the maximum clique of friends with whom the target person / pet could go out with, such that each of his/her friends is also friends with everyone else in the set.
        ArrayList<LivingEntity> clique = new ArrayList<>();
        for (LivingEntity ent : this.getFriends()) {
            if (isClique(new ArrayList<LivingEntity>() {{
                add(LivingEntity.this);
                add(ent);
                addAll(clique);
            }})) {
                clique.add(ent);
            }
        }
        return clique;
    }

    public static boolean isClique(ArrayList<LivingEntity> set) {
        for (LivingEntity a : set) {
            for (LivingEntity b : set) {
                if (!a.equals(b)) {
                    if (!a.getFriends().contains(b)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
