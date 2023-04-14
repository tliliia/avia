package com.tronina.avia.model.mapper;

import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.model.entity.Airplane;
import com.tronina.avia.model.entity.Flight;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "airplane", ignore = true)
    FlightDto toDto(Flight e);

    List<FlightDto> toDtoList(List<Flight> e);

    @InheritInverseConfiguration
    Flight toEntity(FlightDto dto);
}

