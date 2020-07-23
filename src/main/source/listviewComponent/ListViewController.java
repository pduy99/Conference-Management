package listviewComponent;

import POJO.ConferenceEntity;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import javafx.collections.ObservableList;
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

    private ObservableList<ConferenceEntity> conferencesObservableList;
    private ConferenceEntity selectedConference;
    private UserEntity account;

    public ListViewController(){
        try{
            ConferenceListSingleton conferenceListSingleton = ConferenceListSingleton.getInstance();
            conferencesObservableList = conferenceListSingleton.getConferenceObservableList();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvConferences.setItems(conferencesObservableList);
        lvConferences.setCellFactory(customListViewCell -> new CustomListViewCell());
        account = CurrentAccountSingleton.getInstance().getAccount();
        ConferenceListSingleton.getInstance().setConferenceListView(lvConferences);
    }
}
