package authentification.recoverProcess;

/**
 * Class that controls the password recovery view (not completed)
 * @created on 7/13/2020
 * @author: Helios - 1712018
 */

import DAO.UserDAO;
import alertsDialog.CustomAlertType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import handlers.Convenience;
import handlers.InternetHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class RecoverController implements Initializable {
    private String generateKey = null;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Text firstRecoveryText;

    @FXML
    private Text secondRecoveryText;

    @FXML
    private JFXTextField tfRecoveryEmail;

    @FXML
    private JFXButton btnSendEmail;

    @FXML
    private JFXTextField tfConfirmationCode;

    @FXML
    private JFXButton btnConfirmationCode;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private Label notifyUser;

    @FXML
    void confirmButton(MouseEvent event) {

    }

    @FXML
    void generateConfirmCode(MouseEvent event) {
        tfRecoveryEmail.setDisable(true);
        btnSendEmail.setDisable(true);

        Thread thread = new Thread(()->{
            boolean userExist = false;
            try {
                userExist = checkUserExist(tfRecoveryEmail.getText().trim());
            }catch (Exception exception){
                Platform.runLater(()->{
                    if(!InternetHandler.checkInternetConnection()){
                        Convenience.showAlert(stackPane,anchorpane, CustomAlertType.ERROR,"Error","You have no internet connection, please try again later");
                    }
                    else{
                        Convenience.showAlert(stackPane,anchorpane, CustomAlertType.ERROR,"Opps","Something went wrong, please try again later");
                    }
                });
                Thread.currentThread().interrupt();
                return;
            }
            if(userExist){
                generateKey = UUID.randomUUID().toString();
            }
        });
    }

    private boolean checkUserExist(String email){
        if(UserDAO.findByEmail(email)!=null){
            return true;
        }
        else{
            return false;
        }
    }

    @FXML
    void handleCancelClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfConfirmationCode.setDisable(true);
        btnConfirmationCode.setDisable(true);
    }
}

