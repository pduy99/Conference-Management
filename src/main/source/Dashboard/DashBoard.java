package Dashboard;

import MainScreen.MainScreenComponentSingleton;
import adminApprovePendingList.ApprovePendingListController;
import adminManageConferenceComponent.ManageConferenceController;
import alertsDialog.CustomAlertType;
import com.jfoenix.controls.JFXTabPane;
import handlers.Convenience;
import handlers.InternetHandler;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import adminManageUserComponent.ManageUserController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class DashBoard implements Initializable {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private VBox rootVBox;

    @FXML
    private Pane toolbar;

    @FXML
    private ImageView btnExit;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private AnchorPane tabUserManagement;

    @FXML
    private AnchorPane tabConferenceManagement;

    @FXML
    private AnchorPane tabPendingList;

    @FXML
    private Label lbBackToHome;

    @FXML
    void handleBackHomeScreen(MouseEvent event) throws IOException {
        Convenience.switchScene(event,getClass().getResource("/FXML/Main.fxml"));
    }

    @FXML
    private void handleMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btnExit){
            System.exit(0);
        }
        if(mouseEvent.getSource() == btnMinimize){
            ((Stage)((ImageView)mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
        }
    }

    private Stage stage = null;
    private double xOffset = 0;
    private double yOffset = 0;
    private final FXMLLoader manageUserLoader = new FXMLLoader();
    private final FXMLLoader manageConferenceLoader = new FXMLLoader();
    private FXMLLoader managePendingListLoader = new FXMLLoader();
    private boolean loadingFinished = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DashboardComponentSingleton.getInstance().setRootStackPane(rootStackPane);
        DashboardComponentSingleton.getInstance().setRootVBox(rootVBox);

        makeDraggable(tabPane);
        makeDraggable(toolbar);
        manageUserLoader.setLocation(getClass().getResource("/FXML/admin_manage_user.fxml"));
        manageConferenceLoader.setLocation(getClass().getResource("/FXML/admin_manage_conference.fxml"));
        managePendingListLoader.setLocation(getClass().getResource("/FXML/admin_approve_pendingList.fxml"));

        AnchorPane manageUserContent;
        AnchorPane manageConferenceContent;
        AnchorPane managePendingListContent;


        try{
            manageUserContent = manageUserLoader.load();
            ManageUserController manageUsersController = manageUserLoader.getController();
            manageUsersController.init();

            manageConferenceContent = manageConferenceLoader.load();
            ManageConferenceController manageConferenceController = manageConferenceLoader.getController();
            manageConferenceController.init();

            managePendingListContent = managePendingListLoader.load();
            ApprovePendingListController approvePendingListController = managePendingListLoader.getController();
            approvePendingListController.init();

            Task<Void> task = new Task<Void>() {

                @Override
                protected void succeeded(){
                    super.succeeded();
                        loadingFinished = true;
                }

                @Override
                protected Void call() throws Exception {
                    tabUserManagement.getChildren().addAll(manageUserContent);
                    tabConferenceManagement.getChildren().add(manageConferenceContent);
                    tabPendingList.getChildren().add(managePendingListContent);
                    return null;
                }
            };
            task.run();
        }catch (Exception e){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (!InternetHandler.checkInternetConnection()) {
                        try {
                            Convenience.popupDialog(MainScreenComponentSingleton.getInstance().getStackPane(), MainScreenComponentSingleton.getInstance().getBorderPane(), getClass().getResource("/FXML/noInternet.fxml"));
                        } catch (IOException e1) {
                            Convenience.showAlert(MainScreenComponentSingleton.getInstance().getStackPane(), MainScreenComponentSingleton.getInstance().getBorderPane(),CustomAlertType.ERROR, "Something went wrong. Please, try again later","");
                        }
                    } else{
                        Convenience.showAlert(MainScreenComponentSingleton.getInstance().getStackPane(), MainScreenComponentSingleton.getInstance().getBorderPane(),CustomAlertType.ERROR, "Something went wrong. Please, try again later.","");
                    }
                }
            });
        }
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


}
