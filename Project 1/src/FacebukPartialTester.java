import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This is a SUBSET of the unit tests with which we will grade your code.
 * <p>
 * Make absolute sure that your code can compile together with this tester!
 * If it does not, you may get a very low grade for your assignment.
 */
public class FacebukPartialTester {
    private Person _barack, _michelle, _kevin, _ina, _joe, _malia;
    private Pet _bo, _sunny;
    private Moment _meAndBarack;
    private ArrayList _michelleAndBarack, _michelleJoeBoAndMalia;
    private Person _buddy, _buddy2, _buddy3;

    @Before
    public void setUp() {
        initPeople();
        initPets();
        initGroups();
        initPossessions();
        initMoments();
        initTestIsClique();
    }

    private void initTestIsClique() {
        _buddy = new Person("Tom", new Image("Tom.jpg"));
        _buddy2 = new Person("Dick", new Image("Dick.jpg"));
        _buddy3 = new Person("Harry", new Image("Harry.jpg"));
        ArrayList<LivingEntity> friendsList = new ArrayList<>();
        friendsList.add(_buddy2);
        friendsList.add(_buddy3);
        _buddy.setFriends(friendsList);
        friendsList = new ArrayList<>();
        friendsList.add(_buddy);
        friendsList.add(_buddy3);
        _buddy2.setFriends(friendsList);
        friendsList = new ArrayList<>();
        friendsList.add(_buddy);
        friendsList.add(_buddy2);
        _buddy3.setFriends(friendsList);

    }

    private void initPeople() {
        _michelle = new Person("Michelle", new Image("Michelle.png"));
        _barack = new Person("Barack", new Image("Barack.png"));
        _kevin = new Person("Kevin", new Image("Kevin.png"));
        _ina = new Person("Ina", new Image("Ina.png"));
        _joe = new Person("Joe", new Image("Joe.png"));
        _malia = new Person("Malia", new Image("Malia.png"));
    }

    private void initPets() {
        _bo = new Pet("Bo", new Image("Bo.png"));
        _sunny = new Pet("Sunny", new Image("Sunny.png"));

        _bo.setOwner(_barack);
        _sunny.setOwner(_michelle);
    }

    private void initGroups() {
        // Kevin, Barack, and Ina
        final ArrayList michelleFriends = new ArrayList();
        michelleFriends.add(_kevin);
        michelleFriends.add(_barack);
        michelleFriends.add(_ina);
        michelleFriends.add(_bo);


        // Michelle and Barack
        _michelleAndBarack = new ArrayList();
        _michelleAndBarack.add(_michelle);
        _michelleAndBarack.add(_barack);

        // Michelle, Joe, Bo, and Malia
        _michelleJoeBoAndMalia = new ArrayList();
        _michelleJoeBoAndMalia.add(_michelle);
        _michelleJoeBoAndMalia.add(_joe);
        _michelleJoeBoAndMalia.add(_bo);
        _michelleJoeBoAndMalia.add(_malia);

        // Malia and Sunny
        final ArrayList maliaAndSunny = new ArrayList();
        maliaAndSunny.add(_malia);
        maliaAndSunny.add(_sunny);

        // Malia and Bo
        final ArrayList maliaAndBo = new ArrayList();
        maliaAndSunny.add(_malia);
        maliaAndSunny.add(_bo);

        // Michelle
        final ArrayList michelleList = new ArrayList();
        michelleList.add(_michelle);

        // Bo
        final ArrayList boList = new ArrayList();
        boList.add(_bo);

        // Set people's friend lists
        _michelle.setFriends(michelleFriends);
        _malia.setFriends(boList);
        _sunny.setFriends(boList);
        _barack.setFriends(michelleFriends);
        _kevin.setFriends(michelleList);
        _ina.setFriends(michelleList);

        // Finish configuring pets
        _bo.setFriends(boList);
        _sunny.setFriends(maliaAndBo);
        final ArrayList boAndSunny = new ArrayList();
        boAndSunny.add(_bo);
        boAndSunny.add(_sunny);
        _michelle.setPets(boAndSunny);
    }

    private void initPossessions() {
        final Possession boxingBag = new Possession("BoxingBag", new Image("BoxingBag.png"), 20.0f);
        boxingBag.setOwner(_michelle);
        final ArrayList michellePossessions = new ArrayList();
        michellePossessions.add(boxingBag);
        _michelle.setPossessions(michellePossessions);
    }

    private void initMoments() {
        // Smiles
        final ArrayList michelleAndBarackSmiles = new ArrayList();
        michelleAndBarackSmiles.add(0.25f);
        michelleAndBarackSmiles.add(0.75f);

        final ArrayList michelleJoeBoAndMaliaSmiles = new ArrayList();
        michelleJoeBoAndMaliaSmiles.add(0.2f);
        michelleJoeBoAndMaliaSmiles.add(0.3f);
        michelleJoeBoAndMaliaSmiles.add(0.4f);
        michelleJoeBoAndMaliaSmiles.add(0.5f);

        // Moments
        _meAndBarack = new Moment("Me & Barack", new Image("MeAndBarack.png"), _michelleAndBarack, michelleAndBarackSmiles);
        final Moment meJoeAndCo = new Moment("Me, Joe & co.", new Image("MeJoeAndCo.png"), _michelleJoeBoAndMalia, michelleJoeBoAndMaliaSmiles);

        final ArrayList michelleMoments = new ArrayList();
        michelleMoments.add(_meAndBarack);
        michelleMoments.add(meJoeAndCo);
        _michelle.setMoments(michelleMoments);

        final ArrayList barackMoments = new ArrayList();
        barackMoments.add(_meAndBarack);
        _barack.setMoments(barackMoments);

        final ArrayList joeMoments = new ArrayList();
        joeMoments.add(meJoeAndCo);
        _joe.setMoments(joeMoments);

        final ArrayList maliaMoments = new ArrayList();
        maliaMoments.add(meJoeAndCo);
        _malia.setMoments(maliaMoments);

        final ArrayList boMoments = new ArrayList();
        boMoments.add(meJoeAndCo);
        _bo.setMoments(boMoments);
    }

    @Test
    public void testEquals() {
        assertEquals(_michelle, new Person("Michelle", new Image("Michelle.png")));
        assertEquals(_michelle, new Person("Michelle", new Image("Michelle2.png")));  // should still work
        assertNotEquals(_michelle, _barack);
    }

    @Test
    public void testFindBestMoment() {
        assertEqualsa(_michelle.getOverallHappiestMoment(), _meAndBarack);
    }

    @Test
    public void testGetFriendWithWhomIAmHappiest() {
        assertEquals(_michelle.getFriendWithWhomIAmHappiest(), _barack);
    }

    @Test
    public void testIsClique() {

        ArrayList<LivingEntity> set = new ArrayList<>();
        set.add(_buddy);
        set.add(_buddy2);
        set.add(_buddy3);
        assertEquals(LivingEntity.isClique(set), true);
    }

    @Test
    public void testFindMaximumCliqueOfFriends() {
        ArrayList<LivingEntity> set = new ArrayList<>();
        set.add(_buddy2);
        set.add(_buddy3);
        assertEquals(_buddy.findMaximumCliqueOfFriends(), set);
    }

}
