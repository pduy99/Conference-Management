package authentification.loginProcess;

import POJO.UserEntity;

/**
 * Singleton class that holds the instance of the current account logged into system
 * @created om 7/10/2020
 * @author: Helios - 1712018
 */
public class CurrentAccountSingleton {
    private static CurrentAccountSingleton instance = null;
    private static Object mutex = new Object();
    private UserEntity user;

    private CurrentAccountSingleton(){}

    public static CurrentAccountSingleton getInstance(){
        CurrentAccountSingleton result = instance;
        if (result == null) {
            synchronized (mutex){
                result = instance;
                if(result == null){
                    instance = result = new CurrentAccountSingleton();
                }
            }
        }
        return result;
    }

    /***
     * Set the user instance equal to the argument
     * @param user current user logging into system
     */
    public void setAccount(UserEntity user){
        this.user = user;
    }

    /***
     * Get the current user logging into system
     * @return
     */
    public UserEntity getAccount(){
        return this.user;
    }

    public int getRole(){
        return user.getRole();
    }
}
