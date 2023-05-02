package ime.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import ime.control.commands.Blur;
import ime.control.commands.Brighten;
import ime.control.commands.Dither;
import ime.control.commands.Flip;
import ime.control.commands.GreyscaleComposite;
import ime.control.commands.IMECommand;
import ime.control.commands.Load;
import ime.control.commands.RGBCombine;
import ime.control.commands.RGBSplit;
import ime.control.commands.Run;
import ime.control.commands.Save;
import ime.control.commands.Sepia;
import ime.control.commands.Sharpen;
import ime.model.MIME;

/**
 * This is an easily extensible controller for the application.
 * Implements IController.
 * Follows the command design pattern.
 * Reads the user commands and makes the specific command call.
 * Interacts with the user.
 */
public class Controller implements IController {
  private final Readable in;
  private final Appendable out;

  /**
   * Constructs the controller with a Readable and
   * Appendable object. It has been designed to accept a sequence of multiple
   * inputs from the Readable object.
   *
   * @param in  - Readable Object
   * @param out - Appendable Object
   */
  public Controller(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void run(MIME ime) throws IOException {
    Objects.requireNonNull(ime);
    Scanner scan = new Scanner(this.in);

    Map<String, Function<Scanner, IMECommand>> knownCommands = new HashMap<>();

    knownCommands.put("load", s -> new Load(s.next(), s.next()));
    knownCommands.put("save", s -> new Save(s.next(), s.next()));
    knownCommands.put("brighten", s -> new Brighten(s.nextInt(), s.next(), s.next()));
    knownCommands.put("vertical-flip", s -> new Flip(s.next(), s.next(), 1));
    knownCommands.put("horizontal-flip", s -> new Flip(s.next(), s.next(), 0));
    knownCommands.put("rgb-split", s -> new RGBSplit(s.next(), s.next(), s.next(), s.next()));
    knownCommands.put("rgb-combine", s -> new RGBCombine(s.next(), s.next(), s.next(), s.next()));
    knownCommands.put("greyscale", s -> new GreyscaleComposite(s.next(), s.next(), s.next()));
    knownCommands.put("run", s -> new Run(s.next()));

    knownCommands.put("blur", s -> new Blur(s.next(), s.next()));
    knownCommands.put("sharpen", s -> new Sharpen(s.next(), s.next()));
    knownCommands.put("sepia", s -> new Sepia(s.next(), s.next()));
    knownCommands.put("dither", s -> new Dither(s.next(), s.next()));


    while (scan.hasNext()) {
      try {
        IMECommand c;
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          return;
        }
        Function<Scanner, IMECommand> cmd = knownCommands.getOrDefault(in, null);
        if (cmd == null) {
          throw new IllegalArgumentException("Please provide a valid command.");
        } else {
          c = cmd.apply(scan);
          c.execute(ime);
          out.append("Operation performed: ").append(in).append("\n");
        }
      } catch (IOException | IllegalArgumentException | InputMismatchException e) {
        out.append(e.getMessage());
      }
    }

  }
}