package ime.control.commands;

import java.io.IOException;
import java.io.InputStream;

import ime.control.ImageIOLoader;
import ime.control.ImageLoader;
import ime.control.PPMLoader;
import ime.model.MIME;

/**
 * This command loads an image.
 * Stores the image with the resulting name provided.
 */
public class Load implements IMECommand {
  private final String fileName;
  private final String imageName;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param imageName the new name of the image that is to be given to the loaded image.
   * @param fileName  path and file name of the image to be loaded.
   */
  public Load(String fileName, String imageName) {
    this.fileName = fileName;
    this.imageName = imageName;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    InputStream imageData;
    if (fileName.endsWith(".ppm")) {
      ImageLoader ppmLoader = new PPMLoader();
      imageData = ppmLoader.load(fileName);
    } else if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
            || fileName.endsWith(".bmp")) {
      ImageLoader ioLoader = new ImageIOLoader();
      imageData = ioLoader.load(fileName);
    } else {
      throw new IllegalArgumentException("We only support ppm, png, jpg and bmp formats as of now");
    }
    ime.readData(imageData, imageName);
  }
}


