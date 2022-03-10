package com.gmail.vanyasudnishnikov.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UpdateItemDTO {
    private Integer id;
    @NotBlank(message = "Please,enter status in upper case")
    private String status;
}
