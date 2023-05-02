package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * This command creates sharpened effect to the image through filter.
 * Stores the edited image with the resulting name provided.
 */
public class Sharpen implements IMECommand {
  private final String source;
  private final String result;

  /**
   * Constructor that helps to get the required values for the operation.
   *
   * @param source the name of the original image using which the operation has to be done.
   * @param result the name of the resulting image after the operation is done.
   */
  public Sharpen(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    ime.sharpen(source, result);
  }
}
