package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * This command creates a greyscale image by applying the value of the RED channel to all its
 * channels.
 * Stores the edited image with the resulting name provided.
 */
public class GreyscaleRed implements IMECommand {
  private final String imageName;
  private final String resultName;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param imageName  the name of the original image using which the operation has to be done.
   * @param resultName the name of the resulting image after the operation is done.
   */
  public GreyscaleRed(String imageName, String resultName) {
    this.imageName = imageName;
    this.resultName = resultName;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    ime.redGreyscale(imageName, resultName);
  }
}
