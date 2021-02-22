package edu.wpi.teamO.Controllers;

import edu.wpi.teamO.Controllers.model.Node;
import edu.wpi.teamO.GraphSystem.GraphSystem;
import edu.wpi.teamO.Opp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;

public class IndexController implements Initializable {
  public MenuItem edgeEditorButton;
  public MenuItem nodeEditorButton;
  public MenuItem securityButton;
  public MenuItem maintenanceButton;
  public Button exitButton;
  public Button pathfindingButton;
  public MenuItem loc1Button;
  public MenuItem loc2Button;
  public MenuItem loc3Button;
  public MenuItem dest1Button;
  public MenuItem dest2Button;
  public MenuItem dest3Button;
  public MenuButton menu;
  public Label label;

  // @FXML private Button edgeEditorButton; are these suposed to look like this or what they are
  // now?

  String loc = "-1";
  String dest = "-1";
  boolean selectingLoc = true;
  // Graph testGraph;
  // these variables show which of the three locations/destinations respectivly is currently being
  // tracked
  public ImageView mapimage;
  // the campus image is 2989 x 2457
  public Canvas mapcanvas;
  public Button saveBtn;
  public AnchorPane mapanchor;
  // private ArrayList<Circle> circleList;
  private ObservableList<Node> nodeList = FXCollections.observableArrayList();
  private Hashtable<String, Circle> stringCircleHashtable;
  private GraphicsContext gc;
  double cW = 10.0;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeList = FXCollections.observableArrayList();
    nodeList = DatabaseFunctionality.showNodes(nodeList);
    // circleList = new ArrayList<>();
    stringCircleHashtable = new Hashtable<>();

    gc = mapcanvas.getGraphicsContext2D();

    drawNodeCircles(/*nodeList*/ );
    System.out.println("Initalized");
  }

  public void drawNodeCircles(/*ObservableList<Node> nodeList*/ ) {
    // divide them by a scale factor (image is ~2937 pixels wide?) --
    // would be imageWidth/canvasWidth and imageHeight/canvasHeight
    double scaleX = 2989 / mapcanvas.getWidth();
    double scaleY = 2457 / mapcanvas.getHeight();

    // circle widths:
    // double cW = 10.0;
    // TODO: (x,y) should already adjust when scrolling, but probably should also change radius

    for (Node n : nodeList) {
      Circle circle = new Circle();

      double nodeX = Double.valueOf(n.getXCoord()) / scaleX;
      double nodeY = Double.valueOf(n.getYCoord()) / scaleY;

      circle.setCenterX(nodeX);
      circle.setCenterY(nodeY);
      circle.setRadius(cW / 2);
      stringCircleHashtable.put(n.getID(), circle);

      gc.setFill(Color.YELLOW); // default nodes are yellow
      gc.setGlobalAlpha(.75); // will make things drawn slightly transparent (if we want to)
      // DON'T DELETE -> JUST SET TO "1.0" IF NO TRANSPARENCY IS WANTED

      // sets color to blue/red if loc or dest are selected
      if (!loc.equals("-1") && n.getID().equals(loc)) {
        gc.setFill(Color.BLUE);
      }
      if (!dest.equals("-1") && n.getID().equals(dest)) {
        gc.setFill(Color.RED);
      }

      gc.fillOval(circle.getCenterX() - cW / 2, circle.getCenterY() - cW / 2, cW, cW);

      // sets alpha to 1.0 and draw a black border around circle
      gc.setGlobalAlpha(1.0);
      gc.strokeOval(circle.getCenterX() - cW / 2, circle.getCenterY() - cW / 2, cW, cW);
    }
  }

  public void pathfindingPress(ActionEvent actionEvent) {
    // make new graph system and get the path
    GraphSystem gs = new GraphSystem();
    LinkedList<String> pathSTRING = gs.findPath(loc, dest);

    // then clear canvas and draw given path
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    drawPath(pathSTRING);
  }

  // helper that actually draws the provided path
  public void drawPath(LinkedList<String> path) {
    // want to just draw start and end nodes, then draw lines (will be arrows eventually)
    // TODO: should probably make a drawCircle(), singular, helper at some point
    Circle locC = stringCircleHashtable.get(loc);
    Circle destC = stringCircleHashtable.get(dest);

    // draw start node and outline
    gc.setGlobalAlpha(0.75); // TODO: should make alpha a variable at some point
    gc.setFill(Color.BLUE);
    gc.fillOval(locC.getCenterX() - cW / 2, locC.getCenterY() - cW / 2, cW, cW);
    gc.setGlobalAlpha(1.0);
    gc.strokeOval(locC.getCenterX() - cW / 2, locC.getCenterY() - cW / 2, cW, cW);

    // draw dest node and outline
    gc.setGlobalAlpha(0.75);
    gc.setFill(Color.RED);
    gc.fillOval(destC.getCenterX() - cW / 2, destC.getCenterY() - cW / 2, cW, cW);
    gc.setGlobalAlpha(1.0);
    gc.strokeOval(destC.getCenterX() - cW / 2, destC.getCenterY() - cW / 2, cW, cW);

    gc.setGlobalAlpha(1.0); // makes the lines fully opaque
    for (int i = 0; i < path.size() - 1; i++) {
      Circle a = stringCircleHashtable.get(path.get(i));
      Circle b = stringCircleHashtable.get(path.get(i + 1));

      gc.strokeLine(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
    }
  }

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToSecurityRequest(ActionEvent actionEvent) throws IOException {
    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/SecurityForm.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void goToEditNodes(ActionEvent actionEvent) throws IOException {
    // add the scene switch
    try {
      AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EditPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void save(ActionEvent actionEvent) throws IOException {

    GraphicsContext gc = mapcanvas.getGraphicsContext2D();

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapImageThingy.png");

    WritableImage map = mapanchor.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);

    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EmailPage.fxml"));
    // errors TODO: fix
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void canvasClick(MouseEvent mouseEvent) {
    double clickX = mouseEvent.getX();
    double clickY = mouseEvent.getY();
    System.out.println("CANVAS CLICKING");
    String closestID = closestCircle(clickX, clickY);

    if (selectingLoc) {
      loc = closestID;
    } else {
      dest = closestID;
    }

    // clear canvas and redraw circles
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    drawNodeCircles();
  }

  // helper that return nodeID of closest node to click
  public String closestCircle(double x, double y) {
    double currentDist = 1000000000;
    String nodeID = "-1";

    for (Node n : nodeList) {
      Circle c = stringCircleHashtable.get(n.getID());
      double cX = c.getCenterX();
      double cY = c.getCenterY();

      double dist = Math.pow(Math.abs(x - cX), 2.0) + Math.pow(Math.abs(y - cY), 2.0);
      if (dist < currentDist) {
        currentDist = dist;
        nodeID = n.getID();
      }
    }

    // dummy return
    return nodeID;
  }

  public void locClick(ActionEvent actionEvent) {
    selectingLoc = true;
  }

  public void destClick(ActionEvent actionEvent) {
    selectingLoc = false;
  }

  public void resetClick(ActionEvent actionEvent) {
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    loc = "-1";
    dest = "-1";
    drawNodeCircles();
  }
}
