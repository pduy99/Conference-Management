
package handlers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @created on 7/12/2020
 * @author: Helios - 1712018
 */


public class NoInternet implements Initializable {

    private FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pollServer();
    }

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

