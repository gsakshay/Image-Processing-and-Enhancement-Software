# GRIME: Graphical Image Manipulation and Enhancement

Welcome to GRIME, an advanced image manipulation and enhancement software developed using Java Swing and built on robust design principles. This software empowers users to manipulate and enhance images with ease.

![Screenshot](https://imgur.com/7Tl73Xc.png)

## Usage

To get hands on and start using the software, follow [USEME.md](USEME.md)

1. **Running the Program**: Navigate to `src/Main` and execute the static `main` method in the `Main` file.
2. **Commands**: Use text-based commands to interact with the software. Refer to the `USEME.md` file for detailed command usage.

## Features

- **Graphical User Interface (GUI)**: Built with Java Swing, providing an intuitive interface for image manipulation.
- **Flexible File Handling** : Supports popular image formats such as PPM, PNG, JPG, and BMP. Allows users to specify the image to be loaded and saved, ensuring versatility in file handling.
- **Image Operations**: Offers a wide array of operations including brightening, darkening, flipping, greyscale, sepia, blurring, sharpening, and dithering.
- **Live Histogram**: Provides a real-time histogram displaying red, green, blue, and intensity components.
- **Error Handling**: Displays error messages for any encountered issues during operations, ensuring a smooth user experience.

## Architecture Overview

### MVC Design Pattern

- **Model**: Represents data and its operations, handling image manipulation and enhancement.
- **Controller**: Interacts with users, coordinating between the model and the view.
- **View**: Utilizes Java Swing to provide a graphical interface for user interaction.

#### Factory Method for File Types Support

The software incorporates a **Factory Method** design pattern to support multiple file types for image loading and saving operations. The `ImageIOLoader` and `ImageIOSaver` classes effectively utilize this pattern, allowing the software to handle diverse image formats such as JPG, PNG, and BMP seamlessly.

#### Strategy and Facade Design Patterns for Image Processing

The software's extensible image processing effects and filters are implemented using **Strategy** and **Facade** design patterns. Initially starting with basic functionalities like Blur and Sharpen, the architecture employed these patterns to pave the way for future enhancements.

- **Strategy Pattern**: The `MatrixFilter` class leverages this pattern, offering a flexible approach for applying various image filters by accepting different matrices (kernels).
- **Facade Pattern**: Implemented through the `ColorTransformer` class, this pattern encapsulates complex color transformation operations, such as Sepia and Dithering, simplifying their usage and ensuring seamless integration within the software.

The utilization of these design patterns not only broadened the scope of available features but also laid a robust foundation for incorporating new filters and operations efficiently.

#### Adapter Design for MVVM Integration

To facilitate the integration of MVVM architecture within the system, an **Adapter** design pattern was employed in the view model. This adaptation between the model and view layers enhances the software's architecture by establishing seamless interaction.

- **View Model Adapter**: The new View Model design, serving as an adapter, accesses the primary model and presents processed data required by the view. Its interface allows for easy extension or implementation of future adapters to support evolving view requirements.

By incorporating the Adapter design, the software extends its capability to integrate modern architectural paradigms while maintaining a streamlined interaction between the model and view components.


### Packages Overview

- **ime**: The initial version of the software, implementing image manipulation using text-based commands in a Model-View-Controller architecture.
- **mime**: An improved version building upon the previous design, adding extensive functionality, flexibility in file handling, and a GUI using Java Swing.
- **Additional Packages**: Detailed implementations for image loading, saving, filtering, and command-based functionalities.

## Development Process

The complete development process is documented in [DEVELOPMENT.md](DEVELOPMENT.md)