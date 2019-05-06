package model;

import java.sql.Date;

public class User {
    private Integer id;
    private String imie;
    private String nazwisko;
    private String login;
    private String haslo;
    private String email;
    private String uprawnienia;
    private String data_rejestracji;

    public User(Integer id, String imie, String nazwisko, String login, String haslo,
                String email, String uprawnienia, String data_rejestracji){

        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.login = login;
        this.haslo = haslo;
        this.email = email;
        this.uprawnienia = uprawnienia;
        this.data_rejestracji = data_rejestracji;
    }

    public User(String imie, String nazwisko, String login, String haslo,
                String email, String uprawnienia, String data_rejestracji){

        this.imie = imie;
        this.nazwisko = nazwisko;
        this.login = login;
        this.haslo = haslo;
        this.email = email;
        this.uprawnienia = uprawnienia;
        this.data_rejestracji = data_rejestracji;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public String getData_rejestracji() {
        return data_rejestracji;
    }

    public void setData_rejestracji(String data_rejestracji) {
        this.data_rejestracji = data_rejestracji;
    }
}