package com.tronina.avia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
    private Long id;
    private String actualDate;
    private List<AirlineStat> statLines;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AirlineStat {
        private Long id;
        private String name;
        private Long comission;
    }
}
