package Utils.CustomValidator;

import Utils.StringUtils;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsernameValidator extends ValidatorBase {
    public UsernameValidator(String message) {
        super(message);
    }

    public UsernameValidator() {
        try {
            Image icon = new Image(new FileInputStream("icons/icon_error.png"));
            this.setIcon(new ImageView(icon));
        }catch (FileNotFoundException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,null,ex);
        }
        this.setMessage("Username is not valid");
    }

    @Override
    protected void eval() {
        if(srcControl.get() instanceof TextInputControl){
            evalTextInputField();
        }
    }

    public void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(!StringUtils.validateUsername(textField.getText()));
    }
}
