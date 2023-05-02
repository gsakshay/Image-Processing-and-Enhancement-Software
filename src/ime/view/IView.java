package ime.view;

import java.io.IOException;

import ime.control.Features;

/**
 * This interface represents the View component in the Image Manipulation and Enhancement.
 * It defines the methods that should be implemented by the View to provide the user interface.
 */
public interface IView {
  /**
   * Setting the name of current working image and displaying it.
   *
   * @param imageName - name of the image currently being worked on.
   */
  void refreshScreen(String imageName) throws IOException;

  /**
   * Add additional features to the View's user interface.
   *
   * @param features The additional features to be added.
   */
  void addFeatures(Features features);

  /**
   * Communicates an error message on the View's user interface.
   *
   * @param errorMessage The error message to be displayed.
   */
  void displayErrorMessage(String errorMessage);
}

