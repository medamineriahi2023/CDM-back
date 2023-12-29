package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.factories.factory.CategoryFactory;
import oga.microservice.athentification.factories.factory.SousCategoryFactory;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("ArticleDTO")
public class ArticleDTO extends AbstractDTO {
    private SousCategoryDTO sousCategory;
    private CategoryDTO category;
    private int code;
    private int stockMin;
    private int stockCri;
    private String name;
    private boolean archived;
    private int qteTotal;

    public ArticleDTO(Article entity, CategoryFactory categoryFactory , SousCategoryFactory sousCategoryFactory) {
        this(entity,categoryFactory,sousCategoryFactory, null);
    }
    public ArticleDTO(Article entity, CategoryFactory categoryFactory , SousCategoryFactory sousCategoryFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("code", filters)) {
            this.setCode(entity.getCode());
        }
        if (_isFetched("stockMin", filters)) {
            this.setStockMin(entity.getStockMin());
        }
        if (_isFetched("stockCri", filters)) {
            this.setStockCri(entity.getStockCri());
        }
        if (_isFetched("name", filters)) {
            this.setName(entity.getName());
        }
        if (_isFetched("archived", filters)) {
            this.setArchived(entity.isArchived());
        }

        if (_isFetched("qteTotal", filters)) {
            this.setQteTotal(entity.getQteTotal());
        }

        if (_isFetched("category", filters)) {
            this.setCategory(categoryFactory.entityToDto(entity.getCategory(), _fieldsFromChildFields("category", fields)));
        }
        if (_isFetched("sousCategory", filters)) {
            this.setSousCategory(sousCategoryFactory.entityToDto(entity.getSousCategory(), _fieldsFromChildFields("sousCategory", fields)));
        }
    }
}
