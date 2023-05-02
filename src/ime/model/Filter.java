package ime.model;

/**
 * A filter interface that helps edit and apply a filter on the image.
 * A new image can be created when the filter is applied.
 */
public interface Filter {
  /**
   * Method to apply the filter.
   *
   * @param inputImage the image on which the filter has to be applied.
   * @return a new Image with filter applied.
   */
  Image apply(Image inputImage);
}
