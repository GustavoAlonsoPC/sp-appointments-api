package com.appointments.spappoitmentsapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel
public class TestDTO {

    @ApiModelProperty(readOnly = true) private Long id;
    @ApiModelProperty private String name;
    @ApiModelProperty private String description;
}
