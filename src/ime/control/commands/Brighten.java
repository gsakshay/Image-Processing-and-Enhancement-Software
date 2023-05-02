package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * This command Brightens the image by the provided value.
 * Stores the edited image with the resulting name provided.
 */
public class Brighten implements IMECommand {

  private final int value;
  private final String imageName;
  private final String resultImage;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param value       the scale through which the brightness has to be increased or decreased.
   * @param imageName   the name of the original image using which the operation has to be done.
   * @param resultImage the name of the resulting image after the operation is done.
   */
  public Brighten(int value, String imageName, String resultImage) {
    this.value = value;
    this.imageName = imageName;
    this.resultImage = resultImage;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    ime.brighten(value, imageName, resultImage);
  }
}
