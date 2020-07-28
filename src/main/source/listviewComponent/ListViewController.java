package listviewComponent;

import MainScreen.MainPane;
import POJO.ConferenceEntity;
import conferenceDetail.ConferenceDetail;
import handlers.Convenience;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

import java.io.IOException;
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
        lvConferences.setPlaceholder(new Label("No conferences to display"));
    }

    @FXML
    private void cellClick(MouseEvent event){
        if(event.getClickCount() == 2){
            ConferenceEntity selectedConference = lvConferences.getSelectionModel().getSelectedItem();
            if(selectedConference != null) {
                try {
                    ConferenceDetail conferenceDetail = Convenience.popupDialog(MainPane.getInstance().getStackPane(),
                            MainPane.getInstance().getBorderPane(), getClass().getResource("/FXML/conference_detail.fxml"));
                    conferenceDetail.setupModel(selectedConference);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
