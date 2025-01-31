package com.appointments.spappoitmentsapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_affiliate")
    private Affiliate affiliate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_test")
    private Test test;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
    
    
}
