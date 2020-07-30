package profileController;

import DAO.UserDAO;
import MainScreen.MainPane;
import POJO.UserEntity;
import Utils.StringUtils;
import alertsDialog.CustomAlertType;
import authentification.changePassword.ChangePassword;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import handlers.Convenience;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    Circle ivAvatar;

    @FXML
    private JFXButton btnSaveChanges;

    @FXML
    private JFXTextField tfUsername;

    @FXML
    private JFXTextField tfCreateDate;

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    void btnChangePasswordClick(MouseEvent event) throws IOException {
        ChangePassword changePassword = Convenience.popupDialog(MainPane.getInstance().getStackPane(),
                MainPane.getInstance().getBorderPane(),getClass().getResource("/FXML/change_password.fxml"));
    }

    @FXML
    void btnEditProfileClick(MouseEvent event) {
        //Enable editable on textfield
        setTextFieldsEditable();
        btnSaveChanges.setVisible(true);
    }

    @FXML
    void btnSaveChangesClick(MouseEvent event) {
        if(updateProfile()) {
            Convenience.showAlert(MainPane.getInstance().getStackPane(),
                    MainPane.getInstance().getBorderPane(),CustomAlertType.SUCCESS,"Update profile successfully","");
            setTextFieldsUneditable();
            btnSaveChanges.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(CurrentAccountSingleton.getInstance().getRole() !=0) {
            setupInformation();
            btnSaveChanges.setVisible(false);
            //binding
            tfTitleDisplayName.textProperty().bind(tfDisplayName.textProperty());
        }
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
        String address = user.getAddress() != null ? user.getAddress() : "";
        tfAddress.setText(address);
        tfEmail.setText(user.getEmail());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String birthday = user.getBirthday() != null ? dateFormat.format(user.getBirthday()) : "";
        tfBirthday.setText(birthday);
        tfUsername.setText(user.getUsername());
        tfCreateDate.setText(user.getCreateDate().toLocaleString());

        //AvatarImage
        try {
            String avatar_name = "/img/" + user.getUsername() + "_avatar.png";
            InputStream imageURL = getClass().getResourceAsStream(avatar_name);
            Image image = new Image(imageURL);
            ivAvatar.setFill(new ImagePattern(image));
        }catch (Exception e){
            e.printStackTrace();
        }

        String role = user.getRole() == 1 ? "User" : "Admin";
        tfRole.setText(role);
    }

    private boolean updateProfile(){
        String username = tfUsername.getText();
        String displayName = tfDisplayName.getText();
        String address = tfAddress.getText();
        String email = tfEmail.getText();
        String stringBirthday = tfBirthday.getText();
        Date birthday = null;
        if(checkValidProfile(username,displayName,address,email,stringBirthday)){
            UserEntity user = UserDAO.findByPk(CurrentAccountSingleton.getInstance().getID());
            assert user != null;
            user.setUsername(username);
            user.setDisplayName(displayName);
            user.setEmail(email);
            user.setAddress(address);
            if(!stringBirthday.isEmpty()){
                try {
                    birthday = new SimpleDateFormat("dd/MM/yyyy").parse(stringBirthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            user.setBirthday(birthday);

            if(UserDAO.updateUser(user)){
                CurrentAccountSingleton.getInstance().setAccount(user);
                return true;
            }
        }
        return false;
    }

    private boolean checkValidProfile(String username, String displayName, String address,  String email, String stringBirthday){
        if(displayName.isEmpty()){
            return false;
        }

        //Username is changed but duplicate another account
        if(!username.equals(CurrentAccountSingleton.getInstance().getAccount().getUsername())){
            if(UserDAO.findByUserName(username)==null){
                Convenience.showAlert(MainPane.getInstance().getStackPane(),
                        MainPane.getInstance().getBorderPane(), CustomAlertType.ERROR,"Username already existed","Please choose another username");
                return false;
            }
        }

        //Birthday is entered but invalid
        if(!stringBirthday.equals("")) {
            try {
                Date birthday = new SimpleDateFormat("dd/MM/yyyy").parse(stringBirthday);
            } catch (ParseException e) {
                Convenience.showAlert(MainPane.getInstance().getStackPane(),
                        MainPane.getInstance().getBorderPane(), CustomAlertType.ERROR,"Birthday invalid","");
                return false;
            }
        }

        // Email is invalid
        if(!StringUtils.validateEmail(email)){
            Convenience.showAlert(MainPane.getInstance().getStackPane(),
                    MainPane.getInstance().getBorderPane(), CustomAlertType.ERROR,"Email not valid","");
            return false;
        }
        return true;
    }
}
