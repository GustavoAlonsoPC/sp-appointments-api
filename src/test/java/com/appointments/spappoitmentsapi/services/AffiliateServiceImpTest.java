package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AffiliateServiceImpTest {

    @Mock
    private AffiliateRepository affiliateRepositoryMock;

    @InjectMocks
    private AffiliateServiceImp underTest;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        underTest = new AffiliateServiceImp(affiliateRepositoryMock, modelMapper);
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

        when(affiliateRepositoryMock.findAll()).thenReturn(affiliateListMocked);

        List<AffiliateDTO> result = underTest.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(1).getName()).isEqualTo("AffName2");
    }

    @Test
    void validPost() {
        AffiliateDTO affiliateDTO = new AffiliateDTO();
        affiliateDTO.setName("testPostName");
        affiliateDTO.setAge(20);
        affiliateDTO.setMail("testPostMail");

        Affiliate affCreatedMocked = new Affiliate(1L,
                affiliateDTO.getName(),
                affiliateDTO.getAge(),
                affiliateDTO.getMail());

        when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(affCreatedMocked);

        AffiliateDTO result = underTest.post(affiliateDTO);
        assertThat(result.getName()).isEqualTo(affiliateDTO.getName());
    }

    @Test
    void postPassingId() {
        AffiliateDTO affiliateDTO = new AffiliateDTO();
        affiliateDTO.setId(1L);
        affiliateDTO.setName("testPostName");
        affiliateDTO.setAge(20);
        affiliateDTO.setMail("testPostMail");

        AffiliateDTO result = underTest.post(affiliateDTO);
        assertThat(result).isNull();
    }

    @Test
    void postPassingNullNameOrNullAge() {
        AffiliateDTO affiliateDTO = new AffiliateDTO();
        affiliateDTO.setName(null);
        affiliateDTO.setAge(20);

        AffiliateDTO result = underTest.post(affiliateDTO);
        assertThat(result).isNull();

        affiliateDTO.setName("SomeName");
        affiliateDTO.setAge(null);

        result = underTest.post(affiliateDTO);
        assertThat(result).isNull();

        affiliateDTO.setName(null);
        affiliateDTO.setAge(null);

        result = underTest.post(affiliateDTO);
        assertThat(result).isNull();
    }

    @Test
    void getByExistingID() {
        Affiliate affiliateMocked = new Affiliate(1L,
                "AffTestName",
                20,
                "affTestMail");

        when(affiliateRepositoryMock.existsById(affiliateMocked.getId())).thenReturn(true);
        when(affiliateRepositoryMock.findById(affiliateMocked.getId())).thenReturn(Optional.of(affiliateMocked));

        AffiliateDTO expected = new AffiliateDTO();
        expected.setId(affiliateMocked.getId());
        expected.setName(affiliateMocked.getName());
        expected.setMail(affiliateMocked.getMail());
        expected.setAge(affiliateMocked.getAge());

        assertThat(underTest.getByID(affiliateMocked.getId())).isEqualTo(expected);
    }

    @Test
    void getByNonExistingID() {
        when(affiliateRepositoryMock.existsById(999L)).thenReturn(false);
        assertThat(underTest.getByID(999L)).isNull();
    }

    @Test
    void putWhenThereIsACorrectInput() {

        Affiliate existingAffiliateMocked = new Affiliate(1L,
                "AffTestName",
                20,
                "affTestMail");

        AffiliateDTO affUpdater = new AffiliateDTO();
        affUpdater.setId(existingAffiliateMocked.getId());
        affUpdater.setMail("AffMailTestUpdated");

        when(affiliateRepositoryMock.existsById(affUpdater.getId())).thenReturn(true);
        when(affiliateRepositoryMock.findById(affUpdater.getId())).thenReturn(Optional.of(existingAffiliateMocked));
        when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(existingAffiliateMocked);

        AffiliateDTO result = underTest.put(affUpdater);

        assertThat(result.getMail()).isEqualTo(affUpdater.getMail());
        assertThat(result.getName()).isEqualTo(existingAffiliateMocked.getName());
        assertThat(result.getId()).isEqualTo(existingAffiliateMocked.getId());
    }

    @Test
    void putWhenNoIdProvided() {
        AffiliateDTO affUpdater = new AffiliateDTO();
        affUpdater.setId(null);
        affUpdater.setMail("someMail");

        AffiliateDTO result = underTest.put(affUpdater);

        assertThat(result).isNull();
    }

    @Test
    void putWhenNoExistingAffiliate() {
        AffiliateDTO affUpdater = new AffiliateDTO();
        affUpdater.setId(999L);
        affUpdater.setMail("someMail");

        when(affiliateRepositoryMock.existsById(affUpdater.getId())).thenReturn(false);
        AffiliateDTO result = underTest.put(affUpdater);

        assertThat(result).isNull();
    }

    @Test
    void deleteWhenAffiliateExists() {
        Long idOfExistingAffiliate = 1L;
        when(affiliateRepositoryMock.existsById(idOfExistingAffiliate)).thenReturn(true);
        doNothing().when(affiliateRepositoryMock).deleteById(idOfExistingAffiliate);
        Boolean result = underTest.delete(idOfExistingAffiliate);

        assertThat(result).isTrue();
    }

    @Test
    void deleteWhenAffiliateDoesNotExists() {
        Long idOfExistingAffiliate = 999L;
        when(affiliateRepositoryMock.existsById(idOfExistingAffiliate)).thenReturn(false);
        Boolean result = underTest.delete(idOfExistingAffiliate);

        assertThat(result).isFalse();
    }

    @Test
    void deleteWhenAffiliateEntityIsBeingReferencedByOthers() {
        when(affiliateRepositoryMock.existsById(1L)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(affiliateRepositoryMock).deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.delete(1L));
    }
}