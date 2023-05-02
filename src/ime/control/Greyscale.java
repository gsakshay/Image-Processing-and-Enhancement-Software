package ime.control;

/**
 * This enumerated type represents a topping on the burrito.
 */
public enum Greyscale {
  Red("red-component"),
  Green("green-component"),
  Blue("blue-component"),
  Value("value-component"),
  Intensity("intensity-component"),
  Luma("luma-component");

  private final String greyscale;

  /**
   * Constructs a Greyscale type.
   *
   * @param greyscale the type of the greyscale that has to be created.
   */
  Greyscale(String greyscale) {
    this.greyscale = greyscale;
  }

  /**
   * To get the type of greyscale.
   *
   * @return the type of greyscale.
   */
  public String getGreyscale() {
    return this.greyscale;
  }
}
