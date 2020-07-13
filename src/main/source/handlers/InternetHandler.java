package handlers;

import java.net.URL;
import java.net.URLConnection;

/**
 * This class checks whether users has internet connection or not
 * @created on 7/12/2020
 * @author: Helios - 1712018
 */
public class InternetHandler {
    private static String test_url = "https://www.google.com.vn/";

    /***
     *  This method pings www.google.com and returns true if everything is fine
     *  if false,user has no internet connection
     * @return {@link Boolean}
     */
    public static boolean checkInternetConnection(){
        try{
            final URL url = new URL(test_url);
            final URLConnection connection = url.openConnection();
            connection.connect();
            connection.getInputStream().close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
