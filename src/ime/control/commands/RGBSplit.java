package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * This command splits an image into 3 greyscale images based on the channels.
 * Stores the edited images with the resulting names provided.
 */
public class RGBSplit implements IMECommand {
  private final String imageName;
  private final String red;
  private final String green;
  private final String blue;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param imageName the name of the original image using which the operation has to be done
   * @param red       the name of the resulting red split image
   * @param green     the name of the resulting green split image
   * @param blue      the name of the resulting blue split image
   */
  public RGBSplit(String imageName, String red, String green, String blue) {
    this.imageName = imageName;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    ime.rgbSplit(imageName, red, green, blue);
  }
}
