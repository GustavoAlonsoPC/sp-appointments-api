package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.entities.Appointment;
import com.appointments.spappoitmentsapi.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PutMapping
    public ResponseEntity<AppointmentDTO> put(@RequestBody AppointmentDTO appointmentDTO) {
        appointmentDTO = appointmentService.put(appointmentDTO);
        return appointmentDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(appointmentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return appointmentService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/aff",params = "idAffiliate")
    public ResponseEntity<List<AppointmentDTO>> getByAffiliateID(@RequestParam Long idAffiliate) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getByAffiliateID(idAffiliate);
        if (appointmentDTOList == null) return ResponseEntity.notFound().build();
        if (appointmentDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/date", params = "dateAppointment")
    public ResponseEntity<List<AppointmentDTO>> getByDate(@RequestParam LocalDate dateAppointment) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getByDate(dateAppointment);
        if (appointmentDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }
}
