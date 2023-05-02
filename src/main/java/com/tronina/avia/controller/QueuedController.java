package com.tronina.avia.controller;

import com.tronina.avia.service.impl.QueuedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/queue")
public class QueuedController {
    private final QueuedService service;

    @PostMapping
    public ResponseEntity<?> downloadFile(@RequestParam("email") String email) {
        service.produceReportAndSend(email);
        return ResponseEntity
                .ok()
                .build();
    }
}
