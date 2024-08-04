package edu.java.variations;

import edu.java.Types.Point;

public class HeartVariation implements Variation {
    private static final double YSHIFT = 0.3;

    @Override
    public void applyToPoint(Point point) {
        double x2 = point.x * point.x;
        double y2 = point.y * point.y;
        double r = Math.sqrt(x2 + y2);
        double theta = Math.atan2(point.y, point.x);

        point.x =  r * Math.sin(r * theta);
        point.y = -r * Math.cos(r * theta) - YSHIFT;
    }
}
