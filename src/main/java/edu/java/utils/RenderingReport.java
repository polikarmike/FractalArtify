package edu.java.utils;

import edu.java.Render.FractalRenderer;
import edu.java.Render.ParallelFractalRender;
import edu.java.variations.Variation;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class RenderingReport {
    private static final long NANOSECONDS_TO_MILLISECONDS = 1_000_000;
    private static final String MILLISECONDS_UNIT = " ms\n";

    private RenderingReport() {

    }

    @SuppressWarnings("ParameterNumber")
    public static void analyzeRendering(int n, int eqCount, int it, int xRes, int yRes, int symmetry,
        List<Variation> variations, String outputPath) throws IOException {

        Path outputPathPNG = Files.createTempFile("outputTest", ".png");

        long singleThreadedTime = measureTime(() ->
            FractalRenderer.render(n, eqCount, it, xRes, yRes, symmetry, variations, outputPathPNG.toString()));

        long multiThreadedTime = measureTime(() ->
            ParallelFractalRender.render(n, eqCount, it, xRes, yRes, symmetry, variations, outputPathPNG.toString()));

        double acceleration = (double) singleThreadedTime / multiThreadedTime;

        writeReportToFile(n, eqCount, it, xRes, yRes, symmetry, singleThreadedTime, multiThreadedTime,
            acceleration, variations.size(), outputPath);
    }

    @SuppressWarnings("ParameterNumber")
    private static void writeReportToFile(int n, int eqCount, int it, int xRes, int yRes, int symmetry,
        long singleThreadedTime, long multiThreadedTime, double acceleration, int sizeVariations, String outputPath) {

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("Параметры\n");
            writer.write("Разрешение: " + xRes + "x" + yRes + "\n");
            writer.write("Количество точек для отрисовки: " + n + "\n");
            writer.write("Количество аффинных преобразований: " + eqCount + "\n");
            writer.write("Количество итераций для каждой точки: " + it + "\n");
            writer.write("Количество осей симметрий: " + symmetry + "\n");
            writer.write("Количество вариаций: " + sizeVariations + "\n");

            writer.write("\nВремя рендеринга\n");
            writer.write("Однопоточная реализация: " + singleThreadedTime + MILLISECONDS_UNIT);
            writer.write("Многопоточная реализация: " + multiThreadedTime + MILLISECONDS_UNIT);

            writer.write("\nУскорение: " + acceleration + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long measureTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / NANOSECONDS_TO_MILLISECONDS;
    }
}
