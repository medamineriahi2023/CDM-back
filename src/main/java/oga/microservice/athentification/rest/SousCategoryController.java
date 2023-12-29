package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.SousCategoryDTO;
import oga.microservice.athentification.entities.SousCategory;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "sous-category")
public class SousCategoryController extends AbstractCrudController<SousCategory, SousCategoryDTO> {
    public SousCategoryController() {
        super(SousCategory.class);
    }
}
