package ime.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of the IME interface that operates on PPM image and performs the operations.
 */
public class IMEImp implements IME {
  protected final Map<String, Image> images;

  /**
   * Constructs IME, creates a set of images to store the images and results in the run.
   */
  public IMEImp() {
    this.images = new HashMap<>();
  }


  @Override
  public void readData(InputStream imageData, String imageName) {
    Scanner sc = new Scanner(imageData);
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();

    if (height < 0 || width < 0 || max < 0) {
      throw new IllegalArgumentException("Please provide a valid file");
    }

    Image newImage = new RGBImage(height, width, max);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        if (r < 0 || r > max || g < 0 || g > max || b < 0 || b > max) {
          throw new IllegalArgumentException("Please provide a valid file");
        }

        newImage.setPixel(i, j, new RGBPixel(r, g, b, max));
      }
    }
    images.put(imageName, newImage);
  }

  @Override
  public OutputStream writeData(String imageName) throws IOException {
    Image requiredImage = getImage(imageName);
    StringBuilder sb = new StringBuilder();
    int width = requiredImage.getWidth();
    int height = requiredImage.getHeight();
    int max = requiredImage.getMax();
    sb.append(width).append(" ").append(height).append(System.lineSeparator());
    sb.append(max).append(System.lineSeparator());
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sb.append(requiredImage.getPixel(i, j).getRed()).append(System.lineSeparator());
        sb.append(requiredImage.getPixel(i, j).getGreen()).append(System.lineSeparator());
        sb.append(requiredImage.getPixel(i, j).getBlue()).append(System.lineSeparator());
      }
    }
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    outputStream.write(sb.toString().getBytes());
    return outputStream;
  }

  /**
   * Method to get an image by name.
   * This is specifically package private method, that should be available only to the model.
   * Availability of the Image and editing its data should be happening through the model.
   *
   * @param imageName name that represents an Image
   * @return the image if found
   */
  Image getImage(String imageName) {
    if (images.containsKey(imageName)) {
      return images.get(imageName);
    } else {
      throw new InputMismatchException("Image " + imageName + " not found " + "Please try again "
              + "with valid image.");
    }
  }

  @Override
  public void brighten(int value, String image, String result) {
    Image baseImage = getImage(image);
    Filter brighten = new PixelFilter(base -> i -> j -> new RGBPixel(base.getPixel(i, j).getRed()
            + value, base.getPixel(i, j).getGreen() + value, base.getPixel(i, j).getBlue()
            + value, base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(brighten));
  }

  @Override
  public void verticalFlip(String image, String result) {
    Image baseImage = getImage(image);
    Filter verticalF = new PixelFilter(base -> i -> j ->
            base.getPixel(base.getHeight() - 1 - i, j));
    images.put(result, baseImage.applyFilter(verticalF));
  }


  @Override
  public void horizontalFlip(String image, String result) {
    Image baseImage = getImage(image);
    Filter horizontalF = new PixelFilter(base -> i -> j ->
            base.getPixel(i, base.getWidth() - 1 - j));
    images.put(result, baseImage.applyFilter(horizontalF));
  }

  @Override
  public void redGreyscale(String image, String result) {
    Image baseImage = getImage(image);
    Filter redGrey = new PixelFilter(base -> i -> j ->
            new RGBPixel(base.getPixel(i, j).getRed(), base.getPixel(i, j).getRed(),
                    base.getPixel(i, j).getRed(), base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(redGrey));
  }

  @Override
  public void greenGreyscale(String image, String result) {
    Image baseImage = getImage(image);
    Filter greenGrey = new PixelFilter(base -> i -> j ->
            new RGBPixel(base.getPixel(i, j).getGreen(), base.getPixel(i, j).getGreen(),
                    base.getPixel(i, j).getGreen(), base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(greenGrey));
  }

  @Override
  public void blueGreyscale(String image, String result) {
    Image baseImage = getImage(image);
    Filter blueGrey = new PixelFilter(base -> i -> j -> new RGBPixel(base.getPixel(i, j).getBlue(),
            base.getPixel(i, j).getBlue(),
            base.getPixel(i, j).getBlue(), base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(blueGrey));
  }

  @Override
  public void valueGreyscale(String image, String result) {
    Image baseImage = getImage(image);
    Filter valueGrey = new PixelFilter(base -> i -> j ->
            new RGBPixel(base.getPixel(i, j).getValue(), base.getPixel(i, j).getValue(),
                    base.getPixel(i, j).getValue(), base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(valueGrey));
  }

  @Override
  public void lumaGreyscale(String image, String result) {
    Image baseImage = getImage(image);
    Filter lumaGrey = new PixelFilter(base -> i -> j -> new RGBPixel(base.getPixel(i, j).getLuma(),
            base.getPixel(i, j).getLuma(), base.getPixel(i, j).getLuma(),
            base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(lumaGrey));
  }

  @Override
  public void intensityGreyscale(String image, String result) {
    Image baseImage = getImage(image);
    Filter intensityGrey = new PixelFilter(base -> i -> j ->
            new RGBPixel(base.getPixel(i, j).getIntensity(), base.getPixel(i, j).getIntensity(),
                    base.getPixel(i, j).getIntensity(), base.getPixel(i, j).getMax()));
    images.put(result, baseImage.applyFilter(intensityGrey));
  }

  @Override
  public void rgbSplit(String image, String redResult, String greenResult, String blueResult) {
    this.redGreyscale(image, redResult);
    this.greenGreyscale(image, greenResult);
    this.blueGreyscale(image, blueResult);
  }

  @Override
  public void rgbCombine(String redImage, String greenImage, String blueImage, String resultImage) {
    Image red = getImage(redImage);
    Image green = getImage(greenImage);
    Image blue = getImage(blueImage);
    if (red.getHeight() == green.getHeight() && blue.getHeight() == red.getHeight()
            && red.getWidth() == blue.getWidth() && red.getWidth() == green.getWidth()) {
      // Max value image
      int maxValue = Math.max(red.getMax(), Math.max(green.getMax(), blue.getMax()));
      Image combinedImage = new RGBImage(red.getHeight(), red.getWidth(), maxValue);
      for (int i = 0; i < combinedImage.getHeight(); i++) {
        for (int j = 0; j < combinedImage.getWidth(); j++) {
          combinedImage.setPixel(i, j, new RGBPixel(red.getPixel(i, j).getRed(),
                  green.getPixel(i, j).getGreen(), blue.getPixel(i, j).getBlue(), maxValue));
        }
      }
      images.put(resultImage, combinedImage);
    } else {
      throw new IllegalArgumentException("To combine, images should be of same dimensions");
    }
  }
}
