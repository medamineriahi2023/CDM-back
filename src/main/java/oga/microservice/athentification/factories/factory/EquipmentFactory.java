package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.EquipmentDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.ArticleService;
import oga.microservice.athentification.service.Impl.CategoryService;
import oga.microservice.athentification.service.Impl.EquipmentService;
import oga.microservice.athentification.service.Impl.SousCategoryService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EquipmentFactory extends AbstractEntityFactory<Equipment, EquipmentDTO> {
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

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ArticleFactory articleFactory;

    @Override
    public Equipment dtoToEntity(EquipmentDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Equipment entity;

        if (dto.getId() != null) {
            entity = this.equipmentService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Equipment();
        }

        if (canUpdate("sousCategory", updateFields) && dto.getSousCategory() != null && dto.getSousCategory().getId() != null) {
            entity.setSousCategory(_sousCategoryService.findById(dto.getSousCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("category", updateFields) && dto.getCategory() != null && dto.getCategory().getId() != null) {
            entity.setCategory(_categoryService.findById(dto.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("article", updateFields) && dto.getArticle() != null && dto.getArticle().getId() != null) {
            entity.setArticle(_articleService.findById(dto.getArticle().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("qte", updateFields)) {
            entity.setQte(dto.getQte());
        }

        if (canUpdate("etat", updateFields)) {
            entity.setEtat(dto.getEtat());
        }

        return entity;
    }

    @Override
    public EquipmentDTO entityToDto(Equipment entity, String fieldsParams) {
        return new EquipmentDTO(entity, _categoryFactory, _sousCategoryFactory,articleFactory, fieldsParams);
    }

    @Override
    public EquipmentDTO entityToDto(Equipment entity) {
        return new EquipmentDTO(entity, _categoryFactory, _sousCategoryFactory,articleFactory);
    }


}
