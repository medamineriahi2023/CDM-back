package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.CategoryDTO;
import oga.microservice.athentification.entities.Category;
import oga.microservice.athentification.service.ICategoryService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudService<Category, CategoryDTO> implements ICategoryService {
}
