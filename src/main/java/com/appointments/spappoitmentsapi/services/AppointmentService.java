package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AppointmentsDTO;
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

    public List<AppointmentsDTO> getAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentsDTO> appointmentsDTOList = new ArrayList<>();
        for (Appointment app : appointments) {
            AppointmentsDTO appointmentsDTO = modelMapper.map(app, AppointmentsDTO.class);
            appointmentsDTOList.add(appointmentsDTO);
        }
        return appointmentsDTOList;
    }
}
