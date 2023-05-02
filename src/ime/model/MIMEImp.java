package ime.model;

import java.util.function.Function;

/**
 * Implementation of MIME.
 */
public class MIMEImp extends IMEImp implements MIME {

  /**
   * Constructs a MIME Implementation.
   */
  public MIMEImp() {
    super();
  }

  /**
   * Method to return a matrix filter given the matrix.
   *
   * @param matrix the matrix that has to be applied on the image while filtering.
   * @return a new instance of Filter that can be applied on an Image.
   */
  private Filter getMatrixFilter(double[][] matrix) {
    return new MatrixFilter(matrix);
  }

  /**
   * Method to return a color transformation filter given the kernel.
   *
   * @param matrix the matrix that has to be used on the image while color transformation.
   * @return a new instance of ColorTransformation that can be applied on an Image.
   */
  private Filter getColorTransformer(double[][] matrix) {
    return new ColorTransformer(matrix);
  }

  /**
   * Method that applies the greyscale on an image given the greyscale function (the one that
   * determines the type of greyscale needed).
   *
   * @param image             the image on which the greyscale operation has to be done.
   * @param result            the name in which resulting image has to be stored.
   * @param greyscaleFunction the function through which value for the pixels to be made grey
   *                          gets decided.
   */
  private void applyGreyscale(String image, String result, Function<RGBPixel,
          RGBPixel> greyscaleFunction) {
    Image baseImage = getImage(image);
    Filter greyscaleFilter =
            new PixelFilter(base -> i -> j -> greyscaleFunction.apply((RGBPixel)
                    base.getPixel(i, j)));
    images.put(result, baseImage.applyFilter(greyscaleFilter));
  }

  @Override
  public void redGreyscale(String image, String result) {
    applyGreyscale(image, result, pixel -> new RGBPixel(pixel.getRed(), pixel.getRed(),
            pixel.getRed(), pixel.getMax()));
  }

  @Override
  public void greenGreyscale(String image, String result) {
    applyGreyscale(image, result, pixel -> new RGBPixel(pixel.getGreen(), pixel.getGreen(),
            pixel.getGreen(), pixel.getMax()));
  }

  @Override
  public void blueGreyscale(String image, String result) {
    applyGreyscale(image, result, pixel -> new RGBPixel(pixel.getBlue(), pixel.getBlue(),
            pixel.getBlue(), pixel.getMax()));
  }

  @Override
  public void valueGreyscale(String image, String result) {
    applyGreyscale(image, result, pixel -> new RGBPixel(pixel.getValue(), pixel.getValue(),
            pixel.getValue(), pixel.getMax()));
  }

  @Override
  public void lumaGreyscale(String image, String result) {
    applyGreyscale(image, result, pixel -> new RGBPixel(pixel.getLuma(), pixel.getLuma(),
            pixel.getLuma(), pixel.getMax()));
  }

  @Override
  public void intensityGreyscale(String image, String result) {
    applyGreyscale(image, result, pixel -> new RGBPixel(pixel.getIntensity(), pixel.getIntensity(),
            pixel.getIntensity(), pixel.getIntensity()));
  }

  /**
   * A method that applies the given filter to the given image and stores the image.
   *
   * @param imageName    name of the image on which the filter has to be applied
   * @param resultName   name in which the resulting image has to be stored.
   * @param filterMatrix the kernel of the filter matrix.
   */
  private void applyFilter(String imageName, String resultName, double[][] filterMatrix) {
    Image source = getImage(imageName);
    Image result = source.applyFilter(getMatrixFilter(filterMatrix));
    images.put(resultName, result);
  }

  @Override
  public void blur(String imageName, String resultName) {
    double[][] gaussianFilterMatrix = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}};
    applyFilter(imageName, resultName, gaussianFilterMatrix);
  }

  @Override
  public void sharpen(String imageName, String resultName) {
    double[][] sharperMatrix = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}};
    applyFilter(imageName, resultName, sharperMatrix);
  }

  /**
   * A method that applies the given color transformation to the given image and stores the image.
   *
   * @param imageName  name of the image on which the filter has to be applied
   * @param resultName name in which the resulting image has to be stored.
   * @param ctMatrix   the kernel of the color transformation.
   */
  private void applyColorTransformation(String imageName, String resultName,
                                        double[][] ctMatrix) {
    Image source = getImage(imageName);
    Image result = source.applyFilter(getColorTransformer(ctMatrix));
    images.put(resultName, result);
  }

  @Override
  public void sepia(String imageName, String resultName) {
    double[][] sepiaMatrix = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
    applyColorTransformation(imageName, resultName, sepiaMatrix);
  }

  @Override
  public void dither(String imageName, String resultName) {
    // greyscale it first
    this.lumaGreyscale(imageName, resultName);
    Image result = getImage(resultName);

    int height = result.getHeight();
    int width = result.getWidth();
    int max = result.getMax();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int oldColor = result.getPixel(i, j).getRed();
        int newColor = oldColor <= 127 ? 0 : 255;
        int error = oldColor - newColor;

        result.setPixel(i, j, new RGBPixel(newColor, newColor, newColor, max));

        double right = (7.0 / 16) * error;
        double nextLeft = (3.0 / 16) * error;
        double below = (5.0 / 16) * error;
        double nextRight = (1.0 / 16) * error;

        if (j + 1 < width) {
          int resultantValue = (int) Math.round(result.getPixel(i, j + 1).getRed() + right);
          result.setPixel(i, j + 1, new RGBPixel(resultantValue, resultantValue, resultantValue,
                  max));
        }

        if (i + 1 < height && j - 1 >= 0) {
          int resultantValue = (int) Math.round(result.getPixel(i + 1, j - 1).getRed() + nextLeft);
          result.setPixel(i + 1, j - 1, new RGBPixel(resultantValue, resultantValue,
                  resultantValue, max));
        }

        if (i + 1 < height) {
          int resultantValue = (int) Math.round(result.getPixel(i + 1, j).getRed() + below);
          result.setPixel(i + 1, j, new RGBPixel(resultantValue, resultantValue, resultantValue,
                  max));
        }

        if (i + 1 < height && j + 1 < width) {
          int resultantValue = (int) Math.round(result.getPixel(i + 1, j + 1).getRed() + nextRight);
          result.setPixel(i + 1, j + 1, new RGBPixel(resultantValue, resultantValue,
                  resultantValue, max));
        }
      }
    }

    images.put(resultName, result);
  }


}
