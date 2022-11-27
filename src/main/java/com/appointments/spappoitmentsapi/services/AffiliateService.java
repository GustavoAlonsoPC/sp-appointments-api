package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import org.modelmapper.ModelMapper;
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
}
