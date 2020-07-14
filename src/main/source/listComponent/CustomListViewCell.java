package listComponent;

import POJO.ConferenceEntity;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

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
    private Label descriptionLabel;

    @FXML
    private JFXButton btnEnroll;

    @FXML
    private Label locationLabel;

    @FXML
    private Label priceLabel;

    private int id;
    private UserEntity account;

    @Override
    protected synchronized void updateItem(ConferenceEntity conference, boolean empty){
        super.updateItem(conference,empty);

        if(empty || conference == null){
            setText(null);
            setGraphic(null);
            account = CurrentAccountSingleton.getInstance().getAccount();
        }else{
            boolean isConnecting = true;
            setText(null);
            id = conference.getId();
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/listcell.fxml"));
                loader.setController(this);
                loader.load();
            }catch (Exception ignored){ }
        }
    }

    protected void checkIfHaveEnrolled(){

    }
}
