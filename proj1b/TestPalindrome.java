import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator cc = new OffByOne();
    static CharacterComparator cc5 = new OffByN(5);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        System.out.println(actual);
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("T"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome(" "));
    }

    @Test
    /* OffByOne */
    public void testIsPalindromeComparator() {
        assertFalse(palindrome.isPalindrome("noon", cc));
        assertFalse(palindrome.isPalindrome("racecar", cc));
        assertTrue(palindrome.isPalindrome("T", cc));
        assertFalse(palindrome.isPalindrome("cat", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
    }

    @Test
    /* OffByN */
    public void testIsPalindromeBy5Comparator() {
        assertTrue(palindrome.isPalindrome("abDIgf", cc5));
        assertTrue(palindrome.isPalindrome("x", cc5));
        assertFalse(palindrome.isPalindrome("cat", cc5));
    }
}
