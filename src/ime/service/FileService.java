package ime.service;

import java.io.FileNotFoundException;

/**
 * Provides file-service such as reading a file in the overall program.
 */
public interface FileService {
  /**
   * Method to read a file, any file and return its contents.
   *
   * @param filepath path of the file to be read.
   * @return contents of the file as String.
   * @throws FileNotFoundException if hit a block trying to find the file.
   */
  String readFile(String filepath) throws FileNotFoundException;
}
