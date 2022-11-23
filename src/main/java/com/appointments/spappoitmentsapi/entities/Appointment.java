package com.appointments.spappoitmentsapi.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appointments", uniqueConstraints = {
        @UniqueConstraint(name = "uc_appointments_id_test", columnNames = {"id_test"})
})
public class Appointments {
    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime hour;
    private Long id_test;
    private Long id_affiliate;

    //constructors

    public Appointments() {
    }

    public Appointments(Long id, LocalDate date, LocalTime hour, Long id_test, Long id_affiliate) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.id_test = id_test;
        this.id_affiliate = id_affiliate;
    }

    public Appointments(LocalDate date, LocalTime hour, Long id_test, Long id_affiliate) {
        this.date = date;
        this.hour = hour;
        this.id_test = id_test;
        this.id_affiliate = id_affiliate;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public Long getId_test() {
        return id_test;
    }

    public void setId_test(Long id_test) {
        this.id_test = id_test;
    }

    public Long getId_affiliate() {
        return id_affiliate;
    }

    public void setId_affiliate(Long id_affiliate) {
        this.id_affiliate = id_affiliate;
    }


    //toString

    @Override
    public String toString() {
        return "Appointments{" +
                "id=" + id +
                ", date=" + date +
                ", hour=" + hour +
                ", id_test=" + id_test +
                ", id_affiliate=" + id_affiliate +
                '}';
    }
}
