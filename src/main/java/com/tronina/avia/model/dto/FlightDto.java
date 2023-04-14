package com.tronina.avia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDto {
    private Long id;
    private String departure;
    private String destination;
    private String departureTime;
    private String arrivalTime;
}
