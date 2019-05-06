package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Event;
import model.User;
import model.Zapis;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController implements Initializable {

    @FXML
    private ComboBox<String> wydarzenia;

    @FXML
    private Label agenda;

    @FXML
    private Label termin;

    @FXML
    private ChoiceBox<String> typUczestnictwa;

    @FXML
    private ChoiceBox<String> wyzywienie;

    @FXML
    private Button wyloguj;

    @FXML
    private TableView tableZatw;

    @FXML
    private TableColumn idZatw;

    @FXML
    private TableColumn nazwaZatw;

    @FXML
    private TableColumn agendaZatw;

    @FXML
    private TableColumn terminZatw;

    @FXML
    private TableView tableNiezatw;

    @FXML
    private TableColumn idNiezatw;

    @FXML
    private TableColumn nazwaNiezatw;

    @FXML
    private TableColumn agendaNiezatw;

    @FXML
    private TableColumn terminNiezatw;

    private Integer idUzytkownika;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemsInWydarzenia();
        setItemsInTypUczestnictwa();
        setItemsInWyzywienie();
    }

    public void uwaga(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    public void clearTextField(){
        agenda.setText(null);
        termin.setText(null);
        typUczestnictwa.setValue(null);
        wyzywienie.setValue(null);
    }

    public void setItemsInWydarzenia(){
        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        ObservableList<String> list = FXCollections.observableArrayList();
        for (Event event : data) {
            String nazwa = event.getNazwa();
            list.add(nazwa);
        }

        wydarzenia.setItems(list);
        wydarzenia.setValue(null);

        agenda.setText(null);
        termin.setText(null);
    }

    public void setItemsInTypUczestnictwa(){

        ObservableList<String> list = FXCollections.observableArrayList("Słuchacz", "Autor", "Sponsor", "Organizator");
        typUczestnictwa.setItems(list);
    }

    public void setItemsInWyzywienie(){

        ObservableList<String> list = FXCollections.observableArrayList("Bez preferencji", "Wegetariańskie", "Bez glutenu");
        wyzywienie.setItems(list);
    }

    public void setItemsInZatwWyd(){
        EventDAO event = new EventDAOImpl();
        List<Event> data = event.findConfirmedEventsForUser(idUzytkownika);
        ObservableList<Event> list = FXCollections.observableArrayList(data);

        idZatw.setCellValueFactory(
                new PropertyValueFactory<Event,Integer>("id"));

        nazwaZatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("nazwa"));

        agendaZatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("agenda"));

        terminZatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("termin"));

        tableZatw.setItems(list);
    }

    public void setItemsInNiezatwWyd(){
        EventDAO event = new EventDAOImpl();
        List<Event> data = event.findNotConfirmedEventsForUser(idUzytkownika);
        ObservableList<Event> list = FXCollections.observableArrayList(data);

        idNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,Integer>("id"));

        nazwaNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("nazwa"));

        agendaNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("agenda"));

        terminNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("termin"));

        tableNiezatw.setItems(list);
    }

    @FXML
    public void wydarzeniaChanged(ActionEvent actionEvent) {
        String wydarzenie = wydarzenia.getValue();

        if(wydarzenie != null) {
            setAgenda(wydarzenie);
            setTermin(wydarzenie);
        }
    }

    public void setAgenda(String wydarzenie){
        String agendaTxt = null;

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getNazwa())) {
                agendaTxt = event.getAgenda();
                break;
            }
        }
        agenda.setText(setNewLines(agendaTxt));
    }

    public String setNewLines(String string){
        String patternStr = "\\. [A-Z]";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(string);
        while(matcher.find()){
            string = string.substring(0, matcher.start() + 1)
                    + "\n"
                    + string.substring(matcher.start() + 2);

            matcher = pattern.matcher(string);
        }
        return string;
    }

    public void setTermin(String wydarzenie){
        String terminTxt = null;

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getNazwa())) {
                terminTxt = event.getTermin();
                break;
            }
        }
        termin.setText(terminTxt);
    }

    @FXML
    public void zapisz(ActionEvent actionEvent){
        String typUczestnictwaTxt = typUczestnictwa.getValue();
        String wyzywienieTxt = wyzywienie.getValue();
        String nazwaTxt = wydarzenia.getValue();

        if(typUczestnictwaTxt != null && wyzywienieTxt != null && nazwaTxt != null) {
            Integer idWyd = getId(nazwaTxt);
            getData();
            Zapis zapis = new Zapis(idUzytkownika, idWyd, typUczestnictwaTxt, wyzywienieTxt);

            ZapisDAO zapisDAO = new ZapisDAOImpl();
            zapisDAO.save(zapis);

            uwaga("Zapisano na podane wydarzenie!");
            clearTextField();
            setItemsInWydarzenia();
            setItemsInZatwWyd();
            setItemsInNiezatwWyd();
        }
        else {
            uwaga("Nie wybrano wszystkich danych!");
        }
    }

    public Integer getId(String nazwa){
        Integer id = null;

        List<Event> data = FXCollections.observableArrayList();
        EventDAO eventDAO = new EventDAOImpl();
        data = eventDAO.findAllEvents();

        for(Event event : data){
            if(event.getNazwa().equals(nazwa)){
                id = event.getId();
                break;
            }
        }
        return id;
    }

    @FXML
    public void wyloguj(ActionEvent actionEvent){
        Stage stage =(Stage)wyloguj.getScene().getWindow();
        stage.close();

        showMain();
    }

    public void showMain(){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Panel rejestracji");
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
    }

    @FXML
    public void odswiez(ActionEvent actionEvent){
        getData();
        setItemsInZatwWyd();
        setItemsInNiezatwWyd();
    }

    public void getData(){
        String login = getLoginUzytkownika();
        idUzytkownika = getIdUzytkownika(login);
    }

    public String getLoginUzytkownika(){
        Stage stage =(Stage)wyloguj.getScene().getWindow();
        String title = stage.getTitle();
        String login = title.substring(19);

        return login;
    }

    public Integer getIdUzytkownika(String login){
        Integer id = 0;

        List<User> data;
        UserDAO userDAO = new UserDAOImpl();
        data = userDAO.findAllUsers();

        for(User user : data){
            if(user.getLogin().equals(login)){
                id = user.getId();
                break;
            }
        }
        return id;
    }
}