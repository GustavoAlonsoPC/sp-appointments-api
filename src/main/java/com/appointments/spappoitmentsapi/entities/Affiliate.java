package com.appointments.spappoitmentsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.dynalink.linker.LinkerServices;

import javax.persistence.*;
import java.util.List;

@Entity
public class Affiliate {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String mail;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "id_affiliate")
    @JsonIgnore
    private List<Appointment> appointments;

    //constructors

    public Affiliate() {
    }

    public Affiliate(Long id, String name, Integer age, String mail) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mail = mail;
    }

    //getter and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        for (Appointment appointment : appointments) appointment.setAffiliate(this);
    }

    //toString

    @Override
    public String toString() {
        return "Affiliates{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                '}';
    }
}
