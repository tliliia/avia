package com.tronina.avia.model.mapper;

import com.tronina.avia.model.dto.AirlineDto;
import com.tronina.avia.model.entity.Airline;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface AirlineMapper {
    AirlineMapper INSTANCE = Mappers.getMapper(AirlineMapper.class);

    @Mapping(source = "id", target = "id")
    AirlineDto toDto(Airline e);

    List<AirlineDto> toDtoList(List<Airline> e);

    @InheritInverseConfiguration
    Airline toEntity(AirlineDto dto);
}

