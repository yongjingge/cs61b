/**
 * ArrayDeque.
 * implemented in a circular way.
 *
 * @author yongjing
 */


public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int INIT_CAP = 8;
    private static final double MIN_RATIO = 0.25;

    /* create an empty deque */
    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAP];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /* minusOne index */
    public int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /* plusOne index */
    public int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /* resize the deque */
    public void resize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        System.arraycopy(items, 0, newArr, 0, size);
        items = newArr;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /* up-size the deque */
    public void upSize() {
        resize(size * 2);
    }

    /* down-size the deque */
    public void downSize() {
        resize(items.length / 2);
    }

    /* add to the front of the deque */
    public void addFirst(T x) {
        if (size == items.length) {
            upSize();
        }
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /* add to the back of the deque */
    public void addLast(T x) {
        if (size == items.length) {
            upSize();
        }
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /* remove the first item of the deque */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int curFirst = plusOne(nextFirst);
        T rm = items[curFirst];
        items[curFirst] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;

        if (items.length >= 16 && size < items.length * MIN_RATIO) {
            downSize();
        }

        return rm;
    }

    /* get the back of the deque */
    public T getLast() {
        return items[size - 1];
    }

    /* remove the last item of the deque */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T rm = getLast();
        items[size - 1] = null;
        nextLast = minusOne(nextLast);
        size -= 1;

        if (items.length >= 16 && size < items.length * MIN_RATIO) {
            downSize();
        }

        return rm;
    }

    /* get */
    public T get(int index) {
        if (index > size) {
            return null;
        }
        return items[index];
    }

    /* size */
    public int size() {
        return size;
    }

    /* isEmpty */
    public boolean isEmpty() {
        return size == 0;
    }

    /* print ArrayDeque */
    public void printDeque() {
        if (isEmpty()) {
            return;
        }

        for (int i = plusOne(nextFirst); i != nextLast; i = plusOne(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}