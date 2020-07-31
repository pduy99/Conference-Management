package Dashboard;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class DashboardComponentSingleton {
    private static DashboardComponentSingleton instance;

    private static StackPane rootStackPane;
    private static VBox rootVBox;

    private DashboardComponentSingleton(){}

    public StackPane getRootStackPane() {
        return rootStackPane;
    }

    public void setRootStackPane(StackPane rootStackPane) {
        DashboardComponentSingleton.rootStackPane = rootStackPane;
    }

    public VBox getRootVBox() {
        return rootVBox;
    }

    public void setRootVBox(VBox rootVBox) {
        DashboardComponentSingleton.rootVBox = rootVBox;
    }

    public static DashboardComponentSingleton getInstance(){
        if(instance == null){
            instance = new DashboardComponentSingleton();
        }
        return instance;
    }


}
