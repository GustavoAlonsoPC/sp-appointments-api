package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AffiliateService {

    private final AffiliateRepository affiliateRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AffiliateService(AffiliateRepository affiliateRepository, ModelMapper modelMapper) {
        this.affiliateRepository = affiliateRepository;
        this.modelMapper = modelMapper;
    }

    public List<AffiliateDTO> getAll() {
        List<Affiliate> affiliates = affiliateRepository.findAll();
        List<AffiliateDTO> affiliateDTOList = new ArrayList<>();
        for (Affiliate aff : affiliates) {
            AffiliateDTO affiliateDTO = modelMapper.map(aff, AffiliateDTO.class);
            affiliateDTOList.add(affiliateDTO);
        }
        return affiliateDTOList;
    }

    public AffiliateDTO post(AffiliateDTO affiliateDTO) {
        if (affiliateDTO.getId() <= 0 || affiliateDTO != null) {
            System.out.println("Trying to POST with an ID Field");
            return null;
        }
        Affiliate affiliate = modelMapper.map(affiliateDTO, Affiliate.class);
        affiliateRepository.save(affiliate);
        return affiliateDTO;
    }
}
