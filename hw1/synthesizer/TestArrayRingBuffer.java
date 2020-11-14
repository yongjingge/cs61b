package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        System.out.println(arb.isEmpty());
        System.out.println(arb.isFull());
        System.out.println(arb.dequeue());
        System.out.println(arb.dequeue());
        System.out.println(arb.peek());
    }

    @Test
    public void testIsEmpty() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(3);
        assertEquals(true, rb.isEmpty());
        assertTrue(rb.isEmpty());
        rb.enqueue(1);
        assertFalse(rb.isEmpty());
    }

    @Test
    public void testIsFull() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(10);
        assertFalse(rb.isFull());
        for (int i = 0; i < rb.capacity(); i += 1) {
            rb.enqueue(i);
        }
        assertTrue(rb.isFull());
    }

    @Test
    public void testDequeue() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < rb.capacity(); i += 1) {
            rb.enqueue(i);
        }
        Integer expected1 = 0;
        assertEquals(expected1, rb.dequeue());
//        Integer expected2 = 2;
//        assertEquals(expected2, rb.dequeue()); //should be false
        rb.enqueue(11);
        Integer expected3 = 1;
        assertEquals(expected3, rb.dequeue());
    }

    @Test
    public void testPeek() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < rb.capacity(); i += 1) {
            rb.enqueue(i);
        }

    }

    @Test
    public void testEnqueue() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < rb.capacity(); i += 1) {
            rb.enqueue(i);
        }
        int expected = 0;
        Iterator<Integer> rbi = rb.iterator();
        while (rbi.hasNext()) {
            assertEquals((int)rbi.next(),expected);
            expected += 1;
        }
//        for (int i = 0; i < rb.capacity(); i += 1) {
//            assertEquals(expected, i);
//            expected += 1;
//        }
        assertEquals(expected, 5);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
