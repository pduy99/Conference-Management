package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern STRONG_PASSWORD_REGEX =
            Pattern.compile("^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})",Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^[a-z0-9_-]{3,15}$");

    public static boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean determineStrongPassword(String rawPassword){
        Matcher matcher = STRONG_PASSWORD_REGEX.matcher(rawPassword);
        return matcher.find();
    }

    public static boolean validateUsername(String username){
        Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
        return matcher.find();
    }


}
