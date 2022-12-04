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
public class AffiliateServiceImp implements AffiliateService{

    private final AffiliateRepository affiliateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AffiliateServiceImp(AffiliateRepository affiliateRepository, ModelMapper modelMapper) {
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
        if (affiliateDTO.getId() != null) {
            System.out.println("Trying to POST with an ID Field");
            return null;
        }

        if (affiliateDTO.getName() == null || affiliateDTO.getAge() == null) {
            System.out.println("Missing requerided data");
            return null;
        }
        Affiliate affiliate = modelMapper.map(affiliateDTO, Affiliate.class);
        return modelMapper.map(affiliateRepository.save(affiliate), AffiliateDTO.class);
    }

    public AffiliateDTO getByID(Long id) {
        if (!affiliateRepository.existsById(id)) {
            System.out.println("Do not exist");
            return null;
        }
        Affiliate affiliate = affiliateRepository.findById(id).get();
        return modelMapper.map(affiliate, AffiliateDTO.class);
    }

    public AffiliateDTO put(AffiliateDTO affiliateDTO) {
        if (affiliateDTO.getId() == null) return null;
        if (!affiliateRepository.existsById(affiliateDTO.getId())) return null;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        Affiliate affiliate = affiliateRepository.findById(affiliateDTO.getId()).get();
        modelMapper.map(affiliateDTO, affiliate);
        affiliateRepository.save(affiliate);
        return modelMapper.map(affiliate, AffiliateDTO.class);
    }

    public Boolean delete(Long id) {
        if (affiliateRepository.existsById(id)) {
            affiliateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
