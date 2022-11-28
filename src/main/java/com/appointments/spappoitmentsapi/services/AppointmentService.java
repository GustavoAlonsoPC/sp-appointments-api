package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.entities.Appointment;
import com.appointments.spappoitmentsapi.repositories.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<AppointmentDTO> getAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment app : appointments) {
            AppointmentDTO appointmentDTO = modelMapper.map(app, AppointmentDTO.class);
            appointmentDTOList.add(appointmentDTO);
        }
        return appointmentDTOList;
    }

    public AppointmentDTO post(AppointmentDTO appointmentDTO) {
        if (appointmentDTO.getId() != null) {
            System.out.println("Trying to POST with an ID Field");
            return null;
        }

        if (appointmentDTO.getIdTest() == null || appointmentDTO.getIdAffiliate() == null) {
            System.out.println("Missing requerided ID data");
            return null;
        }

        if (appointmentDTO.getDateAppointment() == null || appointmentDTO.getHourAppointment() == null) {
            System.out.println("Missing requerided TIME data");
            return null;
        }

        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDTO.class);
    }
}
