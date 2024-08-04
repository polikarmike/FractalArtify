import edu.java.utils.RenderingReport;
import edu.java.variations.PolarVariation;
import edu.java.variations.Variation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RenderingReportTest {
    @Test
    @DisplayName("Создание отчета")
    void analyzeRenderingTest() throws IOException {
        // Given
        int n = 1000;
        int eqCount = 5;
        int it = 500;
        int xRes = 1920;
        int yRes = 1080;
        int symmetry = 1;

        List<Variation> variations = List.of(new PolarVariation());

        // When
        Path tempFilePath = Files.createTempFile("temp_report", ".txt");
        String outputPath = tempFilePath.toString();

        RenderingReport.analyzeRendering(n, eqCount, it, xRes, yRes, symmetry, variations, outputPath);

        // Then
        assertTrue(Files.exists(tempFilePath));
        assertTrue(Files.isRegularFile(tempFilePath));
    }
}
