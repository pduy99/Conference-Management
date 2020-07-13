package DB;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnection {
    private static SessionFactory factory;

    private DBConnection(){

    }

    public static synchronized SessionFactory getSessionFactory(){
        if(factory == null){
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return factory;
    }
}
