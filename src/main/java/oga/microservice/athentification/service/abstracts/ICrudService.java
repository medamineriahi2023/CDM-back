package oga.microservice.athentification.service.abstracts;

import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T extends AbstractEntity, TDTO extends AbstractDTO> {
    Optional<T> findById(Long id);

    List<T> findAll();


    List<T> findAll(Sort sort);

    List<T> findAll(Pageable pageable);


    T create(TDTO dto) throws EntityNotFoundException;

    T create(T entity);

    List<T> createAllDtos(List<TDTO> dtos) throws EntityNotFoundException;

    List<T> createAll(List<T> entities);

    T update(TDTO dto) throws EntityNotFoundException;

    List<T> updateAllDtos(List<TDTO> dtos) throws EntityNotFoundException;

    T update(T entity);

    List<T> updateAll(List<T> entities);

    T updatePartial(TDTO dto, String updateFields) throws EntityNotFoundException;

    List<T> updatePartialAllDtos(List<TDTO> dtos, String updateFields) throws EntityNotFoundException;

    void delete(Long id);

    /**
     * Filter function for controller getAll
     * @param entities entities findAll
     * @return entities filtered
     */
    List<T> filter(List<T> entities);
}