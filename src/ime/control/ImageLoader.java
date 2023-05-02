package ime.control;

import java.io.IOException;
import java.io.InputStream;


/**
 * Represents an Image loader.
 * Reads an Image file and converts it into an object representing an Image.
 */
public interface ImageLoader {
  /**
   * Loads the image.
   *
   * @param filename name of the file representing an image type
   * @return an object of type Image representing the image in the file
   * @throws IOException when a block is hit while reading the image
   */
  InputStream load(String filename) throws IOException;
}
