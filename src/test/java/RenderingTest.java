import edu.java.Render.FractalRenderer;
import edu.java.Render.ParallelFractalRender;
import edu.java.variations.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RenderingTest {
    @Test
    @DisplayName("Рендеринг однопоточной реализации")
    void singleThreadedRenderTest() throws IOException {
        // Given
        int n = 1000;
        int eqCount = 5;
        int it = 500;
        int xRes = 1920;
        int yRes = 1080;
        int symmetry = 1;

        List<Variation> variations = new ArrayList<>();
        variations.add(new SphericalVariation());

        Path outputPath = Files.createTempFile("outputTest", ".png");

        // When
        FractalRenderer.render(n, eqCount, it, xRes, yRes, symmetry, variations, outputPath.toString());


        // Then
        assertTrue(Files.exists(outputPath));
        assertTrue(Files.isRegularFile(outputPath));

    }

    @Test
    @DisplayName("Рендеринг многопоточной реализации")
    void multiThreadedRenderTest() throws IOException {
        // Given
        int n = 1000;
        int eqCount = 5;
        int it = 500;
        int xRes = 1920;
        int yRes = 1080;
        int symmetry = 1;

        List<Variation> variations = new ArrayList<>();
        variations.add(new SphericalVariation());

        Path outputPath = Files.createTempFile("outputTest", ".png");

        // When
        ParallelFractalRender.render(n, eqCount, it, xRes, yRes, symmetry, variations, outputPath.toString());


        // Then
        assertTrue(Files.exists(outputPath));
        assertTrue(Files.isRegularFile(outputPath));

    }

    @Test
    @DisplayName("Рендеринг c нескольками вариациями")
    void multipleVariationsTest() throws IOException {
        // Given
        int n = 1000;
        int eqCount = 5;
        int it = 500;
        int xRes = 1920;
        int yRes = 1080;
        int symmetry = 1;

        List<Variation> variations = new ArrayList<>();
        variations.add(new SphericalVariation());
        variations.add(new SinusoidalVariation());
        variations.add(new HeartVariation());
        variations.add(new PolarVariation());

        Path outputPath = Files.createTempFile("outputTest", ".png");

        // When
        ParallelFractalRender.render(n, eqCount, it, xRes, yRes, symmetry, variations, outputPath.toString());


        // Then
        assertTrue(Files.exists(outputPath));
        assertTrue(Files.isRegularFile(outputPath));

    }
}
