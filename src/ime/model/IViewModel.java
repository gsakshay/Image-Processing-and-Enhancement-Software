package ime.model;

import java.awt.Image;
import java.io.IOException;
import java.util.List;

/**
 * Represents READ ONLY model. It offers methods that only read data from our model.
 * It cannot perform any operation of the model that updates the data.
 * Helps view directly get the data it wants to present in the view, without disturbing the
 * controller.
 * It is designed in a way that it represents a single image at a time, helps in getting
 * synchronization with View which represents a single image.
 */
public interface IViewModel {
  /**
   * Since our View model represents an individual image and our Main model represents set of
   * images, we have to process an individual image before we can query on it.
   *
   * @param imageName name of the image to be processed.
   * @throws IOException if hit any block while trying to perform IO operations.
   */
  void processImage(String imageName) throws IOException;

  /**
   * Method that returns an Image for the view to display.
   *
   * @return an Image object for presentation.
   */
  Image presentImage();

  /**
   * Returns distribution of red colour, provides the count of each of its values.
   *
   * @return the distribution of red color
   */
  List<Integer> getRedData();

  /**
   * Returns distribution of green colour, provides the count of each of its values.
   *
   * @return the distribution of green color
   */
  List<Integer> getGreenData();

  /**
   * Returns distribution of blue colour, provides the count of each of its values.
   *
   * @return the distribution of blue color
   */
  List<Integer> getBlueData();

  /**
   * Returns distribution of intensity value, provides the count of each of its values.
   *
   * @return the distribution of intensity value
   */
  List<Integer> getIntensityData();

}
