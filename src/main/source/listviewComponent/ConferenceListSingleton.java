package listviewComponent;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import POJO.ConferenceEntity;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @created on 7/15/2020
 * @author: Helios - 1712018
 */
public class ConferenceListSingleton {
    private static ConferenceListSingleton instance;
    private static final Object mutex = new Object();
    private static volatile ObservableList<ConferenceEntity> conferenceObservableList;
    private static ListView<ConferenceEntity> conferenceListView;
    private static FilteredList<ConferenceEntity> filteredList;
    private int listType = 0; //0: all conference list, 1: my conference list

    private ConferenceListSingleton(){
        List<ConferenceEntity> tempList = ConferenceDAO.getAll();
        conferenceObservableList = FXCollections.observableArrayList();
        conferenceObservableList.addAll(tempList);
        filteredList = new FilteredList<>(conferenceObservableList);
        conferenceListView = new ListView<>();
        conferenceListView.setItems(filteredList);
    }

    public static ConferenceListSingleton getInstance(){
        ConferenceListSingleton res = instance;
        if(res == null){
            synchronized (mutex){
                res = instance;
                if(res == null){
                    instance = res = new ConferenceListSingleton();
                }
            }
        }
        return res;
    }

    public void setConferenceListView(ListView<ConferenceEntity> conferenceListView) {
        ConferenceListSingleton.conferenceListView = conferenceListView;
    }

    public ListView<ConferenceEntity> getConferenceListView() {
        return conferenceListView;
    }

    public ObservableList<ConferenceEntity> getConferenceObservableList(){
        return conferenceObservableList;
    }

    public void setConferenceObservableList(ObservableList<ConferenceEntity> observableList){
        conferenceObservableList = observableList;
    }

    public void refresh(){
        if(listType == 0)
        {
            getAllConference();
        }
        if(listType == 1){
            getMyConferenceList(CurrentAccountSingleton.getInstance().getID());
        }
    }

    public void getAllConference(){
        List<ConferenceEntity> tempList = ConferenceDAO.getAll();
        conferenceObservableList.clear();
        conferenceObservableList.addAll(tempList);
        listType = 0;
    }

    public void getMyConferenceList(int userID){
        UserEntity user = UserDAO.findByPk(userID);
        assert user != null;
        List<ConferenceEntity> myConferenceList = new ArrayList<>(user.getConferences());
        conferenceObservableList.clear();
        conferenceObservableList.addAll(myConferenceList);
        listType = 1;
    }

    public FilteredList<ConferenceEntity> getFilteredList() {
        return filteredList;
    }
}
