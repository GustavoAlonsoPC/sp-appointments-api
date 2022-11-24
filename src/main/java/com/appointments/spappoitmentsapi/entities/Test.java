package com.appointments.spappoitmentsapi.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    //constructors

    public Test() {
    }

    public Test(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Test(Long id, String name, String description, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.appointments = appointments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        for (Appointment appointment : appointments) appointment.setTest(this);
    }

    //toString

    @Override
    public String toString() {
        return "Tests{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
