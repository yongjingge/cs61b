package synthesizer;
import java.util.Iterator;

// Make sure to make this class and all of its methods public
// Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        /*
        Create new array with capacity elements.
        first, last, and fillCount should all be set to 0.
        this.capacity should be set appropriately. Note that the local variable
        here shadows the field we inherit from AbstractBoundedQueue, so
        you'll need to use this.capacity to set the capacity.
        */
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /* a helper method to increase first / last */
    private int onePlus(int cur) {
        if (cur == capacity - 1) {
            return 0;
        } else {
            return cur + 1;
        }
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last = onePlus(last);
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T delete = rb[first];
            rb[first] = null;
            first = onePlus(first);
            fillCount -= 1;
            return delete;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    // When you get to part 5, implement the needed code to support iteration.
    private class BufferIterator implements Iterator<T> {
        int remain;
        int cur;

        BufferIterator() {
            remain = fillCount;
            cur = first;
        }

        @Override
        public boolean hasNext() {
            return remain > 0;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T delete = rb[cur];
                remain -= 1;
                cur = onePlus(cur);
                return delete;
            }
            return null;
        }
    }

    // BoundedQueue<T> interface extends Iterable<T>, so we need an iterator method here.
    public Iterator<T> iterator() {
        return new BufferIterator();
    }
}
