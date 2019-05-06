package dao;

import model.Event;
import database.SQLConnection;
import javafx.collections.FXCollections;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl implements EventDAO {

    @Override
    public List<Event> findAllEvents(){
        List<Event> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM wydarzenia";
        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            while(rs.next()){
                Integer id = rs.getInt("id");
                String nazwa = rs.getString("nazwa");
                String agenda = rs.getString("agenda");
                String termin = rs.getString("termin");

                Event row = new Event(id, nazwa, agenda, termin);
                data.add(row);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            conn.closeConnect(rs);
        }
        return data;
    }

    @Override
    public List<Event> findConfirmedEventsForUser(Integer id){
        String query = "SELECT DISTINCT id_wydarzenia FROM zapisy WHERE id_uzytkownika=" + id + " AND " + "zgoda='tak'";

        return getData(query);
    }

    @Override
    public List<Event> findNotConfirmedEventsForUser(Integer id){
        String query = "SELECT DISTINCT id_wydarzenia FROM zapisy WHERE id_uzytkownika=" + id + " AND " + "zgoda IS NULL;";

        return getData(query);
    }

    public List<Event> getData(String query){
        List<Integer> ids = new ArrayList<>();
        SQLConnection conn1 = new SQLConnection();
        ResultSet rs1 = conn1.makeQuery(query);

        try{
            while(rs1.next()){
                Integer id_wydarzenia = rs1.getInt("id_wydarzenia");
                ids.add(id_wydarzenia);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            conn1.closeConnect();
        }

        List<Event> data = FXCollections.observableArrayList();
        for(Integer Id : ids){
            String query2 = "SELECT * FROM wydarzenia WHERE id=" + Id + ";";
            SQLConnection conn2 = new SQLConnection();
            ResultSet rs2 = conn2.makeQuery(query2);

            try{
                while(rs2.next()){
                    Integer id_wydarzenia = rs2.getInt("id");
                    String nazwa = rs2.getString("nazwa");
                    String agenda = rs2.getString("agenda");
                    String termin = rs2.getString("termin");

                    Event event = new Event(id_wydarzenia, nazwa, agenda, termin);
                    data.add(event);
                }
            } catch(SQLException e){
                e.printStackTrace();
            } finally{
                conn2.closeConnect();
            }
        }
        return data;
    }

    @Override
    public Boolean findOne(String nazwa){
        String query = "SELECT * FROM wydarzenia WHERE nazwa = '" + nazwa + "';";

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
    public void save(Event event){
        String query = "INSERT INTO wydarzenia(nazwa, agenda, termin) VALUES ('" +
                event.getNazwa() + "', '" + event.getAgenda() + "', '" + event.getTermin() + "');";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void update(Event event){
        String query = "UPDATE wydarzenia SET nazwa='" + event.getNazwa() + "', " +
                "agenda='" + event.getAgenda() + "', termin='" + event.getTermin() + "' " +
                "WHERE id=" + event.getId() + ";";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void delete(String nazwa){
        String query = "DELETE FROM wydarzenia WHERE nazwa='" + nazwa + "';";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }
}