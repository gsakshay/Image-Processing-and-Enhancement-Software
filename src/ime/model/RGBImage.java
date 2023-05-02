package ime.model;

import java.util.Objects;

/**
 * Implementation of Image, represents an RGB Image and its functions.
 */
class RGBImage implements Image {
  private final RGBPixel[][] pixels;
  private final int width;
  private final int height;
  private final int max;

  /**
   * Constructor to create an instance of RGB image.
   *
   * @param height height of the image, i.e., rows of the image matrix
   * @param width  width of the image, i.e., columns of the image matrix
   * @param max    maximum value of a channel of a pixel in the image
   */
  public RGBImage(int height, int width, int max) {
    this.height = height;
    this.width = width;
    this.max = max;
    pixels = new RGBPixel[height][width];
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getMax() {
    return this.max;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    try {
      return this.pixels[x][y];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Please specify the Pixel indices in the bounds.");
    }
  }

  @Override
  public void setPixel(int x, int y, Pixel p) {
    this.pixels[x][y] = (RGBPixel) p;
  }

  @Override
  public Image applyFilter(Filter filter) {
    return filter.apply(this);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof RGBImage)) {
      return false;
    }

    Image that = (RGBImage) o;

    boolean isEqual =
            this.height == that.getHeight() && this.max == that.getMax()
                    && this.width == that.getWidth();


    if (isEqual) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          isEqual = isEqual && this.getPixel(i, j).equals(that.getPixel(i, j));
        }
      }
    }

    return isEqual;
  }

  @Override
  public int hashCode() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sb.append(this.pixels[i][j].getRed()).append(this.pixels[i][j].getGreen())
                .append(this.pixels[i][j].getBlue());
      }
    }
    return Objects.hash(this.height, this.width,
            this.max, sb.toString());
  }
}
