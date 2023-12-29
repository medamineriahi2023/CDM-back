package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.CategoryDTO;
import oga.microservice.athentification.entities.Category;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.CategoryService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryFactory extends AbstractEntityFactory<Category, CategoryDTO> {

    @Autowired
    CategoryService _categoryService;
    @Override
    public Category dtoToEntity(CategoryDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Category entity;

        if (dto.getId() != null) {
            entity = this._categoryService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Category();
        }
        if (canUpdate("catName", updateFields)) {
            entity.setCatName(dto.getCatName());
        }

        return entity;
    }

    @Override
    public CategoryDTO entityToDto(Category entity, String fieldsParams) {
        return new CategoryDTO(entity ,fieldsParams);
    }

    @Override
    public CategoryDTO entityToDto(Category entity) {
        return new CategoryDTO(entity);
    }
}