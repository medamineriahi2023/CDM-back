package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.CanalDTO;
import oga.microservice.athentification.entities.Canal;
import oga.microservice.athentification.service.ICanalService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CanalService extends AbstractCrudService<Canal, CanalDTO> implements ICanalService {
}
