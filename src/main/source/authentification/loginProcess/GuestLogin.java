package authentification.loginProcess;

import POJO.UserEntity;

/**
 * @created om 7/10/2020
 * @author: Helios - 1712018
 */
public class GuestLogin{
    private CurrentAccountSingleton currentAccountSingleton;

    public void loginAsGuest(){
        currentAccountSingleton = CurrentAccountSingleton.getInstance();
        UserEntity guest = new UserEntity("guest",0);
        currentAccountSingleton.setAccount(guest);
    }
}
