/**
 * LinkedListDeque.
 * implemented in a circular way.
 *
 * @author yongjing
 */

public class LinkedListDeque<T> {
    private ListNode sentinel;
    private int size;

    /* Nested Class ListNode 节点 */
    private class ListNode {
        public ListNode prev;
        public T item;
        public ListNode next;

        public ListNode(ListNode prev, T item, ListNode next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    /* Constructor of LinkedListDeque */
//    public LinkedListDeque(T x) {
//        sentinel = new ListNode(null, null, null);
//        sentinel.next = new ListNode(sentinel, x, sentinel);
//        size += 1;
//    }

    /* an empty LinkedListDeque */
    public LinkedListDeque() {
        sentinel = new ListNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /* public void addFirst(T item) */
    public void addFirst(T x) {
        sentinel.next = new ListNode(null,x,sentinel.next);
        size += 1;
    }

    /* public void addLast(T item) */
    /* add and remove operations must not involve any looping or recursion. A single such operation must take “constant time”, i.e. execution time should not depend on the size of the deque. */
    public void addLast(T x) {
        sentinel.prev = new ListNode(sentinel.prev, x, sentinel);
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
        for(int i = 0; i < size; i++) {
            System.out.print(current.item + "");
            current = current.next;
        }
        System.out.println();
    }

    /* public T removeFirst():
    /* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }
        ListNode first = sentinel.next;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first.item;
    }

    /* public T removeLast():
    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        // last.next = sentinel;
        // sentinel.prev = last;
        ListNode last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last.item;
    }

    /* public T get(int index) :
    /* must use iteration, not recursion. */
    public T get(int index) {
        if(isEmpty()) {
            return null;
        }
        ListNode p = sentinel;
        for(int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.next.item;
    }

    /* public T getRecursive(int index) */
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }
    private T getRecursiveHelper(ListNode current, int index) {
        if(index == 0) {
            return current.item;
        } else {
            return getRecursiveHelper(current.next, index - 1);
        }
    }
}
