
package handlers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class which handles loss of internet connection,
 * displays a Pop Up with the respective message,
 * polls the server in a loop until connection is reestablished
 * and locks the UI in the meanwhile
 * @created on 7/12/2020
 * @author: Helios - 1712018
 */


public class NoInternet implements Initializable {

    private FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pollServer();
    }

    /**
     * Method which polls the server in the loop until connection is reestablished
     */
    private synchronized void pollServer(){
        Thread loop = new Thread(()->{
            while (!InternetHandler.checkInternetConnection()){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Convenience.getDialog().setOverlayClose(false);
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            Convenience.getDialog().setOverlayClose(true);
            Convenience.closePreviousDialog();
        });
        if(!loop.isAlive()){
            loop.start();
        }
    }
}

