## User interface of the application and functionalities.

### Features

- Load and save image files in JPG, PNG, BMP and PPM formats.
- Basic image manipulation operations such as adjusting brightness, horizontal and vertical flips.
- Advanced image manipulation operations, such as greyscale conversion, blur, sharpen,
  sepia, dither.
- RGB split and combine operations.
- Live histogram display of the loaded image.

### Usage

1. To load an image, click on File > Open, select an image file from the file dialog, and click
   Open.
2. The loaded image will be displayed in the left panel, and its histogram in the right panel.
3. To manipulate the image, use the buttons in the Basic Operations panel, or the dropdowns in
   the Advanced Operations panel.
4. To save the manipulated image, click on File > Save As, and choose a destination and format for
   the file.
5. To exit the application, click on File > Exit.

Please refer the screenshot below for more details.

![Screenshot](https://imgur.com/7Tl73Xc.png)

#### Image View Panel

The image view panel on the top left of our application displays the image once it is loaded. It
keeps updating the live image as and when the edits are made by the users.

#### Basic Operations Panel

![Basic Operations](https://imgur.com/Nde0ELm.png)
The basic operations panel allows editing the image to apply the basic operations. The value of
the brightness adjustment should be numeric and should be entered in the text box, after which
clicking the button would brighten (if the value is positive) and darken (if the value entered
is negative) by that value. Horizontal and vertical flip can be simply performed by clicking the
respective buttons.

#### Image Histogram Panel

The histogram panel, which is on the top right of our application displays the live histogram of
the image. It is an interactive histogram, which allows to be zoomed, to focus on a specific
part of the histogram.

#### Advanced Operations Panel

![Advanced Operations](https://imgur.com/dFFgHqQ.png)

The advanced operations panel supports the greyscale variants (Red, Green, Blue, Value, Luma,
Intensity), filters (blur and sharpen). To apply operations, choose the options from the
dropdown menu and click the execute button for respective operations.

Additional operations supported in the advanced operations panel are sepia and dither.

Moreover, RGB Split and RGB combine allows to split an image, or combine three images of same
dimensions into one. For splitting, three different file locations should be input by the user
to choose where to save the split images. On the other hand, for combine, the user must choose
three different file types to combine. Failing to do so will result into an error.

## Commands that are supported by the application and its usage.

### To run the script file in command-line

* Open terminal/cmd at the location of jar file : `res` ->
  `ImageManipulationAndEnhancement.jar`<br>
* Run `java -jar ImageManipulationAndEnhancement.jar -file commands.txt`

The above step runs all the commands that are supported by the applications and operates
on the test image and saves all the resulting images in the res folder.

### Running the project interactively.

#### Method 1. To run the project from jar file.

* open terminal/cmd at location where the jar file is present : `res` ->
  `ImageManipulationAndEnhancement.jar`<br>
* Run `java -jar ImageManipulationAndEnhancement.jar`

#### Method 2. To run the project in IDE or code.

* In `src`
* Open file `Main.java`
* Run the `public static void main` method

##### Command to run a set of commands in a file.

```
run commands.txt
```

Running the above script also runs all the commands, similar to what we saw above, but here it
is running inside the program interactively.

##### Commands to `load` a file.

```
load test.ppm test-ppm
load test.png test-png
load test.jpg test-jpg
load test.bmp test-bmp
```

##### Commands to `save` a file.

```
save test-ppm.ppm test-ppm
save test-png.png test-png
save test-jpg.jpg test-jpg
save test-bmp.bmp test-bmp
```

##### Commands to `rgb-split` the file `test.ppm`. And save the file in any of the supported formats.

```
rgb-split test-ppm test-red test-green test-blue
save test-green-split.jpg test-green
```

##### Commands to `rgb-combine` the three red, green, and blue image. And save the file in any of the supported formats.

```
rgb-combine test-combine test-red test-green test-blue
save test-combine.png test-tint
```

##### Commands to `brighten` the file `test.ppm`. And save the file in any of the supported formats.

```
brighten 50 test-ppm test-brighter
save test-brighter-bmp.bmp test-brighter
```

```
brighten -50 test-ppm test-darken
save test-darken-bmp.bmp test-darken
```

##### Commands to `greyscale` the file `test.png` and `test.jpg` into all component. And save the file in any of the supported formats.

```
greyscale value-component test-png test-value-greyscale
save test-value-greyscale-ppm.ppm test-value-greyscale

greyscale luma-component test-png test-luma-greyscale
save test-luma-greyscale-jpg.jpg test-luma-greyscale

greyscale intensity-component test-png test-intensity-greyscale
save test-intensity-greyscale-bmp.bmp test-intensity-greyscale

greyscale red-component test-jpg test-red-greyscale
save test-red-greyscale-ppm.ppm test-red-greyscale

greyscale green-component test-jpg test-green-greyscale
save test-green-greyscale-png.png test-green-greyscale

greyscale blue-component test-jpg test-blue-greyscale
save test-blue-greyscale-bmp.bmp test-blue-greyscale
```

One thing to note here is that, we were already supporting the method for greyscale (luma) which
is also theoretically, as well as practically the same image as color transformation of
greyscale. Hence, the client can make use of the `luma-component` command for the use-cases.

##### Commands to `flip` (Both vertically and horizontally) the file `test.bmp`. And save the file in any of the supported formats.

```
horizontal-flip test-bmp test-horizontal
save test-horizontal-ppm.ppm test-horizontal

vertical-flip test-horizontal test-horizontal-vertical
save test-horizontal-vertical-png.png test-horizontal-vertical

vertical-flip test-bmp test-vertical
save test-vertical-jpg.jpg test-vertical
```

##### Commands to `blur` the file `test.ppm`. And save the file in any of the supported formats.

```
blur test-ppm test-blur
save test-blur-ppm.ppm test-blur
```

##### Commands to `sharpen` the file `test.png`. And save the file in any of the supported formats.

```
sharpen test-png test-sharpen
save test-sharpen-png.png test-sharpen
```

##### Commands to apply color transformations to create `greyscale` of the file `test.jpg`. And save the file in any of the supported formats.

```
greyscale luma-component test-png test-luma-greyscale
save test-luma-greyscale-jpg.jpg test-luma-greyscale
```

Commands to apply color transformations to create `sepia-tone` of the file `test.jpg`. And save the
file in any of the supported formats.

```
sepia test-jpg test-sepia
save test-sepia-jpg.jpg test-sepia
```

##### Commands to `dither` the file `test.bmp`. And save the file in any of the supported formats.

```
dither test-bmp test-dither
save test-dither-bpm.bmp test-dither
```

