package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.History;
import oga.microservice.athentification.factories.factory.ServiceFactory;
import oga.microservice.athentification.factories.factory.UserFactory;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("HistoryDTO")
public class HistoryDTO extends AbstractDTO {
    private String action;
    private UserDto user;
    private ServiceDTO service;


    public HistoryDTO(History entity, UserFactory userFactory, ServiceFactory serviceFactory) {
        this(entity,userFactory,serviceFactory,null);
    }

    public HistoryDTO(History entity, UserFactory userFactory, ServiceFactory serviceFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);
        if (_isFetched("action", filters)) {
            this.setAction(entity.getAction());
        }
        if (_isFetched("user", filters)) {
            this.setUser(userFactory.entityToDto(entity.getUser(), _fieldsFromChildFields("user", fields)));
        }
        if (_isFetched("service", filters) && entity.getService() != null) {
            this.setService(serviceFactory.entityToDto(entity.getService(), _fieldsFromChildFields("service", fields)));
        }
    }
}