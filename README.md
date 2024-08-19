# FractalArtify

## Project Description

This project implements a fractal flame generator with options for both sequential and parallel rendering. Users can customize various generation parameters, including the number of iterations, the number of equations, image resolution, and choose variations to create unique fractal images.

## Fractal Examples

Below are examples of fractal images that can be created using this generator:

![Fractal Example 1](output/Example1.png)
![Fractal Example 2](output/Example4.png)
![Fractal Example 3](output/Example8.png)

## Key Features

- **Sequential Rendering**: Renders the image in a single thread.
- **Parallel Rendering**: Speeds up the rendering process by utilizing multithreading.
- **Customizable Parameters**: Users can choose the number of iterations, the number of equations, resolution, and variations to create the fractal.
- **Image Saving**: Generated fractals are saved as PNG files in the `output` folder.

## Available Variations

Fractal images are created using various variations that alter the shape and structure of the fractal. The following variations are supported in the project:

1. **Spherical**: Transforms points on a spherical surface, creating rounded shapes.
2. **Disk**: Transforms points into a disk structure, creating concentric circle effects.
3. **Heart**: Transforms points to form heart-shaped figures.
4. **Polar**: Converts Cartesian coordinates into polar coordinates, creating spiral effects.
5. **Sinusoidal**: Applies a sinusoidal transformation, creating wavy forms.

## Impact of Parameters on Fractal Generation

- **Number of Iterations**: Determines the number of repetitions in the mathematical process, affecting the complexity and detail of the image.
- **Number of Equations**: Influences the number of different transformations used to create the fractal.
- **Number of Iteration Steps**: Controls the accuracy of the fractal display, where a higher number of steps results in a more precise image.
- **Resolution for X and Y**: Determines the final image size horizontally and vertically, directly impacting the level of detail.
- **Number of Symmetries**: Adds symmetry to the fractal, which can result in more orderly and aesthetically pleasing images.

## Technologies Used

- **Java**: The main programming language for the project implementation.
- **Multithreading**: To speed up the rendering process.
- **Fractal Variations**: Various mathematical functions for creating unique fractal forms.
- **AWT (Abstract Window Toolkit)**: Used for handling graphics and images, including creating and saving PNG files.
