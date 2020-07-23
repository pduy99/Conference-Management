package tableviewComponent;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import POJO.ConferenceEntity;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import listviewComponent.ConferenceListSingleton;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @created on 7/22/2020
 * @author: Helios - 1712018
 */
public class TableViewController implements Initializable {

    @FXML
    private TableView<ConferenceEntity> tableview;

    @FXML
    private TableColumn<ConferenceEntity, String> colName;

    @FXML
    private TableColumn<ConferenceEntity, String> colShortDescription;

    @FXML
    private TableColumn<ConferenceEntity, String> colTime;

    @FXML
    private TableColumn<ConferenceEntity, String> colLocation;

    @FXML
    private TableColumn<ConferenceEntity, String> colTicket;

    @FXML
    private TableColumn<ConferenceEntity, Button> colEnrollButton;

    @FXML
    private TableColumn<ConferenceEntity, String> colStatus;

    private ObservableList<ConferenceEntity> conferencesObservableList;
    private static UserEntity account;
    private int currentAccountID;

    public TableViewController(){
        try{
            ConferenceListSingleton conferenceListSingleton = ConferenceListSingleton.getInstance();
            conferencesObservableList = conferenceListSingleton.getConferenceObservableList();
            account = CurrentAccountSingleton.getInstance().getAccount();
            currentAccountID = account.getId();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colShortDescription.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));
        colTime.setCellValueFactory(param->{
            String res = param.getValue().getTime().toLocaleString();
            return new SimpleStringProperty(res);
        });
        colLocation.setCellValueFactory(param->{
            String res = param.getValue().getLocation().getName();
            return new SimpleStringProperty(res);
        });
        colTicket.setCellValueFactory(param->{
            String res;
            int conferenceID = param.getValue().getId();
            int number = ConferenceDAO.countEnrollment(conferenceID);
            int totalTicket = param.getValue().getLocation().getCapacity();
            int ticketLeft = totalTicket - number;
            res = ticketLeft + "/" + totalTicket;
            return new SimpleStringProperty(res);
        });
        colStatus.setCellValueFactory(param->{
            String res;
            boolean isEnrolled = Objects.requireNonNull(UserDAO.findByPk(currentAccountID)).getConferences().contains(param.getValue());
            if(!isEnrolled){
                res = "";
            }else{
                if(UserDAO.checkApprovedEnrollment(account.getId(),param.getValue().getId())){
                    res = "Approved";
                }
                else{
                    res = "Pending Approval";
                }
            }
            return new SimpleStringProperty(res);
        });

        colEnrollButton.setCellValueFactory(new EnrollButtonCellValueFactory());
        tableview.setItems(conferencesObservableList);
        tableview.setPlaceholder(new Label("No conferences to display"));
    }

    private void update(){
        conferencesObservableList.clear();
        ConferenceListSingleton conferenceListSingleton = ConferenceListSingleton.getInstance();
        conferenceListSingleton.refresh();
        conferencesObservableList = conferenceListSingleton.getConferenceObservableList();
        tableview.setItems(conferencesObservableList);
    }

    public class EnrollButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<ConferenceEntity,Button>,ObservableValue<Button>>{

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<ConferenceEntity, Button> param) {
            Button enrollButton = new Button();
            ConferenceEntity conference = param.getValue();
            UserEntity user = UserDAO.findByPk(currentAccountID);
            if(user.getConferences().contains(conference)) {
                enrollButton.setText("Disenroll me");
                enrollButton.setOnMouseClicked((MouseEvent event)->{
                    UserDAO.DisEnrollConference(currentAccountID,conference.getId());
                    update();
                });
            }
            else{
                enrollButton.setText("Enroll me");
                enrollButton.setOnMouseClicked((MouseEvent event)->{
                    UserDAO.EnrollConference(currentAccountID,conference.getId());
                    update();
                });
            }
            return new SimpleObjectProperty<>(enrollButton);
        }
    }
}
