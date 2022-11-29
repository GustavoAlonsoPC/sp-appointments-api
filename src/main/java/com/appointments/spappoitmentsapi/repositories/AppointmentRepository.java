package com.appointments.spappoitmentsapi.repositories;

import com.appointments.spappoitmentsapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT A FROM Appointment A WHERE A.affiliate.id = ?1")
    List<Appointment> getAppointmentByAffiliateId (Long IdAffiliate);

    @Query("SELECT A FROM Appointment A WHERE A.dateAppointment = ?1 ORDER BY A.affiliate.id")
    List<Appointment> getAppointmentByDate(LocalDate localDate);
}