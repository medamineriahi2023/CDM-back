package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.SousCategoryDTO;
import oga.microservice.athentification.entities.SousCategory;
import oga.microservice.athentification.service.ISousCategoryService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class SousCategoryService extends AbstractCrudService<SousCategory, SousCategoryDTO> implements ISousCategoryService{
}
