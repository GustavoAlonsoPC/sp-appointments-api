package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<AppointmentDTO> post(@RequestBody AppointmentDTO appointmentDTO) {
        appointmentDTO = appointmentService.post(appointmentDTO);
        return appointmentDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(appointmentDTO, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AppointmentDTO> getByID(@PathVariable Long id) {
        AppointmentDTO appointmentDTO = appointmentService.getByID(id);
        return appointmentDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }
}
