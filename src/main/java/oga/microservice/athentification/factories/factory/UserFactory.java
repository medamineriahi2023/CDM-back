package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.UserDto;
import oga.microservice.athentification.entities.User;
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
public class UserFactory extends AbstractEntityFactory<User, UserDto> {
    @Autowired
    UserService userService;

    @Autowired
    ServiceFactory serviceFactory;

    @Autowired
    ServiceService serviceService;
@Override
public User dtoToEntity(UserDto dto, List<String> updateFields) throws EntityNotFoundException {
    User entity;

    if (dto.getId() != null) {
        entity = this.userService.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
    } else {
        entity = new User();
    }

    if (canUpdate("service", updateFields) && dto.getService() != null && dto.getService().getId() != null) {
        entity.setService(serviceService.findById(dto.getService().getId())
                .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
    }
    if (canUpdate("userId", updateFields)) {
        entity.setUserId(dto.getUserId());
    }
    if (canUpdate("role", updateFields)) {
        entity.setRole(dto.getRole());
    }

    if (canUpdate("lastname", updateFields)) {
        entity.setLastname(dto.getLastname());
    }
    if (canUpdate("firstname", updateFields)) {
        entity.setFirstname(dto.getFirstname());
    }
    if (canUpdate("email", updateFields)) {
        entity.setEmail(dto.getEmail());
    }
    if (canUpdate("password", updateFields)) {
        entity.setPassword(dto.getPassword());
    }
    if (canUpdate("disabled", updateFields)) {
        entity.setDisabled(dto.isDisabled());
    }



    return entity;
}

    @Override
    public UserDto entityToDto(User entity) {
        return new UserDto(entity,serviceFactory);
    }

    @Override
    public UserDto entityToDto(User entity, String fieldsParams) {
        return new UserDto(entity, serviceFactory,fieldsParams);
    }
}
