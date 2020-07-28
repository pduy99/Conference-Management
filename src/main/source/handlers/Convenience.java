package handlers;

import alertsDialog.CustomAlert;
import alertsDialog.CustomAlertFactory;
import alertsDialog.CustomAlertType;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import com.jfoenix.controls.JFXDialog;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * This class contains the convenience methods for switching the scenes in the app,
 * showing alerts to the user, getting the window of the node or the event, etc.
 * @created on 7/11/2020
 * @author: Helios - 1712018
 */
public final class Convenience {
    private static JFXDialog previousDialog;

    /***
     * Switch the current scene to the specified scene
     * @param windows the main window where the scene should be displayed in
     * @param destination location of the fxml file which is to be loaded
     * @throws IOException throws if destination could not be loaded
     */
    public static void switchScene(Stage windows, URL destination) throws IOException{
        windows.close();
        Parent root = FXMLLoader.load(destination);
        Scene scene = new Scene(root);
        windows.setScene(scene);
        windows.show();
    }

    /***
     * Switches the current scene to the specified scene.
     * Gets the window of the event and calls {@link #switchScene(Stage, URL)}
     * @param event the event which invoked the change of scene
     * @param destination location of the fxml file which is to be loaded
     * @throws IOException throws if destination could not be loaded
     */
    public static void switchScene(Event event, URL destination) throws IOException{
        switchScene(getWindow(event),destination);
    }

    /***
     * Switches the current scene to the specified scene.
     * Gets the window of the node and calls {@link #switchScene(Stage, URL)}
     * @param node any node which is located in the current scene
     * @param destination location of the fxml file which is to be loaded
     * @throws IOException throws if destination could not be loaded
     */
    public static void switchScene(Node node, URL destination) throws IOException{
        switchScene(getWindow(node),destination);
    }

    /**
     * Gets the window of the provided event.
     *
     * @param event - event the window of which is to be fetched
     * @return {@link Stage} object (the fetched window)
     */
    public static Stage getWindow(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    /**
     * Gets the window of the provided node.
     *
     * @param node - node the window of which is to be fetched
     * @return {@link Stage} object (the fetched window)
     */
    public static Stage getWindow(Node node) {
        return (Stage) node.getScene().getWindow();
    }

    public static void showAlert(StackPane rootPane, Node nodeToBlur, CustomAlertType customAlertType, String header, String body){
        CustomAlertFactory alertFactory = new CustomAlertFactory();
        CustomAlert customAlert = alertFactory.createAlert(customAlertType);
        customAlert.showAlert(rootPane,nodeToBlur,header, body);
    }

    /***
     * Pops up a dialog with custom content
     * @param stackPane pane on that the dialog should appear
     * @param paneToBlur pane that the blur effect should affect
     * @param fxmlUrl the content of the dialog
     * @throws IOException throws if the content could not be loaded
     */
    public static <T> T popupDialog(StackPane stackPane, Pane paneToBlur, URL fxmlUrl) throws IOException {
        closePreviousDialog();

        BoxBlur blur = new BoxBlur(3, 3, 3);

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent parent = loader.load();

        JFXDialog dialog = new JFXDialog(stackPane, (Region)parent, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(event -> paneToBlur.setEffect(null));
        dialog.setOnDialogOpened(event -> paneToBlur.setEffect(blur));
        dialog.show();

        previousDialog = dialog;

        return loader.getController();
    }

    /***
     * Closes the previously opened dialog.
     */
    public static void closePreviousDialog(){
        if(previousDialog!=null){
            previousDialog.close();
            previousDialog = null;
        }
    }

    /***
     * Return the currently dialog
     * @return
     */
    public static JFXDialog getDialog(){
        return previousDialog;
    }
}
