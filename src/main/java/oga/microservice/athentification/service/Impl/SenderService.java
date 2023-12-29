package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.SenderDTO;
import oga.microservice.athentification.entities.Sender;
import oga.microservice.athentification.service.ISenderService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SenderService extends AbstractCrudService<Sender, SenderDTO> implements ISenderService {
}
