package authentification.changePassword;

import DAO.UserDAO;
import MainScreen.MainScreenComponentSingleton;
import POJO.UserEntity;
import Utils.CustomValidator.StrongPasswordValidator;
import Utils.MD5;
import alertsDialog.CustomAlertType;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.validation.RequiredFieldValidator;
import handlers.Convenience;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/30/2020
 * @author: Helios - 1712018
 */
public class ChangePassword implements Initializable {
    @FXML
    private JFXPasswordField tfNewPassword;

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXPasswordField tfConfirmPassword;

    @FXML
    private JFXPasswordField tfCurrentPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field cannot empty!");
        tfCurrentPassword.getValidators().add(requiredFieldValidator);
        tfNewPassword.getValidators().add(requiredFieldValidator);
        StrongPasswordValidator strongPasswordValidator = new StrongPasswordValidator();
        tfNewPassword.getValidators().add(strongPasswordValidator);
    }

    @FXML
    private void handleChangePassword(MouseEvent mouseEvent) {
        System.out.println("clicked");
        if(tfCurrentPassword.validate() && tfNewPassword.validate() && checkConfirmPassword()){
            if(authenticateCurrentPassword()){
                UserEntity user = UserDAO.findByPk(CurrentAccountSingleton.getInstance().getID());
                assert user != null;
                user.setPassword(MD5.getMD5(tfNewPassword.getText()));
                if(UserDAO.updateUser(user)) {
                    Convenience.showAlert(MainScreenComponentSingleton.getInstance().getStackPane(),
                            MainScreenComponentSingleton.getInstance().getBorderPane(), CustomAlertType.SUCCESS, "Change password successfully", "");
                    CurrentAccountSingleton.getInstance().setAccount(user);
                }else{
                    Convenience.showAlert(MainScreenComponentSingleton.getInstance().getStackPane(),
                            MainScreenComponentSingleton.getInstance().getBorderPane(), CustomAlertType.ERROR, "Something went wrong!", "Please try again later");
                }
            }
        }
    }

    @FXML
    private void handleCancelButtonClick(MouseEvent mouseEvent) {
        Convenience.closePreviousDialog();
    }

    private boolean checkConfirmPassword(){
        if(!tfNewPassword.getText().equals(tfConfirmPassword.getText())){
            Convenience.showAlert(MainScreenComponentSingleton.getInstance().getStackPane(),
                    MainScreenComponentSingleton.getInstance().getBorderPane(), CustomAlertType.ERROR,"Confirm password does not match","");
            return false;
        }
        return true;
    }

    private boolean authenticateCurrentPassword(){
        String hashPassword = MD5.getMD5(tfCurrentPassword.getText());
        if(!CurrentAccountSingleton.getInstance().getAccount().getPassword().equals(hashPassword)){
            Convenience.showAlert(MainScreenComponentSingleton.getInstance().getStackPane(),
                    MainScreenComponentSingleton.getInstance().getBorderPane(), CustomAlertType.ERROR,"Password invalid","");
            return false;
        }
        return true;
    }
}
