package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.entities.Category;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("CategoryDTO")
public class CategoryDTO extends AbstractDTO {
    private String catName;
    @JsonIgnore
    private Set<Article> articles;


    public CategoryDTO(Category entity) {
        this(entity,null);
    }

    public CategoryDTO(Category entity, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);
        if (_isFetched("catName", filters)) {
            this.setCatName(entity.getCatName());
        }
    }
}
