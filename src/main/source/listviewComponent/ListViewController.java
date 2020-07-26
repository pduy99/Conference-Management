package listviewComponent;

import POJO.ConferenceEntity;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
public class ListViewController implements Initializable {

    @FXML
    private VBox listLayout;

    @FXML
    private ListView<ConferenceEntity> lvConferences;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvConferences.setItems(ConferenceListSingleton.getInstance().getFilteredList());
        lvConferences.setCellFactory(customListViewCell -> new CustomListViewCell());
        ConferenceListSingleton.getInstance().setConferenceListView(lvConferences);
    }
}
