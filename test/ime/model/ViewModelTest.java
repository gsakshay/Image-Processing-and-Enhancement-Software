package ime.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test for view model.
 */
public class ViewModelTest {
  private IViewModel viewModel;
  private StringBuilder sb;
  private String newImage = "2 2 255 10 10 10 10 10 10 10 10 10 10 10 10";

  @Before
  public void setUp() {
    MIME mockModel;

    sb = new StringBuilder();
    mockModel = new MockModel(sb);
    viewModel = new ViewModel(mockModel);
  }

  @Test(expected = IllegalStateException.class)
  public void testPresentImageFailure() throws IllegalStateException {
    viewModel.presentImage();
  }


  @Test(expected = IllegalStateException.class)
  public void testGetRedDataFailure() throws IllegalStateException {
    viewModel.getRedData();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetGreenDataFailure() throws IllegalStateException {
    viewModel.getGreenData();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetBlueDataFailure() throws IllegalStateException {
    viewModel.getBlueData();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetIntensityDataFailure() throws IllegalStateException {
    viewModel.getIntensityData();
  }

  // Proper usage

  @Test
  public void testProcessImage() throws IllegalStateException, IOException {
    viewModel.processImage("temp");
    assertEquals(sb.toString(), "Input: temp");
  }

  @Test
  public void testPresentImage() throws IllegalStateException, IOException {
    viewModel.processImage("temp");
    Image imageData = viewModel.presentImage();

    BufferedImage bufferedImage = new BufferedImage(imageData.getWidth(null),
            imageData.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bufferedImage.getGraphics();
    g.drawImage(imageData, 0, 0, null);
    g.dispose();

    // Get image dimensions
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    // Loop through each pixel and read its RGB value
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = bufferedImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Test the pixel values
        assertEquals(red, 10);
        assertEquals(green, 10);
        assertEquals(blue, 10);
      }

    }
  }

  @Test
  public void testRedData() throws IOException {
    viewModel.processImage("temp");
    ArrayList<Integer> redData = new ArrayList<>(viewModel.getRedData());
    assertEquals(redData.get(10), Integer.valueOf(4));
  }

  @Test
  public void testGreenData() throws IOException {
    viewModel.processImage("temp");
    ArrayList<Integer> greenData = new ArrayList<>(viewModel.getGreenData());
    assertEquals(greenData.get(10), Integer.valueOf(4));
  }

  @Test
  public void testBlueData() throws IOException {
    viewModel.processImage("temp");
    ArrayList<Integer> blueData = new ArrayList<>(viewModel.getBlueData());
    assertEquals(blueData.get(10), Integer.valueOf(4));
  }

  @Test
  public void testIntensityData() throws IOException {
    viewModel.processImage("temp");
    ArrayList<Integer> intensityData = new ArrayList<>(viewModel.getIntensityData());
    assertEquals(intensityData.get(10), Integer.valueOf(4));
  }

  /**
   * Class for mocking the model.
   */
  private class MockModel implements MIME {

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
    public void rgbCombine(String redImage, String greenImage, String blueImage,
                           String resultImage) {
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