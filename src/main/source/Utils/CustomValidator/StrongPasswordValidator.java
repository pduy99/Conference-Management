package Utils.CustomValidator;

import Utils.StringUtils;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StrongPasswordValidator extends ValidatorBase {

    public StrongPasswordValidator(String message){super(message);}
    public StrongPasswordValidator(){
        InputStream imageURL = getClass().getResourceAsStream("/icons/icon_error.png");
        Image icon = new Image(imageURL);
        this.setIcon(new ImageView(icon));
        this.setMessage("Password is not strong enough");
    }

    @Override
    protected void eval() {
        if(srcControl.get() instanceof TextInputControl){
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(!StringUtils.determineStrongPassword(textField.getText()));
    }
}
