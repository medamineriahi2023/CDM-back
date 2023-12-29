package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.ServiceDTO;
import oga.microservice.athentification.entities.Service;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.ServiceService;
import oga.microservice.athentification.service.Impl.UserService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ServiceFactory extends AbstractEntityFactory<Service, ServiceDTO> {
    @Autowired
    ServiceService _serviceService;

    @Autowired
    UserService userService;

    @Autowired
    UserFactory userFactory;
    @Override
    public Service dtoToEntity(ServiceDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Service entity;

        if (dto.getId() != null) {
            entity = this._serviceService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Service();
        }
        if (canUpdate("serviceName", updateFields)) {
            entity.setServiceName(dto.getServiceName());
        }
        if (canUpdate("responsable", updateFields)) {
            entity.setResponsable(dto.getResponsable());
        }
        if (canUpdate("disabled", updateFields)) {
            entity.setDisabled(dto.isDisabled());
        }

        return entity;
    }

    @Override
    public ServiceDTO entityToDto(Service entity, String fieldsParams) {
        return new ServiceDTO(entity ,fieldsParams);
    }

    @Override
    public ServiceDTO entityToDto(Service entity) {
        return new ServiceDTO(entity);
    }
}
