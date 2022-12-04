package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.TestDTO;
import com.appointments.spappoitmentsapi.repositories.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @Mock
    private TestRepository testRepositoryMock;

    @InjectMocks
    private TestService underTest;

    @BeforeEach
    void setUp() {
        //    @Mock
        ModelMapper modelMapper = new ModelMapper();
        underTest = new TestService(testRepositoryMock, modelMapper);
    }

    @Test
    void getAll() {
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

        when(testRepositoryMock.findAll()).thenReturn(testListMocked);

        List<TestDTO> result = underTest.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(1).getName()).isEqualTo("NameTest2");
    }

    @Test
    void validPost() {
        TestDTO testDTO = new TestDTO();
        testDTO.setName("Hello");
        testDTO.setDescription("World");

        when(testRepositoryMock.save(any(com.appointments.spappoitmentsapi.entities.Test.class)))
                .thenReturn(new com.appointments.spappoitmentsapi.entities.Test(1L, "Hello", "World"));

        TestDTO createdDTO = underTest.post(testDTO);
        assertEquals(testDTO.getName(), createdDTO.getName());
    }

    @Test
    void postPassingId() {
        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setName("Hello");
        testDTO.setDescription("World");

        TestDTO createdDTO = underTest.post(testDTO);
        assertEquals(createdDTO, null);
    }

    @Test
    void postPassingNullNameOrNullDescription() {
        TestDTO testDTO = new TestDTO();
        testDTO.setName(null);
        testDTO.setDescription("World");

        TestDTO createdDTO = underTest.post(testDTO);
        assertEquals(createdDTO, null);

        testDTO.setName("Hello");
        testDTO.setDescription(null);

        createdDTO = underTest.post(testDTO);
        assertEquals(createdDTO, null);

        testDTO.setName(null);
        testDTO.setDescription(null);

        createdDTO = underTest.post(testDTO);
        assertEquals(createdDTO, null);
    }

    @Test
    void getByExistingID() {
        com.appointments.spappoitmentsapi.entities.Test testMocked =
                new com.appointments.spappoitmentsapi.entities.Test(1L, "testName", "testDesc");
        when(testRepositoryMock.existsById(1L)).thenReturn(true);
        when(testRepositoryMock.findById(1L)).thenReturn(Optional.of(testMocked));

        TestDTO expected = new TestDTO();
        expected.setId(1L);
        expected.setName("testName");
        expected.setDescription("testDesc");

        assertThat(underTest.getByID(1L)).isEqualTo(expected);
    }

    @Test
    void getByNonExistingID() {
        when(testRepositoryMock.existsById(999L)).thenReturn(false);
        assertThat(underTest.getByID(999L)).isNull();
    }

    @Test
    void putWhenThereIsACorrectInput() {
        com.appointments.spappoitmentsapi.entities.Test existingTestMocked =
                new com.appointments.spappoitmentsapi.entities.Test(1L, "testName", "testDesc");

        TestDTO testDTOUpdater = new TestDTO();
        testDTOUpdater.setId(1L);
        testDTOUpdater.setDescription("Description Updated");

        when(testRepositoryMock.existsById(1L)).thenReturn(true);
        when(testRepositoryMock.findById(testDTOUpdater.getId())).thenReturn(Optional.of(existingTestMocked));
        when(testRepositoryMock.save(any(com.appointments.spappoitmentsapi.entities.Test.class))).thenReturn(existingTestMocked);

        TestDTO result = underTest.put(testDTOUpdater);

        assertThat(result.getDescription()).isEqualTo(testDTOUpdater.getDescription());
        assertThat(result.getName()).isEqualTo(existingTestMocked.getName());
        assertThat(result.getId()).isEqualTo(existingTestMocked.getId());
    }

    @Test
    void putWhenNoIdProvided() {
        TestDTO testDTOUpdater = new TestDTO();
        testDTOUpdater.setId(null);
        testDTOUpdater.setDescription("Description Updated");

        TestDTO result = underTest.put(testDTOUpdater);

        assertThat(result).isNull();
    }

    @Test
    void putWhenNoExistingTestEntity() {
        TestDTO testDTOUpdater = new TestDTO();
        testDTOUpdater.setId(999L);
        testDTOUpdater.setDescription("Description Updated");

        when(testRepositoryMock.existsById(testDTOUpdater.getId())).thenReturn(false);
        TestDTO result = underTest.put(testDTOUpdater);

        assertThat(result).isNull();
    }

    @Test
    void deleteWhenTestEntityExists() {
        Long idOfExistingTestEntity = 1L;
        when(testRepositoryMock.existsById(idOfExistingTestEntity)).thenReturn(true);
        doNothing().when(testRepositoryMock).deleteById(idOfExistingTestEntity);
        Boolean result = underTest.delete(idOfExistingTestEntity);

        assertThat(result).isTrue();
    }

    @Test
    void deleteWhenTestEntityDoesNotExists() {
        Long idOfExistingTestEntity = 999L;
        when(testRepositoryMock.existsById(idOfExistingTestEntity)).thenReturn(false);
        Boolean result = underTest.delete(idOfExistingTestEntity);

        assertThat(result).isFalse();
    }
}