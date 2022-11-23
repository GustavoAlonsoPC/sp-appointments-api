package com.appointments.spappoitmentsapi.repositories;

import com.appointments.spappoitmentsapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}