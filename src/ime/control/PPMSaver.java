package ime.control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;


/**
 * Implements ImageSaver that saves a .ppm image.
 * It gets constructed with P3 token, representing ASCII ppm file.
 */
public class PPMSaver implements ImageSaver {
  private final String token;

  /**
   * Creates a PPMSaver assigning its token.
   */
  public PPMSaver() {
    this.token = "P3";
  }

  @Override
  public void save(OutputStream imageData, String filename) throws IOException {
    try {
      String data = imageData.toString();
      Scanner sc = new Scanner(data);

      StringBuilder sb = new StringBuilder();

      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      sb.append(this.token).append(System.lineSeparator());
      sb.append("# This image is an output of the Software IME ~ Akshay Gunjur Surya Prakash & "
              + "Yug Deepak Rajani").append(System.lineSeparator());

      sb.append(width).append(" ").append(height).append(System.lineSeparator());
      sb.append(maxValue).append(System.lineSeparator());

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          sb.append(sc.nextInt()).append(System.lineSeparator());
          sb.append(sc.nextInt()).append(System.lineSeparator());
          sb.append(sc.nextInt()).append(System.lineSeparator());
        }
      }

      FileWriter myWriter = new FileWriter(filename);
      myWriter.write(sb.toString());
      myWriter.close();
    } catch (IOException e) {
      throw new IOException("Please provide valid path");
    }

  }
}
