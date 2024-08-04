package edu.java.Types;

import java.util.Random;

import static edu.java.utils.FractalUtils.randomBetween;

public class Coefficients {
    double a;
    double b;
    double c;
    double d;
    double e;
    double f;
    private final Random random = new Random();
    public final int red = random.nextInt(256);
    public final int green = random.nextInt(256);
    public final int blue = random.nextInt(256);
    private static final double MIN_RANDOM_VALUE = -1.0;
    private static final double MAX_RANDOM_VALUE = 1.0;


    public Coefficients() {

        f = randomBetween(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
        c = randomBetween(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
        do {
            do {
                a = randomBetween(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
                d = randomBetween(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
            } while ((a * a + d * d) > 1);
            do {
                b = randomBetween(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
                e = randomBetween(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
            } while ((b * b + e * e) > 1);

        } while ((a * a + b * b + d * d + e * e) > (1 + (a * e - d * b) * (a * e - d * b)));
    }

    public void applyToPoint(Point point) {
        double newX = a * point.x + b * point.y + c;
        double newY = d * point.x + e * point.y + f;
        point.x = newX;
        point.y = newY;
    }

}
