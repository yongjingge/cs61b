package hw4.puzzle;


import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private static final int BLANK = 0;
    private int N;
    private int[][] tiles;

    /* Board(tiles): Constructs a board from an N-by-N array of tiles where
              tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        this.N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    /* tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
    * should have corner cases check:
    * both int i and j should be in range 0 and N-1 */
    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i > N - 1 || j > N - 1) {
            throw new IndexOutOfBoundsException("Invalid index input, please check!");
        }
        return tiles[i][j];
    }

    /* size():       Returns the board size N */
    public int size() {
        return N;
    }

    /**
     * Returns neighbors of the current board
     * @author http://joshh.ug/neighbors.html
     * @return a sequence of WorldStates
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /* Hamming estimate: The number of tiles in the wrong position */
    public int hamming() {
        int estimates = 0;
        int expected = 1; // starting from 1, and increment by 1
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (tileAt(i, j) == BLANK) {
                    break;
                }
                if (tileAt(i, j) != expected) {
                    estimates += 1;
                }
                expected += 1;
            }
        }
        return estimates;
    }

    /* Manhattan estimate:
    * The sum of the Manhattan distances (sum of the vertical and horizontal distance)
    * from the tiles to their goal positions. */
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int currentTile = tileAt(i, j);
                if (currentTile == BLANK) {
                    continue;
                }
                int[] goalPosition = tilesGoalPosition(currentTile);
                res += Math.abs(goalPosition[0] - i);
                res += Math.abs(goalPosition[1] - j);
            }
        }
        return res;
    }

    /* a helper method to get a tile's correct position */
    private int[] tilesGoalPosition (int currentTile) {
        int[] res = new int[2];
        res[0] = (currentTile - 1) / N;
        res[1] = (currentTile - 1) % N;
        return res;
    }

    /* estimatedDistanceToGoal(): Estimated distance to goal. This method should
    * simply return the results of manhattan() when submitted to Gradescope. */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /* equals(y):    Returns true if this board's tile values are the same position as y's */
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y.getClass() != this.getClass() || y == null) {
            return false;
        }
        Board compareTarget = (Board) y;
        if (compareTarget.N != this.N) {
            return false;
        }
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (compareTarget.tileAt(i, j) != this.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode () {
        return super.hashCode();
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
