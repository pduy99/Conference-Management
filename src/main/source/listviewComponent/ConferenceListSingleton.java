package listviewComponent;

import DAO.ConferenceDAO;
import POJO.ConferenceEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

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

    private ConferenceListSingleton(){
        List<ConferenceEntity> tempList = ConferenceDAO.getAll();
        conferenceObservableList = FXCollections.observableArrayList();
        conferenceObservableList.addAll(tempList);
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

    public ObservableList<ConferenceEntity> getConferenceObservableList(){
        return conferenceObservableList;
    }

    public void setConferenceObservableList(ObservableList<ConferenceEntity> observableList){
        conferenceObservableList = observableList;

    }

    public void refresh(){
        List<ConferenceEntity> tempList = ConferenceDAO.getAll();
        conferenceObservableList.clear();
        conferenceObservableList.addAll(tempList);
    }

    public void setConferenceListView(ListView<ConferenceEntity> listView){
        conferenceListView = listView;
    }
}
