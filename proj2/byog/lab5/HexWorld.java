package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /* @Source: Josh Hug */
    private static final int WIDTH = 70;
    private static final int HEIGHT = 70;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static class Position {
        public int x;
        public int y;
        public Position (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param size The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth (int size, int i) {
        int effectiveI = i;
        if (i >= size) {
            effectiveI = 2 * size - 1 - effectiveI;
        }
        return size + 2 * effectiveI;
    }

    @Test
    public void testHexRowWidth () {
        // a hexagon of size 2 should be 2-4-4-2
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
        // a hexagon of size 4 should be 4-6-8-10-10-8-6-4
        assertEquals(4, hexRowWidth(4, 0));
        assertEquals(10, hexRowWidth(4, 3));
        assertEquals(8, hexRowWidth(4, 5));
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   xxxx
     *
     * @param size size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int xOffset (int size, int i) {
        int effectiveI = i;
        if (i >= size) {
            effectiveI = 2 * size - 1 - effectiveI;
        }
        return -effectiveI;
    }

    @Test
    public void testXOffset () {
        assertEquals(-2, xOffset(4, 2));
        assertEquals(-2, xOffset(4, 5));
    }

    /** Adds a row of the same tile.
     * a helper method to draw hexagons.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow (TETile[][] world, Position p, int width, TETile t) {
        for (int xnew = 0; xnew < width; xnew += 1) {
            int xCoord = p.x + xnew;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param size the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon (TETile[][] world, Position p, int size, TETile t) {
        if (size < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int yi = 0; yi < 2 * size; yi += 1) {
            int thisRowY = p.y + yi; // start from the bottom
            int xRowStart = p.x + xOffset(size, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);
            int rowWidth = hexRowWidth(size, yi);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    /**
     * Adds hexagons vertically at a given position with randomly assigned styles.
     * @param world the world to draw on
     * @param p a given position
     * @param size the size of the hexagon
     * @param num number of hexagons to be added
     * @source airbust
     */
    public static void addHexaVertical (TETile[][] world, Position p, int size, int num) {
        Position next = new Position(p.x, p.y); // starts from a given position
        for (int i = 0; i < num; i += 1) {
            addHexagon(world, next, size, randomTile());
            next.y += 2 * size; // y pluses the height of the hexagon, which should be twice of its size.
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.UNLOCKED_DOOR;
            case 4: return Tileset.LOCKED_DOOR;
            case 5: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * 19 hexagons should be vertically 3-4-5-4-3
     * this helper method provides a way to calculate the appropriate number of hexagons
     * to be added each time
     * @param i given number of hexagons to be added
     * @source airbust
     */
    private static int calNum (int i) {
        int res = i + 3;
        if (i > 2) {
            res = 10 - res;
        }
        return res;
    }

    /**
     * @source airbust
     * @param size size of the hexagon
     * @return
     */
    private static int xMove (int size) {
        return 2 * size + xOffset(size, 0) - 1;
    }

    /**
     * @source airbust
     * @param size size of the hexagon
     * @param i
     * @return
     */
    private static int yMove (int size, int i) {
        if (i > 2) {
            return size;
        }
        return -size;
    }

    /**
     * draw a tesselation of hexagons
     * @source airbust
     * @param world
     * @param p
     * @param size size of the hexagon
     */
    public static void drawTesselationHexagons (TETile[][] world, Position p, int size) {
        Position next = new Position(p.x, p.y);
        if (p.y < size * 2) {
            throw new ArrayIndexOutOfBoundsException("Position of y should be no less than " + 2 * size);
        }
        int newX = xMove(size);
        for (int i = 0; i < 5; i += 1) {
            addHexaVertical(world, next, size, calNum(i));
            next.x += newX;
            next.y += yMove(size, i + 1);
        }
    }

    public static void main (String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Position p1 = new Position(3, 8);
        Position p2 = new Position(11,22);
        Position p3 = new Position(3,5);
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        // addHexagon(world, p1, 3, Tileset.FLOWER);

        // addHexagon(world, p2, 4, Tileset.TREE);

        // addHexaVertical(world, p1, 3, 5);

        drawTesselationHexagons(world, p3, 4);
        ter.renderFrame(world);
    }
}
