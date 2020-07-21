package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @created on 7/20/2020
 * @author: Helios - 1712018
 */
public class MD5 {
    public static String getMD5(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1,messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while(hashText.length()<32){
                hashText.insert(0, "0");
            }
            return hashText.toString();

        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
