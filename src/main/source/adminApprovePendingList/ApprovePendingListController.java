package adminApprovePendingList;

import DAO.ConferenceDAO;
import DAO.EnrollmentDAO;
import DAO.UserDAO;
import Dashboard.DashboardComponentSingleton;
import POJO.ConferenceEntity;
import POJO.UserConferenceEntity;
import POJO.UserEntity;
import conferenceDetail.ConferenceDetail;
import handlers.Convenience;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import listviewComponent.ConferenceListSingleton;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class ApprovePendingListController implements Initializable {
    @FXML
    private TableView<UserConferenceEntity> tableview;

    @FXML
    private TableColumn<UserConferenceEntity, String> colIndex;

    @FXML
    private TableColumn<UserConferenceEntity, String> colUsername;

    @FXML
    private TableColumn<UserConferenceEntity, String> colConferenceName;

    @FXML
    private TableColumn<UserConferenceEntity, Button> colApproveButton;

    @FXML
    private TableColumn<UserConferenceEntity, Button> colDeleteButton;

    private static ObservableList<UserConferenceEntity> observableList = FXCollections.observableArrayList();
    private static FilteredList<UserConferenceEntity> filteredList;
    private static SortedList<UserConferenceEntity> sortedList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
    }

    private void loadList(){
        List<UserConferenceEntity> tempList = EnrollmentDAO.getAllPending();
        observableList.addAll(tempList);
        filteredList = new FilteredList<>(observableList);
        sortedList = new SortedList<>(filteredList);
    }

    public void init(){
        colIndex.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(tableview.getItems().indexOf(item.getValue()) +1 + ""));
        colUsername.setCellValueFactory(param ->{
            UserEntity user = UserDAO.findByPk(param.getValue().getUserId());
            String res = user.getUsername();
            return new SimpleStringProperty(res);
        });

        colConferenceName.setCellValueFactory(param->{
            ConferenceEntity conference = ConferenceDAO.findByPk(param.getValue().getConferenceId());
            String res = conference.getName();
            return new SimpleStringProperty(res);
        });
        colApproveButton.setCellValueFactory(new ApproveButtonCellValueFactory());
        colDeleteButton.setCellValueFactory(new DeleteButtonCellValueFactory());
        tableview.setItems(sortedList);
        sortedList.comparatorProperty().bind(tableview.comparatorProperty());

    }

    private static void refresh(){
        List<UserConferenceEntity> tempList = EnrollmentDAO.getAllPending();
        observableList.clear();
        observableList.addAll(tempList);
    }

    public static class ApproveButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<UserConferenceEntity,Button>, ObservableValue<Button>> {

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<UserConferenceEntity, Button> param) {
            Button approveButton = new Button();
            approveButton.setText("Approve");
            approveButton.setOnMouseClicked((MouseEvent event)->{
                EnrollmentDAO.approveEnrollment(param.getValue().getUserId(),param.getValue().getConferenceId());
                refresh();
            });
            return new SimpleObjectProperty<>(approveButton);
        }
    }

    public static class DeleteButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<UserConferenceEntity,Button>, ObservableValue<Button>> {

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<UserConferenceEntity, Button> param) {
            Button approveButton = new Button();
            approveButton.setText("Delete");
            approveButton.setOnMouseClicked((MouseEvent event)->{
                EnrollmentDAO.remove(param.getValue().getUserId(),param.getValue().getConferenceId());
                refresh();
            });
            return new SimpleObjectProperty<>(approveButton);
        }
    }
}
