package com.appointments.spappoitmentsapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dateAppointment")
    private LocalDate dateAppointment;

    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("hourAppointment")
    private LocalTime hourAppointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "id_affiliate")
    private Affiliate affiliate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "id_test")
    private Test test;

    //constructors

    public Appointment() {
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return dateAppointment;
    }

    public void setDate(LocalDate date) {
        this.dateAppointment = date;
    }

    public LocalTime getHour() {
        return hourAppointment;
    }

    public void setHour(LocalTime hour) {
        this.hourAppointment = hour;
    }

    public LocalDate getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(LocalDate dateAppointment) {
        this.dateAppointment = dateAppointment;
    }

    public LocalTime getHourAppointment() {
        return hourAppointment;
    }

    public void setHourAppointment(LocalTime hourAppointment) {
        this.hourAppointment = hourAppointment;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    //toString

    @Override
    public String toString() {
        return "Appointments{" +
                "id=" + id +
                ", date=" + dateAppointment +
                ", hour=" + hourAppointment +
                '}';
    }
}
