package ime.control.commands;

import java.io.IOException;

import ime.model.MIME;

/**
 * Interface that defines the general command for IME.
 */
public interface IMECommand {
  /**
   * Method to execute the command.
   *
   * @param ime instance on and in which the operations will be performed.
   * @throws IOException when hit unknown or illegal block
   */
  void execute(MIME ime) throws IOException;
}
