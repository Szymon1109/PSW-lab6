package model;

public class Event {
    private Integer id;
    private String nazwa;
    private String agenda;
    private String termin;

    public Event(Integer id, String nazwa, String agenda, String termin){
        this.id = id;
        this.nazwa = nazwa;
        this.agenda = agenda;
        this.termin = termin;
    }

    public Event(String nazwa, String agenda, String termin){
        this.nazwa = nazwa;
        this.agenda = agenda;
        this.termin = termin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }
}