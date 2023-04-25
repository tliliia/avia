package com.tronina.avia.model.mapper;

import com.tronina.avia.model.dto.AirlineDto;
import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.entity.Airline;
import com.tronina.avia.model.entity.Airplane;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface PlaneMapper {
    PlaneMapper INSTANCE = Mappers.getMapper(PlaneMapper.class);

    @Mapping(source = "airline.id", target = "airlineId")
    AirplaneDto toDto(Airplane e);

    List<AirplaneDto> toDtoList(List<Airplane> e);

    @InheritInverseConfiguration
    Airplane toEntity(AirplaneDto dto);
}

