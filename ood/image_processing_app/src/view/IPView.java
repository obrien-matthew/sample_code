package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import controller.Filter;
import controller.ImageController;

/**
 * A class representing the view of the program - a GUI interface that displays an image and
 * receives user input regarding image editing commands.  Responds to user events by calling methods
 * from the controller's Features list.  Supports interactive edits as well as creating and running
 * scripts.
 */
public class IPView extends JFrame implements IIPView {
  //==================== Main Stuff====================//
  /**
   * TODO: comments
   */
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  //==================== Menu Stuff====================//
  private JMenuBar menuBar;
  private JMenu fileMenu, editMenu, generateMenu;
  private JMenuItem MBlur, MSharpen, MGreyscale, MSepia, MDither, MMosaic, MRainbow, MCheckerboard,
          MSave, MLoad, MExecute, MUndo, MRedo;

  //=================== Script Stuff ===================//
  private JPanel scriptPanel;
  private JTextArea scriptBox;
  private JScrollPane scriptScrollPane;
  private JButton executeButton;

  //==================== Image Stuff ====================//
  private JLabel image;
  private JScrollPane imageScrollPane;

  //=================== Button Stuff ===================//
  private JPanel buttonPanel;
  private JButton exitButton, openButton, saveButton;

  /**
   * Constructs the view's GUI interface and displays it in a window with the provided program name.
   * Will not be visible until display method is called.
   *
   * @param programName the name of the program.
   */
  public IPView(String programName) {
    super(programName);

    //================== Main Window ==================//
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocation(200, 200);
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(10, 10));
    mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    //==================== Menu Bar ====================//
    menuBar = new JMenuBar();
    mainPanel.add(menuBar, BorderLayout.PAGE_START);

    //=================== File Menu ===================//
    fileMenu = new JMenu("File");
    MSave = new JMenuItem("Save");
    MLoad = new JMenuItem("Load");
    generateMenu = new JMenu("Generate");
    MRainbow = new JMenuItem("Rainbow");
    MCheckerboard = new JMenuItem("Checkerboard");
    generateMenu.add(MRainbow);
    generateMenu.add(MCheckerboard);
    fileMenu.add(MSave);
    fileMenu.add(MLoad);
    fileMenu.addSeparator();
    fileMenu.add(generateMenu);
    menuBar.add(fileMenu);

    //=================== Edit Menu ===================//
    editMenu = new JMenu("Edit Image");
    MBlur = new JMenuItem("Blur");
    MSharpen = new JMenuItem("Sharpen");
    MGreyscale = new JMenuItem("Greyscale");
    MSepia = new JMenuItem("Sepia");
    MDither = new JMenuItem("Dither");
    MMosaic = new JMenuItem("Mosaic...");
    MExecute = new JMenuItem("Execute Script");
    MUndo = new JMenuItem("Undo");
    MRedo = new JMenuItem("Redo");
    editMenu.add(MUndo);
    editMenu.add(MRedo);
    editMenu.addSeparator();
    editMenu.add(MBlur);
    editMenu.add(MSharpen);
    editMenu.add(MGreyscale);
    editMenu.add(MSepia);
    editMenu.add(MDither);
    editMenu.add(MMosaic);
    editMenu.addSeparator();
    editMenu.add(MExecute);
    menuBar.add(editMenu);


    //================== Script Field ==================//
    scriptPanel = new JPanel();
    scriptPanel.setLayout(new BoxLayout(scriptPanel, BoxLayout.PAGE_AXIS));
    scriptBox = new JTextArea(10, 20);
    scriptBox.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(5, 5, 5, 5),
            "Script Editor", TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
    scriptScrollPane = new JScrollPane(scriptBox);
    executeButton = new JButton("Execute Script");
    executeButton.setAlignmentX(CENTER_ALIGNMENT);
    scriptPanel.add(scriptScrollPane);
    scriptPanel.add(executeButton);
    mainPanel.add(scriptPanel, BorderLayout.LINE_START);

