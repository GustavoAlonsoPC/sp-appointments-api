package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.TestDTO;
import com.appointments.spappoitmentsapi.services.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    @Mock
    private TestService testServiceMock;

    @InjectMocks
    private TestController underTest;

    @BeforeEach
    void setUp() {
        underTest = new TestController(testServiceMock);
    }

    @Test
    void getNotEmptyList() {
        List<TestDTO> testDTOListMocked = new ArrayList<>();
        TestDTO testDTO1 = new TestDTO();
        testDTO1.setId(1L);
        testDTO1.setName("TestName1");
        testDTO1.setDescription("TestDesc1");

        TestDTO testDTO2 = new TestDTO();
        testDTO2.setId(2L);
        testDTO2.setName("TestName2");
        testDTO2.setDescription("TestDesc2");

        testDTOListMocked.add(testDTO1);
        testDTOListMocked.add(testDTO2);

        when(testServiceMock.getAll()).thenReturn(testDTOListMocked);

        ResponseEntity<List<TestDTO>> response = underTest.getList();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testDTOListMocked);
    }

    @Test
    void getEmptyList() {
        List<TestDTO> testDTOListMocked = new ArrayList<>();
        when(testServiceMock.getAll()).thenReturn(testDTOListMocked);
        ResponseEntity<List<TestDTO>> response = underTest.getList();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void post() {
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