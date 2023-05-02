package ime.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;


/**
 * Test class for More Image Manipulation and Enhancement which tests the main features.
 */
public class MIMEImpTest {
  private final TestHelper testHelper = new TestHelper();
  private MIMEImp ime;
  private String filePath;
  private Image image;

  @Before
  public void setUp() {
    try {
      filePath = "res/test.ppm";
      ime = new MIMEImp();

      ime.readData(testHelper.getImageInputStream(filePath), "test");
      image = ime.getImage("test");
    } catch (Exception e) {
      fail("Failed to instantiate the PPM image");
    }
  }

  @Test
  public void testValidPPM() {
    int lineWithMaxValue = 4;
    int lineWithP3 = 1;
    int lineWithHeightWidth = 3;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      int currentLine = 1;

      while ((line = br.readLine()) != null) {
        if (currentLine == lineWithP3) {
          assertEquals("P3", line);
        } else if (currentLine == lineWithMaxValue) {
          assertEquals(Integer.parseInt(line), ime.getImage("test").getMax());
          break;
        } else if (currentLine == lineWithHeightWidth) {
          int width = Integer.parseInt(line.split(" ")[0]);
          int height = Integer.parseInt(line.split(" ")[1]);
          assertEquals(width, ime.getImage("test").getWidth());
          assertEquals(height, ime.getImage("test").getHeight());
        }
        currentLine++;
      }

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = InputMismatchException.class)
  public void testGetImageFailure() {
    ime.getImage("no_such_image");
  }

