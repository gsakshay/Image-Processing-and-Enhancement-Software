package ime.control;

import java.io.IOException;

import ime.model.MIME;

/**
 * Controller interface for the IME.
 * The functions here have been designed to give control to the controller, and the primary
 * operation for the controller to function - To take commands and perform action.
 */
public interface IController {
  /**
   * Start the program, i.e. give control to the controller.
   */
  void run(MIME ime) throws IOException;
}
