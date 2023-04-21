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

    Promo findByTitle(String title) {
        Optional<Promo> optionalE = repository.findByTitle(title);
        if (optionalE.isPresent()) {
            return optionalE.get();
        } else {
            throw new NotFoundEntityException(0l);
        }
    }
}
