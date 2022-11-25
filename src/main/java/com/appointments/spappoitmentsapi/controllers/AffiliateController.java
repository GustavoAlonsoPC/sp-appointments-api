package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.services.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/controller/affiliates")
public class AffiliateController {

    private final AffiliateService affiliateService;

    @Autowired
    public AffiliateController(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }
    
    @GetMapping
    public List<Affiliate> getAllAffiliates() {
        return affiliateService.getAll();
    }
}
