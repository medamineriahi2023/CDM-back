package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.factories.factory.ServiceFactory;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("UserDTO")
public class UserDto extends AbstractDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String userId;
    private ServiceDTO service;
    private String role;
    private String password;
    private boolean disabled;

    public UserDto(User entity, ServiceFactory serviceFactory) {
        this(entity,serviceFactory,null);
    }

    public UserDto(User entity, ServiceFactory serviceFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("userId", filters)) {
            this.setUserId(entity.getUserId());
        }
        if (_isFetched("email", filters)) {
            this.setEmail(entity.getEmail());
        }
        if (_isFetched("role", filters)) {
            this.setRole(entity.getRole());
        }
        if (_isFetched("lastname", filters)) {
            this.setLastname(entity.getLastname());
        }
        if (_isFetched("firstname", filters)) {
            this.setFirstname(entity.getFirstname());
        }
        if (_isFetched("password", filters)) {
            this.setPassword(entity.getPassword());
        }
        if (_isFetched("disabled", filters)) {
            this.setDisabled(entity.isDisabled());
        }
        if (_isFetched("service", filters)) {
            this.setService(serviceFactory.entityToDto(entity.getService(), _fieldsFromChildFields("service", fields)));
        }
    }


}
