import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    private static final int n = 100; //test n times
    private static String msg = "";

    private void addRandom (double random, Integer i, StudentArrayDeque<Integer> sad, ArrayDequeSolution<Integer> ad) {
        if (random < 0.5) {
            sad.addFirst(i);
            ad.addFirst(i);
            msg += "\naddFirst(" + i + ")";
        } else {
            sad.addLast(i);
            ad.addLast(i);
            msg += "\naddLast(" + i + ")";
        }
    }

    private void removeRandom (double random, StudentArrayDeque<Integer> sad, ArrayDequeSolution<Integer> ad) {
        Integer expected, actual;
        if (random < 0.5) {
            expected = ad.removeFirst();
            actual = sad.removeFirst();
            msg += "\nremoveFirst()";
        } else {
            expected = ad.removeLast();
            actual = sad.removeLast();
            msg += "\nremoveLast()";
        }
        assertEquals(msg, expected, actual);
    }

    @Test
    public void test() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();
        for (Integer i = 0; i < n; i += 1) {
            if (sad.isEmpty()) {
                double random = StdRandom.uniform();
                addRandom(random, i, sad, ad);
            } else {
                double rd1 = StdRandom.uniform();
                double rd2 = StdRandom.uniform();
                if (rd1 < 0.5) {
                    addRandom(rd2, i, sad, ad);
                } else {
                    removeRandom(rd2, sad, ad);
                }
            }
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestArrayDequeGold.class);
    }
}
