package com.appointments.spappoitmentsapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AffiliateDTO {

    @ApiModelProperty(readOnly = true) private Long id;
    @ApiModelProperty private String name;
    @ApiModelProperty private Integer age;
    @ApiModelProperty private String mail;
}
