package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.HistoryDTO;
import oga.microservice.athentification.entities.History;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.HistoryService;
import oga.microservice.athentification.service.Impl.ServiceService;
import oga.microservice.athentification.service.Impl.UserService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryFactory extends AbstractEntityFactory<History, HistoryDTO> {
    @Autowired
    HistoryService historyService;

    @Autowired
    UserService userService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceFactory serviceFactory;

    @Autowired
    UserFactory userFactory;
    @Override
    public History dtoToEntity(HistoryDTO dto, List<String> updateFields) throws EntityNotFoundException {
        History entity;

        if (dto.getId() != null) {
            entity = this.historyService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new History();
        }

        if (canUpdate("user", updateFields) && dto.getUser() != null && dto.getUser().getId() != null) {
            entity.setUser(userService.findById(dto.getUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }
        if (canUpdate("service", updateFields) && dto.getService() != null && dto.getService().getId() != null) {
            entity.setService(serviceService.findById(dto.getService().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }
        if (canUpdate("action", updateFields)) {
            entity.setAction(dto.getAction());
        }
        return entity;
    }

    @Override
    public HistoryDTO entityToDto(History entity, String fieldsParams) {
        return new HistoryDTO(entity ,userFactory,serviceFactory,fieldsParams);
    }

    @Override
    public HistoryDTO entityToDto(History entity) {
        return new HistoryDTO(entity, userFactory,serviceFactory);
    }
}
