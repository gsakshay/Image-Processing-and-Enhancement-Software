package ime.control;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents an Image Saver.
 * Saves an image file at the location provided, representing an Image.
 */
public interface ImageSaver {
  /**
   * Method to save the image.
   *
   * @param imageData the data of the image
   * @param filename  the path and filename where the image should be stored in the system
   * @throws IOException when a block is hit while writing the file
   */
  void save(OutputStream imageData, String filename) throws IOException;
}
