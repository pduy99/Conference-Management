package alertsDialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @created on 7/11/2020
 * @author: Helios - 1712018
 */
public abstract class CustomAlert {
    private BoxBlur blur = new BoxBlur(3,3,3);
    protected Text header;
    protected Text content;
    protected JFXButton confirmButton;
    protected JFXDialog dialog;

    public CustomAlert(){
        dialog = new JFXDialog();
        header = new Text();
        content = new Text();
        confirmButton = new JFXButton();

        this.setBody();
    }

    public void showAlert(StackPane rootPane, Node nodeToBlur, String header, String content){
        dialog.setDialogContainer(rootPane);
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        this.header.setText(header);
        this.content.setText(content);
        this.confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            dialog.close();
        });
        nodeToBlur.setEffect(blur);
        dialog.setOnDialogClosed((JFXDialogEvent e)->{
            nodeToBlur.setEffect(null);
        });
        dialog.show();
    }

    public abstract void setBody();
}
