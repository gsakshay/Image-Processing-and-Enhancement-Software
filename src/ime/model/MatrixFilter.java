package ime.model;

/**
 * An implementation of Filter that applies filter on images by using a matrix that operates on
 * each pixel using its values as well as the computed values of its surrounding pixels using the
 * kernel that is provided for the expected behaviour.
 * Sharpen and blur are some examples.
 * Given the kernel while creating an object of this class will apply the filter to
 * the image when its apply method is called on using the kernel. Will return a filtered image.
 */
public class MatrixFilter implements Filter {
  private final double[][] kernel;
  private final int size;

  /**
   * Creates a MatrixFilter given the kernel.
   *
   * @param kernel a 2D matrix representing the kernel that is used for filtering.
   */
  public MatrixFilter(double[][] kernel) {
    if (validateKernel(kernel)) {
      this.kernel = kernel;
      this.size = kernel.length;
    } else {
      throw new IllegalArgumentException("Please provide valid filter of Odd dimension");
    }
  }

  /**
   * To validate the filter.
   * Kernels should be of odd dimension for the filter operation.
   *
   * @param kernel a 2D matrix representing the kernel that is used for filtering.
   * @return boolean whether the kernel is valid or not.
   */
  private boolean validateKernel(double[][] kernel) {
    int l = kernel.length;
    if (l % 2 == 1) {
      for (int i = 0; i < l; i++) {
        if (kernel[i].length != l) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public Image apply(Image inputImage) {
    Image newImage = new RGBImage(inputImage.getHeight(), inputImage.getWidth(),
            inputImage.getMax());

    // Iterate over each pixel in the image
    for (int x = 0; x < newImage.getHeight(); x++) {
      for (int y = 0; y < newImage.getWidth(); y++) {
        // Iterate over each element in the kernel
        double sumR = 0;
        double sumG = 0;
        double sumB = 0;

        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            int px = x - size / 2 + i;
            int py = y - size / 2 + j;

            // Check if the current kernel element overlaps with a pixel in the image and compute
            // it for channels
            if (px >= 0 && px < newImage.getHeight() && py >= 0 && py < newImage.getWidth()) {
              sumR += kernel[i][j] * inputImage.getPixel(px, py).getRed();
              sumG += kernel[i][j] * inputImage.getPixel(px, py).getGreen();
              sumB += kernel[i][j] * inputImage.getPixel(px, py).getBlue();
            }
          }
        }
        newImage.setPixel(x, y, new RGBPixel((int) Math.round(sumR), (int) Math.round(sumG),
                (int) Math.round(sumB), inputImage.getMax()));
      }
    }
    return newImage;
  }
}
