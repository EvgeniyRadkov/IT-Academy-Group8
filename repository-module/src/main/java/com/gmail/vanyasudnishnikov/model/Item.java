package com.gmail.vanyasudnishnikov.model;

import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class Item {
    private Integer id;
    private String name;
    private String description;
    private StatusEnum status;
}