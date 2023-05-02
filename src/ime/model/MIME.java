package ime.model;

/**
 * This interface extends the IME interface and allows more functionality and also supports the
 * old ones. It also optimizes the code it inherited in the case of greyscale.
 *
 * <p>It introduces new operations such as ColorTransformations and Matrix filters.
 *
 * <p>Provides different methods to operate on different images in a session and remembers them.
 */
public interface MIME extends IME {
  /**
   * Applies blur filter on an image and stores the resultant image in the memory.
   *
   * @param imageName  image using which the filter has to be applied.
   * @param resultName name in which the resultant image has to be stored.
   */
  void blur(String imageName, String resultName);

  /**
   * Applies blur filter on an image and stores the resultant image in the memory.
   *
   * @param imageName  image using which the filter has to b e applied.
   * @param resultName name in which the resultant image has to be stored.
   */
  void sharpen(String imageName, String resultName);

  /**
   * Applies Sepia color transformation on an image and stores the resultant image in the memory.
   *
   * @param imageName  image using which the transformation has to be applied.
   * @param resultName name in which the resultant image has to be stored.
   */
  void sepia(String imageName, String resultName);

  /**
   * Method that operates on an Image and creates a dithered image and stores that in the memory.
   *
   * @param imageName  image using which the operation has to be performed.
   * @param resultName name in which the resultant image has to be stored.
   */
  void dither(String imageName, String resultName);
}
