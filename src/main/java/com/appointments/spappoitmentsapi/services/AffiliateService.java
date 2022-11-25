package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.repositories.AffiliateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliateService {

    private final AffiliateRepository affiliateRepository;

    @Autowired
    public AffiliateService(AffiliateRepository affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }
}
