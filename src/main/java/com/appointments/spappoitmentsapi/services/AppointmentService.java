package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.entities.Appointment;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import com.appointments.spappoitmentsapi.repositories.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AffiliateRepository affiliateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              ModelMapper modelMapper,
                              AffiliateRepository affiliateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
        this.affiliateRepository = affiliateRepository;
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

    public AppointmentDTO getByID(Long id) {
        if (!appointmentRepository.existsById(id)) {
            System.out.println("Do not exist");
            return null;
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    public AppointmentDTO put(AppointmentDTO appointmentDTO) {
        if (appointmentDTO.getId() == null) return null;
        if (!appointmentRepository.existsById(appointmentDTO.getId())) return null;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        Appointment appointment = appointmentRepository.findById(appointmentDTO.getId()).get();
        modelMapper.map(appointmentDTO, appointment);
        appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    public Boolean delete(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<AppointmentDTO> getByAffiliateID(Long idAffiliate) {

        if (!affiliateRepository.existsById(idAffiliate)) return null;
        List<Appointment> appointments = appointmentRepository.getAppointmentByAffiliateId(idAffiliate);
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment app : appointments) {
            AppointmentDTO appointmentDTO = modelMapper.map(app, AppointmentDTO.class);
            appointmentDTOList.add(appointmentDTO);
        }
        return appointmentDTOList;
    }
}
