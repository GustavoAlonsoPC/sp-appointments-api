package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.entities.Appointment;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import com.appointments.spappoitmentsapi.repositories.AppointmentRepository;
import com.appointments.spappoitmentsapi.repositories.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImpTest {

    @Mock
    private TestRepository testRepositoryMock;

    @Mock
    private AffiliateRepository affiliateRepositoryMock;

    @Mock
    private AppointmentRepository appointmentRepositoryMock;

    @InjectMocks
    private AppointmentServiceImp underTest;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        underTest = new AppointmentServiceImp(appointmentRepositoryMock,
                modelMapper,
                affiliateRepositoryMock,
                testRepositoryMock);
    }

    @Test
    void getAll() {
        Affiliate aff1 = new Affiliate(1L, "AffName1", 20, "mail1");
        Affiliate aff2 = new Affiliate(2L, "AffName2", 21, "mail2");
        Affiliate aff3 = new Affiliate(3L, "AffName3", 22, "mail3");

        List<Affiliate> affiliateListMocked = new ArrayList<>();
        affiliateListMocked.add(aff1);
        affiliateListMocked.add(aff2);
        affiliateListMocked.add(aff3);

        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest");
        com.appointments.spappoitmentsapi.entities.Test test2
                = new com.appointments.spappoitmentsapi.entities.Test(2L, "NameTest2", "DescTest2");
        com.appointments.spappoitmentsapi.entities.Test test3
                = new com.appointments.spappoitmentsapi.entities.Test(3l, "NameTest3", "DescTest3");

        List<com.appointments.spappoitmentsapi.entities.Test> testListMocked = new ArrayList<>();
        testListMocked.add(test1);
        testListMocked.add(test2);
        testListMocked.add(test3);

        Appointment app1 = new Appointment(1L,
                LocalDate.of(2022, 12, 1),
                LocalTime.of(12, 00),
                aff1,
                test1);

        Appointment app2 = new Appointment(2L,
                LocalDate.of(2022, 12, 2),
                LocalTime.of(12, 00),
                aff2,
                test2);

        Appointment app3 = new Appointment(3L,
                LocalDate.of(2022, 12, 3),
                LocalTime.of(12, 00),
                aff3,
                test3);

        List<Appointment> appointmentListMocked = new ArrayList<>();
        appointmentListMocked.add(app1);
        appointmentListMocked.add(app2);
        appointmentListMocked.add(app3);

        when(appointmentRepositoryMock.findAll()).thenReturn(appointmentListMocked);

        List<AppointmentDTO> result = underTest.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(1).getIdAffiliate()).isEqualTo(aff2.getId());
        assertThat(result.get(2).getIdTest()).isEqualTo(test3.getId());
    }

    @Test
    void validPost() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateAppointment(LocalDate.of(2022, 12, 01));
        appointmentDTO.setHourAppointment(LocalTime.of(12, 00));
        appointmentDTO.setIdAffiliate(1L);
        appointmentDTO.setIdTest(1L);

        Affiliate aff1 = new Affiliate(1L, "AffName1", 20, "mail1");
        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest");

        when(affiliateRepositoryMock.existsById(appointmentDTO.getIdAffiliate())).thenReturn(true);
        when(testRepositoryMock.existsById(appointmentDTO.getIdTest())).thenReturn(true);

        Appointment affCreatedMocked = new Appointment(1L,
                appointmentDTO.getDateAppointment(),
                appointmentDTO.getHourAppointment(),
                aff1,
                test1);

        when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(affCreatedMocked);

        AppointmentDTO result = underTest.post(appointmentDTO);
        assertThat(result).isNotNull();
        assertThat(result.getIdTest()).isEqualTo(appointmentDTO.getIdTest());
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void postPassingId() {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(1L);
        appointmentDTO.setDateAppointment(LocalDate.of(2022, 12, 01));
        appointmentDTO.setHourAppointment(LocalTime.of(12, 00));
        appointmentDTO.setIdAffiliate(1L);
        appointmentDTO.setIdTest(1L);

        AppointmentDTO result = underTest.post(appointmentDTO);
        assertThat(result).isNull();
    }

    @Test
    void postPassingNullIdAffiliateOrNullIdTest() {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateAppointment(LocalDate.of(2022, 12, 01));
        appointmentDTO.setHourAppointment(LocalTime.of(12, 00));
        appointmentDTO.setIdTest(null);
        appointmentDTO.setIdAffiliate(1L);

        AppointmentDTO result = underTest.post(appointmentDTO);
        assertThat(result).isNull();

        appointmentDTO.setIdTest(1L);
        appointmentDTO.setIdAffiliate(null);

        result = underTest.post(appointmentDTO);
        assertThat(result).isNull();

        appointmentDTO.setIdTest(null);
        appointmentDTO.setIdAffiliate(null);

        result = underTest.post(appointmentDTO);
        assertThat(result).isNull();
    }

    @Test
    void postPassingNonExistingAffiliate() {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateAppointment(LocalDate.of(2022, 12, 01));
        appointmentDTO.setHourAppointment(LocalTime.of(12, 00));
        appointmentDTO.setIdAffiliate(999L);
        appointmentDTO.setIdTest(1L);


        when(affiliateRepositoryMock.existsById(appointmentDTO.getIdAffiliate())).thenReturn(false);
        AppointmentDTO result = underTest.post(appointmentDTO);
        assertThat(result).isNull();
    }

    @Test
    void postPassingNonExistingTest() {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateAppointment(LocalDate.of(2022, 12, 01));
        appointmentDTO.setHourAppointment(LocalTime.of(12, 00));
        appointmentDTO.setIdAffiliate(1L);
        appointmentDTO.setIdTest(999L);


        when(affiliateRepositoryMock.existsById(appointmentDTO.getIdAffiliate())).thenReturn(true);
        when(testRepositoryMock.existsById(appointmentDTO.getIdTest())).thenReturn(false);

        AppointmentDTO result = underTest.post(appointmentDTO);
        assertThat(result).isNull();
    }

    @Test
    void postPassingNullDateOrNullHour() {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setHourAppointment(LocalTime.of(12, 00));
        appointmentDTO.setIdAffiliate(1L);
        appointmentDTO.setIdTest(1L);


        when(affiliateRepositoryMock.existsById(appointmentDTO.getIdAffiliate())).thenReturn(true);
        when(testRepositoryMock.existsById(appointmentDTO.getIdTest())).thenReturn(true);

        AppointmentDTO result = underTest.post(appointmentDTO);
        assertThat(result).isNull();

        appointmentDTO.setDateAppointment(LocalDate.of(2022, 12, 01));
        appointmentDTO.setHourAppointment(null);

        result = underTest.post(appointmentDTO);
        assertThat(result).isNull();

        appointmentDTO.setDateAppointment(null);
        appointmentDTO.setHourAppointment(null);

        result = underTest.post(appointmentDTO);
        assertThat(result).isNull();
    }

    @Test
    void getByID() {
        Affiliate aff1 = new Affiliate(1L, "AffName1", 20, "mail1");
        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest");

        Appointment appointmentMocked = new Appointment(1L,
                LocalDate.of(2022, 12, 01),
                LocalTime.of(12, 00),
                aff1,
                test1);

        when(appointmentRepositoryMock.existsById(appointmentMocked.getId())).thenReturn(true);
        when(appointmentRepositoryMock.findById(appointmentMocked.getId())).thenReturn(Optional.of(appointmentMocked));

        AppointmentDTO expected = new AppointmentDTO();
        expected.setId(appointmentMocked.getId());
        expected.setDateAppointment(appointmentMocked.getDateAppointment());
        expected.setHourAppointment(appointmentMocked.getHourAppointment());
        expected.setIdTest(appointmentMocked.getTest().getId());
        expected.setIdAffiliate(appointmentMocked.getAffiliate().getId());

        assertThat(underTest.getByID(appointmentMocked.getId())).isEqualTo(expected);
    }

    @Test
    void getByNonExistingID() {
        when(appointmentRepositoryMock.existsById(999L)).thenReturn(false);
        assertThat(underTest.getByID(999L)).isNull();
    }

    @Test
    void putWhenThereIdACorrectInput() {
        Affiliate aff1 = new Affiliate(1L, "AffName1", 20, "mail1");
        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest");

        Appointment existingAppointment = new Appointment(1L,
                LocalDate.of(2022, 12, 01),
                LocalTime.of(12, 00),
                aff1,
                test1);

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(existingAppointment.getId());
        appUpdater.setDateAppointment(LocalDate.of(2023, 01, 18));

        when(appointmentRepositoryMock.existsById(appUpdater.getId())).thenReturn(true);
        when(appointmentRepositoryMock.findById(appUpdater.getId())).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(existingAppointment);

        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result.getDateAppointment()).isEqualTo(appUpdater.getDateAppointment());
        assertThat(result.getId()).isEqualTo(existingAppointment.getId());
        assertThat(result.getHourAppointment()).isEqualTo(existingAppointment.getHourAppointment());
    }

    @Test
    void putWhenNoIdProvided() {

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(null);
        appUpdater.setDateAppointment(LocalDate.of(2023, 01, 18));

        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result).isNull();
    }

    @Test
    void putWhenNoExistingAppointment() {

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(999L);
        appUpdater.setDateAppointment(LocalDate.of(2023, 01, 18));

        when(appointmentRepositoryMock.existsById(appUpdater.getId())).thenReturn(false);
        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result).isNull();
    }

    @Test
    void putWhenNewAffProvidedAndExist() {

        Appointment existingAppointment = new Appointment();
        Appointment updatedAppointmentMock = new Appointment();
        com.appointments.spappoitmentsapi.entities.Test oldTest = new com.appointments.spappoitmentsapi.entities.Test();

        Affiliate oldAffiliate = new Affiliate();
        Affiliate newAff = new Affiliate();

        oldTest.setId(1L);

        oldAffiliate.setId(1L);
        newAff.setId(2L);

        existingAppointment.setId(1L);
        existingAppointment.setTest(oldTest);
        existingAppointment.setAffiliate(oldAffiliate);
        existingAppointment.setDateAppointment(LocalDate.of(2023, 01, 18));
        existingAppointment.setHourAppointment(LocalTime.of(12, 00));

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(existingAppointment.getId());
        appUpdater.setIdAffiliate(newAff.getId());

        updatedAppointmentMock.setId(existingAppointment.getId());
        updatedAppointmentMock.setTest(existingAppointment.getTest());
        updatedAppointmentMock.setHourAppointment(existingAppointment.getHourAppointment());
        updatedAppointmentMock.setDateAppointment(existingAppointment.getDateAppointment());

        updatedAppointmentMock.setAffiliate(newAff);

        when(appointmentRepositoryMock.existsById(appUpdater.getId())).thenReturn(true);
        when(appointmentRepositoryMock.findById(appUpdater.getId())).thenReturn(Optional.of(existingAppointment));
        when(affiliateRepositoryMock.existsById(appUpdater.getIdAffiliate())).thenReturn(true);
        when(affiliateRepositoryMock.findById(appUpdater.getIdAffiliate())).thenReturn(Optional.of(newAff));
        when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(updatedAppointmentMock);

        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingAppointment.getId());
        assertThat(result.getIdAffiliate()).isEqualTo(newAff.getId());
    }

    @Test
    void putWhenNewAffProvidedButDoesNotExist() {

        Appointment existingAppointment = new Appointment();
        com.appointments.spappoitmentsapi.entities.Test oldTest = new com.appointments.spappoitmentsapi.entities.Test();

        Affiliate oldAffiliate = new Affiliate();

        oldTest.setId(1L);
        oldAffiliate.setId(1L);

        existingAppointment.setId(1L);
        existingAppointment.setAffiliate(oldAffiliate);

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(existingAppointment.getId());
        appUpdater.setIdAffiliate(999L);

        when(appointmentRepositoryMock.existsById(appUpdater.getId())).thenReturn(true);
        when(appointmentRepositoryMock.findById(appUpdater.getId())).thenReturn(Optional.of(existingAppointment));
        when(affiliateRepositoryMock.existsById(appUpdater.getIdAffiliate())).thenReturn(false);
        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result).isNull();
    }

    @Test
    void putWhenNewTestProvidedAndExist() {

        Appointment existingAppointment = new Appointment();
        Appointment updatedAppointmentMock = new Appointment();
        Affiliate oldAffiliate = new Affiliate();

        com.appointments.spappoitmentsapi.entities.Test oldTest
                = new com.appointments.spappoitmentsapi.entities.Test();

        com.appointments.spappoitmentsapi.entities.Test newTest
                = new com.appointments.spappoitmentsapi.entities.Test();

        oldTest.setId(1L);
        oldAffiliate.setId(1L);
        newTest.setId(2L);

        existingAppointment.setId(1L);
        existingAppointment.setTest(oldTest);
        existingAppointment.setAffiliate(oldAffiliate);
        existingAppointment.setDateAppointment(LocalDate.of(2023, 01, 18));
        existingAppointment.setHourAppointment(LocalTime.of(12, 00));

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(existingAppointment.getId());
        appUpdater.setIdTest(newTest.getId());

        updatedAppointmentMock.setId(existingAppointment.getId());
        updatedAppointmentMock.setAffiliate(existingAppointment.getAffiliate());
        updatedAppointmentMock.setHourAppointment(existingAppointment.getHourAppointment());
        updatedAppointmentMock.setDateAppointment(existingAppointment.getDateAppointment());

        updatedAppointmentMock.setTest(newTest);

        when(appointmentRepositoryMock.existsById(appUpdater.getId())).thenReturn(true);
        when(appointmentRepositoryMock.findById(appUpdater.getId())).thenReturn(Optional.of(existingAppointment));
        when(testRepositoryMock.existsById(appUpdater.getIdTest())).thenReturn(true);
        when(testRepositoryMock.findById(appUpdater.getIdTest())).thenReturn(Optional.of(newTest));
        when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(updatedAppointmentMock);

        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingAppointment.getId());
        assertThat(result.getIdTest()).isEqualTo(newTest.getId());
    }

    @Test
    void putWhenNewTestProvidedButDoesNotExist() {

        Appointment existingAppointment = new Appointment();
        com.appointments.spappoitmentsapi.entities.Test oldTest = new com.appointments.spappoitmentsapi.entities.Test();

        Affiliate oldAffiliate = new Affiliate();

        oldTest.setId(1L);
        oldAffiliate.setId(1L);

        existingAppointment.setId(1L);
        existingAppointment.setTest(oldTest);

        AppointmentDTO appUpdater = new AppointmentDTO();
        appUpdater.setId(existingAppointment.getId());
        appUpdater.setIdTest(999L);

        when(appointmentRepositoryMock.existsById(appUpdater.getId())).thenReturn(true);
        when(appointmentRepositoryMock.findById(appUpdater.getId())).thenReturn(Optional.of(existingAppointment));
        when(testRepositoryMock.existsById(appUpdater.getIdTest())).thenReturn(false);
        AppointmentDTO result = underTest.put(appUpdater);

        assertThat(result).isNull();
    }

    @Test
    void deleteWhenAppointmentExist() {
        Long idOfExistingAppointment = 1L;
        when(appointmentRepositoryMock.existsById(idOfExistingAppointment)).thenReturn(true);
        Boolean result = underTest.delete(idOfExistingAppointment);

        assertThat(result).isTrue();
    }

    @Test
    void deleteWhenAppointmentDoesNotExist() {
        Long idOfExistingAppointment = 999L;
        when(appointmentRepositoryMock.existsById(idOfExistingAppointment)).thenReturn(false);
        Boolean result = underTest.delete(idOfExistingAppointment);

        assertThat(result).isFalse();
    }

    @Test
    void deleteAllAppOfATest() {
        Long idOfExistingTest = 1L;
        when(testRepositoryMock.existsById(idOfExistingTest)).thenReturn(true);
        doNothing().when(appointmentRepositoryMock).deleteAppointmentByTestId(idOfExistingTest);

        Boolean result = underTest.deleteByTest(idOfExistingTest);
        assertThat(result).isTrue();
    }

    @Test
    void deleteAllAppOfAUnexistingTest() {
        Long idOfNonExistingTest = 999L;
        when(testRepositoryMock.existsById(idOfNonExistingTest)).thenReturn(false);

        Boolean result = underTest.deleteByTest(idOfNonExistingTest);
        assertThat(result).isFalse();
    }

    @Test
    void deleteAllAppOfAnAff() {
        Long idOfExistingAff = 1L;
        when(affiliateRepositoryMock.existsById(idOfExistingAff)).thenReturn(true);

        Boolean result = underTest.deleteByAffiliate(idOfExistingAff);
        assertThat(result).isTrue();
    }

    @Test
    void deleteAllAppOfAUnexistingAff() {
        Long idOfNonExistingAff = 999L;
        when(affiliateRepositoryMock.existsById(idOfNonExistingAff)).thenReturn(false);

        Boolean result = underTest.deleteByAffiliate(idOfNonExistingAff);
        assertThat(result).isFalse();
    }

    @Test
    void getByAffiliateID() {
        Affiliate aff1 = new Affiliate(1L, "AffName1", 20, "mail1");

        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest");
        com.appointments.spappoitmentsapi.entities.Test test2
                = new com.appointments.spappoitmentsapi.entities.Test(2L, "NameTest2", "DescTest2");
        com.appointments.spappoitmentsapi.entities.Test test3
                = new com.appointments.spappoitmentsapi.entities.Test(3l, "NameTest3", "DescTest3");

        Appointment app1 = new Appointment(1L,
                LocalDate.of(2022, 12, 1),
                LocalTime.of(12, 00),
                aff1,
                test1);

        Appointment app2 = new Appointment(2L,
                LocalDate.of(2022, 12, 2),
                LocalTime.of(12, 00),
                aff1,
                test2);

        Appointment app3 = new Appointment(3L,
                LocalDate.of(2022, 12, 3),
                LocalTime.of(12, 00),
                aff1,
                test3);

        List<Appointment> appointmentListMocked = new ArrayList<>();
        appointmentListMocked.add(app1);
        appointmentListMocked.add(app2);
        appointmentListMocked.add(app3);

        when(affiliateRepositoryMock.existsById(aff1.getId())).thenReturn(true);

        when(appointmentRepositoryMock.getAppointmentByAffiliateId(aff1.getId())).thenReturn(appointmentListMocked);

        List<AppointmentDTO> result = underTest.getByAffiliateID(aff1.getId());

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream().allMatch(appointmentDTO -> appointmentDTO.getIdAffiliate().equals(aff1.getId())));
    }

    @Test
    void getByNonExistingAffiliateID() {
        when(affiliateRepositoryMock.existsById(999L)).thenReturn(false);
        List<AppointmentDTO> result = underTest.getByAffiliateID(999L);

        assertThat(result).isNull();
    }

    @Test
    void getByDate() {
        Affiliate aff1 = new Affiliate(1L, "AffName1", 20, "mail1");

        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest");

        Appointment app1 = new Appointment(1L,
                LocalDate.of(2022, 12, 1),
                LocalTime.of(12, 00),
                aff1,
                test1);

        Appointment app2 = new Appointment(2L,
                LocalDate.of(2022, 12, 1),
                LocalTime.of(12, 00),
                aff1,
                test1);

        Appointment app3 = new Appointment(3L,
                LocalDate.of(2022, 12, 1),
                LocalTime.of(12, 00),
                aff1,
                test1);

        List<Appointment> appointmentListMocked = new ArrayList<>();
        appointmentListMocked.add(app1);
        appointmentListMocked.add(app2);
        appointmentListMocked.add(app3);

        when(appointmentRepositoryMock.getAppointmentByDate(app1.getDateAppointment())).thenReturn(appointmentListMocked);
        List<AppointmentDTO> result = underTest.getByDate(app1.getDateAppointment());

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream().allMatch(appointmentDTO -> appointmentDTO.getDateAppointment().equals(app1.getDateAppointment())));
    }
}