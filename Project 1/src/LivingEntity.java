import jdk.jshell.spi.ExecutionControl;

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

    public ArrayList findMaximumCliqueOfFriends() throws ExecutionControl.NotImplementedException {
        //returns a list containing the maximum clique of friends with whom the target person / pet could go out with, such that each of his/her friends is also friends with everyone else in the set.
        throw new ExecutionControl.NotImplementedException("Method Not Implemented.");
    }

    public static boolean isClique(ArrayList<LivingEntity> set){
        for (LivingEntity a : set) {
            for (LivingEntity b : set) {
                if( !a.getFriends().contains(b)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static ArrayList<LivingEntity> mutualFriendsFinder(LivingEntity livingEntity, LivingEntity livingEntity1) {
        ArrayList<LivingEntity> mutual = new ArrayList<>();
        for (LivingEntity friend : livingEntity.getFriends()) {
            if (livingEntity1.getFriends().contains(friend)) {
                mutual.add(friend);
            }
        }
        return mutual;
    }
}