    //================== Image Display ==================//
    image = new JLabel("Load an image to see it displayed here.", JLabel.CENTER);
    imageScrollPane = new JScrollPane(image);
    imageScrollPane.setPreferredSize(new Dimension(600, 400));
    mainPanel.add(imageScrollPane, BorderLayout.CENTER);

    //===================== Buttons =====================//
    exitButton = new JButton("Exit");
    openButton = new JButton("Open Image");
    saveButton = new JButton("Save Image");
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
    buttonPanel.add(openButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(exitButton);
    mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

    pack();
  }

  /**
   * Display the view. To be called after the the view is fully constructed.
   */
  @Override
  public void display() {
    setVisible(true);
  }

  /**
   * Get the set of feature callbacks that the view can call from the model.
   *
   * @param f the set of feature callbacks as a Features object.
   */
  @Override
  public void setFeatures(Features f) {
    exitButton.addActionListener(l -> f.exitProgram());
    openButton.addActionListener(l -> f.openFileFromGUI());
    saveButton.addActionListener(l -> f.saveFileFromGUI());
    executeButton.addActionListener(l -> f.executeScript(scriptBox.getText()));

    MLoad.addActionListener(l -> f.openFileFromGUI());
    MSave.addActionListener(l -> f.saveFileFromGUI());
    MBlur.addActionListener(l -> f.filter(Filter.BLUR, null));
    MSharpen.addActionListener(l -> f.filter(Filter.SHARPEN, null));
    MDither.addActionListener(l -> f.filter(Filter.DITHER, null));
    MGreyscale.addActionListener(l -> f.filter(Filter.GREYSCALE, null));
    MSepia.addActionListener(l -> f.filter(Filter.SEPIA, null));
    MMosaic.addActionListener(l -> f.filter(Filter.MOSAIC, new ArrayList<>()));
    MRainbow.addActionListener(l -> f.generateRainbow());
    MCheckerboard.addActionListener(l -> f.generateCheckerboard());
    MExecute.addActionListener(l -> f.executeScript(scriptBox.getText()));
    MUndo.addActionListener(l -> f.undoOp());
    MRedo.addActionListener(l -> f.redoOp());

  }

  /**
   * Adds the provided buffered image to the view's display, replacing any existing image.
   */
  @Override
  public void addImage(BufferedImage image) {
    this.image.setText("");
    this.image.setIcon(new ImageIcon(image));
  }

