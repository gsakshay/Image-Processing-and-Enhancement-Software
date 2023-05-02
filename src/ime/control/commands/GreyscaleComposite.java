package ime.control.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ime.model.MIME;

/**
 * This describes a composite command. This helps to create a greyscale image and logic is
 * dependent on the second command.
 * Stores the edited image with the resulting name provided.
 */
public class GreyscaleComposite implements IMECommand {
  private final IMECommand greyscaleCommand;

  /**
   * Constructor that helps get the required values to perform the command operation.
   *
   * @param greyscaleMethod the child command for the greyscale, specifying the logic to be used
   *                        for the greyscale effect
   * @param imageName       the name of the original image using which the operation has to be done.
   * @param resultName      the name of the resulting image after the operation is done.
   */
  public GreyscaleComposite(String greyscaleMethod, String imageName, String resultName) {

    Map<String, Function<String, IMECommand>> knownCommands = new HashMap<>();

    knownCommands.put("value-component", s -> new GreyscaleValue(imageName, resultName));
    knownCommands.put("intensity-component", s -> new GreyscaleIntensity(imageName, resultName));
    knownCommands.put("luma-component", s -> new GreyscaleLuma(imageName, resultName));
    knownCommands.put("red-component", s -> new GreyscaleRed(imageName, resultName));
    knownCommands.put("green-component", s -> new GreyscaleGreen(imageName, resultName));
    knownCommands.put("blue-component", s -> new GreyscaleBlue(imageName, resultName));

    Function<String, IMECommand> cmd = knownCommands.getOrDefault(greyscaleMethod, null);

    IMECommand c;
    if (cmd == null) {
      throw new IllegalArgumentException("Unknown greyscale method, please provide a valid method");
    } else {
      c = cmd.apply(greyscaleMethod);
      greyscaleCommand = c;
    }

  }

  @Override
  public void execute(MIME ime) throws IOException {
    this.greyscaleCommand.execute(ime);
  }
}
