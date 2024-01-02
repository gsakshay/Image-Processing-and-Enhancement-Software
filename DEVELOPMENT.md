
# Development Process

## Iterative Development Approach

The development of the GRIME software followed an iterative process, adhering to SOLID design principles, emphasizing the Open-Closed principle throughout its evolution.

### Initial Development Phase

The software's initial phase laid the groundwork for image manipulation and enhancement. Following the principles of SOLID design, the architecture focused on:

- **Single Responsibility Principle (SRP)**: 
- **Open-Closed Principle (OCP)**: 

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

### Second Iteration: Incorporating Advanced Features and Patterns

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

### Third Iteration: MVVM Integration and Adapter Design

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