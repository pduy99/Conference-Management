package DAO;

import DB.DBConnection;
import POJO.ConferenceEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
public class ConferenceDAO {
    public static ConferenceEntity findByPk(int id){
        Session session = DBConnection.getSessionFactory().openSession();
        String hql = "FROM POJO.ConferenceEntity c WHERE c.id = :id ";
        List list = session.createQuery(hql).setParameter("id",id).list();
        if(list.isEmpty()){
            session.close();
            return null;
        }else{
            session.close();
            return (ConferenceEntity) list.get(0);
        }
    }
}
