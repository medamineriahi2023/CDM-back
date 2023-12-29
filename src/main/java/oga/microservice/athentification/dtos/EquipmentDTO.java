package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.Etat;
import oga.microservice.athentification.factories.factory.ArticleFactory;
import oga.microservice.athentification.factories.factory.CategoryFactory;
import oga.microservice.athentification.factories.factory.SousCategoryFactory;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("Equipment")
public class EquipmentDTO extends AbstractDTO {
    private CategoryDTO category;
    private SousCategoryDTO sousCategory;
    private ArticleDTO article;
    private int qte;
    private Etat etat;

    public EquipmentDTO(Equipment entity, CategoryFactory categoryFactory , SousCategoryFactory sousCategoryFactory, ArticleFactory articleFactory) {
        this(entity,categoryFactory,sousCategoryFactory,articleFactory, null);
    }
    public EquipmentDTO(Equipment entity, CategoryFactory categoryFactory , SousCategoryFactory sousCategoryFactory, ArticleFactory articleFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("qte", filters)) {
            this.setQte(entity.getQte());
        }
        if (_isFetched("category", filters)) {
            this.setCategory(categoryFactory.entityToDto(entity.getCategory(), _fieldsFromChildFields("category", fields)));
        }
        if (_isFetched("sousCategory", filters)) {
            this.setSousCategory(sousCategoryFactory.entityToDto(entity.getSousCategory(), _fieldsFromChildFields("sousCategory", fields)));
        }
        if (_isFetched("article", filters)) {
            this.setArticle(articleFactory.entityToDto(entity.getArticle(), _fieldsFromChildFields("article", fields)));
        }
        if (_isFetched("etat", filters)) {
            this.setEtat(entity.getEtat());
        }
    }
}