  /**
   * Opens a dialog box that allows a user to select an image file for opening. The file must be one
   * of the following types: "png", "jpg", "jpeg", "bmp", "wbmp", "gif". Returns a File object
   * referencing the selected file, or null if the dialog box is closed.
   *
   * @return a file object referencing the selected file, or null if the dialog box is closed.
   */
  @Override
  public File getFileToOpen() {
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter supportedImages = new FileNameExtensionFilter(
            "Supported Images", ImageController.VALID_FILE_EXTENSIONS);
    fileChooser.setFileFilter(supportedImages);
    fileChooser.setAcceptAllFileFilterUsed(false);
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    } else {
      return null;
    }
  }

  /**
   * Opens a dialog box that allows a user to select or name an image file for opening.  Returns a
   * File object referencing the chosen file name, or null if the dialog box is closed.  Does not
   * check if the named file has a valid image file extension - this must be tested separately.
   *
   * @return a File object referencing the chosen file name, or null if the dialog box is closed.
   */
  @Override
  public File getFileToSaveAs() {
    JFileChooser fileChooser = new JFileChooser(".");
    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    } else {
      return null;
    }
  }

  /**
   * Display the provided error message as a popup dialog box.
   *
   * @param errorMessage the error message to be displayed.
   */
  @Override
  public void displayError(String errorMessage) {
    JOptionPane.showMessageDialog(
            this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Requests user input via a popup dialog box - user is expected to provide a seed number for a
   * mosaic editing operation.  Returns null if user exits the dialog box without entering
   * information.
   *
   * @return the user's seed input, as a string; null if user exits the dialog box.
   */
  @Override
  public String getSeedPixelNum() {
    return getSingleNumberInput("Number of Seed Pixels: ", "Mosaic Specifications");
  }

  /**
   * Requests user input via a popup dialog box - user is expected to provide orientation, height,
   * and width values for a rainbow generation operation.  Returns null if user exits the dialog box
   * without entering information.
   *
   * @return the user's square size input, as a string; null if user exits the dialog box.
   */
  @Override
  public String[] getRainbowSpecs() {
    // setup pane
    JPanel specPanel = new JPanel();
    JPanel wPanel = new JPanel();
    JPanel hPanel = new JPanel();
    JPanel oPanel = new JPanel();
    specPanel.setLayout(new BoxLayout(specPanel, BoxLayout.PAGE_AXIS));
    wPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 5));
    hPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 5));
    oPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 5));

    JRadioButton horizontal = new JRadioButton("Horizontal");
    JRadioButton vertical = new JRadioButton("Vertical");
    JTextField width = new JTextField(8);
    JTextField height = new JTextField(8);
    JLabel w = new JLabel("Image Width: ");
    JLabel h = new JLabel("Image Height: ");

    wPanel.add(w);
    wPanel.add(width);
    hPanel.add(h);
    hPanel.add(height);
    oPanel.add(horizontal);
    oPanel.add(vertical);
    specPanel.add(wPanel);
    specPanel.add(hPanel);
    specPanel.add(oPanel);

    horizontal.doClick();

    int ok = JOptionPane.showConfirmDialog(this, specPanel,
            "Enter Rainbow Specifications", JOptionPane.OK_CANCEL_OPTION);

    // check input and return
    if (ok == JOptionPane.OK_OPTION) {
      String orientation;
      if (horizontal.isSelected()) {
        orientation = "horizontal";
      } else {
        orientation = "vertical";
      }
      return new String[]{orientation, width.getText(), height.getText()};
    } else {
      return null;
    }

  }

  /**
   * Requests user input via a popup dialog box - user is expected to provide a square size value
   * for a checkerboard generation operation.  Returns null if user exits the dialog box without
   * entering information.
   *
   * @return the user's square size input, as a string; null if user exits the dialog box.
   */
  @Override
  public String getCheckerboardSpecs() {
    return getSingleNumberInput(
            "Checkerboard Square Size: ", "Checkerboard Specifications");
  }

  /**
   * Clears out any text in the script editor box.
   */
  @Override
  public void clearScriptBox() {
    this.scriptBox.setText("");
  }

  /**
   * Asks the user if they are sure they want to overwrite the current image with the load
   * or generation of another.
   *
   * @return true or false, accordingly
   */
  @Override
  public boolean confirmOverwrite() {
    int status = JOptionPane.showConfirmDialog(this, "Doing this will"
            + " overwrite your currently displayed image. Do you wish to proceed?", "Confirm "
            + "Overwrite", JOptionPane.YES_NO_OPTION);
    if (status == JOptionPane.YES_OPTION) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Requests user input via a popup dialog box, using the specified message and title for the box
   * display.  User is typically expected to input a number, but a string is returned so that the
   * value can be evaluated for exceptions outside of the view. Returns null if user exits the
   * dialog box without entering information.
   *
   * @return the user's input as a string; null if user exits the dialog box.
   */
  private String getSingleNumberInput(String message, String title) {
    JPanel specPanel = new JPanel();
    specPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 5));

    JTextField inputField = new JTextField(8);
    JLabel label = new JLabel(message);

    specPanel.add(inputField);
    specPanel.add(label);

    int ok = JOptionPane.showConfirmDialog(
            this, specPanel, title, JOptionPane.OK_CANCEL_OPTION);

    // check input and return
    if (ok == JOptionPane.OK_OPTION) {
      return inputField.getText();
    } else {
      return null;
    }
  }
}
