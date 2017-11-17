import com.cs210x.CPUClock;
import com.cs210x.Collection210X;
import com.cs210x.MysteryDataStructure;

import java.util.Random;

/**
 * Class to deduce the identity of mystery data structures.
 */
public class ExperimentRunner {
    private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;
    private static final int NUM_TRIALS_TO_RUN = 100;
    private static final int SMALLEST_SETSIZE = 1;
    private static final int LARGEST_SETSIZE = 100000;
    private static final int INCREMENT = 10000;

    public static void main(String[] args) {
        final String cs210XTeamIDForProject4 = "mamatticoli"; // TODO CHANGE THIS TO THE TEAM ID YOU USE TO SUBMIT YOUR PROJECT3 ON INSTRUCT-ASSIST.

        // Fetch the collections whose type you must deduce.
        // Note -- you are free to change the type parameter from Integer to whatever you want. In this
        // case, make sure to replace (over the next 4 lines of code) Integer with whatever class you prefer.
        // In addition, you'll need to pass the method getMysteryDataStructure a "sample" (an instance) of
        // the class you want the collection to store.
        @SuppressWarnings("unchecked") final Collection210X<Integer>[] mysteryDataStructures = (Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
        for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
            mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject4.hashCode(), i, new Integer(0));
        }

        // Write your code here...
        final Random random = new Random();  // instantiate a random number generator
        //TODO FIX THIS PIECE OF SHIT
        for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) { //for each data structure
            //for each set size
            System.out.println("\n\nDATA SET "+(i+1));
            System.out.println("Random Search");
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                System.out.print(N+"\t");
            }
            System.out.println();
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                //print(N, timeForRandomSearch(mysteryDataStructures[i], random, N, NUM_TRIALS_TO_RUN),("Data Structure " + (i + 1)), "Random Search");
                System.out.print(timeForRandomSearch(mysteryDataStructures[i], random, N, NUM_TRIALS_TO_RUN) + "\t");
            }
            System.out.println("\nAdd To Structure");
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                System.out.print(N+"\t");
            }
            System.out.println();
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                // print(N, timeForOperationAdd(mysteryDataStructures[i], random, N, NUM_TRIALS_TO_RUN),("Data Structure " + (i + 1)), "Add to Structure");
                System.out.print(timeForOperationAdd(mysteryDataStructures[i], random, N, NUM_TRIALS_TO_RUN) + "\t");
            }
            System.out.println("\nRemove From Structure");
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                System.out.print(N+"\t");
            }
            System.out.println();
            for (int N = SMALLEST_SETSIZE; N <= LARGEST_SETSIZE; N += INCREMENT) {
                // print(N, timeForOperationRemove(mysteryDataStructures[i], random, N, NUM_TRIALS_TO_RUN),("Data Structure " + (i + 1)), "Remove from Structure");
                System.out.print(timeForOperationRemove(mysteryDataStructures[i], random, N, NUM_TRIALS_TO_RUN) + "\t");
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

    public static void print(int size, long averageTime, String name, String operation) {
        System.out.println("=========== " + name + " || " + operation + "===========");

        System.out.println("Size of Dataset\t||\tAvg. Time " + NUM_TRIALS_TO_RUN + " Trials: ");
        System.out.println("" + size + "\t\t|| \t \t " + averageTime);
    }
}
