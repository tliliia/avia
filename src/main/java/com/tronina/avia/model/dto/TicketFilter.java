package com.tronina.avia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketFilter {
    private String airlineName;

    private String planeBrand;
    private String planeModel;

    private String fligthDeparture;
    private String fligthDestination;
    private LocalDateTime fligthDepartureTime;
    private LocalDateTime fligthArrivalTime;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
