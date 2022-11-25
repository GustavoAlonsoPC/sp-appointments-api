package com.appointments.spappoitmentsapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Appointments")
public class Appointment {
    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dateAppointment")
    @Getter @Setter
    private LocalDate dateAppointment;

    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("hourAppointment")
    @Getter @Setter
    private LocalTime hourAppointment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_affiliate")
    @Getter @Setter
    private Affiliate affiliate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "id_test")
    @Getter @Setter
    private Test test;
}
