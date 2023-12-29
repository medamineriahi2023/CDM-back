package oga.microservice.athentification.service.abstracts;

import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.repository.abstracts.ICrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Transactional
public abstract class AbstractCrudService<T extends AbstractEntity, TDTO extends AbstractDTO>
        implements ICrudService<T, TDTO> {

    @Autowired
    protected ICrudRepository<T, Long> _repository;

    @Autowired
    protected AbstractEntityFactory<T, TDTO> _factory;

    @Override
    public Optional<T> findById(Long id) {
        return _repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return _repository.findAll();
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return _repository.findAll(pageable).getContent();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return _repository.findAll(sort);
    }

    @Override
    public List<T> filter(List<T> entities) {
        // Default filter does not filter
        return entities;
    }

    @Override
    public T create(TDTO dto) throws EntityNotFoundException {
        T entity = _factory.dtoToEntity(dto);

        return create(entity);
    }

    @Override
    public T create(T entity) {
        return _repository.save(entity);
    }

    public List<T> createAllDtos(List<TDTO> dtos) throws EntityNotFoundException {
        List<T> entities = new ArrayList<>();

        for (TDTO dto : dtos) {
            T entity = _factory.dtoToEntity(dto);

            entities.add(entity);
        }

        return createAll(entities);
    }

    @Override
    public List<T> createAll(List<T> entities) {
        return _repository.saveAll(entities);
    }

    @Override
    public T update(TDTO dto) throws EntityNotFoundException {
        T entity = _factory.dtoToEntity(dto, null);
        return update(entity);
    }

    public List<T> updateAllDtos(List<TDTO> dtos) throws EntityNotFoundException {
        List<T> entities = new ArrayList<>();

        for (TDTO dto : dtos) {
            T entity = _factory.dtoToEntity(dto, null);

            entities.add(entity);
        }

        return updateAll(entities);
    }

    @Override
    public T update(T entity) {
        return _repository.saveAndFlush(entity);
    }

    @Override
    public List<T> updateAll(List<T> entities) {
        return _repository.saveAll(entities);
    }

    @Override
    public T updatePartial(TDTO dto, String updateFields) throws EntityNotFoundException {
        List<String> updateFieldsList = updateFields != null ? Arrays.asList(updateFields.split(",")) : null;
        T entity = _factory.dtoToEntity(dto, updateFieldsList);
        return update(entity);
    }

    @Override
    public List<T> updatePartialAllDtos(List<TDTO> dtos, String updateFields) throws EntityNotFoundException {
        List<String> updateFieldsList = updateFields != null ? Arrays.asList(updateFields.split(",")) : null;
        List<T> entities = new ArrayList<>();
        for (TDTO dto : dtos) {
            T entity = _factory.dtoToEntity(dto, updateFieldsList);
            entities.add(entity);
        }
        return updateAll(entities);
    }

    @Override
    public void delete(Long id) {
        Optional<T> entityOptional = _repository.findById(id);

        if (entityOptional.isPresent()) {
            T entity = entityOptional.get();

            _repository.delete(entity);
            _repository.flush();
        }
    }
}