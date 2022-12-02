package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.services.AffiliateService;
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
class AffiliateControllerTest {

    @Mock
    private AffiliateService affiliateServiceMock;

    @InjectMocks
    AffiliateController underTest;

    @BeforeEach
    void setUp() {
        underTest = new AffiliateController(affiliateServiceMock);
    }

    @Test
    void getNotEmptyList() {
        List<AffiliateDTO> affiliateDTOListMocked = new ArrayList<>();
        AffiliateDTO aff1 = new AffiliateDTO();
        AffiliateDTO aff2 = new AffiliateDTO();

        aff1.setId(1L);
        aff1.setName("AffName1");
        aff1.setMail("affMail1");
        aff1.setAge(23);

        aff2.setId(2L);
        aff2.setName("AffName2");
        aff2.setMail("affMail2");
        aff2.setAge(24);

        affiliateDTOListMocked.add(aff1);
        affiliateDTOListMocked.add(aff2);

        when(affiliateServiceMock.getAll()).thenReturn(affiliateDTOListMocked);
        ResponseEntity<List<AffiliateDTO>> response = underTest.getList();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(affiliateDTOListMocked);
    }

    @Test
    void getEmptyList() {
        List<AffiliateDTO> affiliateDTOListMocked = new ArrayList<>();

        when(affiliateServiceMock.getAll()).thenReturn(affiliateDTOListMocked);
        ResponseEntity<List<AffiliateDTO>> response = underTest.getList();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void postWhenValidEntry() {
        AffiliateDTO affToBeCreated = new AffiliateDTO();
        affToBeCreated.setName("affName");
        affToBeCreated.setMail("affMail");
        affToBeCreated.setAge(23);

        AffiliateDTO affCreatedMock = new AffiliateDTO();
        affCreatedMock.setId(1L);
        affCreatedMock.setAge(affToBeCreated.getAge());
        affCreatedMock.setName(affToBeCreated.getName());
        affCreatedMock.setMail(affToBeCreated.getMail());

        when(affiliateServiceMock.post(affToBeCreated)).thenReturn(affCreatedMock);
        ResponseEntity<AffiliateDTO> response = underTest.post(affToBeCreated);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(affCreatedMock);
    }

    @Test
    void postWhenNoValidEntry() {
        AffiliateDTO affToBeCreated = new AffiliateDTO();
        affToBeCreated.setId(1L);
        affToBeCreated.setName("affName");
        affToBeCreated.setMail("affMail");
        affToBeCreated.setAge(23);

        when(affiliateServiceMock.post(affToBeCreated)).thenReturn(null);
        ResponseEntity<AffiliateDTO> response = underTest.post(affToBeCreated);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
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