package ime.control;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * Implementation of ImageLoader that loads .png, .jpg and .bmp files.
 */
public class ImageIOLoader implements ImageLoader {
  @Override
  public InputStream load(String filename) throws IOException {
    try {
      // Read image data from file
      File file = new File(filename);
      ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);

      // Create a Buffered Image from this inputStream now
      BufferedImage image = ImageIO.read(imageInputStream);

      StringBuilder sbBeforeMax = new StringBuilder();
      StringBuilder sbAfterMax = new StringBuilder();


      int height = image.getHeight();
      int width = image.getWidth();
      int max = 0;

      sbBeforeMax.append(width).append(" ").append(height).append(System.lineSeparator());

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          // getRGB takes width as the first argument and height as second
          Color rgb = new Color(image.getRGB(j, i));

          int red = rgb.getRed();
          int green = rgb.getGreen();
          int blue = rgb.getBlue();

          max = Math.max(max, Math.max(red, Math.max(green, blue)));

          sbAfterMax.append(red).append(System.lineSeparator());
          sbAfterMax.append(green).append(System.lineSeparator());
          sbAfterMax.append(blue).append(System.lineSeparator());
        }
      }
      String sb = String.valueOf(sbBeforeMax) + max + System.lineSeparator()
              + sbAfterMax;
      return new ByteArrayInputStream(sb.getBytes());
    } catch (IOException ioe) {
      throw new IOException("Please provide a valid file");
    } catch (NoSuchElementException noe) {
      throw new InvalidObjectException("Please provide a valid file");
    }
  }
}
