import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    @Test
    public void testIsOffByOne() {
        // Your tests go here.
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('M', 'N'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertFalse(offByOne.equalChars('c', 'f'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('%', '&'));
    }
}
