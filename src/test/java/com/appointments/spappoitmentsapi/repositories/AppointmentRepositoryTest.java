package com.appointments.spappoitmentsapi.repositories;

import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.entities.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private AffiliateRepository affiliateRepository;
    @Autowired
    private AppointmentRepository underTest;

    @BeforeEach
    void setUp() {

        List<com.appointments.spappoitmentsapi.entities.Test> testList = new ArrayList<>();
        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test1", "Desc1");
        com.appointments.spappoitmentsapi.entities.Test test2
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test2", "Desc2");
        com.appointments.spappoitmentsapi.entities.Test test3
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test3", "Desc3");
        com.appointments.spappoitmentsapi.entities.Test test4
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test4", "Desc4");

        testList.add(test1);
        testList.add(test2);
        testList.add(test3);
        testList.add(test4);

        List<Affiliate> affiliateList = new ArrayList<>();
        Affiliate affiliate1 = new Affiliate(null, "nameaff1", 23, "mail1");
        Affiliate affiliate2 = new Affiliate(null, "nameaff2", 23, "mail2");
        Affiliate affiliate3 = new Affiliate(null, "nameaff3", 23, "mail3");
        Affiliate affiliate4 = new Affiliate(null, "nameaff4", 23, "mail4");

        affiliateList.add(affiliate1);
        affiliateList.add(affiliate2);
        affiliateList.add(affiliate3);
        affiliateList.add(affiliate4);

        List<Appointment> appointmentList = new ArrayList<>();

        Appointment appointment1 = new Appointment(null, LocalDate.of(2022, 11, 30), LocalTime.of(12, 00), affiliate1, test1);
        Appointment appointment2 = new Appointment(null, LocalDate.of(2022, 12, 01), LocalTime.of(12, 00), affiliate2, test2);
        Appointment appointment3 = new Appointment(null, LocalDate.of(2022, 12, 02), LocalTime.of(12, 00), affiliate3, test3);
        Appointment appointment4 = new Appointment(null, LocalDate.of(2022, 12, 03), LocalTime.of(12, 00), affiliate4, test4);

        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);
        appointmentList.add(appointment4);

        testRepository.saveAll(testList);
        affiliateRepository.saveAll(affiliateList);
        underTest.saveAll(appointmentList);

    }

    @Test
    void getAppointmentByAffiliateId() {
        List<Appointment> appointments = underTest.getAppointmentByAffiliateId(9L);
        assertThat(appointments).isNotNull();
        assertThat(appointments.size()).isNotEqualTo(0);
    }

    @Test
    void getAppointmentByDate() {
        List<Appointment> appointments = underTest.getAppointmentByDate(LocalDate.of(2022, 11, 30));
        assertThat(appointments).isNotNull();
        assertThat(appointments.size()).isNotEqualTo(0);
    }

    @Test
    void getAppointmentByTestId() {
        List<Appointment> appointments = underTest.getAppointmentsByTestId(1L);
        assertThat(appointments).isNotNull();
        assertThat(appointments.size()).isNotEqualTo(0);
    }
}