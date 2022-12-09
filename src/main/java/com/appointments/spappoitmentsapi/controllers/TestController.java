package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.TestDTO;
import com.appointments.spappoitmentsapi.services.TestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/controller/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    @ApiOperation(value = "Get all tests", notes = "Retrieves all the tests stored from the database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Tests retrieved"),
            @ApiResponse(code = 204, message = "No tests found")})
    public ResponseEntity<List<TestDTO>> getList() {
        List<TestDTO> testDTOList = testService.getAll();
        if (testDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(testDTOList, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new test", notes = "Creates a new test")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input")})
    public ResponseEntity<TestDTO> post(@RequestBody TestDTO testDTO) {
        if (testDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        testDTO = testService.post(testDTO);
        return testDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(testDTO, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Return a test by ID", notes = "Need an ID paramenter in path")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "test found"),
            @ApiResponse(code = 404, message = "test does not exists")})
    public ResponseEntity<TestDTO> getByID(@PathVariable Long id) {
        TestDTO testDTO = testService.getByID(id);
        return testDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(testDTO, HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Updates an existing test", notes = "Needs an ID parameter in query")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Invalid input")})
    public ResponseEntity<TestDTO> put(@RequestParam Long testId, @RequestBody TestDTO testDTO) {
        if (testId == null || testDTO == null || testDTO.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        testDTO = testService.put(testId, testDTO);
        return testDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(testDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deletes a test", notes = "Deletes a test, if the test is referenced in another entity the deletion can't be completed")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Test does not exists"),
            @ApiResponse(code = 500, message = "Test is referenced in other entities (children)")})
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            return testService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "trying to delete referenced test");
            map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("try", "delete the children first");

            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
