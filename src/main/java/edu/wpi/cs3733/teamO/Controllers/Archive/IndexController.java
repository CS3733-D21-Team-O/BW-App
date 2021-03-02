package edu.wpi.cs3733.teamO.Controllers.Archive;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Opp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

// TODO: make all these private
public class IndexController implements Initializable {
  @FXML private MenuItem edgeEditorButton;
  @FXML private MenuItem nodeEditorButton;
  @FXML private JFXButton securityButton;
  @FXML private MenuItem maintenanceButton;
  @FXML private JFXButton exitButton;
  @FXML private JFXButton pathfindingButton;
  @FXML private MenuItem loc1Button;
  @FXML private MenuItem loc2Button;
  @FXML private MenuItem loc3Button;
  @FXML private MenuItem dest1Button;
  @FXML private MenuItem dest2Button;
  @FXML private MenuItem dest3Button;
  @FXML private MenuButton menu;
  @FXML private Label label;
  @FXML private JFXButton editButton;
  @FXML private StackPane stackPane;
  @FXML private AnchorPane bigAnchor;
  @FXML private JFXButton locButton;
  @FXML private JFXButton destButton;
  @FXML private JFXButton resetButton;
  @FXML private StackPane sharePane;

  public ImageView mapimage;
  public Canvas mapcanvas;
  public JFXButton saveBtn;
  public AnchorPane mapanchor;

  public static final Image bwLogo =
      new Image("Brigham_and_Womens_Hospital_logo.png", 116, 100, true, true);

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //////////////////////////////////// SCALING//////////////////////////////////

    double scale = 1.75;
    mapimage.setScaleX(scale);
    mapimage.setScaleY(scale);
    mapcanvas.setScaleX(scale);
    mapcanvas.setScaleY(scale);
    mapimage.setTranslateX(270);
    mapcanvas.setTranslateX(270);
    mapimage.setTranslateY(225);
    mapcanvas.setTranslateY(225);
    mapcanvas.toFront();

    /////////////////////////////////////////////////////////////////////

    // draws circles on canvas
    customizeButtons();
    sharePane.toFront();

    // Sam has been using the following line for testing, if it is not commented, it is his fault,
    // blame him for all errors in code from now on
    // PopupMaker.incompletePopup(stackPane);

    System.out.println("Initalized");
  }

  // Customize buttons
  private void customizeButtons() {
    // #b4d1ed is pretty nice too
    editButton.setStyle("-fx-background-color: #c3d6e8");
    securityButton.setStyle("-fx-background-color: #c3d6e8");
    exitButton.setStyle("-fx-background-color: #c3d6e8");
    locButton.setStyle("-fx-background-color: #c3d6e8");
    destButton.setStyle("-fx-background-color: #c3d6e8");
    resetButton.setStyle("-fx-background-color: #c3d6e8");
    pathfindingButton.setStyle("-fx-background-color: #c3d6e8");
    saveBtn.setStyle("-fx-background-color: #ffffff");
  }

  public void pathfindingPress(ActionEvent actionEvent) {
    // TODO: implement this

  }

  private static final double arrowLength = 6;
  private static final double arrowWidth = 4;
  private static final double minArrowDistSq = 108;
  // ^ do the dist you wanted squared (probably want 3*(arrowLength^2))

  // draws an arrow from a to b, with the arrowhead halfway between them

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToSecurityRequest(ActionEvent actionEvent) throws IOException {
    // addNodePopup has the content of the popup
    // addNodeDialog creates the dialog popup
    JFXDialogLayout addNodePopup = new JFXDialogLayout();
    addNodePopup.setHeading(new Text("Security Form"));
    VBox addSecVBox = new VBox(12);

    // Creating contents of form
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton clearButton = new JFXButton("Clear");
    JFXButton submitButton = new JFXButton("Submit");
    buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

    JFXTextField locationText = new JFXTextField();
    locationText.setPromptText("Where is security needed?");
    JFXTextField threatText = new JFXTextField();
    threatText.setPromptText("What type of threat?");
    JFXTextArea additionalInfo = new JFXTextArea();
    additionalInfo.setPromptText("Additional Important Information");
    HBox sliderBox = new HBox(20);
    Label sliderLabel = new Label("Urgency Level:");
    JFXSlider slider = new JFXSlider();
    slider.setMin(1);
    slider.setValue(1);
    slider.setMax(5);
    slider.setMajorTickUnit(1);
    slider.setMinorTickCount(0);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setSnapToTicks(true);
    slider.setBlockIncrement(1);
    sliderBox.getChildren().addAll(sliderLabel, slider);
    HBox checkboxBox = new HBox(20);
    Label checkLabel = new Label("Armed Security?");
    JFXCheckBox checkbox = new JFXCheckBox();
    checkboxBox.getChildren().addAll(checkLabel, checkbox);

    // Creating the form with a VBox
    addSecVBox
        .getChildren()
        .addAll(locationText, threatText, additionalInfo, sliderBox, checkboxBox, buttonBox);
    addNodePopup.setBody(addSecVBox);

    // Bringing the popup screen to the front and disabling the background
    stackPane.toFront();
    JFXDialog addNodeDialog =
        new JFXDialog(stackPane, addNodePopup, JFXDialog.DialogTransition.BOTTOM);
    addNodeDialog.setOverlayClose(false);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          addNodeDialog.close();
          stackPane.toBack();
        });
    // Clearing the form, keeps the popup open
    clearButton.setOnAction(
        event -> {
          locationText.clear();
          threatText.clear();
          additionalInfo.clear();
          slider.setValue(1);
          checkbox.setSelected(false);
        });
    // Submits addition to the database
    submitButton.setOnAction(
        event -> {
          addNodeDialog.close();
          stackPane.toBack();
        });
    addNodeDialog.show();
  }

  public void goToEditNodes(ActionEvent actionEvent) throws IOException {
    // add the scene switch
    try {
      AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Archive/EditPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  // private ImageView m;

  public void save(ActionEvent actionEvent) throws IOException {
    sharePane.toBack();
    GraphicsContext gc = mapcanvas.getGraphicsContext2D();

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapimg.png");

    WritableImage map = mapanchor.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);
    Image newimg = map;

    // EmailPageController.setScreenShot(newimg);

    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EmailPage.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void canvasClick(MouseEvent mouseEvent) {
    double clickX = mouseEvent.getX();
    double clickY = mouseEvent.getY();
    System.out.println("CANVAS CLICKING");
    // TODO implement add
  }

  /**
   * test button to upload files to database using file explorer
   *
   * @param actionEvent
   */
  public void uploadPress(ActionEvent actionEvent) {
    AnchorPane root = null;
    try {
      root = FXMLLoader.load(getClass().getResource("/Views/uploadFile.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
