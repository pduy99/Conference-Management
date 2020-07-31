package DAO;

import DB.DBConnection;
import POJO.ConferenceEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
public class ConferenceDAO {

    public static ConferenceEntity findByPk(int id){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.ConferenceEntity c WHERE c.id = :id ";
        List list = session.createQuery(hql).setParameter("id",id).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (ConferenceEntity) list.get(0);
        }
    }

    public static List<ConferenceEntity> getAll(){
        List<ConferenceEntity> res = new ArrayList<>(0);
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from POJO.ConferenceEntity";
        List list = session.createQuery(hql).list();
        list.forEach(o->{
            ConferenceEntity temp = (ConferenceEntity)o;
            res.add(temp);
        });
        transaction.commit();
        session.close();
        return res;
    }

    public static int countEnrollment(int conferenceID){
        ConferenceEntity conference = ConferenceDAO.findByPk(conferenceID);
        if(conference!=null) {
            return conference.getUsers().size();
        }else{
            return 0;
        }
    }

    public static List<String> getListParticipantName(int conferenceID){
        List<String> listParticipantName = new ArrayList<>();
        ConferenceEntity conference = findByPk(conferenceID);
        conference.getUsers().forEach(user -> listParticipantName.add(user.getDisplayName()));

        return listParticipantName;
    }

    public static boolean updateConference(ConferenceEntity conference){
        boolean res = false;
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(conference);
            res = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        transaction.commit();
        session.close();
        return res;
    }

    public static void remove(ConferenceEntity conference){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(conference);
        transaction.commit();
        session.close();
    }
}
