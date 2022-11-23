package com.appointments.spappoitmentsapi.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appointments")
public class Appointment {
    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime hour;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_test")
    private Test test;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_affiliate")
    private Affiliate affiliate;

    //constructors

    public Appointment() {
    }

    public Appointment(Long id, LocalDate date, LocalTime hour) {
        this.id = id;
        this.date = date;
        this.hour = hour;
    }

    public Appointment(LocalDate date, LocalTime hour) {
        this.date = date;
        this.hour = hour;
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

    //toString

    @Override
    public String toString() {
        return "Appointments{" +
                "id=" + id +
                ", date=" + date +
                ", hour=" + hour +
                '}';
    }
}
