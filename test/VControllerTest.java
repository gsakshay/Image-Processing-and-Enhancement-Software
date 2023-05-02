import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ime.control.Features;
import ime.control.Greyscale;
import ime.control.VController;
import ime.model.MIME;
import ime.view.IView;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the implementation of controller of GRIME.
 */
public class VControllerTest {
  private StringBuilder modelLogger;
  private StringBuilder viewLogger;
  private Features viewController;


  @Before
  public void setUp() {
    StringBuilder viewLogger;
    MIME mockModel;
    IView mockView;
    modelLogger = new StringBuilder();
    viewLogger = new StringBuilder();
    mockModel = new VControllerTest.MockModel(modelLogger);
    mockView = new VControllerTest.MockView(viewLogger);
    viewController = new VController(mockModel, mockView);
  }

  @Test
  public void testLoad() throws IOException {
    viewController.load("res/test.ppm");
    assertEquals("Input: UUID0123456789", modelLogger.toString());
  }

  @Test(expected = IOException.class)
  public void testLoadFailure() throws IOException {
    viewController.load("res/invalid.ppm");
  }

  @Test
  public void testSave() throws IOException {
    viewController.save("res/testSave.ppm");
    assertEquals("Input: UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testBrighten() throws IOException {
    viewController.brighten(50);
    assertEquals("Input: 50 UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testFlip() throws IOException {
    viewController.brighten(1);
    assertEquals("Input: 1 UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testGreyscale() throws IOException {
    viewController.greyscale(Greyscale.Red);
    assertEquals("Input: UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testBlur() throws IOException {
    viewController.blur();
    assertEquals("Input: UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testSharpen() throws IOException {
    viewController.sharpen();
    assertEquals("Input: UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testSepia() throws IOException {
    viewController.sepia();
    assertEquals("Input: UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testDither() throws IOException {
    viewController.dither();
    assertEquals("Input: UUID0123456789 UUID0123456789", modelLogger.toString());
  }

  @Test
  public void testRgbSplit() throws IOException {
    viewController.rgbSplit("res/true/test-rgb-split-red.ppm",
            "res/true/test-rgb-split-green.ppm",
            "res/true/test-rgb-split-blue.ppm");
    assertEquals("Input: UUID0123456789 UUID0123456789-red UUID0123456789-green "
            + "UUID0123456789-blueInput: UUID0123456789-redInput: UUID0123456789-greenInput: "
            + "UUID0123456789-blue", modelLogger.toString());
  }

  @Test
  public void testRgbCombine() throws IOException {
    viewController.rgbCombine("res/true/test-split-red.ppm",
            "res/true/test-split-green.ppm",
            "res/true/test-split-blue.ppm");
    assertEquals("Input: red-UUID0123456789Input: green-UUID0123456789Input: "
            + "blue-UUID0123456789Input: UUID0123456789 red-UUID0123456789 "
            + "green-UUID0123456789 blue-UUID0123456789", modelLogger.toString());
  }

  @Test(expected = IOException.class)
  public void testRgbCombineFailure() throws IOException {
    viewController.rgbCombine("res/invalid.ppm",
            "res/invalid2.ppm",
            "res/invalid3.ppm");
  }

  /**
   * Class for mocking the view for the implementation of the controller.
   */
  private static class MockView implements IView {
    private final StringBuilder sb;

    public MockView(StringBuilder sb) {
      this.sb = sb;
    }

    @Override
    public void refreshScreen(String imageName) throws IOException {
      sb.append("Input: ").append(imageName);
    }

    @Override
    public void addFeatures(Features features) {
      sb.append("Input: ").append(features.getClass());
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
      sb.append("Input: ").append(errorMessage);
    }
  }

  /**
   * Class for mocking the test for the implementation of the controller.
   */
  private static class MockModel implements MIME {

    private final StringBuilder sb;

    public MockModel(StringBuilder sb) {
      this.sb = sb;
    }

    @Override
    public void readData(InputStream imageData, String imageName) {
      sb.append("Input: ").append(imageName);
    }

    @Override
    public OutputStream writeData(String imageName) throws IOException {
      sb.append("Input: ").append(imageName);
      // Return does not matter as this is a controller test.
      String newImage = "2 2 255 10 10 10 10 10 10 10 10 10 10 10 10";
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] strBytes = newImage.getBytes(); // Convert string to byte array
      outputStream.write(strBytes);
      return outputStream;
    }

    @Override
    public void brighten(int value, String image, String result) {
      sb.append("Input: ").append(value).append(" ").append(image).append(" ").append(result);
    }


    @Override
    public void verticalFlip(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void horizontalFlip(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void redGreyscale(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void greenGreyscale(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void blueGreyscale(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void valueGreyscale(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void lumaGreyscale(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void intensityGreyscale(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void rgbSplit(String image, String redResult, String greenResult, String blueResult) {
      sb.append("Input: ").append(image).append(" ").append(redResult).append(" ")
              .append(greenResult).append(" ").append(blueResult);

    }

    @Override
    public void rgbCombine(String redImage, String greenImage,
                           String blueImage, String resultImage) {
      sb.append("Input: ").append(resultImage).append(" ").append(redImage).append(" ")
              .append(greenImage).append(" ").append(blueImage);
    }

    @Override
    public void blur(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void sharpen(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void sepia(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }

    @Override
    public void dither(String image, String result) {
      sb.append("Input: ").append(image).append(" ").append(result);
    }
  }
}
