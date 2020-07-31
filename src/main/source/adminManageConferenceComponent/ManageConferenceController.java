package adminManageConferenceComponent;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import Dashboard.DashboardComponentSingleton;
import POJO.ConferenceEntity;
import alertsDialog.CustomAlertType;
import conferenceDetail.ConferenceDetail;
import handlers.Convenience;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import listviewComponent.ConferenceListSingleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class ManageConferenceController implements Initializable {

    @FXML
    private TableView<ConferenceEntity> tableview;

    @FXML
    private TableColumn<ConferenceEntity, String> colIndex;

    @FXML
    private TextField searchBox;

    @FXML
    private Button btnAddConference;

    @FXML
    private TableColumn<ConferenceEntity, String> colConName;

    @FXML
    private TableColumn<ConferenceEntity, String> colTime;

    @FXML
    private TableColumn<ConferenceEntity, String> colLocation;

    @FXML
    private TableColumn<ConferenceEntity, String> colShortDescription;

    @FXML
    private TableColumn<ConferenceEntity, String> colNumEnrollment;

    @FXML
    private TableColumn<ConferenceEntity, Button> colDeleteButton;

    @FXML
    private TableColumn<ConferenceEntity, Button> colDetailButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleSearch();
    }

    public void init(){
        colIndex.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(tableview.getItems().indexOf(item.getValue()) +1 + ""));
        colConName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colShortDescription.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));
        colTime.setCellValueFactory(param->{
            String res = param.getValue().getTime().toLocaleString();
            return new SimpleStringProperty(res);
        });
        colLocation.setCellValueFactory(param->{
            String res = param.getValue().getLocation().getName();
            return new SimpleStringProperty(res);
        });
        colNumEnrollment.setCellValueFactory(param->{
            String res;
            int conferenceID = param.getValue().getId();
            int number = ConferenceDAO.countEnrollment(conferenceID);
            int totalTicket = param.getValue().getLocation().getCapacity();
            res = number + "/" + totalTicket;
            return new SimpleStringProperty(res);
        });
        colDetailButton.setCellValueFactory(new DetailButtonCellValueFactory());
        colDeleteButton.setCellValueFactory(new DeleteButtonCellValueFactory());
        tableview.setItems(ConferenceListSingleton.getInstance().getSortedList());
        ConferenceListSingleton.getInstance().getSortedList().comparatorProperty().bind(tableview.comparatorProperty());
    }

    @FXML
    private void handleAddConference(MouseEvent mouseEvent) {

    }

    public static class DetailButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<ConferenceEntity,Button>, ObservableValue<Button>> {

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<ConferenceEntity, Button> param) {
            Button detailButton = new Button();
            detailButton.setText("Detail");
            detailButton.setOnMouseClicked((MouseEvent event)->{
                try {
                    ConferenceDetail conferenceDetail =  Convenience.popupDialog(DashboardComponentSingleton.getInstance().getRootStackPane(),
                            DashboardComponentSingleton.getInstance().getRootVBox(),getClass().getResource("/FXML/conference_detail.fxml"));
                    conferenceDetail.openForEdit(true);
                    conferenceDetail.setupModel(param.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return new SimpleObjectProperty<>(detailButton);
        }
    }

    public static class DeleteButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<ConferenceEntity,Button>, ObservableValue<Button>> {

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<ConferenceEntity, Button> param) {
            Button deleteButton = new Button();
            deleteButton.setText("Delete");
            deleteButton.setOnMouseClicked((MouseEvent event)->{
                ConferenceEntity conference = ConferenceDAO.findByPk(param.getValue().getId());
                ConferenceDAO.remove(conference);
                ConferenceListSingleton.getInstance().refresh();
            });
            return new SimpleObjectProperty<>(deleteButton);
        }
    }

    private void handleSearch(){
        searchBox.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                String keyword = searchBox.getText();
                if(keyword.equals("")){
                    ConferenceListSingleton.getInstance().getFilteredList().setPredicate(null);
                }
                else{
                    Predicate<ConferenceEntity> searchByKeyWord = item -> item.getName().contains(keyword)
                            || item.getLocation().getName().contains(keyword)
                            || item.getShortDescription().contains(keyword);
                    ConferenceListSingleton.getInstance().getFilteredList().setPredicate(searchByKeyWord);
                }
            }
        });

        searchBox.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals("")){
                ConferenceListSingleton.getInstance().getFilteredList().setPredicate(null);
            }
        });
    }
}
