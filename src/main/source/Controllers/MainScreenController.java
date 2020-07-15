package Controllers;

import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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
    ImageView btnExit;

   @FXML
   ImageView btnMinimize;

   @FXML
    Circle ivAvatarRound;

   @FXML
    Text tfUserDisplayName;

    @FXML
    VBox containerUserNav;

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
        Stage stage = new Stage();
        stage.setScene(rootPane.getScene());
        paneToolbar.setOnMousePressed((MouseEvent event) ->{
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        paneToolbar.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setScene(rootPane.getScene());

        setLogin();
    }

    private void setLogin(){
        account = CurrentAccountSingleton.getInstance().getAccount();
        tfUserDisplayName.setText(account.getDisplayName());

    }

}
