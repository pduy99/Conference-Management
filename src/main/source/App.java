import DB.DBConnection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends javafx.application.Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Views/MainScreen/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed((MouseEvent event) ->{
           xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setScene(scene);
        stage.show();
        System.out.println(connectDatabase());
    }

    public static void main(String[] args){
        launch(args);
    }

    public boolean connectDatabase(){
        try{
            DBConnection.getSessionFactory();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
