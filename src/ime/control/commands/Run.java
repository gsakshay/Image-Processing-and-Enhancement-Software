package ime.control.commands;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;

import ime.control.Controller;
import ime.control.IController;
import ime.model.MIME;
import ime.service.FileService;
import ime.service.FileServiceImp;

/**
 * This command runs the commands present in a file format.
 */
public class Run implements IMECommand {
  private final String filename;

  /**
   * Constructs a run, with a filename.
   *
   * @param filename name of the file in which the commands are present.
   */
  public Run(String filename) {
    this.filename = filename;
  }

  @Override
  public void execute(MIME ime) throws IOException {
    Objects.requireNonNull(ime);

    FileService fileService = new FileServiceImp();
    String fileContents = fileService.readFile(filename);
    Reader in = new StringReader(fileContents);

    IController commandController = new Controller(in, System.out);
    commandController.run(ime);
  }
}
