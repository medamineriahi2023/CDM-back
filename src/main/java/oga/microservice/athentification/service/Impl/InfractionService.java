package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.InfractionDTO;
import oga.microservice.athentification.entities.Infraction;
import oga.microservice.athentification.service.IInfractionService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InfractionService extends AbstractCrudService<Infraction, InfractionDTO> implements IInfractionService {
}
