package userFunctionNavbar;

import MainScreen.MainPane;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import handlers.Convenience;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import listviewComponent.ConferenceListSingleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/24/2020
 * @author: Helios - 1712018
 */

public class UserFunctionNavBar implements Initializable {

    @FXML
    private HBox myProfile;

    @FXML
    private HBox myConferences;

    @FXML
    private HBox allConferences;

    @FXML
    private HBox userManagement;

    @FXML
    private HBox conferenceManagement;

    @FXML
    private HBox pendingList;

    @FXML
    private HBox signIn;

    @FXML
    private HBox logOut;

    private UserEntity account;
    private int accountRole;

    private HBox isSelecting;

    public UserFunctionNavBar(){
        account = CurrentAccountSingleton.getInstance().getAccount();
        accountRole = account.getRole();
        isSelecting = allConferences;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        myProfile.managedProperty().bind(myProfile.visibleProperty());
        allConferences.managedProperty().bind(allConferences.visibleProperty());
        myConferences.managedProperty().bind(myConferences.visibleProperty());
        userManagement.managedProperty().bind(userManagement.visibleProperty());
        conferenceManagement.managedProperty().bind(conferenceManagement.visibleProperty());
        pendingList.managedProperty().bind(pendingList.visibleProperty());
        signIn.managedProperty().bind(signIn.visibleProperty());
        logOut.managedProperty().bind(logOut.visibleProperty());

        switch (accountRole){
            case 0: //Guest
            {
                myProfile.setVisible(false);
                myConferences.setVisible(false);
                userManagement.setVisible(false);
                conferenceManagement.setVisible(false);
                pendingList.setVisible(false);
                logOut.setVisible(false);
                break;
            }
            case 1: //User
            {
                userManagement.setVisible(false);
                conferenceManagement.setVisible(false);
                pendingList.setVisible(false);
                signIn.setVisible(false);
                break;
            }
            case 3: //Admin
            {
                signIn.setVisible(false);
                break;
            }
        }
    }

    @FXML
    void handleConferenceManagement(MouseEvent event) {

    }

    @FXML
    void handleLogOut(MouseEvent event) {
        CurrentAccountSingleton.getInstance().setAccount(null);
        try {
            Convenience.switchScene(event,getClass().getResource("/FXML/Login.fxml"));
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    @FXML
    void handleMyConferences(MouseEvent event) {
        ConferenceListSingleton.getInstance().getMyConferenceList(account.getId());
        MainPane.getInstance().getListTitle().setText("My Conference list");
    }

    @FXML
    void handleAllConferences(MouseEvent event){
        ConferenceListSingleton.getInstance().getAllConference();
        MainPane.getInstance().getListTitle().setText("Conference list: ");
    }

    @FXML
    void handleMyProfile(MouseEvent event) {

    }

    @FXML
    void handlePendingList(MouseEvent event) {

    }

    @FXML
    void handleSignIn(MouseEvent event) {
        StackPane rootStackPane = MainPane.getInstance().getStackPane();
        BorderPane borderPane = MainPane.getInstance().getBorderPane();
        try {
            Convenience.popupDialog(rootStackPane,borderPane,getClass().getResource("/FXML/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleUserManagement(MouseEvent event) {

    }
}
