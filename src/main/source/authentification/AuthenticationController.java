package authentification;

import DB.DBConnection;
import DBController.UserController;
import Utils.CustomValidator.EmailValidator;
import Utils.CustomValidator.StrongPasswordValidator;
import Utils.CustomValidator.UsernameValidator;
import alertsDialog.CustomAlertType;
import animatefx.animation.ZoomIn;
import authentification.loginProcess.UserLogin;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.RequiredFieldValidator;
import handlers.Convenience;
import handlers.InternetHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootPane;

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private ImageView btnExit;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Pane paneSignIn;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSwitchToSignUp;

    @FXML
    private Label lbForgotPassword;

    @FXML
    private Label lbGuestSignIn;

    @FXML
    private JFXTextField tfUserName;

    @FXML
    private JFXPasswordField tfPassword;

    @FXML
    private Pane paneSignUp;

    @FXML
    private ImageView btnArrowBack;

    @FXML
    private Button btnSignUp;

    @FXML
    private JFXTextField tfRegisterDisplayName;

    @FXML
    private JFXTextField tfRegisterUsername;

    @FXML
    private JFXTextField tfRegisterEmail;

    @FXML
    private JFXPasswordField tfRegisterPassword;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnSwitchToSignUp){
            new ZoomIn(paneSignUp).play();
            paneSignUp.toFront();
        }
    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        if(event.getSource() == btnArrowBack){
            new ZoomIn(paneSignIn).play();
            paneSignIn.toFront();
        }
        if(event.getSource() == btnExit){
              System.exit(0);
        }
        if(event.getSource() == btnMinimize){
            ((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
        }

    }

    @FXML
    void handleSignInButton(ActionEvent event) throws Exception{
        String username = tfUserName.getText().trim();
        String password = tfPassword.getText().trim();
        if(tfUserName.validate() && tfPassword.validate()) {
            UserLogin userLogin = new UserLogin();
            try {
                userLogin.getAccount(username, password);
                //Sign in successfully
                Convenience.switchScene(event,getClass().getResource("/FXML/Main.fxml"));

            } catch (Exception ex) {
                if (!InternetHandler.checkInternetConnection()) {
                    Convenience.popupDialog(rootPane, rootBorderPane, getClass().getResource("/FXML/no_internet.fxml"));
                } else if (DBConnection.getSessionFactory() == null) {
                    Convenience.popupDialog(rootPane, rootBorderPane, getClass().getResource("/FXML/no_database.fxml"));
                } else {
                    Convenience.showAlert(rootPane, rootBorderPane, CustomAlertType.ERROR, "Login error", "Username or password invalid");

                }
            }
        }
    }

    @FXML
    void handleSignUpButton(ActionEvent event){
        if(validateRegisterForm()) {
            String displayName = tfRegisterDisplayName.getText().trim();
            String username = tfRegisterUsername.getText().trim();
            String mail = tfRegisterEmail.getText().trim();
            String password = tfRegisterPassword.getText().trim();

            //Check whether username already exists
            if (UserController.findByUserName(username) != null) {
                showExistedUsernameAlertDialog();
            } else {
                if (UserController.addUser(displayName, username, password, mail) == 0) {
                    //Register successfully

                } else {
                    //Something went wrong
                    Convenience.showAlert(rootPane,rootBorderPane,CustomAlertType.ERROR,"Sorry, something went wrong","Please try again later");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field cannot empty!");

        tfUserName.getValidators().add(requiredFieldValidator);
        tfPassword.getValidators().add(requiredFieldValidator);

        tfRegisterDisplayName.getValidators().add(requiredFieldValidator);

        EmailValidator emailValidator = new EmailValidator();
        tfRegisterEmail.getValidators().add(emailValidator);
        tfRegisterEmail.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1){
                tfRegisterEmail.validate();
            }
        });

        StrongPasswordValidator passwordValidator = new StrongPasswordValidator();
        tfRegisterPassword.getValidators().add(passwordValidator);
        tfRegisterPassword.focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){
                tfRegisterPassword.validate();
            }
        }));

        UsernameValidator usernameValidator = new UsernameValidator();
        tfRegisterUsername.getValidators().add(usernameValidator);
        tfRegisterUsername.focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){
                tfUserName.validate();
            }
        }));
    }

    public void showExistedUsernameAlertDialog(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/FXML/alert_error.fxml"));
            Parent node = loader.load();
            JFXButton button = (JFXButton) loader.getNamespace().get("btnConfirm");
            BoxBlur blur = new BoxBlur(3,3,3);
            JFXDialog dialog = new JFXDialog(rootPane,(Region)node ,JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
                dialog.close();
            });
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent e1)->{
                rootBorderPane.setEffect(null);
            });
            rootBorderPane.setEffect(blur);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateRegisterForm(){
        return (tfRegisterUsername.validate() &&
                tfRegisterEmail.validate() &&
                tfRegisterPassword.validate() && tfRegisterDisplayName.validate());
    }

}
