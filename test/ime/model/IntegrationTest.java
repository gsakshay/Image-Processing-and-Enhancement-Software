package ime.model;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ime.control.Controller;
import ime.control.IController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the program implementation as integration of model and controller.
 */
public class IntegrationTest {
  /**
   * Helper method to create an image directly with the values.
   *
   * @param imageData the data of the image.
   * @return the image created with image data.
   */
  public Image createImage(String imageData) {
    Scanner sc = new Scanner(imageData);
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();
    Image newImage = new RGBImage(height, width, max);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newImage.setPixel(i, j, new RGBPixel(sc.nextInt(), sc.nextInt(), sc.nextInt(), max));
      }
    }
    return newImage;
  }


  /**
   * Reads a file, an image file of .ppm and returns the image data as string.
   * This contains width and height in its first line as separate integers.
   * Max value as next integer. And remaining are pixel data.
   *
   * @param filename is the name of the file to be read
   * @return the image data
   * @throws IOException if hit a block while reading an image.
   */
  public String readImage(String filename) throws IOException {
    File file = new File(filename);
    FileInputStream fis = new FileInputStream(file);
    Scanner sc = new Scanner(fis);
    StringBuilder builder = new StringBuilder();

    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IOException("Invalid token found in the file " + filename);
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    if (width < 0 || height < 0 || maxValue < 0) {
      throw new InvalidObjectException("Invalid values found in the file - " + filename);
    }

    StringBuilder sb = new StringBuilder();

    sb.append(width).append(" ").append(height).append(System.lineSeparator());
    sb.append(maxValue).append(System.lineSeparator());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        if (r < 0 || g < 0 || b < 0 || r > maxValue || g > maxValue || b > maxValue) {
          throw new InvalidObjectException("Invalid values found in the file - " + filename);
        }
        sb.append(r).append(System.lineSeparator());
        sb.append(g).append(System.lineSeparator());
        sb.append(b).append(System.lineSeparator());
      }
    }

    return sb.toString();
  }


  @Test
  public void testLoadInvalidFiles() throws IOException {
    MIME testIme;
    IController testController;

    testIme = new MIMEImp();
    List<String> invalidPPMFilePaths = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      invalidPPMFilePaths.add("test/res/invalid" + i + ".ppm");
    }
    for (String invalidFilePath : invalidPPMFilePaths) {
      StringBuilder sb = new StringBuilder();
      testController = new Controller(new StringReader("load " + invalidFilePath
              + " invalidTest"), sb);
      testController.run(testIme);
      assertEquals(sb.toString(), "Please provide a valid file");
    }
  }

  @Test
  public void testTryInvalidSave() throws IOException {
    MIME testIme;
    IController testController;
    IController testController2;
    IController testController3;

    StringBuilder sb = new StringBuilder();

    testIme = new MIMEImp();
    testController = new Controller(new StringReader("load res/test.ppm test"), sb);

    testController.run(testIme);

    StringBuilder sb2 = new StringBuilder();

    testController2 = new Controller(new StringReader("save res/test.ppm test1"), sb2);
    testController2.run(testIme);

    assertEquals(sb2.toString(), "Image test1 not found Please try again with valid image.");

    StringBuilder sb3 = new StringBuilder();

    testController3 = new Controller(new StringReader("save res/test.ppm test2"), sb3);
    testController3.run(testIme);

    assertEquals(sb3.toString(), "Image test2 not found Please try again with valid image.");
  }

  @Test
  public void testTryInvalidOperations() throws IOException {
    MIME testIme;
    IController testController;
    IController testController2;
    IController testController3;
    IController testController4;

    testIme = new MIMEImp();

    StringBuilder sb = new StringBuilder();
    testController = new Controller(new StringReader("load test/res/tiny_image2x2.ppm test1"),
            sb);
    testController.run(testIme);

    StringBuilder sb2 = new StringBuilder();
    testController2 = new Controller(new StringReader("load test/res/tiny_image2x3.ppm test2"),
            sb2);
    testController2.run(testIme);

    StringBuilder sb3 = new StringBuilder();
    testController3 = new Controller(new StringReader("load test/res/tiny_image2x3.ppm test3"),
            sb3);
    testController3.run(testIme);

    StringBuilder sb4 = new StringBuilder();
    testController4 = new Controller(new StringReader("rgb-combine test-combined"
            + " test1 test2 test3"), sb4);
    testController4.run(testIme);

    assertEquals(sb4.toString(), "To combine, images should be of same dimensions");
  }

  @Test
  public void testTryValidOperations() throws IOException {
    MIMEImp testIme;
    IController testController;
    IController testController2;
    IController testController3;
    IController testController4;
    IController testController5;
    IController testController6;
    IController testController7;
    IController testController8;
    testIme = new MIMEImp();

    StringBuilder sb = new StringBuilder();
    testController = new Controller(new StringReader("load res/test.ppm test"), sb);
    testController.run(testIme);

    StringBuilder sb2 = new StringBuilder();
    testController2 = new Controller(new StringReader("brighten 50 test test-brighter"), sb2);
    testController2.run(testIme);

    Image testImage = testIme.getImage("test");
    Image receivedBrightenedImage = testIme.getImage("test-brighter");

    assertEquals(testImage.getHeight(), receivedBrightenedImage.getHeight());
    assertEquals(testImage.getWidth(), receivedBrightenedImage.getWidth());
    assertEquals(testImage.getMax(), receivedBrightenedImage.getMax());

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        if (testImage.getPixel(i, j).getRed() + 50 >= testImage.getMax()) {
          assertEquals(testImage.getMax(), receivedBrightenedImage.getPixel(i, j).getRed());
        } else {
          assertEquals(testImage.getPixel(i, j).getRed() + 50,
                  receivedBrightenedImage.getPixel(i, j).getRed());
        }
        if (testImage.getPixel(i, j).getBlue() + 50 >= testImage.getMax()) {
          assertEquals(testImage.getMax(), receivedBrightenedImage.getPixel(i, j).getBlue());
        } else {
          assertEquals(testImage.getPixel(i, j).getBlue() + 50,
                  receivedBrightenedImage.getPixel(i, j).getBlue());
        }
        if (testImage.getPixel(i, j).getGreen() + 50 >= testImage.getMax()) {
          assertEquals(testImage.getMax(), receivedBrightenedImage.getPixel(i, j).getGreen());
        } else {
          assertEquals(testImage.getPixel(i, j).getGreen() + 50,
                  receivedBrightenedImage.getPixel(i, j).getGreen());
        }
      }
    }

    StringBuilder sb3 = new StringBuilder();
    testController3 = new Controller(new StringReader("greyscale intensity-component "
            + "test test-greyscale-intensity"), sb3);
    testController3.run(testIme);
    Image receivedIntensityImage = testIme.getImage("test-greyscale-intensity");

    assertEquals(testImage.getHeight(), receivedIntensityImage.getHeight());
    assertEquals(testImage.getWidth(), receivedIntensityImage.getWidth());
    assertEquals(testImage.getMax(), receivedIntensityImage.getMax());

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        int averageExpected = (testImage.getPixel(i, j).getRed()
                + testImage.getPixel(i, j).getBlue() + testImage.getPixel(i, j).getGreen()) / 3;
        assertEquals(averageExpected, receivedIntensityImage.getPixel(i, j).getRed());
        assertEquals(averageExpected, receivedIntensityImage.getPixel(i, j).getBlue());
        assertEquals(averageExpected, receivedIntensityImage.getPixel(i, j).getGreen());
      }
    }

    StringBuilder sb4 = new StringBuilder();
    testController4 = new Controller(new StringReader("vertical-flip test test-vertical"), sb4);
    testController4.run(testIme);
    Image receivedVerticalFlippedImage = testIme.getImage("test-vertical");

    assertEquals(testImage.getHeight(), receivedVerticalFlippedImage.getHeight());
    assertEquals(testImage.getWidth(), receivedVerticalFlippedImage.getWidth());
    assertEquals(testImage.getMax(), receivedVerticalFlippedImage.getMax());

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        assertEquals(testImage.getPixel(testImage.getHeight() - i - 1, j),
                receivedVerticalFlippedImage.getPixel(i, j));
      }
    }

    StringBuilder sb5 = new StringBuilder();
    testController5 = new Controller(new StringReader("horizontal-flip test test-horizontal"),
            sb5);
    testController5.run(testIme);
    Image receivedHorizontalFlippedImage = testIme.getImage("test-horizontal");

    assertEquals(testImage.getHeight(), receivedHorizontalFlippedImage.getHeight());
    assertEquals(testImage.getWidth(), receivedHorizontalFlippedImage.getWidth());
    assertEquals(testImage.getMax(), receivedHorizontalFlippedImage.getMax());

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        assertEquals(testImage.getPixel(i, testImage.getWidth() - j - 1),
                receivedHorizontalFlippedImage.getPixel(i, j));
      }
    }

    StringBuilder sb6 = new StringBuilder();
    testController6 = new Controller(new StringReader("horizontal-flip test-horizontal "
            + "test-horizontal-twice"), sb6);
    testController6.run(testIme);
    Image receivedHorizontalFlippedTwiceImage = testIme.getImage("test-horizontal-twice");

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        assertEquals(testImage.getPixel(i, j), receivedHorizontalFlippedTwiceImage.getPixel(i, j));
      }
    }

    StringBuilder sb7 = new StringBuilder();
    testController7 = new Controller(new StringReader("rgb-split test test-red "
            + "test-green test-blue"), sb7);
    testController7.run(testIme);
    Image receivedRedSplitImage = testIme.getImage("test-red");
    Image receivedGreenSplitImage = testIme.getImage("test-green");
    Image receivedBlueSplitImage = testIme.getImage("test-blue");
    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        assertEquals(testImage.getPixel(i, j).getRed(),
                receivedRedSplitImage.getPixel(i, j).getRed());
        assertEquals(testImage.getPixel(i, j).getRed(),
                receivedRedSplitImage.getPixel(i, j).getGreen());
        assertEquals(testImage.getPixel(i, j).getRed(),
                receivedRedSplitImage.getPixel(i, j).getBlue());

        assertEquals(testImage.getPixel(i, j).getGreen(),
                receivedGreenSplitImage.getPixel(i, j).getRed());
        assertEquals(testImage.getPixel(i, j).getGreen(),
                receivedGreenSplitImage.getPixel(i, j).getGreen());
        assertEquals(testImage.getPixel(i, j).getGreen(),
                receivedGreenSplitImage.getPixel(i, j).getBlue());

        assertEquals(testImage.getPixel(i, j).getBlue(),
                receivedBlueSplitImage.getPixel(i, j).getRed());
        assertEquals(testImage.getPixel(i, j).getBlue(),
                receivedBlueSplitImage.getPixel(i, j).getGreen());
        assertEquals(testImage.getPixel(i, j).getBlue(),
                receivedBlueSplitImage.getPixel(i, j).getBlue());
      }
    }

    StringBuilder sb8 = new StringBuilder();
    testController8 = new Controller(new StringReader("rgb-combine test-combined "
            + "test-red test-green test-blue"), sb8);
    testController8.run(testIme);
    Image receivedCombinedImage = testIme.getImage("test-combined");

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        assertEquals(testImage.getPixel(i, j), receivedCombinedImage.getPixel(i, j));
      }
    }
  }

  @Test
  public void testLoadSaveDifferentFileTypesPPM() throws IOException {
    MIMEImp testIme;
    IController testController;
    IController testController2;
    IController testController3;
    IController testController4;
    IController testController5;
    IController testController6;
    IController testController7;

    testIme = new MIMEImp();

    StringBuilder sb = new StringBuilder();
    testController = new Controller(new StringReader("load test/res/tiny_image2x2.ppm "
            + "test-ppm"), sb);
    testController.run(testIme);

    StringBuilder sb2 = new StringBuilder();
    testController2 = new Controller(new StringReader("save test/res/tiny_image2x2.jpg test-ppm"),
            sb2);
    testController2.run(testIme);

    StringBuilder sb3 = new StringBuilder();
    testController3 = new Controller(new StringReader("save test/res/tiny_image2x2.bmp test-ppm"),
            sb3);
    testController3.run(testIme);

    StringBuilder sb4 = new StringBuilder();
    testController4 = new Controller(new StringReader("save test/res/tiny_image2x2.png test-ppm"),
            sb4);
    testController4.run(testIme);

    StringBuilder sb5 = new StringBuilder();
    testController5 = new Controller(new StringReader("load test/res/tiny_image2x2.jpg test-jpg"),
            sb5);
    testController5.run(testIme);

    StringBuilder sb6 = new StringBuilder();
    testController6 = new Controller(new StringReader("load test/res/tiny_image2x2.bmp test-bmp"),
            sb6);
    testController6.run(testIme);

    StringBuilder sb7 = new StringBuilder();
    testController7 = new Controller(new StringReader("load test/res/tiny_image2x2.png test-png"),
            sb7);
    testController7.run(testIme);

    assertTrue(new File("test/res/tiny_image2x2.jpg").exists());
    assertTrue(new File("test/res/tiny_image2x2.bmp").exists());
    assertTrue(new File("test/res/tiny_image2x2.png").exists());

    Image testPpm = testIme.getImage("test-ppm");
    Image testJpg = testIme.getImage("test-jpg");
    Image testBmp = testIme.getImage("test-bmp");
    Image testPng = testIme.getImage("test-png");

    assertEquals(testPpm.getHeight(), testPng.getHeight());
    assertEquals(testPpm.getWidth(), testPng.getWidth());

    assertEquals(testPpm.getHeight(), testBmp.getHeight());
    assertEquals(testPpm.getWidth(), testBmp.getWidth());

    assertEquals(testPpm.getHeight(), testJpg.getHeight());
    assertEquals(testPpm.getWidth(), testJpg.getWidth());

    for (int i = 0; i < testPpm.getHeight(); i++) {
      for (int j = 0; j < testPpm.getWidth(); j++) {
        assertEquals(testPpm.getPixel(i, j).getRed(), testBmp.getPixel(i, j).getRed());
        assertEquals(testPpm.getPixel(i, j).getGreen(), testBmp.getPixel(i, j).getGreen());
        assertEquals(testPpm.getPixel(i, j).getBlue(), testBmp.getPixel(i, j).getBlue());

        assertEquals(testPpm.getPixel(i, j).getRed(), testPng.getPixel(i, j).getRed());
        assertEquals(testPpm.getPixel(i, j).getGreen(), testPng.getPixel(i, j).getGreen());
        assertEquals(testPpm.getPixel(i, j).getBlue(), testPng.getPixel(i, j).getBlue());
      }
    }
  }

  @Test
  public void testMultipleOperationsDifferentFileTypes() throws IOException {
    MIMEImp testIme;
    IController testController;
    IController testController2;
    IController testController3;
    IController testController4;
    IController testController5;
    IController testController6;
    IController testController7;
    IController testController8;
    IController testController9;
    IController testController10;
    IController testController11;
    IController testController12;
    IController testController13;
    IController testController14;
    IController testController15;

    testIme = new MIMEImp();

    StringBuilder sb = new StringBuilder();
    testController = new Controller(new StringReader("load test/res/tiny_image2x2.ppm test-ppm"),
            sb);
    testController.run(testIme);

    StringBuilder sb2 = new StringBuilder();
    testController2 = new Controller(new StringReader("load test/res/tiny_image2x2.png test-png"),
            sb2);
    testController2.run(testIme);

    StringBuilder sb3 = new StringBuilder();
    testController3 = new Controller(new StringReader("load test/res/tiny_image2x2.jpg test-jpg"),
            sb3);
    testController3.run(testIme);

    StringBuilder sb4 = new StringBuilder();
    testController4 = new Controller(new StringReader("load test/res/tiny_image2x2.bmp test-bmp"),
            sb4);
    testController4.run(testIme);

    StringBuilder sb5 = new StringBuilder();
    testController5 = new Controller(new StringReader("rgb-split test-ppm test-ppm-red "
            + "test-ppm-green test-ppm-blue"), sb5);
    testController5.run(testIme);

    StringBuilder sb6 = new StringBuilder();
    testController6 = new Controller(new StringReader("rgb-combine test-ppm-combined test-ppm-red"
            + " test-ppm-green test-ppm-blue"), sb6);
    testController6.run(testIme);

    Image testPPMCombined = testIme.getImage("test-ppm-combined");
    Image testPPM = testIme.getImage("test-ppm");
    Image testPNG = testIme.getImage("test-png");
    Image testBMP = testIme.getImage("test-bmp");
    Image testJPG = testIme.getImage("test-jpg");

    for (int i = 0; i < testPPM.getHeight(); i++) {
      for (int j = 0; j < testPPM.getWidth(); j++) {
        assertEquals(testPPM.getPixel(i, j).getRed(), testPPMCombined.getPixel(i, j).getRed());
        assertEquals(testPPM.getPixel(i, j).getGreen(), testPPMCombined.getPixel(i, j).getGreen());
        assertEquals(testPPM.getPixel(i, j).getBlue(), testPPMCombined.getPixel(i, j).getBlue());

        assertEquals(testPNG.getPixel(i, j).getRed(), testPPMCombined.getPixel(i, j).getRed());
        assertEquals(testPNG.getPixel(i, j).getGreen(), testPPMCombined.getPixel(i, j).getGreen());
        assertEquals(testPNG.getPixel(i, j).getBlue(), testPPMCombined.getPixel(i, j).getBlue());

        assertEquals(testBMP.getPixel(i, j).getRed(), testPPMCombined.getPixel(i, j).getRed());
        assertEquals(testBMP.getPixel(i, j).getGreen(), testPPMCombined.getPixel(i, j).getGreen());
        assertEquals(testBMP.getPixel(i, j).getBlue(), testPPMCombined.getPixel(i, j).getBlue());
      }
    }

    StringBuilder sb7 = new StringBuilder();
    testController7 = new Controller(new StringReader("brighten 50 test-ppm-combined "
            + "test-ppm-brighter"), sb7);
    testController7.run(testIme);

    StringBuilder sb8 = new StringBuilder();
    testController8 = new Controller(new StringReader("brighten -50 test-ppm-brighter "
            + "test-ppm-normal-again"), sb8);
    testController8.run(testIme);

    Image testPPMBrightenedDarkened = testIme.getImage("test-ppm-normal-again");

    for (int i = 0; i < testPPM.getHeight(); i++) {
      for (int j = 0; j < testPPM.getWidth(); j++) {
        assertEquals(testPPM.getPixel(i, j).getRed(),
                testPPMBrightenedDarkened.getPixel(i, j).getRed(), 50);
        assertEquals(testPPM.getPixel(i, j).getGreen(),
                testPPMBrightenedDarkened.getPixel(i, j).getGreen(), 50);
        assertEquals(testPPM.getPixel(i, j).getBlue(),
                testPPMBrightenedDarkened.getPixel(i, j).getBlue(), 50);

        assertEquals(testPNG.getPixel(i, j).getRed(),
                testPPMBrightenedDarkened.getPixel(i, j).getRed(), 50);
        assertEquals(testPNG.getPixel(i, j).getGreen(),
                testPPMBrightenedDarkened.getPixel(i, j).getGreen(), 50);
        assertEquals(testPNG.getPixel(i, j).getBlue(),
                testPPMBrightenedDarkened.getPixel(i, j).getBlue(), 50);

        assertEquals(testBMP.getPixel(i, j).getRed(),
                testPPMBrightenedDarkened.getPixel(i, j).getRed(), 50);
        assertEquals(testBMP.getPixel(i, j).getGreen(),
                testPPMBrightenedDarkened.getPixel(i, j).getGreen(), 50);
        assertEquals(testBMP.getPixel(i, j).getBlue(),
                testPPMBrightenedDarkened.getPixel(i, j).getBlue(), 50);
      }
    }

    StringBuilder sb9 = new StringBuilder();
    testController9 = new Controller(new StringReader("greyscale red-component test-jpg "
            + "test-greyscale-red-jpg"), sb9);
    testController9.run(testIme);

    Image testGreyscaleRedJpg = testIme.getImage("test-greyscale-red-jpg");

    assertEquals(testJPG.getHeight(), testGreyscaleRedJpg.getHeight());
    assertEquals(testJPG.getWidth(), testGreyscaleRedJpg.getWidth());
    assertEquals(testJPG.getMax(), testGreyscaleRedJpg.getMax());

    for (int i = 0; i < testJPG.getHeight(); i++) {
      for (int j = 0; j < testJPG.getWidth(); j++) {
        assertEquals(testJPG.getPixel(i, j).getRed(),
                testGreyscaleRedJpg.getPixel(i, j).getRed());
        assertEquals(testJPG.getPixel(i, j).getRed(),
                testGreyscaleRedJpg.getPixel(i, j).getGreen());
        assertEquals(testJPG.getPixel(i, j).getRed(),
                testGreyscaleRedJpg.getPixel(i, j).getBlue());
      }
    }

    StringBuilder sb10 = new StringBuilder();
    testController10 = new Controller(new StringReader("greyscale green-component test-png "
            + "test-greyscale-green-png"), sb10);
    testController10.run(testIme);

    Image testGreyscaleGreenPng = testIme.getImage("test-greyscale-green-png");

    assertEquals(testPNG.getHeight(), testGreyscaleGreenPng.getHeight());
    assertEquals(testPNG.getWidth(), testGreyscaleGreenPng.getWidth());
    assertEquals(testPNG.getMax(), testGreyscaleGreenPng.getMax());

    for (int i = 0; i < testPNG.getHeight(); i++) {
      for (int j = 0; j < testPNG.getWidth(); j++) {
        assertEquals(testPNG.getPixel(i, j).getGreen(),
                testGreyscaleGreenPng.getPixel(i, j).getRed());
        assertEquals(testPNG.getPixel(i, j).getGreen(),
                testGreyscaleGreenPng.getPixel(i, j).getGreen());
        assertEquals(testPNG.getPixel(i, j).getGreen(),
                testGreyscaleGreenPng.getPixel(i, j).getBlue());
      }
    }

    StringBuilder sb11 = new StringBuilder();
    testController11 = new Controller(new StringReader("greyscale blue-component test-bmp "
            + "test-greyscale-blue-bmp"), sb11);
    testController11.run(testIme);

    Image testGreyscaleBlueBmp = testIme.getImage("test-greyscale-blue-bmp");

    assertEquals(testBMP.getHeight(), testGreyscaleBlueBmp.getHeight());
    assertEquals(testBMP.getWidth(), testGreyscaleBlueBmp.getWidth());
    assertEquals(testBMP.getMax(), testGreyscaleBlueBmp.getMax());

    for (int i = 0; i < testBMP.getHeight(); i++) {
      for (int j = 0; j < testBMP.getWidth(); j++) {
        assertEquals(testBMP.getPixel(i, j).getBlue(),
                testGreyscaleBlueBmp.getPixel(i, j).getRed());
        assertEquals(testBMP.getPixel(i, j).getBlue(),
                testGreyscaleBlueBmp.getPixel(i, j).getGreen());
        assertEquals(testBMP.getPixel(i, j).getBlue(),
                testGreyscaleBlueBmp.getPixel(i, j).getBlue());
      }
    }

    StringBuilder sb12 = new StringBuilder();
    testController12 = new Controller(new StringReader("horizontal-flip test-png "
            + "test-png-horizontal"), sb12);
    testController12.run(testIme);

    // Flipping twice to get the original image back to test
    StringBuilder sb13 = new StringBuilder();
    testController13 = new Controller(new StringReader("horizontal-flip test-png-horizontal "
            + "test-png-original"), sb13);
    testController13.run(testIme);

    Image horizontalFlipTwicePng = testIme.getImage("test-png-original");

    assertEquals(testPNG.getHeight(), horizontalFlipTwicePng.getHeight());
    assertEquals(testPNG.getWidth(), horizontalFlipTwicePng.getWidth());
    assertEquals(testPNG.getMax(), horizontalFlipTwicePng.getMax());

    for (int i = 0; i < testPNG.getHeight(); i++) {
      for (int j = 0; j < testPNG.getWidth(); j++) {
        assertEquals(testPNG.getPixel(i, j), horizontalFlipTwicePng.getPixel(i, j));
      }
    }

    StringBuilder sb14 = new StringBuilder();
    testController14 = new Controller(new StringReader("vertical-flip test-png "
            + "test-png-vertical"), sb14);
    testController14.run(testIme);

    // Flipping twice to get the original image back to test
    StringBuilder sb15 = new StringBuilder();
    testController15 = new Controller(new StringReader("vertical-flip test-png-vertical "
            + "test-png-original"), sb15);
    testController15.run(testIme);

    Image verticalFlipTwicePng = testIme.getImage("test-png-original");

    assertEquals(testPNG.getHeight(), verticalFlipTwicePng.getHeight());
    assertEquals(testPNG.getWidth(), verticalFlipTwicePng.getWidth());
    assertEquals(testPNG.getMax(), verticalFlipTwicePng.getMax());

    for (int i = 0; i < testPNG.getHeight(); i++) {
      for (int j = 0; j < testPNG.getWidth(); j++) {
        assertEquals(testPNG.getPixel(i, j), verticalFlipTwicePng.getPixel(i, j));
      }
    }
  }
}
