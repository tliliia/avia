package com.tronina.avia.service;

import com.tronina.avia.entity.BaseEntity;
import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<E extends BaseEntity, R extends BaseRepository<E>>
        implements CrudService<E> {

    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    public E findById(Long id) {
        Optional<E> optionalE = repository.findById(id);
        if (optionalE.isPresent()) {
            return optionalE.get();
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    @Transactional
    public E create(E entity) {
        return repository.save(entity);
    }

    @Transactional
    public E update(Long id, E entity) {
        E originalEntity = findById(id);
//        originalEntity.fillFromModel(entity);
        return repository.save(originalEntity);
    }

    @Transactional
    public void delete(E entity) {
        repository.delete(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        E entity = findById(id);
        repository.delete(entity);
    }
}