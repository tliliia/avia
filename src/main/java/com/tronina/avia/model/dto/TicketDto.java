package com.tronina.avia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    /**
     * Порядковый номер в салоне самолета
     */
    private Integer number;
    private String status;
    private BigDecimal price;
}
