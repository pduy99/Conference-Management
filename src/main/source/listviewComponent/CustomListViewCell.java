package listviewComponent;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import MainScreen.MainScreenComponentSingleton;
import POJO.ConferenceEntity;
import POJO.LocationEntity;
import POJO.UserEntity;
import alertsDialog.CustomAlertType;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import handlers.Convenience;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
public class CustomListViewCell extends JFXListCell<ConferenceEntity> implements Initializable {
    @FXML
    private HBox boxLayout;

    @FXML
    private ImageView cellLogo;

    @FXML
    private Label tfConferenceName;

    @FXML
    private Text tfShortDescription;

    @FXML
    private JFXButton btnEnroll;

    @FXML
    private Label tfLocation;

    @FXML
    private Label tfTime;

    @FXML
    private Label tfCapacity;

    @FXML
    private ImageView ivStatus;

    private FXMLLoader loader;
    private int conferenceID;
    private int currentAccountID;
    private ConferenceEntity currentConference;
    private String imageULR;
    private Image image;
    private LocationEntity location;

    @Override
    protected synchronized void updateItem(ConferenceEntity conference, boolean empty){
        super.updateItem(conference,empty);

        if(empty || conference == null){
            setText(null);
            setGraphic(null);
        }else{
            try{
                if(loader == null) {
                    loader = new FXMLLoader(getClass().getResource("/FXML/listcell.fxml"));
                    loader.setController(this);
                }
                loader.load();
            }catch (Exception ignored){ }
            currentConference = conference;
            location = conference.getLocation();
            conferenceID = conference.getId();
            try{
                setTicketText();
                setStyleEnrollButton(checkIfHaveEnrolled());
                setStatusImage();
                tfConferenceName.setText(currentConference.getName());
                tfShortDescription.setText(currentConference.getShortDescription());
                tfLocation.setText(location.getName());
                tfTime.setText(currentConference.getTime().toLocaleString());
                imageULR = currentConference.getImage();
                if(imageULR == null){
                    InputStream imageURL = getClass().getResourceAsStream("/img/quest.png");
                    image = new Image(imageURL);
                }else{
                    image = new Image(imageULR);
                }
                cellLogo.setImage(image);
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                setGraphic(boxLayout);
                setText(null);
            }
        }
    }

    protected boolean checkIfHaveEnrolled(){
        currentAccountID = CurrentAccountSingleton.getInstance().getID();
        UserEntity account = UserDAO.findByPk(currentAccountID);
        if(account==null){
            //Guest login
            return false;
        }
        return account.getConferences().contains(currentConference);
    }

    protected void setStyleEnrollButton(boolean isEnrolled){
            int ticketLeft = location.getCapacity() - ConferenceDAO.countEnrollment(conferenceID);
            if (ticketLeft == 0) {
                btnEnroll.setDisable(true);
            }
            if (isEnrolled) {
                // already enrolled
                btnEnroll.setText("Disenroll me");
                btnEnroll.setStyle("-fx-background-color: #B00805;");
            }
            if (!isEnrolled) {
                btnEnroll.setText("Enroll me");
                btnEnroll.setStyle("-fx-background-color: #18C428;");
            }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEffectLocationLabel();
        handleEnrollButton();
    }

    private void setEffectLocationLabel(){
        tfLocation.setOnMouseEntered((MouseEvent event)-> tfLocation.setText(location.getAddress()));
        tfLocation.setOnMouseExited((MouseEvent event)-> tfLocation.setText(location.getName()));
    }

    private void handleEnrollButton(){
        btnEnroll.setOnMouseClicked((MouseEvent event)->{
            if(checkIfHaveEnrolled()){
                UserDAO.DisEnrollConference(currentAccountID, conferenceID);
            }
            else{
                if(CurrentAccountSingleton.getInstance().getRole() ==0){
                    //Guest
                    StackPane rootStackPane = MainScreenComponentSingleton.getInstance().getStackPane();
                    BorderPane nodeToBlur = MainScreenComponentSingleton.getInstance().getBorderPane();
                    Convenience.showAlert(rootStackPane,nodeToBlur, CustomAlertType.WARNING,"Sign in to continue","To enroll conferences, you must be signed in.");
                }
                else{
                    UserDAO.EnrollConference(currentAccountID,conferenceID);
                }
            }
            ConferenceListSingleton.getInstance().refresh();
        });
    }

    private void setStatusImage(){
        if(checkIfHaveEnrolled()){
            ivStatus.setVisible(true);
            if(!UserDAO.checkApprovedEnrollment(currentAccountID,conferenceID)){
                InputStream imageURL = getClass().getResourceAsStream("/img/pending_stamp.png");
                image = new Image(imageURL);
                ivStatus.setImage(image);
            }
            else{
                InputStream imageURL = getClass().getResourceAsStream("/img/approved_stamp.png");
                image = new Image(imageURL);
                ivStatus.setImage(image);
            }
        }else{
            ivStatus.setVisible(false);
        }
    }

    private void setTicketText(){
        int number = ConferenceDAO.countEnrollment(conferenceID);
        int totalTicket = location.getCapacity();
        int ticketLeft = totalTicket - number;
        tfCapacity.setText(ticketLeft + "/" + totalTicket);
    }
}
