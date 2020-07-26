package MainScreen;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.jboss.jandex.Main;

/**
 * A singleton class that keeps the reference to the root StackPane of the main UI.
 * @created on 7/23/2020
 * @author: Helios - 1712018
 */
public class MainPane {
    private static MainPane instance;

    private static StackPane rootStackPane;
    private static BorderPane borderPane;
    private static TextField searchBar;

    private MainPane(){}

    public static MainPane getInstance() {
        if (instance == null) {
            instance = new MainPane();
        }
        return instance;
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

    public TextField getSearchBar(){
        return searchBar;
    }

    public void setSearchBar(TextField searchBar) {
        MainPane.searchBar = searchBar;
    }
}
