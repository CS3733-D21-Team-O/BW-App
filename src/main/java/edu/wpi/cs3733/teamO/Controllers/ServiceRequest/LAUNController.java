package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;
import static edu.wpi.cs3733.teamO.Database.UserHandling.getSessionUsername;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LAUNController implements Initializable {

  @FXML private JFXComboBox sizeComboBox;
  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;
  @FXML private JFXCheckBox field2;
  private String loadSize = "";

  private ObservableList<String> listOfSizes =
      FXCollections.observableArrayList("XS", "S", "M", "L", "XL");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    sizeComboBox.setItems(listOfSizes);
  }

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
    field2.setSelected(false);
  }

  public void submit(ActionEvent actionEvent) {
    String requestedBy = getSessionUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = getReqType();
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();
    String f2 = String.valueOf(field2.isSelected());
    String f3 = loadSize;

    Request r = new Request();
    r.setRequestedBy(requestedBy);
    r.setDateNeeded(dateN);
    r.setRequestType(requestType);
    r.setRequestLocation(loc);
    sum += ". Type: " + f1 + ". Contact With CV19?: " + f2 + ". Load Size: " + f3 + ".";
    r.setSummary(sum);
    RequestHandling.addRequest(r);
  }

  public void comboBoxAction(ActionEvent actionEvent) {
    loadSize = (String) sizeComboBox.getValue();
  }
}
