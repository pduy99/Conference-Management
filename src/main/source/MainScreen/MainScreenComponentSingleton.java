package MainScreen;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A singleton class that keeps the reference to the root StackPane of the main UI.
 * @created on 7/23/2020
 * @author: Helios - 1712018
 */
public class MainScreenComponentSingleton {
    private static MainScreenComponentSingleton instance;

    private static StackPane rootStackPane;
    private static BorderPane borderPane;
    private static Label tfListTitle;
    private static VBox listContainers;
    private static VBox profileContainer;

    private MainScreenComponentSingleton(){}

    public static MainScreenComponentSingleton getInstance() {
        if (instance == null) {
            instance = new MainScreenComponentSingleton();
        }
        return instance;
    }

    public VBox getListContainers() {
        return listContainers;
    }

    public void setListContainers(VBox listContainers) {
        MainScreenComponentSingleton.listContainers = listContainers;
    }

    public VBox getProfileContainer() {
        return profileContainer;
    }

    public void setProfileContainer(VBox profileContainer) {
        MainScreenComponentSingleton.profileContainer = profileContainer;
    }

    public void setStackPane(StackPane stackPane) {
        rootStackPane = stackPane;
    }

    public StackPane getStackPane() {
        return rootStackPane;
    }

    public void setBorderPane(BorderPane bp) {
        borderPane = bp;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public Label getListTitle(){
        return tfListTitle;
    }

    public void setListTitle(Label searchBar) {
        MainScreenComponentSingleton.tfListTitle = searchBar;
    }

    public void showListContainer(){
        listContainers.setManaged(true);
        listContainers.setVisible(true);
        profileContainer.setManaged(false);
        profileContainer.setVisible(false);
    }

    public void showProfileContainer(){
        listContainers.setManaged(false);
        listContainers.setVisible(false);
        profileContainer.setManaged(true);
        profileContainer.setVisible(true);
    }
}
