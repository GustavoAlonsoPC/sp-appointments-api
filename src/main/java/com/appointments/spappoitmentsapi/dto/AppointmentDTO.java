package com.appointments.spappoitmentsapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateAppointment;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hourAppointment;
    private Long idTest;
    private Long idAffiliate;
}
