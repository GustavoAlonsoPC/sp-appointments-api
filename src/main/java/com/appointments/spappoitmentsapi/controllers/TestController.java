package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AffiliateDTO;
import com.appointments.spappoitmentsapi.dto.TestDTO;
import com.appointments.spappoitmentsapi.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/controller/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public ResponseEntity<List<TestDTO>> getList() {
        List<TestDTO> testDTOList = testService.getAll();
        if (testDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(testDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TestDTO> post(@RequestBody TestDTO testDTO) {
        testDTO = testService.post(testDTO);
        return testDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(testDTO, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TestDTO> getByID(@PathVariable Long id) {
        TestDTO testDTO = testService.getByID(id);
        return testDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(testDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TestDTO> put(@RequestBody TestDTO testDTO) {
        testDTO = testService.put(testDTO);
        return testDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(testDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return testService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
