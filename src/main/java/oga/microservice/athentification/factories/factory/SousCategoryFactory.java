package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.SousCategoryDTO;
import oga.microservice.athentification.entities.SousCategory;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.SousCategoryService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SousCategoryFactory extends AbstractEntityFactory<SousCategory, SousCategoryDTO> {
    @Autowired
    SousCategoryService _sousCategoryService;
    @Override
    public SousCategory dtoToEntity(SousCategoryDTO dto, List<String> updateFields) throws EntityNotFoundException {
        SousCategory entity;

        if (dto.getId() != null) {
            entity = this._sousCategoryService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new SousCategory();
        }
        if (canUpdate("sousCatName", updateFields)) {
            entity.setSousCatName(dto.getSousCatName());
        }

        return entity;
    }

    @Override
    public SousCategoryDTO entityToDto(SousCategory entity, String fieldsParams) {
        return new SousCategoryDTO(entity ,fieldsParams);
    }

    @Override
    public SousCategoryDTO entityToDto(SousCategory entity) {
        return new SousCategoryDTO(entity);
    }
}