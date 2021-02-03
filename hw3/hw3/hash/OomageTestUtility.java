package hw3.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        Map<Integer, Integer> mapBucket = new HashMap<>();
        for (Oomage oomage : oomages) {
            int bucketnumber = hashToBucketNumber(oomage.hashCode(), M);
            if (mapBucket.containsKey(bucketnumber)) {
                int numberInBucket = mapBucket.get(bucketnumber);
                mapBucket.put(bucketnumber, numberInBucket + 1);
            } else {
                mapBucket.put(bucketnumber, 1);
            }
        }
        final int N = oomages.size();
        for (int distributed : mapBucket.values()) {
            if (distributed <= N / 50 || distributed >= N / 2.5) {
                return false;
            }
        }
        return true;
    }

    private static Integer hashToBucketNumber (Integer hashcode, int M) {
        return (hashcode & 0x7FFFFFFF) % M;
    }
}
