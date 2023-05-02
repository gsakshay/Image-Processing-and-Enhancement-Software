package ime.control.commands;

import ime.model.MIME;

/**
 * This command creates an image by combining RGB channels of 3 different images, generally
 * greyscale ones.
 * Stores the brightened image with the resulting name provided.
 */
public class RGBCombine implements IMECommand {
  private final String red;
  private final String green;
  private final String blue;
  private final String resultName;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param resultName the name of the resulting image after the operation is done.
   * @param red        the name of the original red image using which the operation has to be done
   * @param green      the name of the original green image using which the operation has to be done
   * @param blue       the name of the original blue image using which the operation has to be done
   */
  public RGBCombine(String resultName, String red, String green, String blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.resultName = resultName;
  }

  @Override
  public void execute(MIME ime) {
    ime.rgbCombine(red, green, blue, resultName);
  }
}
