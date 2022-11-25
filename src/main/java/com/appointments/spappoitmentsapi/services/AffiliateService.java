package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliateService {

    private final AffiliateRepository affiliateRepository;

    @Autowired
    public AffiliateService(AffiliateRepository affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    public List<Affiliate> getAll() {
        return affiliateRepository.findAll();
    }
}
