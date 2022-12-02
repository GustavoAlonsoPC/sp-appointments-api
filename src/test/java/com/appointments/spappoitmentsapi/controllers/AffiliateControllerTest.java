package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.dto.TestDTO;
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

        affiliateDTOListMocked.add(aff1);
        affiliateDTOListMocked.add(aff2);

        when(affiliateServiceMock.getAll()).thenReturn(affiliateDTOListMocked);
        ResponseEntity<List<AffiliateDTO>> response = underTest.getList();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEmpty()).isFalse();
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
        AffiliateDTO affCreatedMock = new AffiliateDTO();

        when(affiliateServiceMock.post(affToBeCreated)).thenReturn(affCreatedMock);
        ResponseEntity<AffiliateDTO> response = underTest.post(affToBeCreated);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(affCreatedMock);
    }

    @Test
    void postWhenNoValidEntry() {
        AffiliateDTO affToBeCreated = new AffiliateDTO();

        when(affiliateServiceMock.post(affToBeCreated)).thenReturn(null);
        ResponseEntity<AffiliateDTO> response = underTest.post(affToBeCreated);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void postWhenNullEntry() {
        ResponseEntity<AffiliateDTO> response = underTest.post(null);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getExistingAffiliateByID() {
        AffiliateDTO affMock = new AffiliateDTO();

        when(affiliateServiceMock.getByID(1L)).thenReturn(affMock);
        ResponseEntity<AffiliateDTO> response = underTest.getByID(1L);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(affMock);
    }

    @Test
    void getNonExtistingAffiliateByID() {

        when(affiliateServiceMock.getByID(999L)).thenReturn(null);
        ResponseEntity<AffiliateDTO> response = underTest.getByID(999L);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void putWhenValidEntry() {
        AffiliateDTO affUpdater = new AffiliateDTO();
        AffiliateDTO affUpdatedMock = new AffiliateDTO();

        when(affiliateServiceMock.put(affUpdater)).thenReturn(affUpdatedMock);
        ResponseEntity<AffiliateDTO> response = underTest.put(affUpdater);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(affUpdatedMock);
    }

    @Test
    void putWhenNoValidEntry() {
        AffiliateDTO affUpdater = new AffiliateDTO();

        when(affiliateServiceMock.put(affUpdater)).thenReturn(null);
        ResponseEntity<AffiliateDTO> response = underTest.put(affUpdater);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void putWithNullEntry() {
        ResponseEntity<AffiliateDTO> response = underTest.put(null);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void deleteWhenExistingId() {

        when(affiliateServiceMock.delete(1L)).thenReturn(true); //1 being an existing id
        ResponseEntity response = underTest.delete(1L);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void deleteWhenNoExistingId() {

        when(affiliateServiceMock.delete(999L)).thenReturn(false); //999 being an unexisting id
        ResponseEntity response = underTest.delete(999L);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}