package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.ArticleDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.ArticleService;
import oga.microservice.athentification.service.Impl.CategoryService;
import oga.microservice.athentification.service.Impl.SousCategoryService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ArticleFactory extends AbstractEntityFactory<Article, ArticleDTO> {
    @Autowired
    private ArticleService _articleService;

    @Autowired
    private SousCategoryService _sousCategoryService;

    @Autowired
    private CategoryService _categoryService;

    @Autowired
    private SousCategoryFactory _sousCategoryFactory;

    @Autowired
    private CategoryFactory _categoryFactory;

    @Override
    public Article dtoToEntity(ArticleDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Article entity;

        if (dto.getId() != null) {
            entity = this._articleService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Article();
        }

        if (canUpdate("sousCategory", updateFields) && dto.getSousCategory() != null && dto.getSousCategory().getId() != null) {
            entity.setSousCategory(_sousCategoryService.findById(dto.getSousCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("category", updateFields) && dto.getCategory() != null && dto.getCategory().getId() != null) {
            entity.setCategory(_categoryService.findById(dto.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("code", updateFields)) {
            entity.setCode(dto.getCode());
        }
        if (canUpdate("stockMin", updateFields)) {
            entity.setStockMin(dto.getStockMin());
        }

        if (canUpdate("stockCri", updateFields)) {
            entity.setStockCri(dto.getStockCri());
        }

        if (canUpdate("name", updateFields)) {
            entity.setName(dto.getName());
        }
        if (canUpdate("archived", updateFields)) {
            entity.setArchived(dto.isArchived());
        }
        if (canUpdate("qteTotal", updateFields)) {
            entity.setQteTotal(dto.getQteTotal());
        }

        return entity;
    }

    @Override
    public ArticleDTO entityToDto(Article entity, String fieldsParams) {
        return new ArticleDTO(entity, _categoryFactory, _sousCategoryFactory, fieldsParams);
    }

    @Override
    public ArticleDTO entityToDto(Article entity) {
        return new ArticleDTO(entity, _categoryFactory, _sousCategoryFactory);
    }


}
