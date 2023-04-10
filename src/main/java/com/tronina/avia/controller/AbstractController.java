package com.tronina.avia.controller;

import com.tronina.avia.entity.BaseEntity;
import com.tronina.avia.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Абстрактный контроллер с круд операциями
 */
@RequiredArgsConstructor
public abstract class AbstractController<E extends BaseEntity, S extends AbstractService<E, ?>> {
    protected final S service;

    @GetMapping("/{id}")
    public ResponseEntity<E> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<E>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> update(@RequestBody E element, @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity(service.update(id, element), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<E> create(@RequestBody E element) {
        return new ResponseEntity(service.create(element), HttpStatus.CREATED);
    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long elementId) {
        service.deleteById(elementId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}