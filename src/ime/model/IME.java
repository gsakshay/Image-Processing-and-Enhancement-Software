package ime.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface represents operations of Image Manipulation and Enhancement.
 * Provides different methods to operate on different images in a session and remembers them.
 */
public interface IME {
  /**
   * Method to load an Image.
   *
   * @param imageData InputStream of image data, this data contains width, height and max as its
   *                  initial 3 integers followed by r,g,b pixel values.
   * @param imageName Name in which the image has to be stored.
   * @throws IOException when hit a block while reading the data.
   */
  void readData(InputStream imageData, String imageName) throws IOException;

  /**
   * Method to write data of an image.
   *
   * @param imageName name in which the image has been stored.
   * @return output stream of Image data, this data contains width, height and RGB pixel values
   * @throws IOException when hit a block while writing the data.
   */
  OutputStream writeData(String imageName) throws IOException;

  /**
   * Method to brighten the image. To edit the brightness.
   *
   * @param value  the scale through which the brightness has to be increased.
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void brighten(int value, String image, String result);

  /**
   * Method to vertically flip the image.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void verticalFlip(String image, String result);

  /**
   * Method to horizontally flip the image.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void horizontalFlip(String image, String result);

  /**
   * Method to apply greyscale using the red channel of the image.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void redGreyscale(String image, String result);

  /**
   * Method to apply greyscale using the green channel of the image.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void greenGreyscale(String image, String result);

  /**
   * Method to apply greyscale using the blue channel of the image.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void blueGreyscale(String image, String result);

  /**
   * Method to apply greyscale using the value formula for its pixels.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void valueGreyscale(String image, String result);

  /**
   * Method to apply greyscale using the luma formula for its pixels.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void lumaGreyscale(String image, String result);

  /**
   * Method to apply greyscale using the intensity formula for its pixels.
   *
   * @param image  on which the operation has to be done.
   * @param result name in which the resulting edited image has to be stored.
   */
  void intensityGreyscale(String image, String result);

  /**
   * Method to split an image into its 3 respective channels greyscale images.
   *
   * @param image       on which the operation has to be done.
   * @param redResult   name in which the resulting red greyscale image has to be stored.
   * @param greenResult name in which the resulting green greyscale image has to be stored.
   * @param blueResult  name in which the resulting blue greyscale image has to be stored.
   */
  void rgbSplit(String image, String redResult, String greenResult, String blueResult);

  /**
   * Method to combine the different channels from 3 different image into one image.
   *
   * @param redImage    name of the red channels image.
   * @param greenImage  name of the green channels image.
   * @param blueImage   name of the red channels image.
   * @param resultImage name in which the resulting combined image has to be stored.
   */
  void rgbCombine(String redImage, String greenImage, String blueImage, String resultImage);
}
