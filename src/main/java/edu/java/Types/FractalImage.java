package edu.java.Types;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class FractalImage {
    private Pixel[][] pixels;
    private static ReentrantLock[][] locks;
    private int xRes;
    private int yRes;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;
    private static final double GAMMA = 2.2;

    public FractalImage(int xRes, int yRes) {
        this.xRes = xRes;
        this.yRes = yRes;
        this.pixels = new Pixel[xRes][yRes];
        initializePixelsArray();
    }

    private void initializePixelsArray() {
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                pixels[i][j] = new Pixel();
            }
        }
    }

    public void initializeLocksArray() {
        locks = new ReentrantLock[xRes][yRes];

        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                locks[i][j] = new ReentrantLock();
            }
        }

    }

    public void updatePixel(int x, int y, Coefficients coefficient) {
        if (isWithinBounds(x, y)) {
            Pixel pixel = pixels[x][y];

            if (pixel.counter == 0) {
                setPixelColor(pixel, coefficient);
            } else {
                blendPixelColors(pixel, coefficient);
            }

            pixel.counter++;
        }
    }

    public void updateParallelPixel(int x, int y, Coefficients coefficient) {
        if (isWithinBounds(x, y)) {
            locks[x][y].lock();
            try {
                Pixel pixel = pixels[x][y];

                if (pixel.counter == 0) {
                    setPixelColor(pixel, coefficient);
                } else {
                    blendPixelColors(pixel, coefficient);
                }

                pixel.counter++;
            } finally {
                locks[x][y].unlock();
            }
        }
    }

    private boolean isWithinBounds(int x, int y) {
        return x < xRes && y < yRes && x > 0 && y > 0;
    }

    private void setPixelColor(Pixel pixel, Coefficients coefficient) {
        pixel.red = coefficient.red;
        pixel.green = coefficient.green;
        pixel.blue = coefficient.blue;
    }

    private void blendPixelColors(Pixel pixel, Coefficients coefficient) {
        pixel.red = (pixel.red + coefficient.red) / 2;
        pixel.green = (pixel.green + coefficient.green) / 2;
        pixel.blue = (pixel.blue + coefficient.blue) / 2;
    }

    public void saveImage(String fileName) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                int rgb = ((int) pixels[i][j].red << RED_SHIFT)
                    | ((int) pixels[i][j].green << GREEN_SHIFT) | (int) pixels[i][j].blue;
                image.setRGB(i, j, rgb);
            }
        }

        try {
            File output = new File(fileName);
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void correction() {
        double max = 0.0;

        for (int row = 0; row < xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                if (pixels[row][col].counter != 0) {
                    pixels[row][col].normal = log10(pixels[row][col].counter);
                    if (pixels[row][col].normal > max) {
                        max = pixels[row][col].normal;
                    }
                }
            }
        }

        for (int row = 0; row < xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                if (pixels[row][col].counter != 0) {
                    pixels[row][col].normal /= max;
                    pixels[row][col].red = (int) (pixels[row][col].red * pow(pixels[row][col].normal, (1.0 / GAMMA)));
                    pixels[row][col].green = (int) (pixels[row][col].green
                        * pow(pixels[row][col].normal, (1.0 / GAMMA)));
                    pixels[row][col].blue = (int) (pixels[row][col].blue * pow(pixels[row][col].normal, (1.0 / GAMMA)));
                }
            }
        }
    }
}
