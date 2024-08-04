package edu.java;

import edu.java.Render.FractalRenderer;
import edu.java.Render.ParallelFractalRender;
import edu.java.variations.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String OUTPUT_FOLDER = "output";
    private static int fileCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRendering = true;

        // Create the "output" folder if it doesn't exist
        File outputFolder = new File(OUTPUT_FOLDER);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }

        while (continueRendering) {
            try {
                System.out.println("Choose fractal rendering version:");
                System.out.println("1. Sequential");
                System.out.println("2. Parallel");
                int choice = scanner.nextInt();

                System.out.println("Enter number of iterations:");
                int numIterations = scanner.nextInt();

                System.out.println("Enter number of equations:");
                int eqCount = scanner.nextInt();

                System.out.println("Enter number of iteration steps:");
                int iterations = scanner.nextInt();

                System.out.println("Enter resolution for X:");
                int xRes = scanner.nextInt();

                System.out.println("Enter resolution for Y:");
                int yRes = scanner.nextInt();

                System.out.println("Enter number of symmetries:");
                int symmetry = scanner.nextInt();

                List<Variation> variations = new ArrayList<>();
                System.out.println("Choose variations (1-Spherical, 2-Disk, 3-Heart, 4-Polar, 5-Sinusoidal, 0-Done):");

                boolean isChoosingVariations = true;
                while (isChoosingVariations) {
                    int varChoice = scanner.nextInt();
                    switch (varChoice) {
                        case 1 -> variations.add(new SphericalVariation());
                        case 2 -> variations.add(new DiskVariation());
                        case 3 -> variations.add(new HeartVariation());
                        case 4 -> variations.add(new PolarVariation());
                        case 5 -> variations.add(new SinusoidalVariation());
                        case 0 -> {
                            if (!variations.isEmpty()) {
                                isChoosingVariations = false; // Exit the variations selection loop
                            } else {
                                System.out.println("No variations selected. Please choose at least one variation.");
                            }
                        }
                        default -> System.out.println("Invalid input, please select from 0 to 5.");
                    }
                }

                String fileName = OUTPUT_FOLDER + File.separator + "fractal_" + fileCounter++ + ".png";

                System.out.println("Rendering...");

                if (choice == 1) {
                    FractalRenderer.render(numIterations, eqCount, iterations, xRes, yRes, symmetry, variations, fileName);
                } else if (choice == 2) {
                    ParallelFractalRender.render(numIterations, eqCount, iterations, xRes, yRes, symmetry, variations, fileName);
                } else {
                    System.out.println("Invalid rendering version choice. Please choose 1 or 2.");
                    continue;
                }

                System.out.println("Rendering completed. File saved as " + fileName);
                System.out.println("Do you want to create another fractal? (yes/no)");
                String response = scanner.next().toLowerCase();
                continueRendering = response.equals("yes");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.next(); // Clear invalid input
            }
        }

        System.out.println("Program terminated.");
        scanner.close();
    }
}


