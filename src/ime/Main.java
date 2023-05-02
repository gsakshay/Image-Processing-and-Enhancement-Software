package ime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import ime.control.Controller;
import ime.control.IController;
import ime.control.VController;
import ime.model.MIME;
import ime.model.MIMEImp;
import ime.model.ViewModel;
import ime.service.FileService;
import ime.service.FileServiceImp;
import ime.view.View;

/**
 * Executable MAIN class. It gives access to the controller.
 */
public class Main {
  /**
   * Creates and starts the controller, there by starting the program.
   */
  public static void main(String[] args) {

    boolean hasFileOption = false;
    boolean hasTextOption = false;
    String filePath = null;

    for (int i = 0; i < args.length; i++) {
      if ("-file".equals(args[i])) {
        hasFileOption = true;
        if (i < args.length - 1) {
          filePath = args[i + 1];
          i++;
        }
      } else if ("-text".equals(args[i])) {
        hasTextOption = true;
      }
      // for future operations.
    }

    try {
      IController imeController;
      if (hasFileOption) {
        // run commands in the file.
        FileService fileService = new FileServiceImp();
        String commands = fileService.readFile(filePath);
        Readable in = new StringReader(commands);
        imeController = new Controller(in, System.out);
        imeController.run(new MIMEImp());
      } else if (hasTextOption) {
        imeController = new Controller(new InputStreamReader(System.in), System.out);
        imeController.run(new MIMEImp());
      } else {
        // Execute the controller that supports GUI.
        MIME model = new MIMEImp();
        VController vController = new VController(model,
                new View("Image Manipulation and Enhancement", new ViewModel(model)));
      }
    } catch (IOException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}