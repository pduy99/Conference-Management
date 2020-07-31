package conferenceDetail;

import DAO.ConferenceDAO;
import DAO.LocationDAO;
import DAO.UserDAO;
import Dashboard.DashboardComponentSingleton;
import MainScreen.MainScreenComponentSingleton;
import POJO.ConferenceEntity;
import POJO.UserEntity;
import Utils.DateTimePicker.DateTimePicker;
import alertsDialog.CustomAlertType;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import handlers.Convenience;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import listviewComponent.ConferenceListSingleton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @created on 7/28/2020
 * @author: Helios - 1712018
 */

public class ConferenceDetail implements Initializable {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private ImageView ivConferenceImage;

    @FXML
    private JFXTextField tfConferenceName;

    @FXML
    private Label tfLocation;

    @FXML
    private Label tfTime;

    @FXML
    private Label tfCapacity;

    @FXML
    private Label linkAttendanceList;

    @FXML
    private JFXTextArea tfShortDescription;

    @FXML
    private JFXTextArea tfDetailDescription;

    @FXML
    private JFXButton btnEnroll;

    @FXML
    private JFXButton btnSaveEdit;

    @FXML
    private Label lbChangeLocation;

    @FXML
    private Label lbChangeTime;

    @FXML
    private TableView<String> tableviewAttendanceList;

    @FXML
    private TableColumn<String, String> colIndex;

    @FXML
    private TableColumn<String, String> colDisplayName;

    private ConferenceEntity currentConference;

    @FXML
    private void handleChangeLocation(MouseEvent mouseEvent) {

    }

    @FXML
    private void handleChangeTime(MouseEvent mouseEvent) throws IOException {
        DateTimePicker dateTimePicker = Convenience.popupDialog(rootStackPane,
                DashboardComponentSingleton.getInstance().getRootVBox(),getClass().getResource("/FXML/datetime_picker.fxml"));
        dateTimePicker.getButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Convenience.closePreviousDialog();
                Date date = dateTimePicker.getDateTime();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String strDate = dateFormat.format(date);
                tfTime.setText(strDate);
            }
        });
    }

    @FXML
    private void handleSaveEdit(MouseEvent mouseEvent) throws ParseException {
        ConferenceEntity conference = ConferenceDAO.findByPk(currentConference.getId());
        assert conference != null;
        conference.setImage(ivConferenceImage.getImage().getUrl());
        conference.setDetailDescription(tfDetailDescription.getText());
        conference.setLocation(LocationDAO.findByAddress(tfLocation.getText()));
        conference.setShortDescription(tfShortDescription.getText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = simpleDateFormat.parse(tfTime.getText());
        conference.setTime(date);
        conference.setName(tfConferenceName.getText());

        if(ConferenceDAO.updateConference(conference)){
            Convenience.showAlert(rootStackPane,DashboardComponentSingleton.getInstance().getRootVBox(),CustomAlertType.SUCCESS,"" +
                    "Update conference successfully","");
            ConferenceListSingleton.getInstance().refresh();
        }else{
            Convenience.showAlert(rootStackPane,DashboardComponentSingleton.getInstance().getRootVBox(),CustomAlertType.ERROR,"" +
                    "Update conference failed","Something went wrong, please try again later");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEnroll.managedProperty().bind(btnEnroll.visibleProperty());
        btnSaveEdit.managedProperty().bind(btnSaveEdit.visibleProperty());
        lbChangeLocation.managedProperty().bind(lbChangeLocation.visibleProperty());
        lbChangeTime.managedProperty().bind(lbChangeTime.visibleProperty());

        lbChangeTime.setVisible(false);
        lbChangeLocation.setVisible(false);
        btnSaveEdit.setVisible(false);

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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = dateFormat.format(conference.getTime());
        tfTime.setText(strDate);
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
                    StackPane rootStackPane = MainScreenComponentSingleton.getInstance().getStackPane();
                    BorderPane nodeToBlur = MainScreenComponentSingleton.getInstance().getBorderPane();
                    Convenience.showAlert(rootStackPane,nodeToBlur, CustomAlertType.WARNING,"Sign in to continue","To enroll conferences, you must be signed in.");
                }
                else{
                    UserDAO.EnrollConference(CurrentAccountSingleton.getInstance().getID(), currentConference.getId());
                }
            }
            ConferenceListSingleton.getInstance().refresh();
            setStyleEnrollButton(checkIfHaveEnrolled());
            setupParticipantTableView();
        });
    }

    public void openForEdit(boolean editable){
        if(editable){
            btnEnroll.setVisible(false);
            btnSaveEdit.setVisible(true);
            lbChangeLocation.setVisible(true);
            lbChangeTime.setVisible(true);
            tfShortDescription.setEditable(true);
            tfDetailDescription.setEditable(true);
            tfConferenceName.setEditable(true);
        }
    }

    public void handleChangeBasicInfor(MouseEvent mouseEvent) {

    }
}

