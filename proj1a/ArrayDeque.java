/* ArrayDequeCircular<Item>
/* reflecting 'circular' */

public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst; //curFirst上一位
    private int nextLast; //curLast下一位
    private static final int INIT_CAP = 8;
    private static final double MIN_RATIO = 0.25;

    /* create an empty deque */
    public ArrayDeque() {
        items = (Item[]) new Object[INIT_CAP];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /* minus one: 前推一位，更新nextFirst用，确保首位index的前一位是原数组的最后一位，形成环状数组
    * 如果是size8的数组，0-7位，addFirst之后，现有的nextFirst要被minusOne一次，如果遇上0位，则需要先扩容，minusOne之后要等于扩容后的末尾前一位 */
    /* index + items.length -1: avoid negative result */
    public int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /* plus one: 后推一位，更新nextLast用 */
    public int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /* resize the deque */
    public void resize(int capacity) {
        Item[] newArr = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, newArr, 0, size);
        items = newArr;
        /* the newArr is starting from index 0, first will be index 0, so nextFirst will be at position capacity-1,         * note the difference between capacity and actual size, capacity might be much larger than the actual size
         */
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
    public void addFirst(Item x) {
        if(size == items.length) {
            upSize();
        }
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /* add to the back of the deque */
    public void addLast(Item x) {
        if(size == items.length) {
            upSize();
        }
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /* remove the first item of the deque */
    public Item removeFirst() {
        if(size == 0) {
            return null;
        }
        int curFirst = plusOne(nextFirst);
        Item rm = items[curFirst];
        items[curFirst] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;

        if(items.length >= 16 && size < items.length*MIN_RATIO) {
            downSize();
        }

        return rm;
    }

    /* get the back of the deque */
    public Item getLast() {
        return items[size - 1];
    }

    /* remove the last item of the deque */
    public Item removeLast() {
        if(size == 0) {
            return null;
        }
        Item rm = getLast();
        items[size - 1] = null;
        nextLast = minusOne(nextLast);
        size -= 1;

        if(items.length >= 16 && size < items.length*MIN_RATIO) {
            downSize();
        }

        return rm;
    }

    /* get */
    public Item get(int index) {
        if(index > size) {
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
        if(isEmpty()) {
            return;
        }

        for(int i = plusOne(nextFirst); i != nextLast; i = plusOne(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}