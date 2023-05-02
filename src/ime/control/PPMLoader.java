package ime.control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ime.service.FileService;
import ime.service.FileServiceImp;

/**
 * Implements ImageLoader and helps load the .ppm files.
 * Loads an ASCII ppm file with P3 token.
 */
public class PPMLoader implements ImageLoader {
  private final String token;

  /**
   * Creates a PPMLoader assigning its token.
   */
  public PPMLoader() {
    this.token = "P3";
  }

  @Override
  public InputStream load(String filename) throws IOException {
    try {
      // Use our file service to read the files
      FileService fileService = new FileServiceImp();
      String fileContents = fileService.readFile(filename);

      Scanner sc = new Scanner(fileContents);

      String token;
      token = sc.next();
      if (!token.equals(this.token)) {
        throw new IOException("Invalid token found in the file " + filename);
      }

      StringBuilder builder = new StringBuilder();
      // read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNext()) {
        String s = sc.next();
        if (s.charAt(0) != '#') {
          builder.append(s).append(System.lineSeparator());
        }
      }

      return new ByteArrayInputStream(builder.toString().getBytes());
    } catch (IOException ioe) {
      throw new IOException("Please provide a valid file");
    } catch (NoSuchElementException noe) {
      throw new InvalidObjectException("Please provide a valid file");
    }
  }
}
