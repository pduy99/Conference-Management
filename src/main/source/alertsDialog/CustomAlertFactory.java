package alertsDialog;

/**
 * @created on 7/11/2020
 * @author: Helios - 1712018
 */
public class CustomAlertFactory {
    public CustomAlert createAlert(CustomAlertType type){
        switch (type){
            case ERROR:
                return new CustomAlertError();
            case WARNING:
                return new CustomAlertWarning();
            case SUCCESS:
                return new CustomAlertSuccess();
            default:
                return null;
        }
    }
}
