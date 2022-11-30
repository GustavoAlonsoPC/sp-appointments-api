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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @Mock
    private TestRepository testRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private TestService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TestService(testRepositoryMock, modelMapperMock);
    }

    @Test
    void getAll() {
        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(null, "NameTest", "DescTest", null);
        com.appointments.spappoitmentsapi.entities.Test test2
                = new com.appointments.spappoitmentsapi.entities.Test(null, "NameTest", "DescTest", null);
        com.appointments.spappoitmentsapi.entities.Test test3
                = new com.appointments.spappoitmentsapi.entities.Test(null, "NameTest", "DescTest", null);

        List<com.appointments.spappoitmentsapi.entities.Test> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);
    }

    @Test
    void post() {
        TestDTO testDTO = new TestDTO();
        testDTO.setName("Hello");
        testDTO.setDescription("World");

        when(testRepositoryMock.save(any(com.appointments.spappoitmentsapi.entities.Test.class))).thenReturn(new com.appointments.spappoitmentsapi.entities.Test());
        when(modelMapperMock.map(any(TestDTO.class),
                eq(com.appointments.spappoitmentsapi.entities.Test.class)))
                .thenReturn(new com.appointments.spappoitmentsapi.entities.Test(null, "Hello", "World", null));

        when(modelMapperMock.map(any(com.appointments.spappoitmentsapi.entities.Test.class),
                eq(TestDTO.class)))
                .thenReturn(testDTO);

        TestDTO createdDTO = underTest.post(testDTO);
        assertEquals(testDTO.getName(), createdDTO.getName());
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