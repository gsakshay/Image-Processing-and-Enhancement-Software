package ime.control;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Implements ImageSaver that saves a .jpg, .png, .bmp image.
 */
public class ImageIOSaver implements ImageSaver {
  @Override
  public void save(OutputStream imageData, String filename) throws IOException {
    Scanner sc = new Scanner(imageData.toString());

    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    WritableRaster raster = image.getRaster();

    int[] pixel = new int[3]; // RGB components
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        pixel[0] = sc.nextInt();   // red component
        pixel[1] = sc.nextInt();   // green component
        pixel[2] = sc.nextInt();   // blue component
        // setPixel takes width first.
        raster.setPixel(y, x, pixel);
      }
    }
    String[] parts = filename.split("\\.");
    String fileType = parts[parts.length - 1];
    try {
      File output = new File(filename);
      ImageIO.write(image, fileType, output);
    } catch (IOException e) {
      throw new IOException("Please provide valid path");
    }

  }
}
