package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/controller/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getList() {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAll();
        if (appointmentDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }
}
