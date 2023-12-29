package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.CanalDTO;
import oga.microservice.athentification.entities.Canal;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.CanalService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CanalFactory extends AbstractEntityFactory<Canal, CanalDTO> {

    @Autowired
    CanalService canalService;
    @Override
    public Canal dtoToEntity(CanalDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Canal entity;

        if (dto.getId() != null) {
            entity = this.canalService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Canal();
        }
        if (canUpdate("canalName", updateFields)) {
            entity.setCanalName(dto.getCanalName());
        }

        return entity;
    }

    @Override
    public CanalDTO entityToDto(Canal entity, String fieldsParams) {
        return new CanalDTO(entity ,fieldsParams);
    }

    @Override
    public CanalDTO entityToDto(Canal entity) {
        return new CanalDTO(entity);
    }
}