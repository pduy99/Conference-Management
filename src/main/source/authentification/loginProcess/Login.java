package authentification.loginProcess;

import DB.DBConnection;
import POJO.UserEntity;
import Utils.MD5;
import org.hibernate.Session;

import java.util.List;

/**
 * @created on 7/11/2020
 * @author: Helios - 1712018
 */
public class Login {
    private CurrentAccountSingleton currentAccountSingleton;

    /***
     * This method is validating the credentials and resets the current Account to an User
     * @param username this is username
     * @param password this is password has been hashed
     * @throws IndexOutOfBoundsException when credentials invalid
     */
    public void getAccount(String username, String password) throws IndexOutOfBoundsException{
        Session session = DBConnection.getSessionFactory().openSession();
        String hql = "from POJO.UserEntity as u where u.username = :username and u.password = :password";
        List list = session.createQuery(hql)
                .setParameter("username",username)
                .setParameter("password",password)
                .list();
        session.close();

        currentAccountSingleton = CurrentAccountSingleton.getInstance();
        currentAccountSingleton.setAccount((UserEntity) list.get(0));
    }

    /***
     * create a temporary account and set that account to current account singleton
     */
    public void loginAsGuest(){
        currentAccountSingleton = CurrentAccountSingleton.getInstance();
        UserEntity guest = new UserEntity("Guest",0);
        currentAccountSingleton.setAccount(guest);
    }
}
