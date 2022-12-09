package com.appointments.spappoitmentsapi.repositories;

import com.appointments.spappoitmentsapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT A FROM Appointment A WHERE A.affiliate.id = ?1")
    List<Appointment> getAppointmentByAffiliateId (Long IdAffiliate);

    @Query("SELECT A FROM Appointment A WHERE A.dateAppointment = ?1 ORDER BY A.affiliate.id")
    List<Appointment> getAppointmentByDate(LocalDate localDate);

    @Query("SELECT A from Appointment  A where A.test.id = ?1")
    List<Appointment> getAppointmentsByTestId(Long idTest);

    @Modifying
    @Transactional
    @Query("DELETE from Appointment A where A.affiliate.id = ?1")
    void deleteAppointmentByAffiliateId(Long affiliateId);

    @Modifying
    @Transactional
    @Query("DELETE from Appointment A where A.test.id = ?1")
    void deleteAppointmentByTestId(Long testId);
}