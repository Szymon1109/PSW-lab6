package dao;

import database.HibernateFactory;
import javafx.collections.FXCollections;
import model.Zapis;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class ZapisDAO {

    public void save(Zapis zapis){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(zapis);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void confirm(Integer id){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Zapis zapis = session.find(Zapis.class, id);
            zapis.setZgoda("tak");
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void reject(Integer id){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Zapis zapis = session.find(Zapis.class, id);
            zapis.setZgoda("nie");
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteForUser(Integer id_uzytkownika){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Zapis zapis = session.find(Zapis.class, id_uzytkownika);
            session.delete(zapis);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteForEvent(Integer id_wydarzenia){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Zapis zapis = session.find(Zapis.class, id_wydarzenia);
            session.delete(zapis);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Zapis> findAllConfirmedZapis(){
        String query = "SELECT * FROM zapisy WHERE zgoda='tak';";

        return getData(query);
    }

    public List<Zapis> findAllNotConfirmedZapis(){
        String query = "SELECT * FROM zapisy WHERE zgoda IS NULL;";

        return getData(query);
    }

    public List<Zapis> getData(String query) {
        List<Zapis> data = FXCollections.observableArrayList();

        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        try {
            Query newQuery = session.createQuery(query);
            data = newQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return data;
    }
}
