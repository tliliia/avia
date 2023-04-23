package com.tronina.avia.service.impl;

import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.dto.TicketFilter;
import com.tronina.avia.model.mapper.TicketMapper;
import com.tronina.avia.repository.repository.FilterTicketRepository;
import com.tronina.avia.repository.repository.impl.FilterTicketRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final FilterTicketRepositoryImpl repository;
    private final TicketMapper mapper = TicketMapper.INSTANCE;

    public List<TicketDto> findAllByFilter(TicketFilter status) {
        return mapper.toDtoList(repository.findAllByFilter(status));
    }

}
