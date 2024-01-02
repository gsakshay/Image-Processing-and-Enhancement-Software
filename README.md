# GRIME: Graphical Image Manipulation and Enhancement

Find out how to use the software in ['./USEME.md'](USEME.md)

## Problem statement

This is built on top of [mime (More Image Manipulation and Enhancement)](#L176)

New requirements were:

* You must use Java Swing to build your graphical user interface. Besides the code examples from
  lecture, the provided code example illustrates some other features of Swing that you may find
  useful.
* The GUI should show the image that is currently being worked on. The image may be bigger than
  the area allocated to it in your graphical user interface. In this case, the user should be able
  to scroll the image. Any changes to the current image as a result of the image operations should
  be visible in the GUI.
* The histogram of the visible image should be visible as a line chart (not a bar chart) on the
  screen at all times. If the image is manipulated, the histogram should automatically refresh. The
  histogram should show the red, green, blue and intensity components.
* The user interface must expose all the required features of the program (flips, component
  visualization, greyscale, blurring, sharpening and sepia). You are not required to support
  interactive scripting through the GUI.
* When saving an image as a PNG/PPM/JPG, it should save what the user is currently seeing.
* The user should be able to specify suitably the image to be loaded and saved that the user is
  going to process. That is, the program cannot assume a hardcoded file or folder to load and save.
* Any error conditions should be suitably displayed to the user, through pop-up messages or
  clearly visible text as appropriate.
* The layout of the UI should be reasonable. Things should be in proper proportion, and laid out
  in a reasonable manner. Buttons/text fields/labels that are oversized, or haphazardly arranged,
  even if functional, will result in a point deduction.
* Each user interaction or user input must be reasonably user-friendly (e.g. making the user type
  the path to a file is poor UI design). We do not expect snazzy, sophisticated user-friendly
  programs. Our standard is: can a user unfamiliar with your code and technical documentation
  operate the program correctly without reading your code and technical documentation?

## New implementations

### View

This application provides a Graphical User Interface (GUI) for users to manipulate and enhance their
images. The current version of the application includes the following features:

* Loading and saving images in formats PPM, PNG, JPG, BMP
* Brighten, Darken, Horizontal Flip, Vertical Flip
* Greyscale, Sepia, Blur, Sharpen, Dither
* Live histogram of the image

The view component is a class that extends the Java Swing JFrame class and has various Swing
components to create the GUI. Here's a brief overview of the Swing components used:

* JMenuBar: A menu bar component that can contain menus, which can contain menu items.
* JMenu: A menu component that can contain menu items or sub-menus.
* JMenuItem: A menu item component that can be clicked to perform an action.
* JPanel: A container component that can hold other components and can have a layout manager.
* GridBagLayout: A layout manager that arranges components in a grid of cells, where each cell
  can have different sizes.
* BorderFactory: A factory class that provides methods to create borders for components.
* JScrollPane: A component that provides scrollbars for other components that are larger than the
  visible area.
* JLabel: A component that displays a text or an image.
* JButton: A component that can be clicked to perform an action.
* JTextField: A component that allows the user to enter text.
* JComboBox: A component that allows the user to select one item from a list of items.
* TitledBorder: A border that can have a title.
* Insets: A class that specifies the margins for a component.

The reason for using GridBagLayout and BorderLayout is to create a flexible and responsive user
interface. Both allow for a flexible and dynamic layout that can adapt to different screen sizes and
window dimensions.

The view provides several operations for the controller to call on it, such as:

1. When it wants to refresh the screen as new changes are done.
2. When it wants to address the user with an Error Message about any of the operations.
3. To provide access of the Features that our program supports to the view.

### View Model.

Now the design supports ViewModel, an adapter, using the power of composition. Hence, now
whenever view needs to represent data that is updated, it can directly query the model.
Our new View Model, uses our main model as its adapter and provides methods that benefit the view.

It reads our old model and provides the processed data whatever the view needs. This new
implementation has 5 useful methods, which provides, image, red, blue, green and intensity data
that the model needs for presentation.

One can easily extend it or write a new adapter using its interface that can help the view based
on the future needs.

## Design changes

There were no changes done to our existing code. None of the existing code was touched.

### Screenshot of the program with an image loaded

![Screenshot](https://imgur.com/7Tl73Xc.png)

### Updated Class Diagram

![GRIME Class Diagram](https://imgur.com/emdMBeJ.png)

### All the methods supported earlier are still supported, thanks to OOP and MVC pattern.

Read below for the documentation of previous version.

# mime: More Image Manipulation and Enhancement

## Problem statement

This is built on top of [ime (Image Manipulation and enhancement)](#L176)

New requirements were:

* Support the ability to load and save conventional file formats (bmp, jpg and png) in addition
  to ASCII ppm files from before.
* Support the ability to load the save images in formats using the same script command as before.
  The extension of the file determines the format to be used. Note that it should be possible to
  load an image in one format, process it and then save it in another format.
* Support the ability to blur and sharpen an image, keeping in mind that other filtering
  operations may be possible in the future.
* Support the ability to produce a greyscale and sepia version of an image using color
  transformations, keeping in mind that other color transformations may be possible in the future.
* Support the ability to dither an image.
* Support the ability to specify the above operations using script commands: blur, sharpen,
  greyscale, sepia and dither with the same set of parameters (source image name, destination image
  name).
* Support the ability to accept a script file as a command-line option. For example "-file
  name-of-script.txt". If a valid file is provided, the program should run the script and exit. If
  the program is run without any command line options, then it should allow interactive entry of
  script commands as before.
* Retain support for the PPM file format, and being able to switch between them (e.g. import a
  PPM file, work on it and save it as a JPEG file, etc.)
* Retain support for all previous operations and script commands.

Adhering to OOP and MVC we have proceeded forward in our implementation, and we have improved
upon feedbacks for the previous version which led to change in our existing code which will be
justified.

### Firstly, changes to our code based on the feedback.

##### Reading and writing of Files.

* Our code was handing, reading a file in the model, which was offered by IME interface.
  Feedbacks and use cases allowed us to understand that model should not know/need not know how
  the data is coming or in which format the data is present or using which OS the file would be
  read. All it needs to care about is the data and operations performed in that.
  Hence, comes our change, we changed the method signature of `readData` and `writeData` in the
  IME. We updated it to take InputStream of data for reading and converting it into our Image
  representation. And used OutputStream to send the raw image data back to the controller.
* This change in model is accompanied by the `Load` and `Save` commands of the controller.
  Now the reading of an image file will happen in the controller and the raw data of the image
  is sent to the model as InputStream of data. And Save receives an OutputStream of raw data of
  the image, creating a file in the rom and writing to it is done using the controller.
  This is one of the crucial changes we did. Although it is not much of code change, just
  shifted where the operations were handled, it helps us significantly in future when a new
  file format comes, if it does.
* Earlier we were delegating the reading and writing to `ImageLoader` and `ImageSaver`
  implementations, which were called through the model.
* Now these are being called by the controller and is a part of the controller. This still
  adhere to OOC and MVC and perhaps improves it.
* These `ImageLoader` and `ImageSaver` interfaces and its implementations were updated to handle
  with InputStream and OutputStream of data.
* One more justification for this change is that, as its likely that the view is also going to
  be included in the future, if we want to show an image on screen, with model giving output of the
  stream of data will help us to easily present it on screen, compared to previous
  implementation where it saved on the ROM directly.

### Updates to the code

#### Learning about Package privacy.

Here, there are no changes in the code per-se, but the `Image` and `Pixel` that were available
for everyone previously, is no longer available outside the model.
This helps us to secure the implementation details. Client will only know what they get.

#### FileService

Earlier, there was Image service that was handing `ImageLoader` and `ImageSaver`.
With that being changed (as mentioned above) and being used directly in the controller, we have
isolated `FileService` as a class along with interface that helps us to read any file.

* This also adheres design principles and actually improves code reuse.
* We use the same to read file of PPM and interpretation of PPM happens at its place at `PPMLoader`.
* Also, we use the same to interpret commands in a file using `run` command or `-file` command
  line command.

Both the change and the update required very less of code changes because of the way the
project was designed, which worked in our favour.

## New implementations

### Ability to handle multiple types of File

This is handled by controller, Model does not come into picture here.

* With the design we followed initially and the change we made regarding reading the file and
  handing Input and Output streams, this step was easier
* We created a new class called `ImageIOLoader` which implements `ImageLoader` interface and
  reads the .jpg, .png and .bmp and send the InputStream back to controller to send it to the model.
* Similarly, created a new class called `ImageIOSaver` which implements `ImageSaver` interface a
  takes an OutputStream and saves the image on the ROM, this again supports .jpg, .png and .bmp
  files.
  Basically `ImageIOLoader` and `ImageIOSaver` works with any image that can be used with ImageIO.
* We already had support for `PPMLoader` and `PPMSaver` which followed `ImageLoader` and
  `ImageSaver` respectively.
  This sums up the support for handling multiple types of files.

### Supporting new Operations

As the new operations have to be supported, we created an interface called `MIME` that extended
`IME` to support all the functionalities that were present before.
A new implementation of this interface called `MIMEImp` was created and `IMEImp` was extended by
this class.
New methods signatures in this interface were - `blur` `sharpen` `sepia` `dither` which all took
name of existing image and provided the name in which the resulting image's name has to be stored.

With this update, our controller will take in this new Model as its model argument. As the face
of the program is changing and new operations are made available, this update is a needed one.

### Supporting Filtering Operations

* This is a place that calls our design **Perfect**.
* Since we already had an interface `Filter` that was "applied" to an image, we just had to
  create classes extending this interface to support Filtering and ColorTransformation.
* We created a new class named `MatrixFilter` that takes an odd dimensioned matrix (Kernel) and
  applies this on the Image it gets.
* We created instances of this `MatrixFilter` as `Blur` and `Sharpen` by providing the
  respective kernels and used them in our `blur` and `sharpen` method signatures.

### Supporting Color Transformation Operations

* Even here, we utilized our Filter interface and created a new class of it called
  `ColorTransformer`.
* It took kernels whose column length matches with number of channels our pixel of Image has and
  computed the matrix multiplication with each pixel.
* We created instance of this `ColorTransformer` as `Sepia` and used it in our `sepia` method of
  `MIMEImp`
* Coming to implementation of "greyscale", as it is theoretically and practically same as
  luma-greyscale, whose functionality we are already supporting, we are letting the client know
  that they can use `greyscale luma-component` with source and destination to get the greyscale
  working.

### Dithering an image

* We reused our `luma-component` greyscale for implementing this method and the algorithm is
  written in our new MIMEImp which offers this functionality through the method `dither`.

### Support for command-line option

* We had to just consider the args in our existing `Main` file, and check for the `-file` option
  and support its functionality.
* As mentioned above we also reused our `FileService` here to read its contents and gave the
  controller streams of data form the file and the controller quits after completing the
  operations in the file, which is different from the `run` script which allows interactivity.

#### Improvements

With the new implementation of model, we also optimized our code to have fewer lines,
making it easier to debug.
Earlier although we had filters for each kind of greyscale and the loop was written only once,
the methods that called these greyscale filter were doing same job, each containing 3 lines, we
reduced it to contain 1 line of arrow function and created a private method that contained the
original 3 lines of:

1. Getting the image given the name
2. Applying the filter function
3. Storing the resultant in the name given.

These methods were overridden by our new extended class. Hence, these new methods will be used now.
By this way, we are also not touching old code, and also crating an optimized version.

### All the methods supported earlier are still supported, thanks to OOP and MVC pattern.

### Updated Class Diagram

![MIME Class Diagram](https://i.imgur.com/GmQZmw5.png)

Read below for the documentation of previous version. New version can be definitely called a
pro version.

# ime: Image Manipulation and Enhancement

## Problem statement

Program should support loading, manipulating and saving images using simple text-based
commands.

* Load an image from an ASCII PPM file (see below).
* Create images that visualize individual R,G,B components of an image.
* Create images that visualize the value, intensity or luma of an image as defined above.
* Flip an image horizontally or vertically.
* Brighten or darken an image.
* Split a single image into 3 images representing each of the three channels.
* Combine three greyscale image into a single color image whose R,G,B values come from the three
  images.
* Save an image to an ASCII PPM file (see below).
* Load and run the script commands in the specified file.

Allow a user to interact with your program to use these operations, using text-based scripting.

## Design

Our design follows MVC Pattern.

1. The model represents data and its operations.
2. The Controller interacts with user, model and view and our controller follows Command Design
   Pattern.
3. There is no dedicated view as of now as, the requirement is user interacting with command,
   that can be satisfied by the controller with Command design patter.

![ime - MVC Design](https://i.imgur.com/2vSpwdX.png)

## Package `ime`

This is our main package, that implements the given problem in MVC design.
Package `model` -> Defines our model, which represents an Image processor that can also remember
the images enhanced in a session.
Package `control` -> Defines our controller that takes one Model object and uses that to
communicate with the model. Our controller follows **Command Design Pattern**.
There is no specific view as of now, as the need has not come yet. As and when the application
scales and user interaction demands view, a separate view with its packages will be implemented.

### Model

Our main model represents an Image Manipulator and Enhancement. Provides different methods to
operate on different images in a session and remembers them.

* Class `IMEImp` is an implementation of interface `ime` that provides operations that satisfies
  the problem statement and is the only model that is known by the controller.
    * It offers all the operations that is required by the user and controller to communicate.
    * Acts as the face of the model.
    * The main model also uses `ImageService`, offered from the `service` package to load files
      and save files of different image types and formats.

#### Package `image`

Offers and Image, with a custom pixel, depending on the channels. Image attributes and methods
are defined in the Image interface. All the images follow or extend this.

* Class `RGBPixel` represents an RGB pixel with red, green, blue and max value a pixel can hold.
  It is an implementation of `Pixel` interface. Any pixel in the future, say `ARGBPixel` having a
  transparency channel can also extend this interface and RGB class respectively.
    * This is used by the objects that are an implementation of an `Image`.
* `Image` interface provides a generic image, represented in terms of pixels. It offers basic
  setters and getters. It offers a filter that can be applied on the image - This filter needs
  to follow the type `Filter`, explained below.
* Class `RGBImage` represents an RGB image that has `RGBPixel` as its pixel, the image has a
  height, width and max value a component of its pixel can have. Along with this, an important
  feature is having the ability to apply a filter on itself.

#### Package `filter`

Offers a filter that can be applied on an Image to get another image. This is built in the
generic way that can also be used by different instances of images. Currently, we have only one
type if Image, that is RGBImage.
In future multiple filters can also be applied easily on an Image using the same.

* Class `PixelFilter` is an implementation of `Pixel` interface that allows us to apply a filter,
  in the sense, it receives an Image object and returns a new Image object that has the filtered
  effect. Here, `PixelFilter` implements this by taking a function that takes Image data and
  returning a "Pixel" in this constructor and applies this function to pixels of the Image
  provided with its "apply" method, returning a new filtered image.
    * The `Filter` can have different implementations based on what different operations needed to
      be done on the image, each takes an `Image` and returns an `Image`.

### Controller

The controller here, follows "Command Design Pattern" as it meets the program requirements
perfectly.

Different commands like, load, brighten, greyscale, vertical and horizontal flip are all treated
as commands and also since greyscale can offer many types in them, a composite design of command
is also visible in our design. All these commands implements the interface `IMECommand`

Interface `IController` and its implementation `Controller`.

* `Controller` is our main controller, and it implements interface `Icontroller` that specifies
  method "run" which basically starts the program.

#### Package `commands`

All our commands for the controller following Command Design pattern is defined here.

* Interface `IMECommands`, defines the type of the command, it has only one method to offer,
  `execute` that executes the respective command.
  Following classes each act as a command.

* `Brighten` - brightens an image with the scale provided.
* `Flip` - flips the image with the orientation provided.
* `Load` - helps load a new image into the model.
* `Save` - helps save a new image into disk.
* `RGBCombine` - combines red, green, blue channels of 3 different (generally greyscale) images
  respectively.
* `RGBSplit` - splits an image into its red, green, and blue greyscale images respectively. And
  saves them with the new names give in memory.
* `Run` - runs a script, that contains all these commands provided the proper script file path.

##### Package `greyscale`

As mentioned above, our greyscale implements the composite nature of these commands, where each
of these are individual commands that follow `IMECommand`. In the future, it can also be used
directly or with any other way easily.

* This is a collection of `IMECommands` that offers various types of ways that result in
  greyscale of an image. Following classes each act as a command.
* `GreyscaleBlue` - greyscale with blue channel.
* `GreyscaleRed` - greyscale with red channel.
* `GreyscaleGreen` - greyscale with green channel.
* `GreyscaleValue` - greyscale with value formula.
* `GreyscaleIntensity` - greyscale with intensity channel.
* `GreyscaleLuma` - greyscale with luma channel.
* `GreyscaleComposite` - helps execute the ideal command once the `greyscale` command is
  encountered.

### Service

A collection of services that our ime program use.
`ImageServiceImp` that follows `ImageService` provides these methods.
This service as of now helps, reading any file, reading image files of particular type, which is
easy to extend again.

#### Package `imageLoader`

Offers load different types of Image files.
`ImageLoader` interface defines a loader, will contain load method.
`PPMLoader` class implements `ImageLoader` and will load ppm image types. Returns the image data,
useful to create an Image.

#### Package `imageSaver`

Offers save a different types of Image files.
`ImageSaver` interface defines a saver, will contain save method.
Run the script `commands-2.txt` attached here to run the program and load a sample image,
perform some operations on it and save the image.
Use the below command to run the script:
`PPMSaver` class implements `ImageSaver` and will save ppm image types.

### Running the program.

Please refer to the `USEME.md` file, which contains more details about all the commands.

(This is of Version 1 - Assignment 4)

Go-to `src` -> `Main` -> Run the static main method in this file.
It creates a controller object and calls the `execute` method of the controller, that basically
starts the program.

#### Commands that the program accepts

###### Loads a new image as test

```
load res/test.ppm test
```

###### Saves the resulting image with the new name provided.

```
save res/test-save.ppm test
```

###### Increases the brightness of test image by a scale of 50 for the loaded image.

```
brighten 50 test test-brighter
```

###### Saves the resulting image with the new name provided.

```
save res/test-brighter-50.ppm test-brighter
```

###### Flips the test image vertically

```
vertical-flip test test-vertical
```

###### Saves the resulting image with the new name provided.

```
save res/test-vertical.ppm test-vertical
```

###### Flips the test image horizontally

```
horizontal-flip test-vertical test-vertical-horizontal
```

###### Saves the resulting image with the new name provided.

```
save res/test-vertical-horizontal.ppm test-vertical-horizontal
```

###### Creates a greyscale image considering the red component

```
greyscale red-component test test-greyscale-red
```

###### Saves the resulting image with the new name provided.

```
save res/test-greyscale-red.ppm test-greyscale-red
```

###### Creates a greyscale image considering the green component

```
greyscale green-component test test-greyscale-green
```

###### Saves the resulting image with the new name provided.

```
save res/test-greyscale-green.ppm test-greyscale-green
```

###### Creates a greyscale image considering the blue component

```
greyscale blue-component test test-greyscale-blue
```

###### Saves the resulting image with the new name provided.

```
save res/test-greyscale-blue.ppm test-greyscale-blue
```

###### Creates a greyscale image with value formula.

```
greyscale value-component test test-greyscale-value
```

###### Saves the resulting image with the new name provided.

```
save res/test-greyscale-value.ppm test-greyscale-value
```

###### Creates a greyscale image with luma formula.

```
greyscale luma-component test test-greyscale-luma
```

###### Saves the resulting image with the new name provided.

```
save res/test-greyscale-luma.ppm test-greyscale-luma
```

###### Creates a greyscale image with intensity formula.

```
greyscale intensity-component test test-greyscale-intensity
```

###### Saves the resulting image with the new name provided.

```
save res/test-greyscale-intensity.ppm test-greyscale-intensity
```

###### Splits an image into its red, green and blue greyscale images respectively.

```
rgb-split test test-red test-green test-blue
```

###### Saves the resulting images with the new names provided.

```
save res/test-split-red.ppm test-red
save res/test-split-green.ppm test-green
save res/test-split-blue.ppm test-blue
```

###### Combines 3 different greyscale images taking each one's red, green and blue components.

```
rgb-combine test-combined test-red test-green test-blue
```

###### Saves the resulting image with the new name provided.

```
save res/test-combined.ppm test-combined
```

###### Runs the commands present in a script file given the path to the file.

###### Please provide this file as command line argument for running a script - res/commands-2.txt

Any valid commands from the above or all, can run successfully here.

```
run res/commands-2.txt
```

#### Error handling

We are handling errors. We catch them when and where if any misspelled command comes or invalid
arguments comes, we throw an error and catch it and present useful information to the user.

### Image Citation:

> A busy street in Boston
> ![Boston](https://i.imgur.com/EdjgDSh.png)

Author: Akshay Gunjur Surya Prakash

Date: March 5, 2023.

Source: Captured on a Spring break evening, near Seaport, Boston.

Usage Terms: This image is my original work, and I authorize its use in this project.