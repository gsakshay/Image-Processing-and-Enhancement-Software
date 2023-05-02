package ime.control.commands;

import java.io.IOException;
import java.io.OutputStream;

import ime.control.ImageIOSaver;
import ime.control.ImageSaver;
import ime.control.PPMSaver;
import ime.model.MIME;

/**
 * This command saves an image at the location specified.
 */
public class Save implements IMECommand {
  private final String imageName;
  private final String pathName;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param pathName  location where the image has to be stored
   * @param imageName name of the image using which the operation has to be done
   */
  public Save(String pathName, String imageName) {
    this.imageName = imageName;
    this.pathName = pathName;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    OutputStream imageData = ime.writeData(imageName);
    if (pathName.endsWith(".ppm")) {
      ImageSaver ppmSaver = new PPMSaver();
      ppmSaver.save(imageData, pathName);
    } else if (pathName.endsWith(".jpg") || pathName.endsWith(".png")
            || pathName.endsWith(".bmp")) {
      ImageSaver imageSaver = new ImageIOSaver();
      imageSaver.save(imageData, pathName);
    } else {
      throw new IllegalArgumentException("We only support ppm, png, jpg and bmp formats as of now");
    }
  }
}
