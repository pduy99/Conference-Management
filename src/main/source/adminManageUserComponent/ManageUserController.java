package adminManageUserComponent;

import DAO.UserDAO;
import POJO.ConferenceEntity;
import POJO.UserEntity;
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

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class ManageUserController implements Initializable {

    @FXML
    private TableView<UserEntity> tableview;

    @FXML
    private TextField searchBox;


    @FXML
    private TableColumn<UserEntity, String> colID;

    @FXML
    private TableColumn<UserEntity, String> colUserName;

    @FXML
    private TableColumn<UserEntity, String> colDisplayName;

    @FXML
    private TableColumn<UserEntity, String> colEmail;

    @FXML
    private TableColumn<UserEntity,String> colBirthday;

    @FXML
    private TableColumn<UserEntity, String> colCreateDate;

    @FXML
    private TableColumn<UserEntity, String> colStatus;

    @FXML
    private TableColumn<UserEntity, Button> colActionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleSearch();
    }

    public void init(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBirthday.setCellValueFactory(param->{
            String birthday = "";
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if(param.getValue().getBirthday() != null) {
                birthday = dateFormat.format(param.getValue().getBirthday());
            }
            return new SimpleStringProperty(birthday);
        });
        colCreateDate.setCellValueFactory(param->{
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String createDate = dateFormat.format(param.getValue().getCreateDate());
            return new SimpleStringProperty(createDate);
        });
        colActionButton.setCellValueFactory(new BlockButtonCellValueFactory());
        colStatus.setCellValueFactory(param->{
            String res = "";
            if (param.getValue().getBlocked() != null){
                res = "Blocked";
            }
            return new SimpleStringProperty(res);
        });
        tableview.setItems(UserListSingleton.getInstance().getSortedList());
        UserListSingleton.getInstance().getSortedList().comparatorProperty().bind(tableview.comparatorProperty());
    }

    private static class BlockButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<UserEntity,Button>, ObservableValue<Button>> {

        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<UserEntity, Button> param) {
            if (param.getValue().getRole() != 2) {
                Button enrollButton = new Button();
                if (param.getValue().getBlocked() == null) {
                    enrollButton.setText("Block");
                    enrollButton.setOnMouseClicked((MouseEvent event) -> {
                        UserDAO.blockUser(param.getValue().getId());
                        //update();
                        UserListSingleton.getInstance().refresh();
                    });
                } else {
                    enrollButton.setText("Unblock");
                    enrollButton.setOnMouseClicked((MouseEvent event) -> {
                        UserDAO.unblockUser(param.getValue().getId());
                        //update();
                        UserListSingleton.getInstance().refresh();
                    });
                }
                return new SimpleObjectProperty<>(enrollButton);
            }
            return null;
        }
    }

    private void handleSearch(){
        searchBox.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                String keyword = searchBox.getText();
                if(keyword.equals("")){
                    UserListSingleton.getInstance().getFilteredList().setPredicate(null);
                }
                else{
                    Predicate<UserEntity> searchByKeyWord = item -> item.getUsername().contains(keyword)
                            || item.getDisplayName().contains(keyword)
                            || item.getEmail().contains(keyword);
                    UserListSingleton.getInstance().getFilteredList().setPredicate(searchByKeyWord);
                }
            }
        });

        searchBox.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals("")){
                UserListSingleton.getInstance().getFilteredList().setPredicate(null);
            }
        });
    }
}
