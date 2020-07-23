package DAO;

import DB.DBConnection;
import POJO.ConferenceEntity;
import POJO.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {
    public static void getAllUser(){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "select u.displayName, u.username from POJO.UserEntity u";
        List list = session.createQuery(hql).list();
        list.forEach(o->{
            Object[] objects = (Object[])o;
            System.out.println(objects[0] + "|" + objects[1]);
        });
        transaction.commit();
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
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.UserEntity u WHERE u.id = :id ";
        List list = session.createQuery(hql).setParameter("id",id).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (UserEntity) list.get(0);
        }
    }

    public static UserEntity findByUserName(String username){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.UserEntity u WHERE u.username = :username ";
        List list = session.createQuery(hql).setParameter("username",username).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (UserEntity) list.get(0);
        }
    }

    public static UserEntity findByEmail(String email){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.UserEntity u WHERE u.email = :email ";
        List list = session.createQuery(hql).setParameter("email",email).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (UserEntity) list.get(0);
        }
    }

    public static void EnrollConference(int userID, int conferenceID) throws NullPointerException, HibernateException {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity user = UserDAO.findByPk(userID);
        ConferenceEntity conference = ConferenceDAO.findByPk(conferenceID);
        user.getConferences().add(conference);
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public static void DisEnrollConference(int userID, int conferenceID) throws NullPointerException, HibernateException{
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity user = UserDAO.findByPk(userID);
        ConferenceEntity conference = ConferenceDAO.findByPk(conferenceID);
        user.getConferences().remove(conference);
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public static boolean checkApprovedEnrollment(int userID, int conferenceID){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "select uc.approved from POJO.UserConferenceEntity uc where uc.userId = :userID and uc.conferenceId = :conferenceID";
        Object res = session.createQuery(hql)
                .setParameter("userID",userID)
                .setParameter("conferenceID",conferenceID)
                .uniqueResult();
        transaction.commit();
        if(res != null){
            return ((byte)res == 1);
        }else{
            return false;
        }
    }
}
