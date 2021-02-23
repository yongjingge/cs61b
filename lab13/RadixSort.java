import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    private static final int R             = 256;   // extended ASCII alphabet size
    private static final int CUTOFF        =  15;   // cutoff to insertion sort

    /* ******************************************************************************************************************* */
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        if (asciis.length <= 1) {
            return asciis;
        }
        // find how many passes do we need
        int maxLength = 0;
        for (String single : asciis) {
            maxLength = Math.max(single.length(), maxLength);
            // maxLength = maxLength > single.length() ? maxLength : single.length();
        }

        // The sorting is non-destructive
        String[] res = Arrays.copyOf(asciis, asciis.length);
        // sort from the Least Significant Digit (the rightmost)
        for (int digit = maxLength - 1; digit >= 0; digit -= 1) {
            sortHelperLSD(res, digit);
        }
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        // Counting Sort of the index-th digit of each String in the String[] asciis
        int[] counts = new int[R + 1];
        for (String single : asciis) {
            int singleChar = charNumAtItem(single, index);
            counts[singleChar] += 1;
        }

        // get the start position array
        int[] starts = new int[R + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        // get the sorted result
        String[] res = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            String single = asciis[i];
            // get the digit we need in its int form
            int charNum = charNumAtItem(single, index);
            // put it into the right position
            int position = starts[charNum];
            res[position] = single;
            starts[charNum] += 1;
        }

        for (int i = 0; i < asciis.length; i += 1) {
            asciis[i] = res[i];
        }
    }

    /* helper method to get the int value of a char at given index of a String */
    private static int charNumAtItem(String item, int index) {
        if (index < 0 || index >= item.length()) {
            return 0;
        }
        return item.charAt(index);
    }

    /* ******************************************************************************************************************* */
    /**
     * Does MSD radix sort on the passed in array
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     * @Source https://algs4.cs.princeton.edu/51radix/MSD.java.html
     */
    public static String[] sortMSD(String[] asciis) {
        int len = asciis.length;
        String[] aux = new String[len];
        sortHelperMSD1(asciis, 0, len-1, 0, aux);
        return asciis;
    }

    // return digit-th character of s, -1 if digit = length of string
    private static int charAt(String s, int digit) {
        assert digit >= 0 && digit <= s.length();
        if (digit == s.length()) return -1;
        return s.charAt(digit);
    }


    // insertion sort a[lo..hi], starting from the digit-th character
    private static void insertion(String[] a, int lo, int hi, int digit) {
        for (int i = lo; i <= hi; i += 1) {
            for (int j = i; j > lo && less(a[j], a[j-1], digit); j -= 1) {
                swap(a, j, j-1);
            }
        }
    }

    // compare value of two elements
    private static boolean less(String a, String b, int digit) {
        for (int i = digit; i < Math.min(a.length(), b.length()); i += 1) {
            if (a.charAt(i) < b.charAt(i)) {
                return true;
            }
            if (a.charAt(i) > b.charAt(i)) {
                return false;
            }
        }
        return a.length() < b.length();
    }

    // swap
    private static void swap(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    // sort from a[lo] to a[hi], starting at the digit-th character
    private static void sortHelperMSD1(String[] a, int lo, int hi, int digit, String[] aux) {
        // cutoff to insertion sort for small sub-arrays
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, digit);
            return;
        }

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i += 1) {
            int c = charAt(a[i], digit);
            count[c + 2] += 1;
        }

        // transform counts to indicies
        for (int r = 0; r < R + 1; r += 1) {
            count[r + 1] += count[r];
        }

        // distribute
        for (int i = lo; i <= hi; i += 1) {
            int c = charAt(a[i], digit);
            count[c + 1] += 1;
            aux[count[c + 1]] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i += 1) {
            a[i] = aux[i - lo];
        }

        // recursively sort for each character (excludes sentinel - 1)
        for (int r = 0; r < R; r += 1) {
            sortHelperMSD1(a, lo + count[r], lo + count[r + 1] - 1, digit + 1, aux);
        }
    }

    /* ******************************************************************************************************************* */
    /* test the LSD and MSD Radix Sort method */
    public static void main(String[] args) {

        String[] origin = new String[] {"abc", "acb", "bac", "apple", "test", "driver", "academic", "destructive", "communication", "conversion", "conversation"};
        System.out.println("The original array is: " + Arrays.toString(origin));

        // sort method is using LSD Radix Sort
        String[] sorted1 = sort(origin);
        assertEquals(origin.length, sorted1.length);
        System.out.println("The LSD Radix Sort produces a result: " + Arrays.toString(sorted1));


        String[] sorted2 = sortMSD(origin);
        assertEquals(origin.length, sorted2.length);
        System.out.println("The MSD Radix Sort produces a result: " + Arrays.toString(sorted2));
    }


}