  @Test
  public void testImageMatrix() {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      int currentLine = 1;

      while ((line = br.readLine()) != null && currentLine < 4) {
        // Do nothing
        currentLine++;
      }
      for (int i = 0; i < ime.getImage("test").getHeight(); i++) {
        for (int j = 0; j < ime.getImage("test").getWidth(); j++) {
          assertEquals(Integer.parseInt(br.readLine()),
                  ime.getImage("test").getPixel(i, j).getRed());
          assertEquals(Integer.parseInt(br.readLine()),
                  ime.getImage("test").getPixel(i, j).getGreen());
          assertEquals(Integer.parseInt(br.readLine()),
                  ime.getImage("test").getPixel(i, j).getBlue());
        }
      }
      if (br.readLine() != null) {
        fail("File pending");
      }

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = NoSuchElementException.class)
  public void testInvalidPPM() throws IOException {
    List<String> invalidPPMFilePaths = new ArrayList<>();
    for (int i = 0; i <= 7; i++) {
      invalidPPMFilePaths.add("test/res/invalid" + i + ".ppm");
    }
    for (String invalidFilePath : invalidPPMFilePaths) {
      ime.readData(testHelper.getImageInputStream(invalidFilePath), "test-invalid");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsI() {
    image.getPixel(image.getHeight(), image.getWidth() - 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsJ() {
    image.getPixel(image.getHeight() - 1, image.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsBoth() {
    image.getPixel(image.getHeight(), image.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsNegative() {
    image.getPixel(-1, -1);
  }

  @Test
  public void testGetIntensity() {
    ime.intensityGreyscale("test", "intensity");
    Image intensity = ime.getImage("intensity");
    assertEquals(image.getHeight(), intensity.getHeight());
    assertEquals(image.getWidth(), intensity.getWidth());
    assertEquals(image.getMax(), intensity.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int averageExpected =
                (image.getPixel(i, j).getRed() + image.getPixel(i, j).getBlue()
                        + image.getPixel(i, j).getGreen()) / 3;
        assertEquals(averageExpected, intensity.getPixel(i, j).getRed());
        assertEquals(averageExpected, intensity.getPixel(i, j).getBlue());
        assertEquals(averageExpected, intensity.getPixel(i, j).getGreen());
      }
    }
  }

  @Test
  public void testGetValueGreyscale() {
    ime.valueGreyscale("test", "value");
    Image value = ime.getImage("value");
    assertEquals(image.getHeight(), value.getHeight());
    assertEquals(image.getWidth(), value.getWidth());
    assertEquals(image.getMax(), value.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int valueExpected = Math.max(Math.max(image.getPixel(i, j).getRed(),
                image.getPixel(i, j).getBlue()), image.getPixel(i, j).getGreen());
        assertEquals(valueExpected, value.getPixel(i, j).getRed());
        assertEquals(valueExpected, value.getPixel(i, j).getBlue());
        assertEquals(valueExpected, value.getPixel(i, j).getGreen());
      }
    }
  }

  @Test
  public void testGetLuma() throws IOException {
    filePath = "test/res/tiny_image2x2.bmp";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-bmp");
    Image tinyImageBmp = ime.getImage("test-tiny-bmp");

    filePath = "test/res/tiny_image2x2.jpg";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-jpg");
    Image tinyImageJpg = ime.getImage("test-tiny-jpg");

    filePath = "test/res/tiny_image2x2.png";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-png");
    Image tinyImagePng = ime.getImage("test-tiny-png");

    ime.lumaGreyscale("test-tiny-bmp", "test-tiny-bmp-luma");
    ime.lumaGreyscale("test-tiny-png", "test-tiny-png-luma");
    ime.lumaGreyscale("test-tiny-jpg", "test-tiny-jpg-luma");
    ime.lumaGreyscale("test", "luma");

    Image luma = ime.getImage("luma");
    Image lumaBmp = ime.getImage("test-tiny-bmp-luma");
    Image lumaPng = ime.getImage("test-tiny-png-luma");
    Image lumaJpg = ime.getImage("test-tiny-jpg-luma");


    assertEquals(image.getHeight(), luma.getHeight());
    assertEquals(image.getWidth(), luma.getWidth());
    assertEquals(image.getMax(), luma.getMax());

    assertEquals(tinyImageJpg.getHeight(), lumaJpg.getHeight());
    assertEquals(tinyImageJpg.getWidth(), lumaJpg.getWidth());
    assertEquals(tinyImageJpg.getMax(), lumaJpg.getMax());

    assertEquals(tinyImagePng.getHeight(), lumaPng.getHeight());
    assertEquals(tinyImagePng.getWidth(), lumaPng.getWidth());
    assertEquals(tinyImagePng.getMax(), lumaPng.getMax());

    assertEquals(tinyImageBmp.getHeight(), lumaBmp.getHeight());
    assertEquals(tinyImageBmp.getWidth(), lumaBmp.getWidth());
    assertEquals(tinyImageBmp.getMax(), lumaBmp.getMax());


    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(image.getPixel(i, j).getRed() * 0.2126
                        + image.getPixel(i, j).getGreen() * 0.7152
                        + image.getPixel(i, j).getBlue() * 0.0722);
        assertEquals(lumaExpected, luma.getPixel(i, j).getRed());
        assertEquals(lumaExpected, luma.getPixel(i, j).getBlue());
        assertEquals(lumaExpected, luma.getPixel(i, j).getGreen());
      }
    }

    for (int i = 0; i < tinyImageJpg.getHeight(); i++) {
      for (int j = 0; j < tinyImageJpg.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(tinyImageJpg.getPixel(i, j).getRed() * 0.2126
                        + tinyImageJpg.getPixel(i, j).getGreen() * 0.7152
                        + tinyImageJpg.getPixel(i, j).getBlue() * 0.0722);
        assertEquals(lumaExpected, lumaJpg.getPixel(i, j).getRed());
        assertEquals(lumaExpected, lumaJpg.getPixel(i, j).getBlue());
        assertEquals(lumaExpected, lumaJpg.getPixel(i, j).getGreen());
      }
    }

    for (int i = 0; i < tinyImageBmp.getHeight(); i++) {
      for (int j = 0; j < tinyImageBmp.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(tinyImageBmp.getPixel(i, j).getRed() * 0.2126
                        + tinyImageBmp.getPixel(i, j).getGreen() * 0.7152
                        + tinyImageBmp.getPixel(i, j).getBlue() * 0.0722);
        assertEquals(lumaExpected, lumaBmp.getPixel(i, j).getRed());
        assertEquals(lumaExpected, lumaBmp.getPixel(i, j).getBlue());
        assertEquals(lumaExpected, lumaBmp.getPixel(i, j).getGreen());
      }
    }

    for (int i = 0; i < tinyImagePng.getHeight(); i++) {
      for (int j = 0; j < tinyImagePng.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(tinyImagePng.getPixel(i, j).getRed() * 0.2126
                        + tinyImagePng.getPixel(i, j).getGreen() * 0.7152
                        + tinyImagePng.getPixel(i, j).getBlue() * 0.0722);
        assertEquals(lumaExpected, lumaPng.getPixel(i, j).getRed());
        assertEquals(lumaExpected, lumaPng.getPixel(i, j).getBlue());
        assertEquals(lumaExpected, lumaPng.getPixel(i, j).getGreen());
      }
    }
  }

  @Test
  public void testSplitAndCombine() {
    List<Image> components = new ArrayList<>();
    ime.rgbSplit("test", "red", "green", "blue");

    Image red = ime.getImage("red");
    Image green = ime.getImage("green");
    Image blue = ime.getImage("blue");

    components.add(red);
    components.add(green);
    components.add(blue);

    for (Image component : components) {
      assertEquals(image.getMax(), component.getMax());
      assertEquals(image.getHeight(), component.getHeight());
      assertEquals(image.getWidth(), component.getWidth());
    }

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j).getRed(), red.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getRed(), red.getPixel(i, j).getGreen());
        assertEquals(image.getPixel(i, j).getRed(), red.getPixel(i, j).getBlue());

        assertEquals(image.getPixel(i, j).getGreen(), green.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getGreen(), green.getPixel(i, j).getGreen());
        assertEquals(image.getPixel(i, j).getGreen(), green.getPixel(i, j).getBlue());

        assertEquals(image.getPixel(i, j).getBlue(), blue.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getBlue(), blue.getPixel(i, j).getGreen());
        assertEquals(image.getPixel(i, j).getBlue(), blue.getPixel(i, j).getBlue());
      }
    }

    ime.rgbCombine("red", "green", "blue", "combined");
    Image combined = ime.getImage("combined");
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j), combined.getPixel(i, j));
      }
    }
  }

  @Test
  public void testSplitAndCombineIndividualGreyscale() {
    List<Image> components = new ArrayList<>();
    ime.redGreyscale("test", "red");
    ime.greenGreyscale("test", "green");
    ime.blueGreyscale("test", "blue");

    Image red = ime.getImage("red");
    Image green = ime.getImage("green");
    Image blue = ime.getImage("blue");

    components.add(red);
    components.add(green);
    components.add(blue);

    for (Image component : components) {
      assertEquals(image.getMax(), component.getMax());
      assertEquals(image.getHeight(), component.getHeight());
      assertEquals(image.getWidth(), component.getWidth());
    }

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j).getRed(), red.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getRed(), red.getPixel(i, j).getGreen());
        assertEquals(image.getPixel(i, j).getRed(), red.getPixel(i, j).getBlue());

        assertEquals(image.getPixel(i, j).getGreen(), green.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getGreen(), green.getPixel(i, j).getGreen());
        assertEquals(image.getPixel(i, j).getGreen(), green.getPixel(i, j).getBlue());

        assertEquals(image.getPixel(i, j).getBlue(), blue.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getBlue(), blue.getPixel(i, j).getGreen());
        assertEquals(image.getPixel(i, j).getBlue(), blue.getPixel(i, j).getBlue());
      }
    }

    ime.rgbCombine("red", "green", "blue", "combined");
    Image combined = ime.getImage("combined");
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j), combined.getPixel(i, j));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineThreeIncompatibleImages() {
    try {
      ime.readData(testHelper.getImageInputStream("test/res/tiny_image2x2.ppm"), "test2x2");
      ime.readData(testHelper.getImageInputStream("test/res/tiny_image2x3.ppm"), "test2x3");
      ime.readData(testHelper.getImageInputStream("test/res/tiny_image3x2.ppm"), "test3x2");
    } catch (Exception e) {
      fail("Failed to instantiate the PPM image");
    }
    ime.rgbCombine("test2x2", "test2x3", "test3x2", "combined");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineTwoIncompatibleImages() {
    try {
      ime.readData(testHelper.getImageInputStream("test/res/tiny_image2x2.ppm"), "test2x2");
      ime.readData(testHelper.getImageInputStream("test/res/tiny_image2x3.ppm"), "test2x3");
      ime.readData(testHelper.getImageInputStream("test/res/tiny_image3x2.ppm"), "test3x2");
    } catch (Exception e) {
      fail("Failed to instantiate the PPM image");
    }
    ime.rgbCombine("test2x2", "test2x2", "test3x2", "combined");
  }

  @Test
  public void testFlipOnce() {
    ime.verticalFlip("test", "test-vertical");
    Image test_vertical = ime.getImage("test-vertical");
    ime.horizontalFlip("test", "test-horizontal");
    Image test_horizontal = ime.getImage("test-horizontal");

    assertEquals(image.getHeight(), test_vertical.getHeight());
    assertEquals(image.getWidth(), test_vertical.getWidth());
    assertEquals(image.getMax(), test_vertical.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(image.getHeight() - i - 1, j),
                test_vertical.getPixel(i, j));
      }
    }

    assertEquals(image.getHeight(), test_horizontal.getHeight());
    assertEquals(image.getWidth(), test_horizontal.getWidth());
    assertEquals(image.getMax(), test_horizontal.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, image.getWidth() - j - 1),
                test_horizontal.getPixel(i, j));
      }
    }
  }

  @Test
  public void testFlipTwiceSame() {
    ime.verticalFlip("test", "test-vertical");
    ime.verticalFlip("test-vertical", "test-vertical-twice");
    Image test_vertical_original = ime.getImage("test-vertical-twice");

    ime.horizontalFlip("test", "test-horizontal");
    ime.horizontalFlip("test-horizontal", "test-horizontal-twice");
    Image test_horizontal_original = ime.getImage("test-horizontal-twice");

    assertEquals(image.getHeight(), test_vertical_original.getHeight());
    assertEquals(image.getWidth(), test_vertical_original.getWidth());
    assertEquals(image.getMax(), test_vertical_original.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j), test_vertical_original.getPixel(i, j));
      }
    }

    assertEquals(image.getHeight(), test_horizontal_original.getHeight());
    assertEquals(image.getWidth(), test_horizontal_original.getWidth());
    assertEquals(image.getMax(), test_horizontal_original.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j), test_horizontal_original.getPixel(i, j));
      }
    }
  }

  @Test
  public void testFlipFourTimesDifferentToProduceOriginal() {
    ime.verticalFlip("test", "test-vertical");
    ime.horizontalFlip("test-vertical", "test-vertical-horizontal");
    ime.verticalFlip("test-vertical-horizontal", "test-vertical-horizontal-vertical");
    ime.horizontalFlip("test-vertical-horizontal-vertical",
            "test-vertical-horizontal-vertical-horizontal");
    Image test_vertical_horizontal_twice = ime.getImage("test-vertical-horizontal-vertical"
            + "-horizontal");

    assertEquals(image.getHeight(), test_vertical_horizontal_twice.getHeight());
    assertEquals(image.getWidth(), test_vertical_horizontal_twice.getWidth());
    assertEquals(image.getMax(), test_vertical_horizontal_twice.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j), test_vertical_horizontal_twice.getPixel(i, j));
      }
    }
  }

  @Test
  public void testAdjustBrightness() {
    ime.brighten(1, "test", "test-bright");
    ime.brighten(-1, "test", "test-dark");
    Image test_brighten = ime.getImage("test-bright");
    Image test_darken = ime.getImage("test-dark");

    assertEquals(image.getHeight(), test_brighten.getHeight());
    assertEquals(image.getWidth(), test_brighten.getWidth());
    assertEquals(image.getMax(), test_brighten.getMax());

    assertEquals(image.getHeight(), test_darken.getHeight());
    assertEquals(image.getWidth(), test_darken.getWidth());
    assertEquals(image.getMax(), test_darken.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        if (image.getPixel(i, j).getRed() == image.getMax()) {
          assertEquals(image.getPixel(i, j).getRed(), test_brighten.getPixel(i, j).getRed());
        } else {
          assertEquals(image.getPixel(i, j).getRed() + 1, test_brighten.getPixel(i, j).getRed());
        }
        if (image.getPixel(i, j).getGreen() == image.getMax()) {
          assertEquals(image.getPixel(i, j).getGreen(), test_brighten.getPixel(i, j).getGreen());
        } else {
          assertEquals(image.getPixel(i, j).getGreen() + 1,
                  test_brighten.getPixel(i, j).getGreen());
        }
        if (image.getPixel(i, j).getBlue() == image.getMax()) {
          assertEquals(image.getPixel(i, j).getBlue(), test_brighten.getPixel(i, j).getBlue());
        } else {
          assertEquals(image.getPixel(i, j).getBlue() + 1, test_brighten.getPixel(i, j).getBlue());
        }
      }
    }

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        if (image.getPixel(i, j).getRed() == 0) {
          assertEquals(0, test_darken.getPixel(i, j).getRed());
        } else {
          assertEquals(image.getPixel(i, j).getRed() - 1, test_darken.getPixel(i, j).getRed());
        }
        if (image.getPixel(i, j).getGreen() == 0) {
          assertEquals(0, test_darken.getPixel(i, j).getGreen());
        } else {
          assertEquals(image.getPixel(i, j).getGreen() - 1, test_darken.getPixel(i, j).getGreen());
        }
        if (image.getPixel(i, j).getBlue() == 0) {
          assertEquals(0, test_darken.getPixel(i, j).getBlue());
        } else {
          assertEquals(image.getPixel(i, j).getBlue() - 1, test_darken.getPixel(i, j).getBlue());
        }
      }
    }

    // Negating the brightness effect to get back the original (or near to original image)
    ime.brighten(-1, "test-bright", "test-bright-dark");
    ime.brighten(1, "test-dark", "test-dark-bright");
    Image test_brighten_negated = ime.getImage("test-bright-dark");
    Image test_darken_negated = ime.getImage("test-dark-bright");

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        if (image.getPixel(i, j).getRed() == 0) {
          assertNotEquals(image.getPixel(i, j).getRed(),
                  test_darken_negated.getPixel(i, j).getRed());
        } else if (image.getPixel(i, j).getRed() == image.getMax()) {
          assertNotEquals(image.getPixel(i, j).getRed(),
                  test_brighten_negated.getPixel(i, j).getRed());
        } else {
          assertEquals(image.getPixel(i, j).getRed(),
                  test_brighten_negated.getPixel(i, j).getRed());
          assertEquals(image.getPixel(i, j).getRed(), test_darken_negated.getPixel(i, j).getRed());
        }
        if (image.getPixel(i, j).getGreen() == 0) {
          assertNotEquals(image.getPixel(i, j).getGreen(),
                  test_darken_negated.getPixel(i, j).getGreen());
        } else if (image.getPixel(i, j).getGreen() == image.getMax()) {
          assertNotEquals(image.getPixel(i, j).getGreen(),
                  test_brighten_negated.getPixel(i, j).getGreen());
        } else {
          assertEquals(image.getPixel(i, j).getGreen(),
                  test_brighten_negated.getPixel(i, j).getGreen());
          assertEquals(image.getPixel(i, j).getGreen(),
                  test_darken_negated.getPixel(i, j).getGreen());
        }
        if (image.getPixel(i, j).getBlue() == 0) {
          assertNotEquals(image.getPixel(i, j).getBlue(),
                  test_darken_negated.getPixel(i, j).getBlue());
        } else if (image.getPixel(i, j).getBlue() == image.getMax()) {
          assertNotEquals(image.getPixel(i, j).getBlue(),
                  test_brighten_negated.getPixel(i, j).getBlue());
        } else {
          assertEquals(image.getPixel(i, j).getBlue(),
                  test_brighten_negated.getPixel(i, j).getBlue());
          assertEquals(image.getPixel(i, j).getBlue(),
                  test_darken_negated.getPixel(i, j).getBlue());
        }
      }
    }
  }

  @Test
  public void testAdjustBrightnessMinMax() {
    ime.brighten(image.getMax(), "test", "test-bright");
    ime.brighten(-1 * image.getMax(), "test", "test-dark");
    Image test_max_brightness = ime.getImage("test-bright");
    Image test_min_brightness = ime.getImage("test-dark");

    assertEquals(image.getHeight(), test_max_brightness.getHeight());
    assertEquals(image.getWidth(), test_max_brightness.getWidth());
    assertEquals(image.getMax(), test_max_brightness.getMax());

    assertEquals(image.getHeight(), test_min_brightness.getHeight());
    assertEquals(image.getWidth(), test_min_brightness.getWidth());
    assertEquals(image.getMax(), test_min_brightness.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getMax(), test_max_brightness.getPixel(i, j).getRed());
        assertEquals(0, test_min_brightness.getPixel(i, j).getRed());

        assertEquals(image.getMax(), test_max_brightness.getPixel(i, j).getGreen());
        assertEquals(0, test_min_brightness.getPixel(i, j).getGreen());

        assertEquals(image.getMax(), test_max_brightness.getPixel(i, j).getBlue());
        assertEquals(0, test_min_brightness.getPixel(i, j).getBlue());
      }
    }
  }

  @Test
  public void testWriteDataLegal() throws IOException {
    OutputStream out = ime.writeData("test");

    Image read_image = testHelper.getImageFromOutputStream(out);

    assertEquals(image.getHeight(), read_image.getHeight());
    assertEquals(image.getWidth(), read_image.getWidth());
    assertEquals(image.getMax(), read_image.getMax());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(image.getPixel(i, j).getRed(), read_image.getPixel(i, j).getRed());
        assertEquals(image.getPixel(i, j).getBlue(), read_image.getPixel(i, j).getBlue());
        assertEquals(image.getPixel(i, j).getGreen(), read_image.getPixel(i, j).getGreen());
      }
    }

    ime.brighten(1, "test", "test-bright-written");

    Image test_brighten = ime.getImage("test-bright-written");

    assertEquals(read_image.getHeight(), test_brighten.getHeight());
    assertEquals(read_image.getWidth(), test_brighten.getWidth());
    assertEquals(read_image.getMax(), test_brighten.getMax());


    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        if (image.getPixel(i, j).getRed() == image.getMax()) {
          assertEquals(image.getPixel(i, j).getRed(), test_brighten.getPixel(i, j).getRed());
        } else {
          assertEquals(image.getPixel(i, j).getRed() + 1, test_brighten.getPixel(i, j).getRed());
        }
        if (image.getPixel(i, j).getGreen() == image.getMax()) {
          assertEquals(image.getPixel(i, j).getGreen(), test_brighten.getPixel(i, j).getGreen());
        } else {
          assertEquals(image.getPixel(i, j).getGreen() + 1,
                  test_brighten.getPixel(i, j).getGreen());
        }
        if (image.getPixel(i, j).getBlue() == image.getMax()) {
          assertEquals(image.getPixel(i, j).getBlue(), test_brighten.getPixel(i, j).getBlue());
        } else {
          assertEquals(image.getPixel(i, j).getBlue() + 1, test_brighten.getPixel(i, j).getBlue());
        }
      }
    }
  }

  @Test
  public void testBlur() throws IOException {
    String filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny");
    Image tinyImage = ime.getImage("test-tiny");
    filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-ppm");
    Image tinyImagePpm = ime.getImage("test-tiny-ppm");

    filePath = "test/res/tiny_image2x2.bmp";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-bmp");
    Image tinyImageBmp = ime.getImage("test-tiny-bmp");

    filePath = "test/res/blur-expected.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "blur-expected");
    Image expectedBlurImage = ime.getImage("blur-expected");
    filePath = "test/res/tiny_image2x2.jpg";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-jpg");
    Image tinyImageJpg = ime.getImage("test-tiny-jpg");

    filePath = "test/res/tiny_image2x2.png";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-png");
    Image tinyImagePng = ime.getImage("test-tiny-png");

    ime.blur("test-tiny-ppm", "test-blur-ppm");
    ime.blur("test-tiny-jpg", "test-blur-jpg");
    ime.blur("test-tiny-bmp", "test-blur-bmp");
    ime.blur("test-tiny-png", "test-blur-png");

    Image receivedImagePpm = ime.getImage("test-blur-ppm");
    Image receivedImageJpg = ime.getImage("test-blur-jpg");
    Image receivedImageBmp = ime.getImage("test-blur-bmp");
    Image receivedImagePng = ime.getImage("test-blur-png");

    filePath = "test/res/blur-expected-ppm.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "blur-expected-ppm");
    Image expectedBlurImagePpm = ime.getImage("blur-expected-ppm");

    filePath = "test/res/blur-expected-jpg.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "blur-expected-jpg");
    Image expectedBlurImageJpg = ime.getImage("blur-expected-jpg");

    filePath = "test/res/blur-expected-png.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "blur-expected-png");
    Image expectedBlurImagePng = ime.getImage("blur-expected-png");

    filePath = "test/res/blur-expected-bmp.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "blur-expected-bmp");
    Image expectedBlurImageBmp = ime.getImage("blur-expected-bmp");

    assertEquals(receivedImagePpm.getHeight(), tinyImagePpm.getHeight());
    assertEquals(receivedImagePpm.getWidth(), tinyImagePpm.getWidth());
    assertEquals(receivedImagePpm.getMax(), tinyImagePpm.getMax());

    assertEquals(receivedImageBmp.getHeight(), tinyImageBmp.getHeight());
    assertEquals(receivedImageBmp.getWidth(), tinyImageBmp.getWidth());
    assertEquals(receivedImageBmp.getMax(), tinyImageBmp.getMax());

    assertEquals(receivedImageJpg.getHeight(), tinyImageJpg.getHeight());
    assertEquals(receivedImageJpg.getWidth(), tinyImageJpg.getWidth());
    assertEquals(receivedImageJpg.getMax(), tinyImageJpg.getMax());

    assertEquals(receivedImagePng.getHeight(), tinyImagePng.getHeight());
    assertEquals(receivedImagePng.getWidth(), tinyImagePng.getWidth());
    assertEquals(receivedImagePng.getMax(), tinyImagePng.getMax());

    for (int i = 0; i < tinyImagePpm.getHeight(); i++) {
      for (int j = 0; j < tinyImagePpm.getWidth(); j++) {
        assertEquals(expectedBlurImagePpm.getPixel(i, j), receivedImagePpm.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImagePng.getHeight(); i++) {
      for (int j = 0; j < tinyImagePng.getWidth(); j++) {
        assertEquals(expectedBlurImagePng.getPixel(i, j), receivedImagePng.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageBmp.getHeight(); i++) {
      for (int j = 0; j < tinyImageBmp.getWidth(); j++) {
        assertEquals(expectedBlurImageBmp.getPixel(i, j), receivedImageBmp.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageJpg.getHeight(); i++) {
      for (int j = 0; j < tinyImageJpg.getWidth(); j++) {
        assertEquals(expectedBlurImageJpg.getPixel(i, j), receivedImageJpg.getPixel(i, j));
      }
    }
  }


  @Test
  public void testSharpen() throws IOException {
    String filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny");
    Image tinyImage = ime.getImage("test-tiny");
    filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-ppm");
    Image tinyImagePpm = ime.getImage("test-tiny-ppm");

    filePath = "test/res/tiny_image2x2.bmp";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-bmp");
    Image tinyImageBmp = ime.getImage("test-tiny-bmp");

    filePath = "test/res/sharpen-expected.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sharpen-expected");
    Image expectedSharpenImage = ime.getImage("sharpen-expected");
    filePath = "test/res/tiny_image2x2.jpg";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-jpg");
    Image tinyImageJpg = ime.getImage("test-tiny-jpg");

    filePath = "test/res/tiny_image2x2.png";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-png");
    Image tinyImagePng = ime.getImage("test-tiny-png");

    ime.sharpen("test-tiny-ppm", "test-sharpen-ppm");
    ime.sharpen("test-tiny-jpg", "test-sharpen-jpg");
    ime.sharpen("test-tiny-bmp", "test-sharpen-bmp");
    ime.sharpen("test-tiny-png", "test-sharpen-png");

    Image receivedImagePpm = ime.getImage("test-sharpen-ppm");
    Image receivedImageJpg = ime.getImage("test-sharpen-jpg");
    Image receivedImageBmp = ime.getImage("test-sharpen-bmp");
    Image receivedImagePng = ime.getImage("test-sharpen-png");

    filePath = "test/res/sharpen-expected-ppm.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sharpen-expected-ppm");
    Image expectedSharpenImagePpm = ime.getImage("sharpen-expected-ppm");

    filePath = "test/res/sharpen-expected-jpg.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sharpen-expected-jpg");
    Image expectedSharpenImageJpg = ime.getImage("sharpen-expected-jpg");

    filePath = "test/res/sharpen-expected-png.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sharpen-expected-png");
    Image expectedSharpenImagePng = ime.getImage("sharpen-expected-png");

    filePath = "test/res/sharpen-expected-bmp.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sharpen-expected-bmp");
    Image expectedSharpenImageBmp = ime.getImage("sharpen-expected-bmp");

    assertEquals(receivedImagePpm.getHeight(), tinyImagePpm.getHeight());
    assertEquals(receivedImagePpm.getWidth(), tinyImagePpm.getWidth());
    assertEquals(receivedImagePpm.getMax(), tinyImagePpm.getMax());

    assertEquals(receivedImageBmp.getHeight(), tinyImageBmp.getHeight());
    assertEquals(receivedImageBmp.getWidth(), tinyImageBmp.getWidth());
    assertEquals(receivedImageBmp.getMax(), tinyImageBmp.getMax());

    assertEquals(receivedImageJpg.getHeight(), tinyImageJpg.getHeight());
    assertEquals(receivedImageJpg.getWidth(), tinyImageJpg.getWidth());
    assertEquals(receivedImageJpg.getMax(), tinyImageJpg.getMax());

    assertEquals(receivedImagePng.getHeight(), tinyImagePng.getHeight());
    assertEquals(receivedImagePng.getWidth(), tinyImagePng.getWidth());
    assertEquals(receivedImagePng.getMax(), tinyImagePng.getMax());

    for (int i = 0; i < tinyImagePpm.getHeight(); i++) {
      for (int j = 0; j < tinyImagePpm.getWidth(); j++) {
        assertEquals(expectedSharpenImagePpm.getPixel(i, j), receivedImagePpm.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImagePng.getHeight(); i++) {
      for (int j = 0; j < tinyImagePng.getWidth(); j++) {
        assertEquals(expectedSharpenImagePng.getPixel(i, j), receivedImagePng.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageBmp.getHeight(); i++) {
      for (int j = 0; j < tinyImageBmp.getWidth(); j++) {
        assertEquals(expectedSharpenImageBmp.getPixel(i, j), receivedImageBmp.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageJpg.getHeight(); i++) {
      for (int j = 0; j < tinyImageJpg.getWidth(); j++) {
        assertEquals(expectedSharpenImageJpg.getPixel(i, j), receivedImageJpg.getPixel(i, j));
      }
    }
  }

  @Test
  public void testSepia() throws IOException {
    String filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny");
    Image tinyImage = ime.getImage("test-tiny");

    filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-ppm");
    Image tinyImagePpm = ime.getImage("test-tiny-ppm");

    filePath = "test/res/tiny_image2x2.bmp";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-bmp");
    Image tinyImageBmp = ime.getImage("test-tiny-bmp");

    filePath = "test/res/sepia-expected.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sepia-expected");
    Image expectedSepiaImage = ime.getImage("sepia-expected");
    filePath = "test/res/tiny_image2x2.jpg";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-jpg");
    Image tinyImageJpg = ime.getImage("test-tiny-jpg");

    filePath = "test/res/tiny_image2x2.png";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-png");
    Image tinyImagePng = ime.getImage("test-tiny-png");

    ime.sepia("test-tiny-ppm", "test-sepia-ppm");
    ime.sepia("test-tiny-jpg", "test-sepia-jpg");
    ime.sepia("test-tiny-bmp", "test-sepia-bmp");
    ime.sepia("test-tiny-png", "test-sepia-png");

    Image receivedImagePpm = ime.getImage("test-sepia-ppm");
    Image receivedImageJpg = ime.getImage("test-sepia-jpg");
    Image receivedImageBmp = ime.getImage("test-sepia-bmp");
    Image receivedImagePng = ime.getImage("test-sepia-png");

    filePath = "test/res/sepia-expected-ppm.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sepia-expected-ppm");
    Image expectedSepiaImagePpm = ime.getImage("sepia-expected-ppm");

    filePath = "test/res/sepia-expected-jpg.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sepia-expected-jpg");
    Image expectedSepiaImageJpg = ime.getImage("sepia-expected-jpg");

    filePath = "test/res/sepia-expected-png.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sepia-expected-png");
    Image expectedSepiaImagePng = ime.getImage("sepia-expected-png");

    filePath = "test/res/sepia-expected-bmp.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "sepia-expected-bmp");
    Image expectedSepiaImageBmp = ime.getImage("sepia-expected-bmp");

    assertEquals(receivedImagePpm.getHeight(), tinyImagePpm.getHeight());
    assertEquals(receivedImagePpm.getWidth(), tinyImagePpm.getWidth());
    assertEquals(receivedImagePpm.getMax(), tinyImagePpm.getMax());

    assertEquals(receivedImageBmp.getHeight(), tinyImageBmp.getHeight());
    assertEquals(receivedImageBmp.getWidth(), tinyImageBmp.getWidth());
    assertEquals(receivedImageBmp.getMax(), tinyImageBmp.getMax());

    assertEquals(receivedImageJpg.getHeight(), tinyImageJpg.getHeight());
    assertEquals(receivedImageJpg.getWidth(), tinyImageJpg.getWidth());
    assertEquals(receivedImageJpg.getMax(), tinyImageJpg.getMax());

    assertEquals(receivedImagePng.getHeight(), tinyImagePng.getHeight());
    assertEquals(receivedImagePng.getWidth(), tinyImagePng.getWidth());
    assertEquals(receivedImagePng.getMax(), tinyImagePng.getMax());

    for (int i = 0; i < tinyImagePpm.getHeight(); i++) {
      for (int j = 0; j < tinyImagePpm.getWidth(); j++) {
        assertEquals(expectedSepiaImagePpm.getPixel(i, j), receivedImagePpm.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImagePng.getHeight(); i++) {
      for (int j = 0; j < tinyImagePng.getWidth(); j++) {
        assertEquals(expectedSepiaImagePng.getPixel(i, j), receivedImagePng.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageBmp.getHeight(); i++) {
      for (int j = 0; j < tinyImageBmp.getWidth(); j++) {
        assertEquals(expectedSepiaImageBmp.getPixel(i, j), receivedImageBmp.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageJpg.getHeight(); i++) {
      for (int j = 0; j < tinyImageJpg.getWidth(); j++) {
        assertEquals(expectedSepiaImageJpg.getPixel(i, j), receivedImageJpg.getPixel(i, j));
      }
    }
  }

  @Test
  public void testDither() throws IOException {
    String filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny");
    Image tinyImage = ime.getImage("test-tiny");
    filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-ppm");
    Image tinyImagePpm = ime.getImage("test-tiny-ppm");

    filePath = "test/res/tiny_image2x2.bmp";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-bmp");
    Image tinyImageBmp = ime.getImage("test-tiny-bmp");

    filePath = "test/res/dither-expected.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "dither-expected");
    Image expectedDitherImage = ime.getImage("dither-expected");
    filePath = "test/res/tiny_image2x2.jpg";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-jpg");
    Image tinyImageJpg = ime.getImage("test-tiny-jpg");

    filePath = "test/res/tiny_image2x2.png";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny-png");
    Image tinyImagePng = ime.getImage("test-tiny-png");

    ime.dither("test-tiny-ppm", "test-dither-ppm");
    ime.dither("test-tiny-jpg", "test-dither-jpg");
    ime.dither("test-tiny-bmp", "test-dither-bmp");
    ime.dither("test-tiny-png", "test-dither-png");

    Image receivedImagePpm = ime.getImage("test-dither-ppm");
    Image receivedImageJpg = ime.getImage("test-dither-jpg");
    Image receivedImageBmp = ime.getImage("test-dither-bmp");
    Image receivedImagePng = ime.getImage("test-dither-png");

    filePath = "test/res/dither-expected-ppm.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "dither-expected-ppm");
    Image expectedDitherImagePpm = ime.getImage("dither-expected-ppm");

    filePath = "test/res/dither-expected-jpg.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "dither-expected-jpg");
    Image expectedDitherImageJpg = ime.getImage("dither-expected-jpg");

    filePath = "test/res/dither-expected-png.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "dither-expected-png");
    Image expectedDitherImagePng = ime.getImage("dither-expected-png");

    filePath = "test/res/dither-expected-bmp.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "dither-expected-bmp");
    Image expectedDitherImageBmp = ime.getImage("dither-expected-bmp");

    assertEquals(receivedImagePpm.getHeight(), tinyImagePpm.getHeight());
    assertEquals(receivedImagePpm.getWidth(), tinyImagePpm.getWidth());
    assertEquals(receivedImagePpm.getMax(), tinyImagePpm.getMax());

    assertEquals(receivedImageBmp.getHeight(), tinyImageBmp.getHeight());
    assertEquals(receivedImageBmp.getWidth(), tinyImageBmp.getWidth());
    assertEquals(receivedImageBmp.getMax(), tinyImageBmp.getMax());

    assertEquals(receivedImageJpg.getHeight(), tinyImageJpg.getHeight());
    assertEquals(receivedImageJpg.getWidth(), tinyImageJpg.getWidth());
    assertEquals(receivedImageJpg.getMax(), tinyImageJpg.getMax());

    assertEquals(receivedImagePng.getHeight(), tinyImagePng.getHeight());
    assertEquals(receivedImagePng.getWidth(), tinyImagePng.getWidth());
    assertEquals(receivedImagePng.getMax(), tinyImagePng.getMax());

    for (int i = 0; i < tinyImagePpm.getHeight(); i++) {
      for (int j = 0; j < tinyImagePpm.getWidth(); j++) {
        assertEquals(expectedDitherImagePpm.getPixel(i, j), receivedImagePpm.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImagePng.getHeight(); i++) {
      for (int j = 0; j < tinyImagePng.getWidth(); j++) {
        assertEquals(expectedDitherImagePng.getPixel(i, j), receivedImagePng.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageBmp.getHeight(); i++) {
      for (int j = 0; j < tinyImageBmp.getWidth(); j++) {
        assertEquals(expectedDitherImageBmp.getPixel(i, j), receivedImageBmp.getPixel(i, j));
      }
    }

    for (int i = 0; i < tinyImageJpg.getHeight(); i++) {
      for (int j = 0; j < tinyImageJpg.getWidth(); j++) {
        assertEquals(expectedDitherImageJpg.getPixel(i, j), receivedImageJpg.getPixel(i, j));
      }
    }
  }

  @Test
  public void testDitherTwice() throws IOException {
    String filePath = "test/res/tiny_image2x2.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "test-tiny");
    Image tinyImage = ime.getImage("test-tiny");

    ime.dither("test-tiny", "test-dither");
    ime.dither("test-dither", "test-dither-twice");
    Image ditherOnceImage = ime.getImage("test-dither");
    Image receivedImage = ime.getImage("test-dither-twice");

    filePath = "test/res/dither-twice-expected.ppm";
    ime.readData(testHelper.getImageInputStream(filePath), "dither-twice-expected");
    Image expectedDitherImage = ime.getImage("dither-twice-expected");

    assertEquals(receivedImage.getHeight(), tinyImage.getHeight());
    assertEquals(receivedImage.getWidth(), tinyImage.getWidth());
    assertEquals(receivedImage.getMax(), tinyImage.getMax());

    assertEquals(expectedDitherImage, ditherOnceImage);
  }

  @Test(expected = InputMismatchException.class)
  public void testBlurNoImage() throws IOException {
    ime.blur("test-invalid", "test-blur");
  }

  @Test(expected = InputMismatchException.class)
  public void testSharpenNoImage() throws IOException {
    ime.sepia("test-invalid", "test-blur");
  }

  @Test(expected = InputMismatchException.class)
  public void testSepiaNoImage() throws IOException {
    ime.sepia("test-invalid", "test-blur");
  }

  @Test(expected = InputMismatchException.class)
  public void testDitherNoImage() throws IOException {
    ime.dither("test-invalid", "test-blur");
  }

  private class TestHelper {
    /**
     * Helper method to read a file.
     *
     * @param filename name of the file.
     * @return contents of the file.
     * @throws FileNotFoundException if invalid path
     */
    public String readFile(String filename) throws FileNotFoundException {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      Scanner sc = new Scanner(fis);
      StringBuilder builder = new StringBuilder();

      // read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        // ignore empty lines
        if (s.isEmpty()) {
          continue;
        }
        if (s.charAt(0) != '#') {
          builder.append(s).append(System.lineSeparator());
        }
      }

      return builder.toString();
    }

    /**
     * Gets input stream of an Image.
     *
     * @param filename name of the image file.
     * @return the stream of image data.
     * @throws IOException if hit a block while creating the stream from file.
     */
    public InputStream getImageInputStream(String filename) throws IOException {

      if (filename.endsWith(".ppm")) {
        String fileContents = readFile(filename);
        Scanner sc = new Scanner(fileContents);
        String token;
        token = sc.next();
        StringBuilder builder = new StringBuilder();
        // read the file line by line, and populate a string. This will throw away any comment lines
        while (sc.hasNext()) {
          String s = sc.next();
          if (s.charAt(0) != '#') {
            builder.append(s + System.lineSeparator());
          }
        }

        return new ByteArrayInputStream(builder.toString().getBytes());
      } else {
        // Read image data from file
        File file = new File(filename);
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);

        // Create a Buffered Image from this inputStream now
        BufferedImage image = ImageIO.read(imageInputStream);

        StringBuilder sbBeforeMax = new StringBuilder();
        StringBuilder sbAfterMax = new StringBuilder();


        int height = image.getHeight();
        int width = image.getWidth();
        int max = 0;

        sbBeforeMax.append(width).append(" ").append(height).append(System.lineSeparator());

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            // getRGB takes width as the first argument and height as second
            Color rgb = new Color(image.getRGB(j, i));

            int red = rgb.getRed();
            int green = rgb.getGreen();
            int blue = rgb.getBlue();

            max = Math.max(max, Math.max(red, Math.max(green, blue)));

            sbAfterMax.append(red).append(System.lineSeparator());
            sbAfterMax.append(green).append(System.lineSeparator());
            sbAfterMax.append(blue).append(System.lineSeparator());
          }
        }

        String sb = String.valueOf(sbBeforeMax) + max + System.lineSeparator()
                + sbAfterMax;

        return new ByteArrayInputStream(sb.getBytes());
      }
    }

    /**
     * Method to get Image from outputStream of image data.
     *
     * @param imageData output stream of image data.
     * @return a new Image that is created from the data.
     */
    public Image getImageFromOutputStream(OutputStream imageData) {
      Scanner sc = new Scanner(imageData.toString());
      int width = sc.nextInt();
      int height = sc.nextInt();
      int max = sc.nextInt();

      Image rgbImage = new RGBImage(height, width, max);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          rgbImage.setPixel(i, j, new RGBPixel(sc.nextInt(), sc.nextInt(), sc.nextInt(), max));
        }
      }
      return rgbImage;
    }
  }
}

