package com.tronina.avia.model.mapper;

import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.model.entity.Airplane;
import com.tronina.avia.model.entity.Flight;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DateMapper.class})
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @InheritInverseConfiguration
    FlightDto toDto(Flight e);

    List<FlightDto> toDtoList(List<Flight> e);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "airplane", ignore = true)
    Flight toEntity(FlightDto dto);
}

