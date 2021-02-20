import edu.princeton.cs.algs4.Queue;

public class QuickSort {

    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;

    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {

        for (Item currentItem : unsorted) {
            if (currentItem.compareTo(pivot) > 0) {
                greater.enqueue(currentItem);
            } else if (currentItem.compareTo(pivot) < 0) {
                less.enqueue(currentItem);
            } else {
                equal.enqueue(currentItem);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // corner case
        if (items.size() <= 1) {
            return items;
        }

        // get the pivot
        Item pivot = getRandomItem(items);
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();

        // partitioning
        partition(items, pivot, less, equal, greater);

        // recursion
        Queue<Item> sortedLess = quickSort(less);
        Queue<Item> sortedGreater = quickSort(greater);

        // first catenate sortedLess and the equal queue,
        // then catenate the result and the sortedGreater queue
        Queue<Item> res = catenate(sortedLess, equal);
        res = catenate(res, sortedGreater);
        return res;
    }

    /**
     * test-driven development
     * @param args
     */
    public static void main(String[] args) {
        Queue<Integer> queueOfNumbers = new Queue<>();
        queueOfNumbers.enqueue(11);
        queueOfNumbers.enqueue(1);
        queueOfNumbers.enqueue(27);
        queueOfNumbers.enqueue(35);
        queueOfNumbers.enqueue(24);
        Queue<Integer> afterQuickSort = quickSort(queueOfNumbers);
        System.out.println("The original queue of Integer is: " + queueOfNumbers.toString());
        System.out.println("After QuickSort, the queue is: " + afterQuickSort.toString());

        Queue<String> queueOfStrings = new Queue<>();
        queueOfStrings.enqueue("this");
        queueOfStrings.enqueue("is");
        queueOfStrings.enqueue("a");
        queueOfStrings.enqueue("queue");
        queueOfStrings.enqueue("of");
        queueOfStrings.enqueue("strings");
        Queue<String> afterQuickSort2 = quickSort(queueOfStrings);
        System.out.println("The original queue of String is: " + queueOfStrings.toString());
        System.out.println("After QuickSort, the queue is: " + afterQuickSort2.toString());
    }
}
