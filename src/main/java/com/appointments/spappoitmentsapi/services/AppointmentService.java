package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDTO> getAll();
    AppointmentDTO post(AppointmentDTO appointmentDTO);
    AppointmentDTO getByID(Long id);
    AppointmentDTO put(AppointmentDTO appointmentDTO);
    Boolean delete(Long id);
    List<AppointmentDTO> getByAffiliateID(Long idAffiliate);
    List<AppointmentDTO> getByDate(LocalDate localDate);
    Boolean deleteByTest(Long testId);
    Boolean deleteByAffiliate(Long affiliateId);
}
