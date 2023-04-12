package com.tronina.avia.service;

import com.tronina.avia.entity.BaseEntity;

import java.util.List;

public interface CrudService<E extends BaseEntity> {
    /**
     * Получить элемент по id
     * @param id идентификатор элемента
     * @return элемент
     */
    E findById(Long id);

    /**
     * Получить список всех эдементов в репозитории
     * @return список всех эдементов в репозитории
     */
    List<E> findAll();

    /**
     * Сохранить элемент
     * @param entity элемент
     * @return созданный элемент
     */
    E create(E entity);

    /**
     * Изменить элемент
     * @param entity элемент
     * @return измененный элемент
     */
    E update(Long id, E entity);

    /**
     * Удалить элемент
     * @param entity элемент
     */
    void delete(E entity);

    /**
     * Удалиьт элент по соответсвующему идентификатору
     * @param id идентификатор элемента
     */
    void deleteById(Long id);
}
