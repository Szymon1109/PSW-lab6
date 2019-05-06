package dao;

import database.SQLConnection;
import javafx.collections.FXCollections;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class  UserDAOImpl implements UserDAO{

    public Boolean findUser(String query) {
        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            conn.closeConnect(rs);
        }
        return false;
    }

    @Override
    public Boolean findOne(String login, String haslo){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "' AND " +
                       "haslo = '" + haslo + "';";
        return findUser(query);
    }

    @Override
    public Boolean findOne(String login){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "';";
        return findUser(query);
    }

    @Override
    public String whoIs(String login){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "';";
        String uprawnieniaDb = null;

        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            if(rs.next()){
                uprawnieniaDb = rs.getString("uprawnienia");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            conn.closeConnect(rs);
        }

        return uprawnieniaDb;
    }

    @Override
    public List<User> findAllUsers(){
        List<User> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM uzytkownicy WHERE uprawnienia = 'user';";
        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            while(rs.next()){
                Integer id = rs.getInt("id");
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String login = rs.getString("login");
                String haslo = rs.getString("haslo");
                String email = rs.getString("email");
                String uprawnienia = rs.getString("uprawnienia");
                String data_rejestracji = rs.getString("data_rejestracji");

                User row = new User(id, imie, nazwisko, login, haslo,
                        email, uprawnienia, data_rejestracji);
                data.add(row);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            conn.closeConnect(rs);
        }
        return data;
    }

    @Override
    public void save(User user){
        String query = "INSERT INTO uzytkownicy(imie, nazwisko, login, haslo, " +
                "email, uprawnienia, data_rejestracji) VALUES ('" +
                user.getImie() + "', '" + user.getNazwisko() + "', '" +
                user.getLogin() + "', '" + user.getHaslo() + "', '" +
                user.getEmail() + "', '" + user.getUprawnienia() + "', '" +
                user.getData_rejestracji() + "');";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void changePassword(String login, String newPassword){
        String query = "UPDATE uzytkownicy SET haslo='" + newPassword +
                "' WHERE login='" + login + "';";
        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void delete(String login){
        String query = "DELETE FROM uzytkownicy WHERE login='" + login + "';";
        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }
}