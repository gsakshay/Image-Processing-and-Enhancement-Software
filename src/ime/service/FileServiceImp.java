package ime.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that provides file services for the overall program.
 */

public final class FileServiceImp implements FileService {

  @Override
  public String readFile(String filepath) throws FileNotFoundException {
    File file = new File(filepath);
    FileInputStream fis = new FileInputStream(file);
    Scanner sc = new Scanner(fis);
    StringBuilder builder = new StringBuilder();

    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      // ignore empty lines
      if (s.isEmpty()) {
        continue;
      }
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    return builder.toString();
  }
}
