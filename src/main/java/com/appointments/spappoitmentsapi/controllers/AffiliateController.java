package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.entities.Affiliate;
import com.appointments.spappoitmentsapi.services.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AffiliateDTO>> getList() {
        List<AffiliateDTO> affiliateDTOList = affiliateService.getAll();
        if (affiliateDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<List<AffiliateDTO>>(affiliateDTOList, HttpStatus.OK);
    }
}
