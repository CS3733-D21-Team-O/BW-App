package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class SideMenuController implements Initializable {
  @FXML private JFXButton logout;
  @FXML private JFXButton notificationBtn;
  @FXML private JFXButton checkInBtn;
  @FXML private JFXButton appointmentsBtn;
  @FXML private JFXButton settingBtn;
  @FXML private JFXButton exitBtn;

  public SideMenuController() {}

  public void goToMainMenu(MouseEvent mouseEvent) {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void toNotifications(ActionEvent actionEvent) {}

  public void toCheckIn(ActionEvent actionEvent) {}

  public void toAppointments(ActionEvent actionEvent) {}

  public void toSettings(ActionEvent actionEvent) {}

  public void goToLogin(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void mouseOnNot(MouseEvent mouseEvent) {
    notificationBtn.setUnderline(true);
    notificationBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffNot(MouseEvent mouseEvent) {
    notificationBtn.setUnderline(false);
    notificationBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnCheck(MouseEvent mouseEvent) {
    checkInBtn.setUnderline(true);
    checkInBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffCheck(MouseEvent mouseEvent) {
    checkInBtn.setUnderline(false);
    checkInBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnApp(MouseEvent mouseEvent) {
    appointmentsBtn.setUnderline(true);
    appointmentsBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffApp(MouseEvent mouseEvent) {
    appointmentsBtn.setUnderline(false);
    appointmentsBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnLog(MouseEvent mouseEvent) {
    logout.setUnderline(true);
    logout.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffLog(MouseEvent mouseEvent) {
    logout.setUnderline(false);
    logout.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnExit(MouseEvent mouseEvent) {
    exitBtn.setUnderline(true);
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffExit(MouseEvent mouseEvent) {
    exitBtn.setUnderline(false);
    exitBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnSet(MouseEvent mouseEvent) {
    settingBtn.setUnderline(true);
    settingBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffSet(MouseEvent mouseEvent) {
    settingBtn.setUnderline(false);
    settingBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }
}
