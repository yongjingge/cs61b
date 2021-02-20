package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /* Min PQ comparator */
    private class Node {
        private int v;
        private Integer priority;

        public Node(int v) {
            this.v = v;
            // source-to-v distance + estimated distance from v to target
            this.priority = distTo[v] + estimatedDistance(v);
        }
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return node1.priority.compareTo(node2.priority);
        }
    }

    /** Estimate of the distance from v to the target. */
    private int estimatedDistance(int v) {
        int vX = maze.toX(v);
        int vY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(vX - targetX) + Math.abs(vY - targetY);
    }

    /** Finds vertex estimated to be closest to target.
    private int findMinimumUnmarked() {

    }
    */

    /** Performs an A star search from vertex s. */
    private void astar(int start) {
        System.out.println("At the beginning, did we find the target? " + targetFound);
        MinPQ<Node> pq = new MinPQ<>(new NodeComparator());
        Node startNode = new Node(start);
        pq.insert(startNode);
        marked[start] = true;
        while (! pq.isEmpty()) {
            Node cur = pq.delMin();
            for (int w : maze.adj(cur.v)) {
                if (! marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = cur.v;
                    distTo[w] = distTo[cur.v] + 1;
                    announce();
                    if (w == t) {
                        targetFound = true;
                        System.out.println("Did we find the target? " + targetFound);
                        return;
                    } else {
                        pq.insert(new Node(w));
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

