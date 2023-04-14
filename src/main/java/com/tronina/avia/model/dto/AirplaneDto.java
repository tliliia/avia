package com.tronina.avia.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirplaneDto {
    private Long id;
    private String brand;
    private String model;
    private Integer seats;

    private Integer airlineId;
}
