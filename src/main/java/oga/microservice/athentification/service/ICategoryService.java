package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.CategoryDTO;
import oga.microservice.athentification.entities.Category;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface ICategoryService extends ICrudService<Category, CategoryDTO> {
}
