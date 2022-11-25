package com.appointments.spappoitmentsapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentsDTO {

    private Long id;
    private LocalDate dateAppointment;
    private LocalTime dateHour;
    private Long id_test;
    private Long id_affiliate;
}
