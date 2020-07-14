package alertsDialog;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * A class that represents the custom JFoenix error alert dialog
 * @created on 7/11/2020
 * @author: Helios - 1712018
 */
public class CustomAlertError extends CustomAlert {
    @Override
    public void setBody() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/alert_error.fxml"));
        try {
            Parent node = loader.load();
            dialog.setContent((Region)node);
            this.confirmButton = (JFXButton)loader.getNamespace().get("btnConfirm");
            this.header = (Text)loader.getNamespace().get("tfHeader");
            this.content = (Text)loader.getNamespace().get("tfContent");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
