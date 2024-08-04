package edu.java.utils;

import edu.java.Types.Coefficients;
import edu.java.Types.Point;

import java.util.Random;

public class FractalUtils {
    private static final double XMIN = -1.777;
    private static final double XMAX = 1.777;
    private static final double YMIN = -1;
    private static final double YMAX = 1;
    private static final Random RANDOM = new Random();

    private FractalUtils() {

    }

    public static Coefficients[] generateCoefficients(int eqCount) {
        Coefficients[] coefficients = new Coefficients[eqCount];
        for (int i = 0; i < eqCount; i++) {
            coefficients[i] = new Coefficients();
        }
        return coefficients;
    }

    public static int[] calculateRotatedCoordinates(Point point, double angle, int xRes, int yRes) {
        double x = point.x * Math.cos(angle) - point.y * Math.sin(angle);
        double y = point.x * Math.sin(angle) + point.y * Math.cos(angle);

        int xNext = xRes - (int) (((XMAX - x) / (XMAX - XMIN)) * xRes);
        int yNext = yRes - (int) (((YMAX - y) / (YMAX - YMIN)) * yRes);

        return new int[]{xNext, yNext};
    }

    public static double randomBetween(double min, double max) {
        return min + (max - (min)) * RANDOM.nextDouble();
    }

    public static int getRandomAffine(int eqCount) {
        return RANDOM.nextInt(eqCount);
    }
}

