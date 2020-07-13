package authentification.loginProcess;

import POJO.UserEntity;

/**
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

    public void setAccount(UserEntity user){
        this.user = user;
    }

    public UserEntity getAccount(){
        return this.user;
    }

    public int getRole(){
        return user.getRole();
    }
}
