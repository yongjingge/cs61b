package lab11.graphs;

import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int[] pathTo;
    private boolean isCyclic = false;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        pathTo = new int[maze.V()];
        Random random = new Random();
        int startX = random.nextInt(maze.N());
        int startY = random.nextInt(maze.N());
        int start = maze.xyTo1D(startX, startY);
        pathTo[start] = start;
        dfs(start);
        announce();
        System.out.println(isCyclic);
    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;
        for (int w : maze.adj(v)) {
            if (isCyclic) {
                return;
            }
            // if vertex[w] hasn't been visited, go normal dfs
            if (! marked[w]) {
                pathTo[w] = v;
                dfs(w);
            } else if (w != pathTo[v]) {
                // if vertex[w] is visited (marked)
                // and [w] is not parent of [v]
                pathTo[w] = v;

                int cur = v;
                edgeTo[cur] = pathTo[cur];
                // will loop back to [w]
                while (cur != w) {
                    cur = pathTo[cur];
                    edgeTo[cur] = pathTo[cur];
                }
                isCyclic = true;
                return;
            }
        }
    }
}

