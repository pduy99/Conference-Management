package DBController;

import DB.DBConnection;
import POJO.UserEntity;
import org.hibernate.Session;

import java.util.List;

public class UserController {
    public static void getAllUser(){
        Session session = DBConnection.getSessionFactory().openSession();
        String hql = "SELECT u.name, u.userName FROM POJO.UserEntity u ";
        List list = session.createQuery(hql).list();
        list.forEach(o->{
            Object[] objects = (Object[])o;
            System.out.println(objects[0] + "|" + objects[1]);
        });
        session.close();
    }

    public static int addUser(String displayName, String username, String password, String mail){
        try {
            UserEntity newUser = new UserEntity(displayName, username, password, mail);
            Session session = DBConnection.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
            session.close();
            return 0;
        }catch (Exception ex){
            return -1;
        }
    }

    public static UserEntity findByPk(int id){
        Session session = DBConnection.getSessionFactory().openSession();
        String hql = "FROM POJO.UserEntity u WHERE u.iduser = :id ";
        List list = session.createQuery(hql).setParameter("id",id).list();
        if(list.isEmpty()){
            session.close();
            return null;
        }else{
            session.close();
            return (UserEntity) list.get(0);
        }
    }

    public static UserEntity findByUserName(String username){
        Session session = DBConnection.getSessionFactory().openSession();
        String hql = "FROM POJO.UserEntity u WHERE u.userName = :username ";
        List list = session.createQuery(hql).setParameter("username",username).list();
        if(list.isEmpty()){
            return null;
        }else{
            return (UserEntity) list.get(0);
        }
    }

    public static UserEntity findByEmail(String email){
        Session session = DBConnection.getSessionFactory().openSession();
        String hql = "FROM POJO.UserEntity u WHERE u.email = :email ";
        List list = session.createQuery(hql).setParameter("email",email).list();
        if(list.isEmpty()){
            return null;
        }else{
            return (UserEntity) list.get(0);
        }
    }
}
