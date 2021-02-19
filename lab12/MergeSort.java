import edu.princeton.cs.algs4.Queue;

public class MergeSort {

    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        if (items.isEmpty() || items == null) {
            return null;
        }
        Queue<Queue<Item>> queueOfQueues = new Queue<>();
        for (Item single : items) {
            Queue<Item> singleQueue = new Queue<>();
            singleQueue.enqueue(single);
            queueOfQueues.enqueue(singleQueue);
        }
        return queueOfQueues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> res = new Queue<>();
        while (! (q1.isEmpty() && q2.isEmpty())) {
            res.enqueue(getMin(q1, q2));
        }
        return res;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {

        if (items.size() <= 1) {
            return items;
        }
        Queue<Queue<Item>> queueOfQueues = makeSingleItemQueues(items);
        Queue<Item> subQueue1 = new Queue<>();
        Queue<Item> subQueue2 = new Queue<>();

        // fill the subQueue1 with left half of the queueOfQueues
        int queue1Range = queueOfQueues.size() / 2;
        for (int i = 0; i < queue1Range; i += 1) {
            Item itemToBeEnqueued = queueOfQueues.dequeue().dequeue();
            subQueue1.enqueue(itemToBeEnqueued);
        }

        // fill the subQueue2 with items left
        // note that the size of queueOfQueues has changed due to the previous dequeue operation
        int queue2Range = queueOfQueues.size();
        for (int i = 0; i < queue2Range; i += 1) {
            Item itemToBeEnqueued = queueOfQueues.dequeue().dequeue();
            subQueue2.enqueue(itemToBeEnqueued);
        }

        Queue<Item> subQueue1Sorted = mergeSort(subQueue1);
        Queue<Item> subQueue2Sorted = mergeSort(subQueue2);
        return mergeSortedQueues(subQueue1Sorted, subQueue2Sorted);

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
        Queue<Integer> afterMergeSort = mergeSort(queueOfNumbers);
        System.out.println("The original queue of Integer is: " + queueOfNumbers.toString());
        System.out.println("After MergeSort, the queue is: " + afterMergeSort.toString());

        Queue<String> queueOfStrings = new Queue<>();
        queueOfStrings.enqueue("this");
        queueOfStrings.enqueue("is");
        queueOfStrings.enqueue("a");
        queueOfStrings.enqueue("queue");
        queueOfStrings.enqueue("of");
        queueOfStrings.enqueue("strings");
        Queue<String> afterMergeSort2 = mergeSort(queueOfStrings);
        System.out.println("The original queue of String is: " + queueOfStrings.toString());
        System.out.println("After MergeSort, the queue is: " + afterMergeSort2.toString());
    }
}
