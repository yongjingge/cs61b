package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {

    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state += 1;
        // int weirdState = state & (state >> 3) % period;
        // int weirdState = state & (state >> 3) & (state >> 8) % period;
        int weirdState = state & (state >> 7) % period;
        return normalize(weirdState);
    }

    private double normalize(int n) {
        double gradient = 2.0 / period;
        return gradient * n - 1.0;
    }
}
