import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import ime.control.Controller;
import ime.control.IController;
import ime.model.MIME;

import static org.junit.Assert.assertEquals;


/**
 * Class to test the implementation of controller of MIME.
 */
public class ControllerTest {

  private IController controller;
  private Reader sc;
  private Writer sb;

  @Test
  public void testError() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "laod ";
    String testArguments = "";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(sb.toString(), "Please provide a valid command.");
  }

  @Test
  public void testErrorGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale value-blablabla";
    String testArguments = " sgdf ;s hdf ;oh soiefj ";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(sb.toString(), "Unknown greyscale method, please provide a valid"
            + " methodPlease provide a valid command.Please provide a valid command.Please "
            + "provide a valid command.");
  }

  @Test
  public void testLoad() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "load";
    String testArguments = " res/test.ppm test";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + " test");
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "horizontal-flip";
    String testArguments = " imageName test";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testSave() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "save";
    String testArguments = " test.ppm test";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + " test");
  }

  @Test
  public void rgbSplit() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "rgb-split";
    String testArguments = " result red green blue";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testBrighten() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "brighten";
    String testArguments = " 50 test test-brighter";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testRedGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale red-component";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testGreenGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale green-component";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testBlueGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale blue-component";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testValueGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale value-component";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testLumaGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale luma-component";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testIntensityGreyscale() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "greyscale intensity-component";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testVerticalFlip() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "vertical-flip";
    String testArguments = " imageName dest.ppm";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testRGBCombine() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "rgb-combine";
    String testArguments = " test-combined test-red test-green test-blue";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testRGBSplit() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "rgb-split";
    String testArguments = " test test-red test-green test-blue";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testBlur() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "blur";
    String testArguments = " test test-blur";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testSharpen() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "sharpen";
    String testArguments = " test test-sharpen";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testS() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "sepia";
    String testArguments = " test test-sepia";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  @Test
  public void testDither() throws IOException {
    StringBuilder logger = new StringBuilder();
    MIME mockModel = new MockModel(logger);

    String testCommand = "dither";
    String testArguments = " test test-dither";

    sc = new StringReader(testCommand + testArguments);
    sb = new StringWriter();

    controller = new Controller(sc, sb);
    controller.run(mockModel);

    assertEquals(logger.toString(), "Input:" + testArguments);
  }

  /**
   * Class for mocking the test for the implementation of the controller of MIME.
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
