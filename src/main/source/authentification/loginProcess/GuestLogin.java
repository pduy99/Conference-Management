package authentification.loginProcess;

import POJO.UserEntity;

/**
 * Class serving as a concrete strategy to login as a guest
 * @created om 7/10/2020
 * @author: Helios - 1712018
 */
public class GuestLogin{
    private CurrentAccountSingleton currentAccountSingleton;

    /***
     * create a temporary account for guest to log into system
     */
    public void loginAsGuest(){
        currentAccountSingleton = CurrentAccountSingleton.getInstance();
        UserEntity guest = new UserEntity("guest",0);
        currentAccountSingleton.setAccount(guest);
    }
}
