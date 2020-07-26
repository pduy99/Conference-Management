package MainScreen;

import POJO.ConferenceEntity;
import POJO.UserEntity;
import authentification.loginProcess.CurrentAccountSingleton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import listviewComponent.ConferenceListSingleton;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainScreenController implements Initializable {

    @FXML
    StackPane rootPane;

    @FXML
    BorderPane borderPane;

    @FXML
    Pane paneToolbar;

   @FXML
   TextField searchBox;

   @FXML
   JFXComboBox<Label> cbViewStyle;

   @FXML
    ImageView btnExit;

   @FXML
   ImageView btnMinimize;

   @FXML
    Circle ivAvatarRound;

   @FXML
    Text tfUserDisplayName;

    @FXML
    VBox listviewLayout;

    @FXML
    VBox tableviewLayout;

   @FXML
   VBox userFunctionNav;


    private Stage stage = null;
    private double xOffset = 0;
    private double yOffset = 0;
    private UserEntity account;
    private Object SimpleObjectProperty;

    @FXML
    public void handleMouseClicked(MouseEvent event){
        if(event.getSource() == btnExit){
            System.exit(0);
        }
        if(event.getSource() == btnMinimize){
            ((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainPane.getInstance().setStackPane(rootPane);
        MainPane.getInstance().setBorderPane(borderPane);

        makeDraggable(paneToolbar);
        setupCombobox();
        setLogin();
        setupViewStyle();
        handleSearch();
    }

    private void setLogin(){
        account = CurrentAccountSingleton.getInstance().getAccount();
        tfUserDisplayName.textProperty().bind(new SimpleStringProperty(account.getDisplayName()));
    }

    private void setupCombobox(){
        cbViewStyle.getItems().add(new Label("Card view"));
        cbViewStyle.getItems().add(new Label("Table view"));
        cbViewStyle.getSelectionModel().selectFirst();
    }

    private void makeDraggable(final Node node){
        node.setOnMousePressed((MouseEvent event)->{
            if(stage==null){
                stage = (Stage)node.getScene().getWindow();
            }
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        node.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private void setupViewStyle(){
        tableviewLayout.managedProperty().bind(tableviewLayout.visibleProperty());
        listviewLayout.managedProperty().bind(listviewLayout.visibleProperty());

        //Default list view appears when main screen launch
        tableviewLayout.setVisible(false);
        listviewLayout.setVisible(true);

        cbViewStyle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
            @Override
            public void changed(ObservableValue<? extends Label> observableValue, Label label, Label t1) {
                if(t1.getText().equals("Card view")){
                    listviewLayout.setVisible(true);
                    tableviewLayout.setVisible(false);
                }
                if(t1.getText().equals("Table view")){
                    tableviewLayout.setVisible(true);
                    listviewLayout.setVisible(false);
                }
            }
        });
    }

    private void handleSearch(){
        searchBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("")){
                    ConferenceListSingleton.getInstance().getFilteredList().setPredicate(null);
                }else {
                    Predicate<ConferenceEntity> nameContains = i -> i.getName().contains(t1);
                    Predicate<ConferenceEntity> locationContains = i->i.getLocation().getName().contains(t1);
                    ConferenceListSingleton.getInstance().getFilteredList().setPredicate(nameContains);
                    ConferenceListSingleton.getInstance().getFilteredList().setPredicate(locationContains);
                }
            }
        });
    }

    private Stage getStage(){
        return (Stage)rootPane.getScene().getWindow();
    }
}
