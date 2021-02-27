package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        state = (state + 1) % period;
        if (state == 0) {
            period = (int) (period * factor);
        }
        return normalize(state);
    }

    private double normalize(int n) {
        double gradient = 2.0 / period;
        return gradient * n - 1.0;
    }
}
