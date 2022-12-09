package com.appointments.spappoitmentsapi.controllers;

import com.appointments.spappoitmentsapi.dto.AppointmentDTO;
import com.appointments.spappoitmentsapi.services.AppointmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/controller/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @ApiOperation(value = "Get all appointments", notes = "Retrieves all the appointments stored from the database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Appointments retrieved"),
            @ApiResponse(code = 204, message = "No appointments found")})
    public ResponseEntity<List<AppointmentDTO>> getList() {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAll();
        if (appointmentDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new appointment", notes = "Creates a new appointment")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input")})
    public ResponseEntity<AppointmentDTO> post(@RequestBody AppointmentDTO appointmentDTO) {
        appointmentDTO = appointmentService.post(appointmentDTO);
        return appointmentDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(appointmentDTO, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Return an appointment by ID", notes = "Need an ID parameter in path")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "appointment found"),
            @ApiResponse(code = 404, message = "appointment does not exists")})
    public ResponseEntity<AppointmentDTO> getByID(@PathVariable Long id) {
        AppointmentDTO appointmentDTO = appointmentService.getByID(id);
        return appointmentDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Updates an existing appointment", notes = "Needs an ID parameter in query")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Invalid input")})
    public ResponseEntity<AppointmentDTO> put(@RequestBody AppointmentDTO appointmentDTO) {
        appointmentDTO = appointmentService.put(appointmentDTO);
        return appointmentDTO == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(appointmentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deletes an appointment", notes = "Deletes an appointment")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Appointment does not exists")})
    public ResponseEntity delete(@PathVariable Long id) {
        return appointmentService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/aff",params = "idAffiliate")
    @ApiOperation(value = "Gets by affiliate", notes = "Gets all appointments of a given affiliate")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully consulted"),
            @ApiResponse(code = 404, message = "Appointment does not exists"),
            @ApiResponse(code = 204, message = "aff does not have appointments")})
    public ResponseEntity<List<AppointmentDTO>> getByAffiliateID(@RequestParam Long idAffiliate) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getByAffiliateID(idAffiliate);
        if (appointmentDTOList == null) return ResponseEntity.notFound().build();
        if (appointmentDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/date", params = "dateAppointment")
    @ApiOperation(value = "Gets by date", notes = "Gets all appointments of a given date")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully consulted"),
            @ApiResponse(code = 204, message = "date does not have appointments")})
    public ResponseEntity<List<AppointmentDTO>> getByDate(@RequestParam LocalDate dateAppointment) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getByDate(dateAppointment);
        if (appointmentDTOList.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "Deletes by affiliate or test", notes = "Deletes all appointments of a given affiliate or test")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 400, message = "Bad request")})

    public ResponseEntity deleteByAffiliateOrTest(@RequestParam(required = false) Long affiliateId,
                                            @RequestParam(required = false) Long testId) {
        if (affiliateId != null && testId != null) return ResponseEntity.badRequest().build();
        if (affiliateId == null && testId == null) return ResponseEntity.badRequest().build();
        Boolean result = testId == null ? appointmentService.deleteByAffiliate(affiliateId)
                : appointmentService.deleteByTest(testId);

        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
