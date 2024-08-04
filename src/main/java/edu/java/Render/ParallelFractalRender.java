package edu.java.Render;

import edu.java.Types.Coefficients;
import edu.java.Types.FractalImage;
import edu.java.Types.Point;
import edu.java.variations.Variation;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static edu.java.utils.FractalUtils.*;

public class ParallelFractalRender {
    private static final double XMIN = -1.777;
    private static final double XMAX = 1.777;
    private static final double YMIN = -1;
    private static final double YMAX = 1;
    private static final Random RANDOM = new Random();
    private static FractalImage fractalImage;
    private static final int ITERATION_STEP_START = -20;

    private ParallelFractalRender() {

    }

    @SuppressWarnings("ParameterNumber")
    public static void render(int numIterations, int eqCount, int iterations, int xRes, int yRes,
        int symmetry, List<Variation> variations, String fileName) {

        fractalImage = new FractalImage(xRes, yRes);
        fractalImage.initializeLocksArray();
        Coefficients[] coefficients = generateCoefficients(eqCount);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int iteration = 0; iteration < numIterations; iteration++) {
            executor.execute(new RenderTask(iterations, eqCount, xRes, yRes, symmetry, variations, coefficients));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
           throw new RuntimeException(e);
        }

        fractalImage.correction();
        fractalImage.saveImage(fileName);
    }

    private record RenderTask(int iterations, int eqCount, int xRes, int yRes, int symmetry, List<Variation> variations,
                              Coefficients[] coefficients) implements Runnable {

            @Override
            public void run() {
            Point point = new Point(randomBetween(XMIN, XMAX), randomBetween(YMIN, YMAX));

            for (int iterationStep = ITERATION_STEP_START; iterationStep < iterations; iterationStep++) {
                int coeffIndex = getRandomAffine(eqCount);
                coefficients[coeffIndex].applyToPoint(point);

                Variation variation = variations.get(RANDOM.nextInt(variations.size()));
                variation.applyToPoint(point);

                double angle = 0;
                double angleIncrement = Math.PI * 2 / symmetry;

                for (int symmetryStep = 0; symmetryStep < symmetry; angle += angleIncrement, symmetryStep++) {
                    int[] coordinates = calculateRotatedCoordinates(point, angle, xRes, yRes);
                    int xNext = coordinates[0];
                    int yNext = coordinates[1];

                    fractalImage.updateParallelPixel(xNext, yNext, coefficients[coeffIndex]);
                }
            }
        }
    }
}
