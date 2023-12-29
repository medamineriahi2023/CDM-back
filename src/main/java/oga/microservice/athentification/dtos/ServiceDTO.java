package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.History;
import oga.microservice.athentification.entities.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("ServiceDTO")
public class ServiceDTO extends AbstractDTO {
    private String serviceName;
    private String responsable;
    @JsonIgnore
    private Set<UserDto> userDtos;
    private boolean disabled;
    @JsonIgnore
    private List<History> history;
    public ServiceDTO(Service entity) {
        this(entity,null);
    }

    public ServiceDTO(Service entity, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("serviceName", filters)) {
            this.setServiceName(entity.getServiceName());
        }
        if (_isFetched("responsable", filters)) {
            this.setResponsable(entity.getResponsable());
        }
        if (_isFetched("disabled", filters)) {
            this.setDisabled(entity.isDisabled());
        }
    }
}