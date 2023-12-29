package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.CategoryDTO;
import oga.microservice.athentification.entities.Category;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "category")
public class CategoryController extends AbstractCrudController<Category, CategoryDTO> {
    public CategoryController() {
        super(Category.class);
    }
}
