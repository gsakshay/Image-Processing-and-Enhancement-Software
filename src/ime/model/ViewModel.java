package ime.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of IViewModel. This takes a model object and implements its read only functions
 * as composition. Acts as an Adapter for our main model, in making it read-only.
 * Here, since the view model represent only one image, the processImage must be called before
 * calling any other methods.
 */
public class ViewModel implements IViewModel {
  private final MIME adapter;
  private List<Integer> redData;
  private List<Integer> greenData;
  private List<Integer> blueData;
  private List<Integer> intensityData;
  private Image imageToPresent;
  private boolean imageProcessed;

  /**
   * Constructs a ViewModel, that takes the main model which will be composed in this
   * implementation.
   *
   * @param mainModel - the main model which will be composed.
   */
  public ViewModel(MIME mainModel) {
    this.adapter = mainModel;
    this.imageProcessed = false;
  }

  @Override
  public void processImage(String imageName) throws IOException {
    OutputStream imageData;
    imageData = this.adapter.writeData(imageName);
    // Now use this data to get a buffered image
    String data = imageData.toString();
    Scanner sc = new Scanner(data);

    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    WritableRaster raster = image.getRaster();

    int[] pixel = new int[3]; // RGB components

    // Update the channels for color data
    redData = new ArrayList<>(Collections.nCopies(256, 0));
    greenData = new ArrayList<>(Collections.nCopies(256, 0));
    blueData = new ArrayList<>(Collections.nCopies(256, 0));
    intensityData = new ArrayList<>(Collections.nCopies(256, 0));

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();

        pixel[0] = red;   // red component
        pixel[1] = green;   // green component
        pixel[2] = blue;   // blue component

        // setPixel takes width first.
        raster.setPixel(y, x, pixel);

        int redIncrement = redData.get(red) + 1;
        int greenIncrement = greenData.get(green) + 1;
        int blueIncrement = blueData.get(blue) + 1;

        int intensity = (red + green + blue) / 3;
        int intensityIncrement = intensityData.get(intensity) + 1;

        redData.set(red, redIncrement);
        greenData.set(green, greenIncrement);
        blueData.set(blue, blueIncrement);

        intensityData.set(intensity, intensityIncrement);
      }
    }
    imageToPresent = image;
    imageProcessed = true;
  }

  /**
   * Only lets the user successfully use other methods if  processImage has been called.
   * Helps in better optimizing code.
   */
  private void checkImageProcessed() {
    if (!imageProcessed) {
      throw new IllegalStateException("Image must be processed before accessing other methods. "
              + "Please call the processImage method to perform this action");
    }
  }

  @Override
  public Image presentImage() {
    checkImageProcessed();
    return this.imageToPresent;
  }

  @Override
  public List<Integer> getRedData() {
    checkImageProcessed();
    return this.redData;
  }

  @Override
  public List<Integer> getGreenData() {
    checkImageProcessed();
    return this.greenData;
  }

  @Override
  public List<Integer> getBlueData() {
    checkImageProcessed();
    return this.blueData;
  }

  @Override
  public List<Integer> getIntensityData() {
    checkImageProcessed();
    return this.intensityData;
  }

}
