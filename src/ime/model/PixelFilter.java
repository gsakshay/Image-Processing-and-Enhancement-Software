package ime.model;

import java.util.function.Function;

/**
 * Implementation of the Filter, that applies a function to each of the pixel of the image and
 * returns a new image with filter applied.
 */
public class PixelFilter implements Filter {
  private final Function<Image, Function<Integer, Function<Integer, Pixel>>> filterFunction;

  /**
   * Constructor that gets the filter function that has to be applied for the image.
   *
   * @param filterFunction the function that takes image and pixels and applies the name to
   *                       resulting image.
   */
  public PixelFilter(Function<Image, Function<Integer, Function<Integer, Pixel>>> filterFunction) {
    this.filterFunction = filterFunction;
  }

  @Override
  public Image apply(Image inputImage) {
    Image newImage = new RGBImage(inputImage.getHeight(), inputImage.getWidth(),
            inputImage.getMax());
    for (int i = 0; i < newImage.getHeight(); i++) {
      for (int j = 0; j < newImage.getWidth(); j++) {
        newImage.setPixel(i, j, filterFunction.apply(inputImage).apply(i).apply(j));
      }
    }
    return newImage;
  }

}
