package adminManageUserComponent;

import DAO.UserDAO;
import POJO.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.List;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class UserListSingleton {
    private static UserListSingleton instance;
    private static final Object mutex = new Object();
    private static volatile ObservableList<UserEntity> userEntityObservableList;
    private static FilteredList<UserEntity> filteredList;
    private static SortedList<UserEntity> sortedList;

    public SortedList<UserEntity> getSortedList() {
        return sortedList;
    }

    public void setSortedList(SortedList<UserEntity> sortedList) {
        UserListSingleton.sortedList = sortedList;
    }

    private UserListSingleton(){
        List<UserEntity> tempList = UserDAO.getAllUser();
        userEntityObservableList = FXCollections.observableArrayList();
        userEntityObservableList.addAll(tempList);
        filteredList = new FilteredList<>(userEntityObservableList);
        sortedList = new SortedList<>(filteredList);
    }

    public FilteredList<UserEntity> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(FilteredList<UserEntity> filteredList) {
        UserListSingleton.filteredList = filteredList;
    }

    public static UserListSingleton getInstance(){
        UserListSingleton res = instance;
        if(res == null){
            synchronized (mutex){
                res = instance;
                if(res == null){
                    instance = res = new UserListSingleton();
                }
            }
        }
        return res;
    }

    public void refresh(){
        List<UserEntity> tempList = UserDAO.getAllUser();
        userEntityObservableList.clear();
        userEntityObservableList.addAll(tempList);
    }


}
