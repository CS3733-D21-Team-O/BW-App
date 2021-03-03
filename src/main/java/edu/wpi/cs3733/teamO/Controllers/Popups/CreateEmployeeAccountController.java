package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class CreateEmployeeAccountController {

  @FXML private JFXCheckBox adminCheck;
  @FXML private JFXTextField user;
  @FXML private JFXPasswordField pass;
  @FXML private JFXTextField email;
  @FXML private JFXTextField fName;
  @FXML private JFXTextField lName;
  @FXML private StackPane popupPane;

  public void create(ActionEvent actionEvent) {
    if (RegexBoi.checkUsername(user.getText())) {
      if (RegexBoi.checkEmail(email.getText())) {

        try {
          UserHandling.createEmployee(
              user.getText(),
              pass.getText(),
              email.getText(),
              fName.getText(),
              lName.getText(),
              adminCheck.isSelected());
        } catch (SQLException throwables) {
          PopupMaker.incompletePopup(popupPane);
          throwables.printStackTrace();
        }
        try {
          Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
          Opp.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      } else {
        PopupMaker.invalidEmail(popupPane);
      }
    } else {
      PopupMaker.invalidUsername(popupPane);
    }
  }

  public void checkEnter(KeyEvent keyEvent) throws SQLException {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      create(new ActionEvent());
    }
  }

  public void goToManageEmployee(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ManageEmployees.fxml");
  }
}