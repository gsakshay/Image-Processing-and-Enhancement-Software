package ime.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Image;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ime.control.Features;
import ime.control.Greyscale;
import ime.model.IViewModel;

/**
 * Implementation of the View.
 * This also takes in a ReadOnly model or view model to query the  data directly without
 * overloading the controller.
 */
public class View extends JFrame implements IView {
  // View Model
  private final IViewModel model;
  private final String redGreyscale = "Red Component";
  private final String greenGreyscale = "Green Component";
  private final String blueGreyscale = "Blue Component";
  private final String valueGreyscale = "Value Component";
  private final String intensityGreyscale = "Intensity Component";
  private final String lumaGreyscale = "Luma Component";
  private final String blur = "Blur";
  private final String sharpen = "Sharpen";

  // View elements
  private final JMenuItem openItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;
  private final JMenuItem aboutItem;
  private final JMenuItem feedbackItem;
  private final JMenuItem creditsItem;
  private final JButton hFlipButton;
  private final JButton vFlipButton;
  private final JButton brightenButton;
  private final FileNameExtensionFilter filter;
  private final JPanel imagePanel;
  private final JLabel imageLabel;
  private final JComboBox<String> filterTypes;
  private final JButton filterTypesButton;
  private final JButton greyscaleExecuteButton;
  private final JComboBox<String> greyscaleTypes;
  private final JButton sepiaButton;
  private final JButton rgbSplitButton;
  private final JButton rgbCombineButton;
  private final JButton ditherButton;
  private final JTextField brightnessValue;
  private final JPanel mainPanel;
  private final JPanel histogramPanel;
  private String selectedGreyscale;
  private String selectedFilter;

