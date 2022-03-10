package com.gmail.vanyasudnishnikov.model;

import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import com.gmail.vanyasudnishnikov.validator.NotNullDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.gmail.vanyasudnishnikov.model.ServiceConstants.MAX_LENGTH_DESCRIPTION;
import static com.gmail.vanyasudnishnikov.model.ServiceConstants.MAX_LENGTH_NAME;

@Data
@Builder
@NotNullDTO(message = "Name, description and status  does not match parameters. Adding item is not possible")
public class ItemDTO {
    @Size(max = MAX_LENGTH_NAME, message = "The name must contain no more than 400 characters")
    private String name;
    @Size(max = MAX_LENGTH_DESCRIPTION, message = "The description must contain no more than 200 characters")
    private String description;
    @NotBlank(message = "Please, enter status in Upper case")
    private StatusEnum status;

}