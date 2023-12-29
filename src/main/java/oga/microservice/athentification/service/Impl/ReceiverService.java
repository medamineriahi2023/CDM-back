package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.ReceiverDTO;
import oga.microservice.athentification.entities.Receiver;
import oga.microservice.athentification.service.IReceiverService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReceiverService extends AbstractCrudService<Receiver, ReceiverDTO> implements IReceiverService {
}
