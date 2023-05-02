package ime.model;

/**
 * Represents a pixel of an Image.
 * Provides common methods that is operated on pixel level.
 */
interface Pixel {
  /**
   * Gets the max value.
   *
   * @return the max value
   */
  int getMax();

  /**
   * Gets the red channel.
   *
   * @return the red value
   */
  int getRed();

  /**
   * Gets the green channel.
   *
   * @return the green value
   */
  int getGreen();

  /**
   * Gets the blue channel.
   *
   * @return the blue value
   */
  int getBlue();

  /**
   * Gets the maximum value of the three components for each pixel.
   *
   * @return the maximum value
   */
  int getValue();

  /**
   * Gets the average of the three components for each pixel.
   *
   * @return the average
   */
  int getIntensity();

  /**
   * Gets the weighted sum - 0.2126 * r + 0.7152 * g + 0.0722 * b.
   *
   * @return the weighted sum
   */
  int getLuma();

}
