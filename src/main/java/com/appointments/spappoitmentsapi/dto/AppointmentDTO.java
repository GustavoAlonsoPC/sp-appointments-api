package com.appointments.spappoitmentsapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel
public class AppointmentDTO {

    @ApiModelProperty(readOnly = true) private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @ApiModelProperty(dataType = "string", example = "01/01/2023") private LocalDate dateAppointment;
    @JsonFormat(pattern = "HH:mm")
    @ApiModelProperty(dataType = "string", example = "12:00")private LocalTime hourAppointment;
    private Long idTest;
    private Long idAffiliate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDateAppointment() {
		return dateAppointment;
	}
	public void setDateAppointment(LocalDate dateAppointment) {
		this.dateAppointment = dateAppointment;
	}
	public LocalTime getHourAppointment() {
		return hourAppointment;
	}
	public void setHourAppointment(LocalTime hourAppointment) {
		this.hourAppointment = hourAppointment;
	}
	public Long getIdTest() {
		return idTest;
	}
	public void setIdTest(Long idTest) {
		this.idTest = idTest;
	}
	public Long getIdAffiliate() {
		return idAffiliate;
	}
	public void setIdAffiliate(Long idAffiliate) {
		this.idAffiliate = idAffiliate;
	}
    
    
}
