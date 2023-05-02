package ime.control;

import java.io.IOException;

/**
 * Represents all the features our Software supports. Makes use of our main model and the
 * controller implements all these functionalities that can be run with different GUI applications.
 *
 * <p>This controller assumes that the GUI is operating with one main Image. All the features are
 * applied on ONE image.
 * Although this controller can work with a model that is supporting multiple images in parallel
 * to be worked on, this controller on its own, only supports one main image.
 */
public interface Features {

  /**
   * Feature of loading an Image into the application.
   *
   * @param filepath the path through which image has to be loaded.
   */
  void load(String filepath) throws IOException;

  /**
   * Feature of saving an image from the application.
   *
   * @param filepath the path to which image has to be saved.
   */
  void save(String filepath) throws IOException;

  /**
   * Feature of brightening an application.
   *
   * @param scale the scale through which the operation has to be performed.
   */
  void brighten(int scale) throws IOException;

  /**
   * Feature of flipping an image, both vertical and horizontal orientations are supported.
   *
   * @param orientation thr orientation through which the image needs to be flipped.
   */
  void flip(int orientation) throws IOException;

  /**
   * Feature to apply greyscale to an Image. Different types of greyscale images are supported,
   * type should be specified.
   *
   * @param greyscale the type of greyscale that needs to be applied.
   */
  void greyscale(Greyscale greyscale) throws IOException;

  /**
   * Feature to blur an image.
   */
  void blur() throws IOException;

  /**
   * Feature to sharpen an image.
   */
  void sharpen() throws IOException;

  /**
   * Feature to apply Sepia color transformation.
   */
  void sepia() throws IOException;

  /**
   * Feature that applies dither operation.
   */
  void dither() throws IOException;

  /**
   * Feature that splits an image into its red, green and blue parts.
   *
   * @param redFilePath   path where red split needs to be stored.
   * @param greenFilePath path where green split needs to be stored.
   * @param blueFilePath  path where blue split needs to be stored.
   */
  void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath) throws IOException;

  /**
   * Feature that combines red, green and blue channels of 3 different img.
   *
   * @param redImageFile   path where red split is stored.
   * @param greenImageFile path where red split is stored.
   * @param blueImageFile  path where red split is stored.
   */
  void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
          throws IOException;

  /**
   * Exit the program.
   */
  void exitProgram();
}
