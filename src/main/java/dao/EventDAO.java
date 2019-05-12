package dao;

import database.HibernateFactory;
import model.Event;
import javafx.collections.FXCollections;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class EventDAO{

    public List<Event> findAllEvents(){
        List<Event> data = FXCollections.observableArrayList();

        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("FROM wydarzenia");
            data = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return data;
    }

    public List<Event> findConfirmedEventsForUser(Integer id){
        String query = "SELECT DISTINCT id_wydarzenia FROM zapisy WHERE id_uzytkownika=" + id + " AND " + "zgoda='tak'";

        return getData(query);
    }

    public List<Event> findNotConfirmedEventsForUser(Integer id){
        String query = "SELECT DISTINCT id_wydarzenia FROM zapisy WHERE id_uzytkownika=" + id + " AND " + "zgoda IS NULL;";

        return getData(query);
    }

    public List<Event> getData(String query){
        List<Integer> ids = new ArrayList<>();

        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        try {
            Query newQuery = session.createQuery(query);
            ids = newQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        List<Event> data = FXCollections.observableArrayList();

        for(Integer Id : ids){
            List<Event> newData;
            String query2 = "SELECT * FROM wydarzenia WHERE id=" + Id + ";";

            HibernateFactory hibernateFactory2 = new HibernateFactory();
            Session session2 = hibernateFactory2.getSessionFactory().openSession();

            try {
                Query newQuery = session2.createQuery(query);
                newData = newQuery.list();
                data.addAll(newData);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return data;
    }

    public Boolean findOne(String nazwa){
        List<Event> data = FXCollections.observableArrayList();

        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        try {
            Query query = session.createQuery(String.format("FROM wydarzenia WHERE nazwa = '%s'", nazwa));
            data = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return !data.isEmpty();
    }

    public void save(Event event){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(event);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Event event){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Event newEvent = session.find(Event.class, event.getId());
            newEvent.setNazwa(event.getNazwa());
            newEvent.setAgenda(event.getAgenda());
            newEvent.setTermin(event.getTermin());
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(String nazwa){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Event event = session.find(Event.class, nazwa);
            session.delete(event);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}