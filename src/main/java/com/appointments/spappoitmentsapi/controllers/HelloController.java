package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.entities.Appointment;
import com.appointments.spappoitmentsapi.entities.Test;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import com.appointments.spappoitmentsapi.repositories.AppointmentRepository;
import com.appointments.spappoitmentsapi.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    AffiliateRepository affiliateRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping ("/prueba/tests")
    public List<Test> pruebaGet() {
        return testRepository.findAll();
    }

    @GetMapping ("/prueba/affiliates")
    public List<Affiliate> pruebaGetAff() {
        return affiliateRepository.findAll();
    }

    @GetMapping ("/prueba/appointments")
    public List<Appointment> pruebaGetApp() {
        return appointmentRepository.findAll();
    }

    @PostMapping("/prueba/tests")
    public void pruebaTests(@RequestBody Test test) {
        testRepository.save(test);
    }

    @PostMapping("/prueba/affiliates")
    public void pruebaAffiliates(@RequestBody Affiliate affiliate) {
        affiliateRepository.save(affiliate);
    }

    @PostMapping("/prueba/appointments")
    public void pruebaAppointments(@RequestBody Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
