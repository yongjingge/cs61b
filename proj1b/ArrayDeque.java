/**
 * ArrayDeque.
 * implemented in a circular way.
 *
 * @author yongjing
 */


public class ArrayDeque<T> implements Deque<T> {
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
    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /* plusOne index */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /* resize the deque */
    private void resize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        int curFirst = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newArr[i] = items[curFirst];
            curFirst = plusOne(curFirst);
        }
        items = newArr;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /* up-size the deque */
    private void upSize() {
        resize(size * 2);
    }

    /* down-size the deque */
    private void downSize() {
        resize(items.length / 2);
    }

    /* add to the front of the deque */
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            upSize();
        }
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /* add to the back of the deque */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            upSize();
        }
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /* remove the first item of the deque */
    @Override
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


    /* remove the last item of the deque */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int curLast = minusOne(nextLast);
        T rm = items[curLast];
        items[curLast] = null;
        nextLast = minusOne(nextLast);
        size -= 1;

        if (items.length >= 16 && size < items.length * MIN_RATIO) {
            downSize();
        }

        return rm;
    }

    /* get */
    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }
        index = (plusOne(nextFirst) + index) % items.length;
        return items[index];
    }

    /* size */
    @Override
    public int size() {
        return size;
    }

    /* isEmpty */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /* print ArrayDeque */
    @Override
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