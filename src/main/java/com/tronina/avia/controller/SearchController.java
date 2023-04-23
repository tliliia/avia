package com.tronina.avia.controller;

import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.dto.TicketFilter;
import com.tronina.avia.service.impl.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets/filter")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService service;

    @GetMapping
    public ResponseEntity<List<TicketDto>> serchBy(@RequestParam TicketFilter filter) {
        return ResponseEntity.ok(service.findAllByFilter(filter));
    }
}
