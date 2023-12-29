package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.entities.SousCategory;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("SousCategoryDTO")
public class SousCategoryDTO extends AbstractDTO {
    private String sousCatName;
    @JsonIgnore
    private Set<Article> articles;

    public SousCategoryDTO(SousCategory entity) {
        this(entity,null);
    }

    public SousCategoryDTO(SousCategory entity, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);
        if (_isFetched("sousCatName", filters)) {
            this.setSousCatName(entity.getSousCatName());
        }
    }

}
