package ime.model;

import java.util.Objects;

/**
 * Implements the Pixel, represents a RGB pixel for an RGB Image.
 */
class RGBPixel implements Pixel {
  private final int max;
  private int red;
  private int green;
  private int blue;

  /**
   * Constructs an RGB pixel with channel and max values.
   *
   * @param red   value of the red channel
   * @param green value of green channel
   * @param blue  value of blue channel
   * @param max   max value a pixel can hold
   */
  public RGBPixel(int red, int green, int blue, int max) {
    // Important to set the max first.
    this.max = max;
    // Later set the colors using setters.
    setRed(red);
    setGreen(green);
    setBlue(blue);
  }

  @Override
  public int getMax() {
    return this.max;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  /**
   * Sets the red channel.
   *
   * @param newVal the red value
   */

  private void setRed(int newVal) {
    if (newVal < 0) {
      newVal = 0;
    } else if (newVal > this.max) {
      newVal = this.max;
    }
    this.red = newVal;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  /**
   * Sets the green channel.
   *
   * @param newVal the green value
   */
  private void setGreen(int newVal) {
    if (newVal < 0) {
      newVal = 0;
    } else if (newVal > this.max) {
      newVal = this.max;
    }
    this.green = newVal;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  /**
   * Sets the blue channel.
   *
   * @param newVal the blue value
   */
  private void setBlue(int newVal) {
    if (newVal < 0) {
      newVal = 0;
    } else if (newVal > this.max) {
      newVal = this.max;
    }
    this.blue = newVal;
  }

  @Override
  public int getValue() {
    return Math.max(this.red, Math.max(this.green, this.blue));
  }

  @Override
  public int getIntensity() {
    return (this.red + this.green + this.blue) / 3;
  }

  @Override
  public int getLuma() {
    return (int) Math.round(0.2126 * red + 0.7152 * green + 0.0722 * blue);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof RGBPixel)) {
      return false;
    }

    Pixel that = (RGBPixel) obj;

    return ((Math.abs(this.getRed() - that.getRed()) < 0.01)
            && (Math.abs(this.getGreen() - that.getGreen()) < 0.01)
            && Math.abs(this.getBlue() - that.getBlue()) < 0.01)
            && (Math.abs(this.getMax() - that.getMax()) < 0.01);

  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.max);
  }

  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue;
  }
}
