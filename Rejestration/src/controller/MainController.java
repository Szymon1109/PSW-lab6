package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import email.Email;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.Date;

public class MainController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password1;

    @FXML
    private TextField password2;

    @FXML
    private CheckBox pokazHaslo;

    @FXML
    private TextField imie;

    @FXML
    private TextField nazwisko;

    @FXML
    private TextField login;

    @FXML
    private TextField haslo1;

    @FXML
    private TextField haslo2;

    @FXML
    private TextField email;

    public int licznik = 0;

    public void uwaga(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    public void clearLTextField(){
        username.clear();
        password1.clear();
        password2.clear();
    }

    public void clearRTextField(){
        imie.clear();
        nazwisko.clear();
        login.clear();
        haslo1.clear();
        haslo2.clear();
        email.clear();
    }

    @FXML
    public void showPassword(javafx.event.ActionEvent actionEvent){
        if(pokazHaslo.isSelected()){
            password2.setText(password1.getText());
            password2.setVisible(true);
            password1.setVisible(false);
        }
        else{
            password1.setText(password2.getText());
            password1.setVisible(true);
            password2.setVisible(false);
        }
    }

    public boolean checkString(String string){
        if(string.length() < 3){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkAccess(String login, String haslo){
        UserDAO userDAO = new UserDAOImpl();

        if(userDAO.findOne(login, haslo)){
            return true;
        }
        else{
            return false;
        }
    }

    public String getUprawnienia(String login){
        UserDAO userDAO = new UserDAOImpl();
        String uprawnienia = userDAO.whoIs(login);

        return uprawnienia;
    }

    public boolean blokada(){
        licznik++;

        if(licznik > 2){
            return true;
        }
        else{
            return false;
        }
    }

    @FXML
    public void logowanie(javafx.event.ActionEvent actionEvent){
        String loginTxt = username.getText();
        String hasloTxt = password1.getText();


        if(checkString(loginTxt) && checkString(hasloTxt)){
            if(checkAccess(loginTxt, hasloTxt)){
                licznik = 0;
                String uprawnienia = getUprawnienia(loginTxt);

                if(uprawnienia.equals("admin")){
                    openAdminView("view/AdminView.fxml");
                }
                else if(uprawnienia.equals("user")){
                    openUserView("view/UserView.fxml");
                }

                clearLTextField();
            }
            else{
                uwaga("Odmowa dostępu - niewłaściwy login lub hasło!");
                if(blokada()){
                    uwaga("Trzykrotnie podano niewłaściwe dane - blokada dostępu!");
                    System.exit(0);
                }
            }
        }
        else{
            uwaga("Dane muszą składać się z conajmniej 3 znaków!");
        }
    }

    private void openUserView(String viewPath) {
        Parent parent;
        try {
            closeMain();
            parent = FXMLLoader.load(getClass().getClassLoader().getResource(viewPath));
            Stage stage = new Stage();
            stage.setTitle("Panel użytkownika: " + username.getText());
            stage.setScene(new Scene(parent, 600, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAdminView(String viewPath) {
        Parent parent;
        try {
            closeMain();
            parent = FXMLLoader.load(getClass().getClassLoader().getResource(viewPath));
            Stage stage = new Stage();
            stage.setTitle("Panel administratora: " + username.getText());
            stage.setScene(new Scene(parent, 600, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeMain(){
        Stage stage =(Stage)username.getScene().getWindow();
        stage.close();
    }

    public boolean checkLogin(String login){
        UserDAO userDAO = new UserDAOImpl();

        if(userDAO.findOne(login)){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkPassword(){
        if(haslo1.getText().equals(haslo2.getText())){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkEmail(String string){
        String pattern = "^[a-zA-Z0-9_.+-]+@gmail.com$";
        return string.matches(pattern);
    }

    public void addData(User user){
        UserDAO userDAO = new UserDAOImpl();
        userDAO.save(user);
    }

    public String getDate(){
        Date date = new Date(System.currentTimeMillis());
        return date.toString();
    }

    private void openView(String viewPath) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getClassLoader().getResource(viewPath));
            Stage stage = new Stage();
            stage.setTitle("Udana rejestracja!");
            stage.setScene(new Scene(parent, 600, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rejestracja(javafx.event.ActionEvent actionEvent){
        String imieTxt = imie.getText();
        String nazwiskoTxt = nazwisko.getText();
        String loginTxt = login.getText();
        String hasloTxt = haslo1.getText();
        String emailTxt = email.getText();
        String uprawnienia = "user";
        String data = getDate();

        if(checkString(imieTxt) && checkString(nazwiskoTxt) && checkString(loginTxt) &&
                checkString(hasloTxt) && checkString(emailTxt)){
            if(checkLogin(loginTxt)){
                if(checkPassword()){
                    if(checkEmail(emailTxt)){
                        User user = new User(imieTxt, nazwiskoTxt, loginTxt, hasloTxt, emailTxt, uprawnienia, data);
                        addData(user);

                        Email email = new Email(user);
                        email.sendEmail();

                        openView("view/RejestrationView.fxml");
                        clearRTextField();
                    }
                    else{
                        uwaga("Podano nieprawidłowy adres e-mail!\n(wymagany format: nazwa@gmail.com)");
                    }
                }
                else{
                    uwaga("Podane hasła nie zgadzają się!");
                }
            }
            else{
                uwaga("Podano istniejący login - wybierz inny!");
            }
        }
        else{
            uwaga("Dane muszą składać się z conajmniej 3 znaków!");
        }
    }
}