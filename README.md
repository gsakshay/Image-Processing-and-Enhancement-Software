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
- **View**: Utilizes Java Swing to provide a graphical interface for user interaction (not explicitly detailed in this README).

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

### Iterative Development Approach

The development of the GRIME software followed an iterative process, adhering to SOLID design principles, emphasizing the Open-Closed principle throughout its evolution.

#### Initial Development Phase

The software's initial phase laid the groundwork for image manipulation and enhancement. Following the principles of SOLID design, the architecture focused on:

- **Single Responsibility Principle (SRP)**: 
- **Open-Closed Principle (OCP)**: 
- 
This phase established the foundation, providing basic functionalities like loading images, basic image manipulation, and scripting support.

The initial implementation centers around the MVC design pattern within the `ime` package. Notably:

1. **Model**: `IMEImp` within `model` defines an image processor, utilizing `ImageService` for file operations.
2. **Image Handling**: The `image` package provides `RGBImage`, leveraging `RGBPixel` for pixel representation and filters via the `PixelFilter` class.
3. **Command Controller**: `Controller` follows Command Design Pattern in the `control` package, handling commands such as load, brighten, and flip through `IMECommand` implementations.
4. **Command Structures**: The `commands` package hosts various commands like `Brighten`, `Flip`, `Load`, `Save`, `RGBCombine`, and `RGBSplit`.
5. **Greyscale Variants**: The `greyscale` package offers different greyscale types via classes like `GreyscaleBlue`, `GreyscaleRed`, etc., organized as composite commands.
6. **Service Layer**: The `service` package houses `ImageServiceImp`, facilitating file reading and writing via loaders (`ImageLoader`) and savers (`ImageSaver`).

![ime - MVC Design](https://i.imgur.com/2vSpwdX.png)

This structure establishes an adaptable foundation, handling image manipulation commands, composite operations, and file I/O operations in a modular manner.

#### Second Iteration: Incorporating Advanced Features and Patterns

Expanding on the initial phase, the software underwent an evolution to incorporate advanced features and design patterns:

- **Strategy and Facade Patterns**: Introduced to enhance image processing capabilities. Starting with Blur and Sharpen effects, these patterns paved the way for future enhancements, including Dithering and Sepia effects.
- **Factory Method**: Integrated to support multiple file types, allowing seamless loading and saving operations for image formats like JPG, PNG, and BMP.

1. **File Handling Enhancements**
   - Implemented `ImageIOLoader` and `ImageIOSaver` to manage multiple image file formats (e.g., .jpg, .png, .bmp) using input/output streams via `ImageIO`.
   - Expanded existing support for `PPMLoader` and `PPMSaver` to handle a wider range of image formats.

2. **New Operation Support**
   - Introduced `MIME` interface extending `IME` to integrate new functionalities like blur, sharpen, sepia, and dither, implemented within `MIMEImp`.
   - Adapted the controller to utilize the new `MIMEImp` as the updated model to accommodate these new operations effectively.

3. **Filtering and Color Transformation**
   - Leveraged existing `Filter` interface to create `MatrixFilter` for operations like blur and sharpen, applied via kernel matrices.
   - Introduced `ColorTransformer` implementing color transformation methods like sepia using matrix multiplication.

4. **Greyscale Enhancement**
   - Optimized greyscale functionality by reusing the luma-component greyscale method, advising clients to utilize this for greyscale operations.

5. **Dithering Implementation**
   - Implemented dithering using the existing luma-component greyscale method, enhancing image processing capabilities.

6. **Command-Line Support**
   - Incorporated command-line options handling within the existing Main file to process file-based operations through the controller.

This phase showcased a conscious effort to apply design patterns and principles, expanding the software's capabilities while ensuring it remains open for further extensions.

#### Third Iteration: MVVM Integration and Adapter Design

The final phase emphasized the integration of MVVM architecture into the system, marking a significant enhancement in the software's design:

- **Adapter Design for MVVM**: Implemented to bridge the gap between the model and view layers, enabling the system to adapt seamlessly to the MVVM architecture.
- **Extension of View Model**: Employed as an adapter, the View Model extended the architecture, allowing for easy integration with future adaptations or requirements from the view layer.

1. **Java Swing GUI**
   - Implemented a graphical user interface (GUI) using Java Swing for image manipulation and visualization.
   - Enabled image scrolling for images larger than the allocated display area.

2. **Live Histogram Display**
   - Incorporated a live histogram (line chart) displaying red, green, blue, and intensity components of the visible image.
   - Ensured automatic refresh of the histogram upon image manipulation.

3. **Functional User Interface**
   - Exposed all program features (e.g., flips, component visualization, filters) via the user interface.
   - Allowed users to load and save images without hardcoding file paths.

4. **Error Handling and UI Layout**
   - Enabled error messages for error conditions, ensuring clarity for users.
   - Ensured a reasonable and proportionate layout for better user experience.

### New Implementations
1. **View Component**
   - Utilized Swing components like JMenuBar, JMenu, JMenuItem, JPanel, JScrollPane, JLabel, JButton, etc., for creating the GUI layout.
   - Employed GridBagLayout and BorderLayout for a flexible and responsive interface design.

2. **View Model**
   - Introduced a ViewModel adapter to directly query and provide updated data to the view from the main model.
   - Facilitated access to image data (image, red, blue, green, intensity) required for presentation in the view.

![GRIME Class Diagram](https://imgur.com/emdMBeJ.png)

The iterative process emphasized adherence to SOLID principles, ensuring each phase built upon the previous one, maintaining extensibility, and enabling the system to accommodate future enhancements efficiently.