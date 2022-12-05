package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.services.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (affiliateDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        if (affiliateDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        affiliateDTO = affiliateService.put(affiliateDTO);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            return affiliateService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "trying to delete referenced affiliate");
            map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("try", "delete the children first");

            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
