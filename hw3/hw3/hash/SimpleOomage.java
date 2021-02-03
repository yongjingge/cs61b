package hw3.hash;
import java.awt.Color;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;


public class SimpleOomage implements Oomage {

    /**
     * each may have any value that is a multiple of 5 between 0 and 255.
     */
    protected int red;
    protected int green;
    protected int blue;

    private static final double WIDTH = 0.01;
    private static final boolean USE_PERFECT_HASH = true;

    /**
     * equals method: reflexive; symmetric; transitive; consistent; not-equal-to-null;
     * Avoid returning a multiple of 5 hashcode to make a nice visualization of hashcode.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (o == null){
            return false;
        }
        if (o.getClass() != this.getClass()){
            return false;
        }
        SimpleOomage that = (SimpleOomage) o;
        return (this.red == that.red)
                && (this.green == that.green)
                && (this.blue == that.blue);
    }

    /**
     * If you’d like to make your hashCodes work well for any number of buckets,
     * you should ensure that it is not always a multiple of any number.
     * One way to do this is to divide the red, green, and blue values by 5 before computing the hash code.
     * @return
     */
    @Override
    public int hashCode() {
        if (!USE_PERFECT_HASH) {
            return red + green + blue;
        } else {
            int hashcode = 19;
            hashcode += red / 5;
            hashcode *= 259;
            hashcode += green / 5;
            hashcode *= 259;
            hashcode += blue / 5;
            hashcode *= 259;
            return hashcode;
        }
    }

    public SimpleOomage(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
        if ((r % 5 != 0) || (g % 5 != 0) || (b % 5 != 0)) {
            throw new IllegalArgumentException("red/green/blue values must all be multiples of 5!");
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public void draw(double x, double y, double scalingFactor) {
        StdDraw.setPenColor(new Color(red, green, blue));
        StdDraw.filledSquare(x, y, WIDTH * scalingFactor);
    }

    public static SimpleOomage randomSimpleOomage() {
        int red = StdRandom.uniform(0, 51) * 5;
        int green = StdRandom.uniform(0, 51) * 5;
        int blue = StdRandom.uniform(0, 51) * 5;
        return new SimpleOomage(red, green, blue);
    }

    public static void main(String[] args) {
        System.out.println("Drawing 4 random simple Oomages.");
        randomSimpleOomage().draw(0.25, 0.25, 1);
        randomSimpleOomage().draw(0.75, 0.75, 1);
        randomSimpleOomage().draw(0.25, 0.75, 1);
        randomSimpleOomage().draw(0.75, 0.25, 1);
    }

    public String toString() {
        return "R: " + red + ", G: " + green + ", B: " + blue;
    }
} 
