package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;

import java.util.List;

public interface AffiliateService {

    List<AffiliateDTO> getAll();
    AffiliateDTO post(AffiliateDTO affiliateDTO);
    AffiliateDTO getByID(Long id);
    AffiliateDTO put(AffiliateDTO affiliateDTO);
    Boolean delete(Long id);
}
