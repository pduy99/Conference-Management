package Utils.DateTimePicker;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class DateTimePicker implements Initializable {

    public JFXDatePicker date;
    public JFXTimePicker time;
    public Button btnOK;

    public Button getButton(){
        return btnOK;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        time.getEditor().setStyle("-fx-text-fill: white");
        date.getEditor().setStyle("-fx-text-fill: white");
        time.set24HourView(true);
    }

    public Date getDateTime(){
        Date res =null;
        String stringDate = date.getEditor().getText();
        String stringTime = time.getEditor().getText();
        String stringHour = stringTime.split(":")[0];
        if(stringTime.split(" ")[1].equals("PM")){
            stringHour = String.valueOf((Integer.parseInt(stringHour) + 12));
        }
        String stringMinute = stringTime.split(":")[1];


        String stringDateTime = stringDate + " " + stringHour + ":" + stringMinute;

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            res = formatter.parse(stringDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
}
