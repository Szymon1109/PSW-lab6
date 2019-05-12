package dao;

import database.HibernateFactory;
import javafx.collections.FXCollections;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDAO{

    public Boolean findUser(String query) {
        List<User> data = FXCollections.observableArrayList();

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

        return !data.isEmpty();
    }

    public Boolean findOne(String login, String haslo){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "' AND " +
                       "haslo = '" + haslo + "';";
        return findUser(query);
    }

    public Boolean findOne(String login){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "';";
        return findUser(query);
    }

    public String whoIs(String login){
        String uprawnienia = null;

        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.find(User.class, login);
            uprawnienia = user.getUprawnienia();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return uprawnienia;
    }

    public List<User> findAllUsers(){
        List<User> data = FXCollections.observableArrayList();

        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("FROM uzytkownicy WHERE uprawnienia = 'user'");
            data = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return data;
    }

    public void save(User user){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void changePassword(String login, String newPassword){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.find(User.class, login);
            user.setHaslo(newPassword);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(String login){
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.find(User.class, login);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}