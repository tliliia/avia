package com.tronina.avia.model.mapper;

import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.Airplane;
import com.tronina.avia.model.entity.Ticket;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "flight", ignore = true)
    TicketDto toDto(Ticket e);

    List<TicketDto> toDtoList(List<Ticket> e);

    @InheritInverseConfiguration
    Ticket toEntity(TicketDto dto);
}

