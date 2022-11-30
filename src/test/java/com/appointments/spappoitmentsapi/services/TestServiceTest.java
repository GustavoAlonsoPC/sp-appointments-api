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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
                = new com.appointments.spappoitmentsapi.entities.Test(1L, "NameTest1", "DescTest", null);
        com.appointments.spappoitmentsapi.entities.Test test2
                = new com.appointments.spappoitmentsapi.entities.Test(2L, "NameTest2", "DescTest2", null);
        com.appointments.spappoitmentsapi.entities.Test test3
                = new com.appointments.spappoitmentsapi.entities.Test(3l, "NameTest3", "DescTest3", null);

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
                .thenReturn(new com.appointments.spappoitmentsapi.entities.Test(1L, "Hello", "World", null));

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
    void getByID() {
    }

    @Test
    void put() {
    }

    @Test
    void delete() {
    }
}