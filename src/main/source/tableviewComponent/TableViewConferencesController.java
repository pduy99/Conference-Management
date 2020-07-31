package tableviewComponent;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import MainScreen.MainScreenComponentSingleton;
import POJO.ConferenceEntity;
import POJO.UserEntity;
import alertsDialog.CustomAlertType;
import authentification.loginProcess.CurrentAccountSingleton;
import conferenceDetail.ConferenceDetail;
import handlers.Convenience;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import listviewComponent.ConferenceListSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @created on 7/22/2020
 * @author: Helios - 1712018
 */
public class TableViewConferencesController implements Initializable {

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

    private UserEntity currentAccount;
    private int currentAccountID;

    public TableViewConferencesController(){
        try{
            currentAccount = CurrentAccountSingleton.getInstance().getAccount();
            currentAccountID = currentAccount.getId();
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
            if(!checkIfHaveEnrolled(currentAccountID,param.getValue().getId())){
                res = "";
            }else{
                if(UserDAO.checkApprovedEnrollment(currentAccountID,param.getValue().getId())){
                    res = "Approved";
                }
                else{
                    res = "Pending Approval";
                }
            }
            return new SimpleStringProperty(res);
        });

        colEnrollButton.setCellValueFactory(new EnrollButtonCellValueFactory());
        tableview.setItems(ConferenceListSingleton.getInstance().getSortedList());
        ConferenceListSingleton.getInstance().getSortedList().comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setPlaceholder(new Label("No conferences to display"));
        tableview.setRowFactory(tv->{
            TableRow<ConferenceEntity> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    try {
                        ConferenceDetail conferenceDetail = Convenience.popupDialog(MainScreenComponentSingleton.getInstance().getStackPane(),
                                MainScreenComponentSingleton.getInstance().getBorderPane(),getClass().getResource("/FXML/conference_detail.fxml"));
                        conferenceDetail.setupModel(row.getItem());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public class EnrollButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<ConferenceEntity,Button>,ObservableValue<Button>>{

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<ConferenceEntity, Button> param) {
            Button enrollButton = new Button();
                if (checkIfHaveEnrolled(currentAccountID, param.getValue().getId())) {
                    enrollButton.setText("Disenroll me");
                    enrollButton.setOnMouseClicked((MouseEvent event) -> {
                        UserDAO.DisEnrollConference(currentAccountID, param.getValue().getId());
                        //update();
                        ConferenceListSingleton.getInstance().refresh();
                    });
                } else {
                    enrollButton.setText("Enroll me");
                    enrollButton.setOnMouseClicked((MouseEvent event) -> {
                        if (currentAccount.getRole() == 0) {
                            StackPane rootStackPane = MainScreenComponentSingleton.getInstance().getStackPane();
                            BorderPane nodeToBlur = MainScreenComponentSingleton.getInstance().getBorderPane();
                            Convenience.showAlert(rootStackPane, nodeToBlur, CustomAlertType.WARNING, "Sign in to continue", "To enroll conferences, you must be signed in.");
                        } else {
                            UserDAO.EnrollConference(currentAccountID, param.getValue().getId());
                            //update();
                            ConferenceListSingleton.getInstance().refresh();
                        }
                    });
                }
            return new SimpleObjectProperty<>(enrollButton);
        }
    }

    private boolean checkIfHaveEnrolled(int userID, int conferenceID){
        ConferenceEntity conference = ConferenceDAO.findByPk(conferenceID);
        UserEntity account = UserDAO.findByPk(userID);
        if(account==null){
            //Guest login
            return false;
        }
        return account.getConferences().contains(conference);
    }
}