  /**
   * Constructs the view taking its name and the ViewModel.
   *
   * @param caption the JFrame caption.
   * @param model   the view model.
   */
  public View(String caption, IViewModel model) {
    super(caption);
    this.model = model;
    filter = new FileNameExtensionFilter("JPG, PNG, BMP & PPM Images", "jpg", "png", "bmp", "ppm");

    // View items
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    openItem = new JMenuItem("Open...");
    saveItem = new JMenuItem("Save As");
    exitItem = new JMenuItem("Exit");

    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.add(exitItem);

    JMenu helpMenu = new JMenu("Help");
    menuBar.add(helpMenu);

    aboutItem = new JMenuItem("About...");
    feedbackItem = new JMenuItem("Submit Feedback");
    creditsItem = new JMenuItem("Credits");

    helpMenu.add(aboutItem);
    helpMenu.add(feedbackItem);
    helpMenu.add(creditsItem);

    setJMenuBar(menuBar);

    // Create main panel with GridBagLayout
    mainPanel = new JPanel(new GridBagLayout());

    // Create mainScreen with GridBagLayout
    JPanel mainScreen = new JPanel(new GridBagLayout());

    // Add mainScreen to main panel with GridBagConstraints.
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new java.awt.Insets(5, 2, 5, 2);
    // mainPanel.add(titlePanel, c);

    c.gridx = 0;
    c.gridy = 1;
    c.weighty = 0.95;
    mainPanel.add(mainScreen, c);

    // Create leftScreen with GridBagLayout.
    JPanel leftScreen = new JPanel(new GridBagLayout());
    // leftScreen.setBorder(BorderFactory.createTitledBorder("Left Screen"));

    // Create rightScreen with GridBagLayout.
    JPanel rightScreen = new JPanel(new GridBagLayout());
    // rightScreen.setBorder(BorderFactory.createTitledBorder("Right Screen"));

    GridBagConstraints leftConstraints = new GridBagConstraints();
    leftConstraints.gridx = 0;
    leftConstraints.gridy = 0;
    leftConstraints.weightx = 0.8;
    leftConstraints.weighty = 1;
    leftConstraints.fill = GridBagConstraints.BOTH;
    // leftConstraints.insets = new java.awt.Insets(5, 2, 5, 2);

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image being worked on"));

    JPanel basicOperationsPanel = new JPanel();
    basicOperationsPanel.setBorder(BorderFactory.createTitledBorder("Basic Operations"));

    brightnessValue = new JTextField(3);
    brightenButton = new JButton("Adjust Brightness");
    hFlipButton = new JButton("Horizontal Flip");
    vFlipButton = new JButton("Vertical Flip");

    // add buttons to basicOperationsPanel
    basicOperationsPanel.add(brightnessValue);
    basicOperationsPanel.add(brightenButton);
    basicOperationsPanel.add(hFlipButton);
    basicOperationsPanel.add(vFlipButton);

    // Set the layout for the leftScreen JPanel
    leftScreen.setLayout(new BorderLayout());

    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    // Add the imagePanel to the center of the leftScreen
    leftScreen.add(scroller, BorderLayout.CENTER);
    // Main image

    imageLabel = new JLabel("Please load an image\n" + "File > Open…");
    imagePanel.add(imageLabel);

    // Add the basicOperationsPanel to the bottom of the leftScreen
    leftScreen.add(basicOperationsPanel, BorderLayout.SOUTH);

    mainScreen.add(leftScreen, leftConstraints);

    GridBagConstraints rightConstraints = new GridBagConstraints();
    rightConstraints.gridx = 1;
    rightConstraints.gridy = 0;
    rightConstraints.weightx = 0.2;
    rightConstraints.weighty = 1;
    rightConstraints.fill = GridBagConstraints.BOTH;

    rightScreen.setLayout(new BorderLayout());

    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Image Exposure - Histogram"));
    histogramPanel.setSize(600, 550);
    histogramPanel.setVisible(true);

    rightScreen.add(histogramPanel, BorderLayout.CENTER);

    JPanel advancedOperationsPanel = new JPanel();
    advancedOperationsPanel.setBorder(BorderFactory.createTitledBorder("Advanced Operations"));

    JPanel controlsPanel = new JPanel();
    controlsPanel.setLayout(new GridBagLayout());

    GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
    controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    controlsPanelConstraints.weightx = 1.0;
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 0;
    controlsPanelConstraints.insets = new Insets(5, 5, 5, 5);

    greyscaleTypes = new JComboBox<>(new String[]{"==Select==",
        redGreyscale, greenGreyscale, blueGreyscale,
        valueGreyscale, intensityGreyscale, lumaGreyscale});
    greyscaleTypes.setToolTipText("Select Greyscale Type");
    JLabel greyscaleTypesLabel = new JLabel("Greyscale Type:");
    controlsPanel.add(greyscaleTypesLabel, controlsPanelConstraints);

    // Add action listener to track the change
    greyscaleTypes.addActionListener(e -> this.selectedGreyscale =
            (String) greyscaleTypes.getSelectedItem());

    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 0;
    controlsPanel.add(greyscaleTypes, controlsPanelConstraints);

    greyscaleExecuteButton = new JButton("Execute Greyscale Type");
    greyscaleExecuteButton.setToolTipText("Execute the selected greyscale type operation");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 0;
    controlsPanel.add(greyscaleExecuteButton, controlsPanelConstraints);

    filterTypes = new JComboBox<>(new String[]{"==Select==", blur, sharpen});
    filterTypes.setToolTipText("Select Color Transformation Type");
    JLabel filterTypesLabel = new JLabel("Filter:");
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 1;
    controlsPanel.add(filterTypesLabel, controlsPanelConstraints);

    // Add action listener to track the change
    filterTypes.addActionListener(e -> this.selectedFilter =
            (String) filterTypes.getSelectedItem());

    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 1;
    controlsPanel.add(filterTypes, controlsPanelConstraints);

    filterTypesButton = new JButton("Execute Filter");
    filterTypesButton.setToolTipText("Execute the selected color transform type operation");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 1;
    controlsPanel.add(filterTypesButton, controlsPanelConstraints);

    sepiaButton = new JButton("Sepia");
    sepiaButton.setToolTipText("Apply Sepia Color Transform");
    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 2;
    controlsPanelConstraints.gridwidth = 1;
    controlsPanel.add(sepiaButton, controlsPanelConstraints);

    ditherButton = new JButton("Dither");
    ditherButton.setToolTipText("Dither the Image");
    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 3;
    controlsPanelConstraints.gridwidth = 1;
    controlsPanel.add(ditherButton, controlsPanelConstraints);

    rgbSplitButton = new JButton("RGB Split");
    rgbSplitButton.setToolTipText("Split the RGB Channels");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 2;
    controlsPanelConstraints.gridwidth = 1;
    controlsPanel.add(rgbSplitButton, controlsPanelConstraints);

    rgbCombineButton = new JButton("RGB Combine");
    rgbCombineButton.setToolTipText("Combine the RGB Channels");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 3;
    controlsPanelConstraints.gridwidth = 1;
    controlsPanel.add(rgbCombineButton, controlsPanelConstraints);

    // Disable all the operative buttons.
    brightenButton.setEnabled(false);
    brightnessValue.setEnabled(false);
    hFlipButton.setEnabled(false);
    vFlipButton.setEnabled(false);
    greyscaleTypes.setEnabled(false);
    greyscaleExecuteButton.setEnabled(false);
    filterTypes.setEnabled(false);
    filterTypesButton.setEnabled(false);
    sepiaButton.setEnabled(false);
    rgbSplitButton.setEnabled(false);
    ditherButton.setEnabled(false);

    // Cannot save an unloaded image
    saveItem.setEnabled(false);

    // Add the controls operations panel to the right screen panel
    advancedOperationsPanel.add(controlsPanel, BorderLayout.CENTER);

    // Add the advanced operations panel to the right screen panel
    rightScreen.add(advancedOperationsPanel, BorderLayout.SOUTH);

    mainScreen.add(rightScreen, rightConstraints);

    // Add main panel to frame
    add(mainPanel);

    // Set frame properties
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 500));
    pack();
    setVisible(true);
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(mainPanel, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Communicates an informative message on View's user interface.
   *
   * @param parentFrame   frame on which the Pane shows up
   * @param informMessage The informative message to be displayed.
   */
  private void displayInformativeMessage(Component parentFrame, String informMessage) {
    JOptionPane.showMessageDialog(parentFrame, informMessage, "Information",
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Generates a 2D array of doubles from a list of integers.
   * The first row of the array contains indices starting from 1 to the size of the input data list,
   * and the second row contains the double values converted from the input data list.
   * Helps in plotting the graph.
   *
   * @param data The list of integers to be converted to a 2D array of doubles.
   * @return The generated 2D array of doubles.
   */
  private double[][] generateDataArray(List<Integer> data) {
    double[][] dataArray = new double[2][256];
    dataArray[0] = IntStream.rangeClosed(1, data.size()).asDoubleStream().toArray();
    dataArray[1] = data.stream().mapToDouble(Integer::doubleValue).toArray();
    return dataArray;
  }


  @Override
  public void refreshScreen(String imageName) {
    try {
      model.processImage(imageName);
      Image bufferedImage = model.presentImage();
      // Flush the current contents of label
      imageLabel.setText("");
      // Now use this image as the main image data.
      imageLabel.setIcon(new ImageIcon(bufferedImage));

      // Using the same source of data, update the histogram
      List<Integer> redData = model.getRedData();
      List<Integer> greenData = model.getGreenData();
      List<Integer> blueData = model.getBlueData();
      List<Integer> intensityData = model.getIntensityData();

      // Format the data for the Graph
      double[][] red = generateDataArray(redData);
      double[][] green = generateDataArray(greenData);
      double[][] blue = generateDataArray(blueData);
      double[][] intensity = generateDataArray(intensityData);

      // Create a combined dataset
      DefaultXYDataset combinedDataset = new DefaultXYDataset();
      combinedDataset.addSeries("Red", red);
      combinedDataset.addSeries("Green", green);
      combinedDataset.addSeries("Blue", blue);
      combinedDataset.addSeries("Intensity", intensity);

      Color[] legendColors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};

      // Create a line chart
      JFreeChart chart = ChartFactory.createXYLineChart("Live Histogram", // Chart title
              "Pixels", // X-axis label
              "Frequency", // Y-axis label
              combinedDataset, // Dataset
              PlotOrientation.VERTICAL, // Plot orientation
              true, // Show legend
              true, // Use tooltips
              false // Configure chart to generate URLs
      );

      XYPlot plot = (XYPlot) chart.getPlot();
      for (int i = 0; i < legendColors.length; i++) {
        plot.getRenderer().setSeriesPaint(i, legendColors[i]);
      }

      // Create a chart panel to hold the chart
      ChartPanel histogramChartPanel = new ChartPanel(chart);
      histogramChartPanel.setPreferredSize(new Dimension(600, 550));

      // Clear the existing chart panel and add the new chart panel
      this.histogramPanel.removeAll();
      this.histogramPanel.add(histogramChartPanel);
      this.histogramPanel.revalidate();
      this.histogramPanel.repaint();

    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Could not display the image. Please provide a valid file.");
    }
  }

  /**
   * Action to be performed when tried to load an image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void loadImage(Features features) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      // Now send this file to controller and perform the required load operation.
      try {
        features.load(f.getPath());
        imagePanel.setBorder(BorderFactory.createTitledBorder(f.getPath()));

        // Enable all the operative buttons
        brightenButton.setEnabled(true);
        brightnessValue.setEnabled(true);
        hFlipButton.setEnabled(true);
        vFlipButton.setEnabled(true);
        greyscaleTypes.setEnabled(true);
        greyscaleExecuteButton.setEnabled(true);
        filterTypes.setEnabled(true);
        filterTypesButton.setEnabled(true);
        sepiaButton.setEnabled(true);
        rgbCombineButton.setEnabled(true);
        rgbSplitButton.setEnabled(true);
        ditherButton.setEnabled(true);

        // Image can be saved after loaded
        saveItem.setEnabled(true);
      } catch (IOException | InputMismatchException e) {
        displayErrorMessage("Please provide a valid file and try again");
      }
    }
  }

  /**
   * A helper method to get path of image to be saved.
   *
   * @param defaultFilename if there is any default name to be provided for the file to be saved
   * @return the filepath
   */
  private String showFileChooserSaveAndGetPath(String defaultFilename) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(filter);
    // Set the default file name
    File defaultFile = new File(defaultFilename);
    fileChooser.setSelectedFile(defaultFile);
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  /**
   * A helper method to get path of image to be loaded.
   *
   * @param defaultFilename if there is any default name to be provided for the file to be saved
   * @return the filepath
   */
  private String showFileChooserLoadAndGetPath(String defaultFilename) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(filter);
    // Set the default file name
    File defaultFile = new File(defaultFilename);
    fileChooser.setSelectedFile(defaultFile);
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  /**
   * Action to be performed when tried to save an image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void saveImage(Features features) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      // Make sure that the extension given is one of the supported formats.
      // Now call the constructor to save this image.
      try {
        String path = f.getAbsolutePath();
        // Make sure that it is a supported format.
        String[] parts = path.split("\\.");
        String format = parts[parts.length - 1];
        List<String> validExtensions = Arrays.asList("png", "jpg", "ppm", "bmp");
        if (validExtensions.contains(format)) {
          features.save(path);
        } else {
          displayErrorMessage("Please provide a valid file path");
        }
        displayInformativeMessage(mainPanel, "Successfully saved the image.");
      } catch (IOException | InputMismatchException e) {
        displayErrorMessage("Could not save the image. Please provide a valid path and try "
                + "again");
      }
    }
  }

  /**
   * Navigates to the page provided.
   *
   * @param url the page to be navigated to
   */
  private void travelToPage(String url) {
    try {
      Desktop.getDesktop().browse(new URI(url));
    } catch (IOException | URISyntaxException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Action to be performed when tried to apply Sepia color transform.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void sepia(Features features) {
    try {
      features.sepia();
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Could not perform the operation. "
              + "Please try again after loading a valid image");
    }
  }

  /**
   * Action to be performed when tried to apply dither on am image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void dither(Features features) {
    try {
      features.dither();
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Could not perform the operation. "
              + "Please try again after loading a valid image");
    }
  }

  /**
   * Action to be performed when tried to apply greyscale to an image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void greyscale(Features features) {
    try {
      Greyscale selected;
      switch (selectedGreyscale) {
        case redGreyscale:
          selected = Greyscale.Red;
          break;
        case greenGreyscale:
          selected = Greyscale.Green;
          break;
        case blueGreyscale:
          selected = Greyscale.Blue;
          break;
        case valueGreyscale:
          selected = Greyscale.Value;
          break;
        case intensityGreyscale:
          selected = Greyscale.Intensity;
          break;
        case lumaGreyscale:
          selected = Greyscale.Luma;
          break;
        default:
          throw new InputMismatchException();
      }
      features.greyscale(selected);
      greyscaleTypes.setSelectedItem("==Select==");
    } catch (IOException | InputMismatchException | NullPointerException e) {
      displayErrorMessage("Please select a valid greyscale method");
      greyscaleTypes.setSelectedItem("==Select==");
    }
  }

  /**
   * Action to be performed when tried to apply a filter on the image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void filter(Features features) {
    try {
      switch (selectedFilter) {
        case blur:
          features.blur();
          break;
        case sharpen:
          features.sharpen();
          break;
        default:
          throw new InputMismatchException();
      }
      filterTypes.setSelectedItem("==Select==");
    } catch (IOException | InputMismatchException | NullPointerException e) {
      displayErrorMessage("Please select a valid filter method");
      filterTypes.setSelectedItem("==Select==");
    }
  }

  /**
   * Action to be performed when tried to flip an image.
   *
   * @param features    are the operations supported by the program and served by the controller
   * @param orientation the orientation of flip - 0 for horizontal and 1 for vertical.
   */
  private void flip(Features features, int orientation) {
    try {
      features.flip(orientation);
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Could not perform the operation. "
              + "Please try again after loading a valid image");
    }
  }

  /**
   * Action to be performed when tried to brighten an image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void brighten(Features features) {
    try {
      int value = Integer.parseInt(brightnessValue.getText());
      features.brighten(value);
      brightnessValue.setText("");
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Could not perform the operation. "
              + "Please try again after loading a valid image");
      brightnessValue.setText("");
    } catch (NumberFormatException e) {
      displayErrorMessage("Please provide a valid value for brightening the image. "
              + "It should be a numeric value");
      brightnessValue.setText("");
    }
  }

  /**
   * Action to be performed when tried to combine split images.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void rgbCombine(Features features) {
    // Inform the user that this operation would require him to select places where he wants to
    // save the images after split
    String message = "This operation would want you to select 3 images, ideally red, green and "
            + "blue greyscale images respectively that you want to combine.";
    JOptionPane.showMessageDialog(mainPanel, message, "RGB Combine",
            JOptionPane.INFORMATION_MESSAGE);

    String filepathRed;
    String filepathGreen = "";
    String filepathBlue = "";

    filepathRed = showFileChooserLoadAndGetPath("red-split.png");
    if (filepathRed.length() > 0) {
      filepathGreen = showFileChooserLoadAndGetPath("green-split.png");
      if (filepathGreen.length() > 0) {
        filepathBlue = showFileChooserLoadAndGetPath("blue-split.png");
      }
    }

    // After receiving all the required paths, we will call rgbCombine from the features
    if (!filepathRed.isEmpty() && !filepathGreen.isEmpty() && !filepathBlue.isEmpty()) {
      try {
        features.rgbCombine(filepathRed, filepathGreen, filepathBlue);
      } catch (IOException | InputMismatchException e) {
        displayErrorMessage("Could not perform the operation. Please try again with valid "
                + "filepath");
      }
    } else {
      displayErrorMessage("Could not perform the operation. Please try again with valid "
              + "filepath");
    }
  }

  /**
   * Action to be performed when tried to split an image.
   *
   * @param features are the operations supported by the program and served by the controller
   */
  private void rgbSplit(Features features) {
    // Inform the user that this operation would require him to select places where he wants to
    // save the images after split
    String message = "This operation would require you to select 3 locations for the splits to "
            + "be saved, for red, green and blue splits respectively.";
    JOptionPane.showMessageDialog(mainPanel, message, "RGB Split",
            JOptionPane.INFORMATION_MESSAGE);

    String filepathRed;
    String filepathGreen = "";
    String filepathBlue = "";

    filepathRed = showFileChooserSaveAndGetPath("red-split.png");
    if (filepathRed.length() > 0) {
      filepathGreen = showFileChooserSaveAndGetPath("green-split.png");
      if (filepathGreen.length() > 0) {
        filepathBlue = showFileChooserSaveAndGetPath("blue-split.png");
      }
    }

    // After receiving all the required paths, we will call rgbSplit from the features
    if (!filepathRed.isEmpty() && !filepathGreen.isEmpty() && !filepathBlue.isEmpty()) {
      try {
        features.rgbSplit(filepathRed, filepathGreen, filepathBlue);
      } catch (IOException e) {
        displayErrorMessage("Could not perform the operation. Please try again with valid "
                + "filepath");
      } catch (InputMismatchException e) {
        displayErrorMessage("Please perform this operation on a valid image");
      }
    } else {
      displayErrorMessage("Could not perform the operation. Please try again with valid "
              + "filepath");
    }
  }

  @Override
  public void addFeatures(Features features) {
    rgbCombineButton.addActionListener(evt -> rgbCombine(features));

    rgbSplitButton.addActionListener(evt -> rgbSplit(features));

    exitItem.addActionListener(evt -> features.exitProgram());

    aboutItem.addActionListener(evt -> travelToPage("https://northeastern.instructure.com/courses/143020/assignments/1760193"));

    feedbackItem.addActionListener(evt -> travelToPage("https://handins.ccs.neu.edu/courses/186/assignments/4228"));

    creditsItem.addActionListener(evt -> displayInformativeMessage(mainPanel,
            "Work of Akshay " + "and Yug"));

    openItem.addActionListener(evt -> loadImage(features));

    saveItem.addActionListener(evt -> saveImage(features));

    sepiaButton.addActionListener(evt -> sepia(features));

    ditherButton.addActionListener(evt -> dither(features));

    greyscaleExecuteButton.addActionListener(evt -> greyscale(features));

    filterTypesButton.addActionListener(evt -> filter(features));

    hFlipButton.addActionListener(evt -> flip(features, 0));

    vFlipButton.addActionListener(evt -> flip(features, 1));

    brightenButton.addActionListener(e -> brighten(features));
  }

}
