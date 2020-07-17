/**
 * LinkedListDeque.
 * implemented in a circular way.
 *
 * @author yongjing
 */

public class LinkedListDeque<T> {
    private ListNode sentinel;
    private int size;

    /* Nested Class ListNode */
    private class ListNode {
        private T item;
        private ListNode prev;
        private ListNode next;

        public ListNode(T item, ListNode prev, ListNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /* an empty LinkedListDeque */
    public LinkedListDeque() {
        sentinel = new ListNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /* public void addFirst(T item) */
    public void addFirst(T x) {
        sentinel.next = new ListNode(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /* public void addLast(T item) */
    public void addLast(T x) {
        sentinel.prev = new ListNode(x, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /* public boolean isEmpty() */
    public boolean isEmpty() {
        return size == 0;
    }

    /* public int size() */
    public int size() {
        return size;
    }

    /* public void printDeque() */
    public void printDeque() {
        ListNode current = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(current.item + "");
            current = current.next;
        }
        System.out.println();
    }

    /* public T removeFirst() */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return first;
    }

    /* public T removeLast():
    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return last;
    }

    /* public T get(int index) :
    /* must use iteration, not recursion. */
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        ListNode p = sentinel;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.next.item;
    }

    /* public T getRecursive(int index) */
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }
    private T getRecursiveHelper(ListNode current, int index) {
        if (index == 0) {
            return current.item;
        } else {
            return getRecursiveHelper(current.next, index - 1);
        }
    }
}
