package DAO;

import DB.DBConnection;
import POJO.ConferenceEntity;
import POJO.UserConferenceEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class EnrollmentDAO {
    public static List<UserConferenceEntity> getAllPending(){
        List<UserConferenceEntity> res = new ArrayList<>(0);
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from POJO.UserConferenceEntity a where a.approved= :approve";
        List list = session.createQuery(hql)
                .setParameter("approve",(byte)0)
                .list();
        list.forEach(o->{
            UserConferenceEntity temp = (UserConferenceEntity)o;
            res.add(temp);
        });
        transaction.commit();
        session.close();
        return res;
    }

    public static void approveEnrollment(int userId, int conferenceID){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "update POJO.UserConferenceEntity a set a.approved = :approve where a.userId = :userID and a.conferenceId = :conferenceID";
        Query query = session.createQuery(hql);
        query.setParameter("approve",(byte)1)
                .setParameter("userID",userId)
                .setParameter("conferenceID",conferenceID);
        int res = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public static void remove(int userID, int conferenceID){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "delete from POJO.UserConferenceEntity a where a.userId= :userID and a.conferenceId= : conferenceID";
        Query query = session.createQuery(hql);
        query.setParameter("userID",userID)
                .setParameter("conferenceID",conferenceID);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
