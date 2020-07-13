package Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    private ObservableList<String> viewTypeList = FXCollections.observableArrayList("Card view", "Table view");

    @FXML
    private TextField searchBox;

    @FXML
    private JFXComboBox<String> cbListViewType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbListViewType.setItems(viewTypeList);
    }
}
