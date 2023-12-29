package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Canal;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("CanalDTO")
public class CanalDTO extends AbstractDTO {
    private String canalName;
    @JsonIgnore
    private List<CanalDTO> canals;


    public CanalDTO(Canal entity) {
        this(entity,null);
    }

    public CanalDTO(Canal entity, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);
        if (_isFetched("canalName", filters)) {
            this.setCanalName(entity.getCanalName());
        }
    }
}
