package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.services.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<AffiliateDTO> post(@RequestBody AffiliateDTO affiliateDTO) {
        affiliateDTO = affiliateService.post(affiliateDTO);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AffiliateDTO> getByID(@PathVariable Long id) {
        AffiliateDTO affiliateDTO = affiliateService.getByID(id);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AffiliateDTO> put(@RequestBody AffiliateDTO affiliateDTO) {
        affiliateDTO = affiliateService.put(affiliateDTO);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return affiliateService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
