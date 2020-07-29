package profileController;

import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/30/2020
 * @author: Helios - 1712018
 */
public class ProfileController implements Initializable {
    @FXML
    private Label tfTitleDisplayName;

    @FXML
    private Text tfRole;

    @FXML
    private JFXTextField tfDisplayName;

    @FXML
    private JFXTextField tfEmail;

    @FXML
    private JFXTextField tfAddress;

    @FXML
    private JFXTextField tfBirthday;

    @FXML
    private JFXButton btnEditProfile;

    @FXML
    private JFXButton btnSaveChanges;

    @FXML
    private JFXTextField tfUsername;

    @FXML
    private JFXTextField tfCreateDate;

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    void btnChangePasswordClick(MouseEvent event) {

    }

    @FXML
    void btnEditProfileClick(MouseEvent event) {
        //Enable editable on textfield
        setTextFieldsEditable();


    }

    @FXML
    void btnSaveChangesClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupInformation();
        btnSaveChanges.setVisible(false);
        //binding
        tfTitleDisplayName.textProperty().bind(tfDisplayName.textProperty());

    }

    private void setTextFieldsEditable(){
        tfDisplayName.setEditable(true);
        tfAddress.setEditable(true);
        tfEmail.setEditable(true);
        tfBirthday.setEditable(true);
        tfUsername.setEditable(true);
    }

    private void setTextFieldsUneditable(){
        tfDisplayName.setEditable(false);
        tfAddress.setEditable(false);
        tfEmail.setEditable(false);
        tfBirthday.setEditable(false);
        tfUsername.setEditable(false);
    }

    private void setupInformation(){
        UserEntity user = CurrentAccountSingleton.getInstance().getAccount();
        tfDisplayName.setText(user.getDisplayName());
        //tfAddress.setText(user.getAddress());
        tfEmail.setText(user.getEmail());
        //tfBirthday.setText(user.getBirthday().toLocaleString());
        tfUsername.setText(user.getUsername());
        //tfCreateDate.setText(user.getCreateDate().toLocaleString());
    }
}
