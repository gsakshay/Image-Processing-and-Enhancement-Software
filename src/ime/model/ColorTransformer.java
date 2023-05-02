package ime.model;

import java.util.InputMismatchException;

/**
 * Implementation of Filter that applies Color transformation.
 * Performs color transformation by transforming a pixel's value in an image using a kernel. A
 * pixel's values change based on its own values and the kernel we use for color transformation.
 * Greyscale, Sepia and others are such examples.
 * Given the kernel while creating an object of this class will apply the color transformation to
 * the image when its apply method is called on using the kernel. Will return a color transformed
 * image.
 */
public class ColorTransformer implements Filter {
  private final double[][] kernel;

  /**
   * Constructing a ColorTransformer of a specific kernel.
   *
   * @param kernel a 2D matrix representing the kernel that is used for color transformation.
   */
  public ColorTransformer(double[][] kernel) {
    if (validateKernel(kernel)) {
      this.kernel = kernel;
    } else {
      throw new IllegalArgumentException("Please provide valid kernel to apply on RGB Pixel");
    }
  }

  /**
   * Method to validate the kernel. If the kernel is such that it can be applied for color
   * transformations. Kernel's column length should be same as the number of pixels.
   *
   * @param kernel 2D matrix representing the kernel that is used for color transformation.
   * @return boolean based on whether the kernel is valid or not.
   */
  private boolean validateKernel(double[][] kernel) {
    return kernel[0].length == 3;
  }

  @Override
  public Image apply(Image inputImage) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    int max = inputImage.getMax();

    Image newImage = new RGBImage(height, width, max);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel rgbP = inputImage.getPixel(i, j);
        double[][] rgbMatrix = {
                {rgbP.getRed()},
                {rgbP.getGreen()},
                {rgbP.getBlue()}};

        int row1 = kernel.length;
        int row2 = rgbMatrix.length;
        int col1 = kernel[0].length;
        int col2 = rgbMatrix[0].length;

        double[][] result = new double[row1][col2];

        if (col1 == row2) {
          // Matrix mul is possible
          for (int x = 0; x < row1; x++) {
            for (int y = 0; y < col2; y++) {
              for (int z = 0; z < col1; z++) {
                result[x][y] += kernel[x][z] * rgbMatrix[z][y];
              }
            }
          }
        } else {
          throw new InputMismatchException("Invalid Matrix multiplication");
        }

        // Now use the resultant matrix to get a new Pixel and insert in the loc

        int red = (int) Math.round(result[0][0]);
        int green = (int) Math.round(result[1][0]);
        int blue = (int) Math.round(result[2][0]);

        newImage.setPixel(i, j, new RGBPixel(red, green, blue, max));

      }
    }
    return newImage;
  }
}
