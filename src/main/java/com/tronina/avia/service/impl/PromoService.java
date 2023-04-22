package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.entity.Promo;
import com.tronina.avia.repository.PromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromoService {
    private final PromoRepository repository;

    Optional<Promo> findByTitle(String title) {
        return repository.findByTitle(title);
    }
}
