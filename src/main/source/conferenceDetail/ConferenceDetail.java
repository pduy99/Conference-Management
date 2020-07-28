package conferenceDetail;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import MainScreen.MainPane;
import POJO.ConferenceEntity;
import POJO.UserEntity;
import alertsDialog.CustomAlertType;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import handlers.Convenience;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import listviewComponent.ConferenceListSingleton;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/28/2020
 * @author: Helios - 1712018
 */

public class ConferenceDetail implements Initializable {

    @FXML
    private ImageView ivConferenceImage;

    @FXML
    private Label tfConferenceName;

    @FXML
    private Label tfLocation;

    @FXML
    private Label tfTime;

    @FXML
    private Label tfCapacity;

    @FXML
    private Label linkAttendanceList;

    @FXML
    private Text tfShortDescription;

    @FXML
    private Text tfDetailDescription;

    @FXML
    private JFXButton btnEnroll;

    @FXML
    private TableView<String> tableviewAttendanceList;

    @FXML
    private TableColumn<String, String> colIndex;

    @FXML
    private TableColumn<String, String> colDisplayName;

    private ConferenceEntity currentConference;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        linkAttendanceList.setOnMouseClicked((MouseEvent event)-> setupParticipantTableView());
        handleEnrollButton();
    }

    public void setupModel(ConferenceEntity conference){
        this.currentConference = conference;

        // Image
        if(conference.getImage() == null){
            InputStream imageURL = getClass().getResourceAsStream("/img/quest.png");
            ivConferenceImage.setImage(new Image(imageURL));
        }else{
            ivConferenceImage.setImage(new Image(conference.getImage()));
        }

        //Name
        tfConferenceName.setText(conference.getName());
        //Location
        tfLocation.setText(conference.getLocation().getAddress());
        //Time
        tfTime.setText(conference.getTime().toLocaleString());
        //Capacity
        setTicketText();
        //Short description
        tfShortDescription.setText(conference.getShortDescription());
        //Detail description
        tfDetailDescription.setText(conference.getDetailDescription());
        //Enroll Button
        setStyleEnrollButton(checkIfHaveEnrolled());
    }

    private void setTicketText(){
        int number = ConferenceDAO.countEnrollment(currentConference.getId());
        int totalTicket = currentConference.getLocation().getCapacity();
        int ticketLeft = totalTicket - number;
        tfCapacity.setText(ticketLeft + "/" + totalTicket);
    }

    private void setStyleEnrollButton(boolean isEnrolled){
        int ticketLeft = currentConference.getLocation().getCapacity() - ConferenceDAO.countEnrollment(currentConference.getId());
        if(ticketLeft == 0){
            btnEnroll.setDisable(true);
        }
        if(isEnrolled){
            // already enrolled
            btnEnroll.setText("Disenroll me");
            btnEnroll.setStyle("-fx-background-color: #B00805;");
        }
        if(!isEnrolled){
            btnEnroll.setText("Enroll me");
            btnEnroll.setStyle("-fx-background-color: #18C428;");
        }
    }

    private boolean checkIfHaveEnrolled(){
        UserEntity account = UserDAO.findByPk(CurrentAccountSingleton.getInstance().getID());
        if(account==null){
            //Guest login
            return false;
        }
        return account.getConferences().contains(currentConference);
    }

    private void setupParticipantTableView(){
        ObservableList<String> listParticipants = FXCollections.observableArrayList(ConferenceDAO.getListParticipantName(currentConference.getId()));
        colDisplayName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        colIndex.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(tableviewAttendanceList.getItems().indexOf(item.getValue()) +1 + ""));
        tableviewAttendanceList.setItems(listParticipants);
    }

    private void handleEnrollButton(){
        btnEnroll.setOnMouseClicked((MouseEvent event)->{
            if(checkIfHaveEnrolled()){
                UserDAO.DisEnrollConference(CurrentAccountSingleton.getInstance().getID(), currentConference.getId());
            }
            else{
                if(CurrentAccountSingleton.getInstance().getRole() ==0){
                    //Guest
                    StackPane rootStackPane = MainPane.getInstance().getStackPane();
                    BorderPane nodeToBlur = MainPane.getInstance().getBorderPane();
                    Convenience.showAlert(rootStackPane,nodeToBlur, CustomAlertType.WARNING,"Sign in to continue","To enroll conferences, you must be signed in.");
                }
                else{
                    UserDAO.EnrollConference(CurrentAccountSingleton.getInstance().getID(), currentConference.getId());
                }
            }
            ConferenceListSingleton.getInstance().refresh();
            setStyleEnrollButton(checkIfHaveEnrolled());
        });
    }
}

