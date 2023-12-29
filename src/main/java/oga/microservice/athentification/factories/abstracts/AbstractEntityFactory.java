
package oga.microservice.athentification.factories.abstracts;

import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;
import oga.microservice.athentification.exceptions.EntityNotFoundException;

import java.util.List;

public abstract class AbstractEntityFactory<T extends AbstractEntity, TDTO extends AbstractDTO> {
    public abstract T dtoToEntity(TDTO dto, List<String> updateFields) throws EntityNotFoundException;

    public T dtoToEntity(TDTO dto) throws EntityNotFoundException {
        return dtoToEntity(dto, null);
    }

    public TDTO entityToDto(T entity) {
        return entityToDto(entity, null);
    }

    public abstract TDTO entityToDto(T entity, String fieldsParams);


    protected boolean canUpdate(String field, List<String> updateField) {
        return updateField == null || updateField.contains(field);
    }
}
