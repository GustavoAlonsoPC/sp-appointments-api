package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.services.AffiliateService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiOperation(value = "Get all affiliates", notes = "Retrieves all the affiliates stored in the database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Affiliates retrieved"),
                            @ApiResponse(code = 204, message = "No affiliates found")})
    public ResponseEntity<List<AffiliateDTO>> getList() {
        List<AffiliateDTO> affiliateDTOList = affiliateService.getAll();
        if (affiliateDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<List<AffiliateDTO>>(affiliateDTOList, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new affiliate", notes = "Creates a new affiliate")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created"),
                            @ApiResponse(code = 400, message = "Invalid input")})
    public ResponseEntity<AffiliateDTO> post(@RequestBody AffiliateDTO affiliateDTO) {
        if (affiliateDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        affiliateDTO = affiliateService.post(affiliateDTO);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Return an affiliate by ID", notes = "Need an ID paramenter in path")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Affiliate found"),
            @ApiResponse(code = 404, message = "Affiliates does not exists")})
    public ResponseEntity<AffiliateDTO> getByID(@PathVariable Long id) {
        AffiliateDTO affiliateDTO = affiliateService.getByID(id);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Updates an existing affiliate", notes = "Needs an ID parameter in query")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Invalid input")})
    public ResponseEntity<AffiliateDTO> put(@RequestBody AffiliateDTO affiliateDTO) {
        if (affiliateDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        affiliateDTO = affiliateService.put(affiliateDTO);
        return affiliateDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<AffiliateDTO>(affiliateDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deletes an affiliate", notes = "Deletes an affiliate, if the affiliate is referenced in another entity the deletion can't be completed")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Affiliate does not exists"),
            @ApiResponse(code = 500, message = "Affiliate is referenced in other entities (children)")})
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
