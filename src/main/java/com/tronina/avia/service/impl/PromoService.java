package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.exception.PromoException;
import com.tronina.avia.model.entity.Promo;
import com.tronina.avia.repository.PromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PromoService {
    private final PromoRepository repository;

    public Promo getByTitle(String title) {
        Promo promo = repository.findByTitle(title).orElseThrow(() -> new NotFoundEntityException(1l));
        if (promo.getExpireDate().isBefore(LocalDateTime.now()) ||
            promo.getMaxUsage() < promo.getActualUsage()) {
            throw new PromoException(title);
        }
        return promo;
    }

    @Transactional
    public Promo changeActualUsage(String title) {
        Promo promo = repository.findByTitle(title).orElseThrow(() -> new NotFoundEntityException(1l));
        promo.setActualUsage(Integer.valueOf(promo.getActualUsage()) + 1);
        return repository.save(promo);
    }
}
