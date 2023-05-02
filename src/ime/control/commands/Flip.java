package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * This command Flips the image by the provided orientation.
 * Stores the edited image with the resulting name provided.
 */
public class Flip implements IMECommand {
  private final String imageName;
  private final String resultImage;

  private final int orientation;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param imageName   the name of the original image using which the operation has to be done.
   * @param resultImage the name of the resulting image after the operation is done.
   * @param orientation the horizontal(0) or vertical(1) orientation.
   */
  public Flip(String imageName, String resultImage, int orientation) {
    this.imageName = imageName;
    this.resultImage = resultImage;
    this.orientation = orientation;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    if (this.orientation == 0) {
      ime.horizontalFlip(imageName, resultImage);
    } else {
      ime.verticalFlip(imageName, resultImage);
    }
  }
}