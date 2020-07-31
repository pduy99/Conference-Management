package DAO;

import DB.DBConnection;
import POJO.ConferenceEntity;
import POJO.LocationEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @created on 7/31/2020
 * @author: Helios - 1712018
 */
public class LocationDAO {
    public static LocationEntity findByPk(int id){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.LocationEntity c WHERE c.id = :id ";
        List list = session.createQuery(hql).setParameter("id",id).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (LocationEntity) list.get(0);
        }
    }

    public static LocationEntity findByName(String name){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.LocationEntity c WHERE c.name = :name ";
        List list = session.createQuery(hql).setParameter("name",name).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (LocationEntity) list.get(0);
        }
    }

    public static LocationEntity findByAddress(String address){
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM POJO.LocationEntity c WHERE c.address = :address ";
        List list = session.createQuery(hql).setParameter("address",address).list();
        if(list.isEmpty()){
            transaction.commit();
            session.close();
            return null;
        }else{
            transaction.commit();
            session.close();
            return (LocationEntity) list.get(0);
        }
    }
}
