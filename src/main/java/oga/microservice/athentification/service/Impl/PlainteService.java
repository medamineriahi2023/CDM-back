package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.PlainteDTO;
import oga.microservice.athentification.entities.Plainte;
import oga.microservice.athentification.service.IPlainteService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlainteService extends AbstractCrudService<Plainte, PlainteDTO> implements IPlainteService {
}
