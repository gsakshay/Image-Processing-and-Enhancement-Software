package ime.model;

/**
 * Represents an Image, its properties and actions(methods).
 */
interface Image {
  /**
   * Gets the height of the image.
   *
   * @return the height of the image.
   */
  int getHeight();

  /**
   * Gets the width of the image.
   *
   * @return the width of the image.
   */
  int getWidth();

  /**
   * Gets the max value of the image.
   *
   * @return the maximum value of the image.
   */
  int getMax();

  /**
   * Gets the pixel value provided the channel.
   *
   * @param x representing the row
   * @param y representing the column
   * @return the pixel channel value
   */
  Pixel getPixel(int x, int y);

  /**
   * Sets the pixel value provided the channel.
   *
   * @param x representing the row
   * @param y representing the column
   */
  void setPixel(int x, int y, Pixel p);

  /**
   * Method that applies a specific filter to the image to generate a new image with filter.
   *
   * @param filter the filter to be applied, instance of Filter
   * @return the filtered image
   */
  Image applyFilter(Filter filter);
}
