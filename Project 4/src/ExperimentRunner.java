import com.cs210x.CPUClock;
import com.cs210x.Collection210X;
import com.cs210x.MysteryDataStructure;

import java.util.Random;

/**
 * Class to deduce the identity of mystery data structures.
 */
public class ExperimentRunner {

    /**
     * Number of mystery data structures to fetch
     */
    private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;

    /**
     * Number of trials to run/average over for each timing test
     */
    private static final int NUM_TRIALS_TO_RUN = 1000;
    /**
     * Minimum set size to run trials on
     */
    private static final int SMALLEST_SETSIZE = 1;
    /**
     * Maximum set size to run trials on
     */
    private static final int LARGEST_SETSIZE = 2501;
    /**
     * Set size multiplier for each consecutive set of trials
     */
    private static final int INCREMENT = 500;

    /**
     * Random object for generating random numbers
     */
    private static Random random = new Random();
//    This is defined globally so it does not need to be passed around

    public static void main(String[] args) {
        final String cs210XTeamIDForProject4 = "mamatticoli";

        // Fetch the collections whose type you must deduce.
        // Note -- you are free to change the type parameter from Integer to whatever you want. In this
        // case, make sure to replace (over the next 4 lines of code) Integer with whatever class you prefer.
        // In addition, you'll need to pass the method getMysteryDataStructure a "sample" (an instance) of
        // the class you want the collection to store.
        @SuppressWarnings("unchecked")
        final Collection210X<Integer>[] mysteryDataStructures = (Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
        for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
            mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject4.hashCode(), i, new Integer(0));
        }

        int i = 0;
        for (Collection210X struct : mysteryDataStructures) { //for each data structure
            i++;
            //for each set size
            System.out.println("\n\nMystery Data Structure " + (i ));

            System.out.println("Random Search");
            System.out.printf("%-10S \t %-10S%n", "SET SIZE", ("AVERAGE TIME OF " + NUM_TRIALS_TO_RUN + " TRIALS"));
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                System.out.printf("%-10d \t %-10d%n", N, timeForRandomSearch(struct, random, N, NUM_TRIALS_TO_RUN));
            }

            System.out.println("\nAdd To Structure");
            System.out.printf("%-10S \t %-10S%n", "SET SIZE", ("AVERAGE TIME OF " + NUM_TRIALS_TO_RUN + " TRIALS"));
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                System.out.printf("%-10d \t %-10d%n", N, timeForOperationAdd(struct, random, N, NUM_TRIALS_TO_RUN));
            }

            System.out.println("\nRemove From Structure");
            System.out.printf("%-10S \t %-10S%n", "SET SIZE", ("AVERAGE TIME OF " + NUM_TRIALS_TO_RUN + " TRIALS"));
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                System.out.printf("%-10d \t %-10d%n", N, timeForOperationRemove(struct, random, N, NUM_TRIALS_TO_RUN));
            }
        }
    }

    public static Long timeForRandomSearch(Collection210X<Integer> MDS, Random random, int size, int trials) {
        for (int a = 0; a < size; a++) {  // populate the mystery data structure with (size) numbers
            MDS.add(a);
        }
        long timeSum = 0L;
        for (int x = 0; x < NUM_TRIALS_TO_RUN; x++) { //takes the average of all the trials
            final int elementToFind = random.nextInt(size);
            final long start = CPUClock.getNumTicks();
            // Time how long it takes to find a single, randomly chosen item stored in the mystery data structure
            final boolean result = MDS.contains(elementToFind);
            final long end = CPUClock.getNumTicks();
            final long elapsed = end - start;
            timeSum += elapsed; //adds the time to the running total.
        }
        long averageTime = timeSum / NUM_TRIALS_TO_RUN;
        return averageTime;
    }

    public static Long timeForOperationAdd(Collection210X<Integer> MDS, Random random, int size, int trials) {
        for (int a = 0; a < size; a++) {  // populate the mystery data structure with (size) numbers
            MDS.add(a);
        }
        long timeSum = 0L;
        for (int x = 0; x < NUM_TRIALS_TO_RUN; x++) { //takes the average of all the trials
            final int elementToAdd = random.nextInt(size);
            final long start = CPUClock.getNumTicks();
            // Time how long it takes to add one integer to the datastructure
            MDS.add(elementToAdd);
            final long end = CPUClock.getNumTicks();
            final long elapsed = end - start;
            timeSum += elapsed; //adds the time to the running total.
        }
        long averageTime = timeSum / NUM_TRIALS_TO_RUN;
        return averageTime;
    }

    public static Long timeForOperationRemove(Collection210X<Integer> MDS, Random random, int size, int trials) {
        for (int a = 0; a < size; a++) {  // populate the mystery data structure with (size) numbers
            MDS.add(a);
        }
        long timeSum = 0L;
        for (int x = 0; x < NUM_TRIALS_TO_RUN; x++) { //takes the average of all the trials
            final int elementToAdd = random.nextInt(size);
            final long start = CPUClock.getNumTicks();
            // Time how long it takes to add one integer to the datastructure
            MDS.remove(random.nextInt(size));
            final long end = CPUClock.getNumTicks();
            final long elapsed = end - start;
            timeSum += elapsed; //adds the time to the running total.
        }
        long averageTime = timeSum / NUM_TRIALS_TO_RUN;
        return averageTime;
    }

}
