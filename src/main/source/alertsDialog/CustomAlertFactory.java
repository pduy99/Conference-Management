package alertsDialog;

/**
 * A factory class, which is responsible for creating
 * specific custom alerts based on the given type.
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
