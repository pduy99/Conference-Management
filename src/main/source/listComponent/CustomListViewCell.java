package listComponent;

import POJO.ConferenceEntity;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
public class CustomListViewCell extends JFXListCell<ConferenceEntity> {
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

    private FXMLLoader loader;
    private int id;
    private UserEntity account;
    private ConferenceEntity currentConference;
    private String imageULR;
    private Image image;

    @Override
    protected synchronized void updateItem(ConferenceEntity conference, boolean empty){
        super.updateItem(conference,empty);

        if(empty || conference == null){
            setText(null);
            setGraphic(null);
            account = CurrentAccountSingleton.getInstance().getAccount();
        }else{
            boolean isConnecting = true;
            id = conference.getId();
            try{
                if(loader == null) {
                    loader = new FXMLLoader(getClass().getResource("/FXML/listcell.fxml"));
                    loader.setController(this);
                }
                loader.load();
            }catch (Exception e){
                System.out.println("Cannot load listcell");
            }
            currentConference = conference;
            try{
                checkIfHaveEnrolled();
                imageULR = currentConference.getImage();
                if(imageULR == null){
                    image = new Image("file:img/quest.png");
                }else{
                    image = new Image(imageULR);
                }
                cellLogo.setImage(image);
                tfConferenceName.setText(currentConference.getName());
                tfShortDescription.setText(currentConference.getShortDescription());
                tfLocation.setText(currentConference.getPlace());
                tfTime.setText(currentConference.getTime().toLocaleString());
                tfCapacity.setText(String.valueOf(currentConference.getCapacity()));
                setGraphic(boxLayout);
                setText(null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void checkIfHaveEnrolled(){
        if(account == null){
            account = CurrentAccountSingleton.getInstance().getAccount();
        }
        boolean flag = account.getConferences().contains(currentConference);
        if(flag){
            // already enrolled
            btnEnroll.setText("Disenroll me");
        }
    }
}
