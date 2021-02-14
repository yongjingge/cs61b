package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Solver {

    /* a second optimization:
    * avoid recomputing 'estimatedDistanceToGoal' */
    private Map<WorldState, Integer> estimatedCaches = new HashMap<>();

    /* Returns a sequence of WorldStates
    * from the initial WorldState to the solution */
    private ArrayDeque<WorldState> path = new ArrayDeque<>();

    private int enqueue_count;

    private class SearchNode {
        private WorldState state;
        private int move;
        private SearchNode prev;
        /* set the sum of 'number of moves' + 'estimatedDistanceToGoal' as its field */
        private Integer priority;

        public SearchNode(WorldState state, int move, SearchNode prev) {
            this.state = state;
            this.move = move;
            this.prev = prev;
            if (! estimatedCaches.containsKey(this.state)) {
                estimatedCaches.put(this.state, this.state.estimatedDistanceToGoal());
            }
            this.priority = this.move + estimatedCaches.get(this.state);
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare (SearchNode node1, SearchNode node2) {
            return node1.priority.compareTo(node2.priority);
        }
    }

    /**
     * Solver(initial): Constructor which solves the puzzle, computing
     *                  everything necessary for moves() and solution() to
     *                  not have to solve the problem again. Solves the
     *                  puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial
     */
    public Solver (WorldState initial) {
        /* first step:
        * create a priority queue of search nodes,
        * and insert an initial searcn node */
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        SearchNode currentNode = new SearchNode(initial, 0, null);
        pq.insert(currentNode);
        enqueue_count = 1;

        while (! pq.isEmpty()) {
            /* node X */
            currentNode = pq.delMin();
            if (currentNode.state.isGoal()) {
                break;
            }
            for (WorldState nextState : currentNode.state.neighbors()) {
                /* critical optimization:
                * reduce exploration of useless search nodes */
                if (currentNode.prev != null && nextState.equals(currentNode.prev.state)) {
                    continue;
                }
                pq.insert(new SearchNode(nextState, currentNode.move + 1, currentNode));
                enqueue_count += 1;
            }
        }

        for (SearchNode node = currentNode; node != null; node = node.prev) {
            path.push(node.state);
        }

    }

    /**
     * moves():         Returns the minimum number of moves to solve the puzzle starting
     *                  at the initial WorldState.
     * @return
     */
    public int moves() {
        return path.size() - 1;
    }

    /**
     * solution():      Returns a sequence of WorldStates from the initial WorldState
     *                  to the solution.
     * @return
     */
    public Iterable<WorldState> solution () {
        return path;
    }

    /**
     * Returns the number of total things enqueued in the MinPQ.
     * @return
     */
    protected int getEnqueue_count () {
        return enqueue_count;
    }
}
