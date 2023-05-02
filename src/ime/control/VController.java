package ime.control;

import java.io.IOException;
import java.util.InputMismatchException;

import ime.control.commands.Blur;
import ime.control.commands.Brighten;
import ime.control.commands.Dither;
import ime.control.commands.Flip;
import ime.control.commands.GreyscaleComposite;
import ime.control.commands.IMECommand;
import ime.control.commands.Load;
import ime.control.commands.RGBCombine;
import ime.control.commands.RGBSplit;
import ime.control.commands.Save;
import ime.control.commands.Sepia;
import ime.control.commands.Sharpen;
import ime.model.MIME;
import ime.view.IView;

/**
 * Represents the controller to interact with the View and the Model that implements the Features
 * that our program supports.
 */
public class VController implements Features {
  private final MIME model;
  private final String currentImage;
  private final IView view;

  /**
   * Constructs a controller with the model to be interacted with.
   * Sets the view that the controller is interacting with.
   *
   * @param model to be interacted with.
   */
  public VController(MIME model, IView view) {
    this.model = model;
    this.view = view;
    view.addFeatures(this);
    currentImage = "UUID0123456789";
  }

  /**
   * A method to display the current image.
   *
   * @throws IOException if there are hurdles in getting the image.
   */
  private void refreshScreen() throws IOException {
    view.refreshScreen(currentImage);
  }

  /**
   * A method that abstracts the part of executing a command on our model and displaying the
   * updated image.
   *
   * @param command the IME command to be executed.
   * @throws IOException if faced hurdles in doing any of the operations.
   */
  private void executeCommand(IMECommand command) throws IOException {
    try {
      command.execute(model);
      refreshScreen();
    } catch (IllegalArgumentException | InputMismatchException e) {
      view.displayErrorMessage(e.getMessage());
    }

  }

  /**
   * Method to load an image with a specific image name.
   *
   * @param filepath  path where the image resides.
   * @param imageName name in which the image has to be stored.
   * @throws IOException if there is any blocker while trying to load and read the image.
   */
  private void load(String filepath, String imageName) throws IOException {
    executeCommand(new Load(filepath, imageName));
  }

  @Override
  public void load(String filepath) throws IOException {
    load(filepath, currentImage);
  }

  /**
   * Method to load an image with a specific image name.
   * This is different from load as it only loads internally and do not call to display the same.
   *
   * @param filepath  path where the image resides.
   * @param imageName name in which the image has to be stored.
   * @throws IOException if there is any blocker while trying to load and read the image.
   */
  private void loadInternally(String filepath, String imageName) throws IOException {
    new Load(filepath, imageName).execute(model);
  }

  /**
   * Method to save an image with a specific image name.
   *
   * @param filepath  path where the image should reside.
   * @param imageName name in which the image has to be stored.
   * @throws IOException if there is any blocker while trying to write and save the image.
   */
  private void save(String filepath, String imageName) throws IOException {
    IMECommand save = new Save(filepath, imageName);
    save.execute(model);
  }

  @Override
  public void save(String filepath) throws IOException {
    save(filepath, currentImage);
  }

  @Override
  public void brighten(int scale) throws IOException {
    executeCommand(new Brighten(scale, currentImage, currentImage));
  }

  @Override
  public void flip(int orientation) throws IOException {
    executeCommand(new Flip(currentImage, currentImage, orientation));
  }

  @Override
  public void greyscale(Greyscale scale) throws IOException {
    executeCommand(new GreyscaleComposite(scale.getGreyscale(), currentImage, currentImage));
  }

  @Override
  public void blur() throws IOException {
    executeCommand(new Blur(currentImage, currentImage));
  }

  @Override
  public void sharpen() throws IOException {
    executeCommand(new Sharpen(currentImage, currentImage));
  }

  @Override
  public void sepia() throws IOException {
    executeCommand(new Sepia(currentImage, currentImage));
  }

  @Override
  public void dither() throws IOException {
    executeCommand(new Dither(currentImage, currentImage));
  }

  @Override
  public void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath)
          throws IOException {
    String red = currentImage + "-red";
    String green = currentImage + "-green";
    String blue = currentImage + "-blue";

    // Perform the operation.
    IMECommand rgbSplit = new RGBSplit(currentImage, red, green, blue);
    rgbSplit.execute(model);


    // Save the resultant files.
    save(redFilePath, red);
    save(greenFilePath, green);
    save(blueFilePath, blue);
  }

  @Override
  public void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
          throws IOException {
    String red = "red-" + currentImage;
    String green = "green-" + currentImage;
    String blue = "blue-" + currentImage;

    loadInternally(redImageFile, red);
    loadInternally(greenImageFile, green);
    loadInternally(blueImageFile, blue);

    executeCommand(new RGBCombine(currentImage, red, green, blue));
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }
}
