package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * This command creates a greyscale image by applying the value of the BLUE channel to all its
 * channels.
 * Stores the edited image with the resulting name provided.
 */
public class GreyscaleBlue implements IMECommand {
  private final String imageName;
  private final String resultImage;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param imageName   the name of the original image using which the operation has to be done.
   * @param resultImage the name of the resulting image after the operation is done.
   */
  public GreyscaleBlue(String imageName, String resultImage) {
    this.imageName = imageName;
    this.resultImage = resultImage;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    ime.blueGreyscale(imageName, resultImage);
  }
}
