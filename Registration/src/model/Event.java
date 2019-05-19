package model;

import javax.persistence.*;

@Entity
@Table(name = "wydarzenia")
public class Event {
    @Id
    @Column(name="id")
    @GeneratedValue(generator="incrementor")
    private Integer id;

    @Column(name="nazwa")
    private String nazwa;

    @Column(name="agenda")
    private String agenda;

    @Column(name="termin")
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