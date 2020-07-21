package Controllers;

import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {



    @FXML
    StackPane rootPane;

    @FXML
    Pane paneToolbar;

   @FXML
   TextField searchBox;

   @FXML
   JFXComboBox<Label> cbViewStyle;

   @FXML
    ImageView btnExit;

   @FXML
   ImageView btnMinimize;

   @FXML
    Circle ivAvatarRound;

   @FXML
    Text tfUserDisplayName;

   @FXML
   VBox guestFunctionNav;

   @FXML
   VBox userFunctionNav;

   @FXML
   VBox adminFunctionNav;

    private Stage stage = null;
    private double xOffset = 0;
    private double yOffset = 0;
    private UserEntity account;

    @FXML
    public void handleMouseClicked(MouseEvent event){
        if(event.getSource() == btnExit){
            System.exit(0);
        }
        if(event.getSource() == btnMinimize){
            ((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeDraggable(paneToolbar);
        setupCombobox();
        setLogin();
        setupUserFunctionNav();
    }

    private void setLogin(){
        account = CurrentAccountSingleton.getInstance().getAccount();
        tfUserDisplayName.setText(account.getDisplayName());

    }

    private void setupUserFunctionNav(){
        adminFunctionNav.managedProperty().bind(adminFunctionNav.visibleProperty());
        userFunctionNav.managedProperty().bind(userFunctionNav.visibleProperty());
        guestFunctionNav.managedProperty().bind(guestFunctionNav.visibleProperty());
        switch (account.getRole()){
            case 0: { // Guest
                userFunctionNav.setVisible(false);
                adminFunctionNav.setVisible(false);
                break;
            }
            case 1:{ // User
                guestFunctionNav.setVisible(false);
                adminFunctionNav.setVisible(false);
                break;
            }
            case 2:{ // Admin
                guestFunctionNav.setVisible(false);
                userFunctionNav.setVisible(false);
                break;
            }
        }
    }

    private void setupCombobox(){
        cbViewStyle.getItems().add(new Label("Card view"));
        cbViewStyle.getItems().add(new Label("Table view"));
        cbViewStyle.getSelectionModel().selectFirst();
    }

    private void makeDraggable(final Node node){
        node.setOnMousePressed((MouseEvent event)->{
            if(stage==null){
                stage = (Stage)node.getScene().getWindow();
            }
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        node.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private Stage getStage(){
        return (Stage)rootPane.getScene().getWindow();
    }
}
