package synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    /* return size of the buffer */
    int capacity();
    /* return number of items currently in the buffer */
    int fillCount();
    /* add x to the end of the queue */
    void enqueue(T x);
    /* delete and return item from the front of the queue */
    T dequeue();
    /* return but not delete item from the front of the queue */
    T peek();
    /* is the buffer empty? (fillCount == 0 ?) */
    default boolean isEmpty() {
        return fillCount() == 0;
    }
    /* is the buffer full? (fillCount == capacity ?) */
    default boolean isFull() {
        return fillCount() == capacity();
    }

    Iterator<T> iterator();
}
