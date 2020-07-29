package authentification;

/***
 * Class that controls the login and sign up view
 */

import DB.DBConnection;
import DAO.UserDAO;
import Utils.CustomValidator.EmailValidator;
import Utils.CustomValidator.StrongPasswordValidator;
import Utils.CustomValidator.UsernameValidator;
import Utils.MD5;
import alertsDialog.CustomAlert;
import alertsDialog.CustomAlertType;
import animatefx.animation.ZoomIn;
import authentification.loginProcess.Login;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import handlers.Convenience;
import handlers.InternetHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;

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
            if(Convenience.getDialog() == null){
                System.exit(0);
            }
            else{
                Convenience.closePreviousDialog();
            }

        }
        if(event.getSource() == btnMinimize){
            if(Convenience.getDialog() == null){
                ((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
            }
        }

    }

    /***
     * Method that handles the login process
     * @param event method trigger {@link ActionEvent}
     * @throws Exception {@link Exception}
     */
    @FXML
    void handleSignInButton(ActionEvent event) throws Exception{
        String username = tfUserName.getText().trim();
        String password = MD5.getMD5(tfPassword.getText());
        if(tfUserName.validate() && tfPassword.validate()) {
            Login login = new Login();
            try {
                login.getAccount(username, password);
                //Sign in successfully
                Convenience.switchScene(rootAnchorPane,getClass().getResource("/FXML/Main.fxml"));

            } catch (Exception ex) {
                if (!InternetHandler.checkInternetConnection()) {
                    Convenience.popupDialog(rootPane, rootBorderPane, getClass().getResource("/FXML/no_internet.fxml"));
                } else if (DBConnection.getSessionFactory() == null) {
                    Convenience.popupDialog(rootPane, rootBorderPane, getClass().getResource("/FXML/no_database.fxml"));
                } else {
                    Convenience.showAlert(rootPane, rootBorderPane, CustomAlertType.ERROR, "Login error", "Username or password invalid");
                }
                ex.printStackTrace();
            }
        }
    }

    /***
     * Method that handles the sign up process
     * @param event method trigger {@link ActionEvent}
     */
    @FXML
    void handleSignUpButton(ActionEvent event){
        if(validateRegisterForm()) {
            String displayName = tfRegisterDisplayName.getText().trim();
            String username = tfRegisterUsername.getText().trim();
            String mail = tfRegisterEmail.getText().trim();
            String password = tfRegisterPassword.getText().trim();
            String hashPassword = MD5.getMD5(password);

            //Check whether username already exists
            if (UserDAO.findByUserName(username) != null) {
                Convenience.showAlert(rootPane,rootBorderPane,CustomAlertType.ERROR,"Username already existed","Please choose another username");
            } else {
                if (UserDAO.addUser(displayName, username, hashPassword, mail) == 0) {
                    //Register successfully
                    Convenience.showAlert(rootPane,rootBorderPane, CustomAlertType.SUCCESS,"Congratulations","You are successfully registered. Thank you!");
                } else {
                    //Something went wrong
                    Convenience.showAlert(rootPane,rootBorderPane,CustomAlertType.ERROR,"Sorry, something went wrong","Please try again later");
                }
            }
        }
    }

    @FXML
    private void handleGuestLogin(MouseEvent event){
        Login login = new Login();
        login.loginAsGuest();
        try{
            Convenience.switchScene(event,getClass().getResource("/FXML/Main.fxml"));
        }catch (Exception e){
            Convenience.showAlert(rootPane,rootBorderPane,CustomAlertType.ERROR,"Login Error","Something went wrong, please try again later");
        }
    }

    /***
     * Method that set validator to fields of login form and sign up form
     * @param url
     * @param resourceBundle
     */
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

        rootAnchorPane.setOnKeyPressed(keyEvent -> {
            System.out.println("Pressed Enter");
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(tfUserName.validate() && tfPassword.validate()){
                    try {
                        handleSignInButton(new ActionEvent());
                    } catch (Exception ignored) {
                    }
                }
            }
        });
    }

    public boolean validateRegisterForm(){
        return (tfRegisterUsername.validate() &&
                tfRegisterEmail.validate() &&
                tfRegisterPassword.validate() && tfRegisterDisplayName.validate());
    }

}
